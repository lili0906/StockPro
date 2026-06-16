package com.stockpro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.stockpro.ui.screens.PantallaCatalogo
import com.stockpro.ui.screens.PantallaEdicion
import com.stockpro.ui.screens.PantallaLogin
import com.stockpro.ui.screens.PantallaReporte
import com.stockpro.viewmodel.StockViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    val stockViewModel: StockViewModel = viewModel()

                    NavHost(navController = navController, startDestination = "login") {
                        composable("login") {
                            PantallaLogin(navController = navController)
                        }
                        composable(
                            route = "catalogo/{nombreOperario}",
                            arguments = listOf(
                                navArgument("nombreOperario") { type = NavType.StringType }
                            )
                        ) { backStackEntry ->
                            val nombre = backStackEntry.arguments?.getString("nombreOperario") ?: ""
                            PantallaCatalogo(
                                navController = navController,
                                viewModel = stockViewModel,
                                nombreOperario = nombre
                            )
                        }
                        composable(
                            route = "edicion/{productoId}",
                            arguments = listOf(
                                navArgument("productoId") { type = NavType.IntType }
                            )
                        ) { backStackEntry ->
                            val id = backStackEntry.arguments?.getInt("productoId") ?: -1
                            PantallaEdicion(
                                navController = navController,
                                viewModel = stockViewModel,
                                productoId = id
                            )
                        }
                        composable("reporte") {
                            PantallaReporte(
                                navController = navController,
                                viewModel = stockViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}