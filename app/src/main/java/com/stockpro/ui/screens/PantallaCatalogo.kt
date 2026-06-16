package com.stockpro.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.stockpro.model.Producto
import com.stockpro.viewmodel.StockViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaCatalogo(
    navController: NavController,
    viewModel: StockViewModel,
    nombreOperario: String
) {
    var mostrarSoloCriticos by remember { mutableStateOf(false) }

    val listaProductos: List<Producto> = if (mostrarSoloCriticos) {
        viewModel.obtenerProductosEnRiesgo()
    } else {
        viewModel.productos
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "StockPro",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.White
                        )
                        Text(
                            text = "Operario: $nombreOperario",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1A237E)
                )
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { navController.navigate("reporte") },
                containerColor = Color(0xFF1A237E),
                contentColor = Color.White,
                icon = {
                    Icon(Icons.Default.Assessment, contentDescription = "Reporte")
                },
                text = { Text("Ver Reporte") }
            )
        },
        containerColor = Color(0xFFF5F5F5)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            // Botones de filtro
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterButton(
                    text = "Ver Todo",
                    isSelected = !mostrarSoloCriticos,
                    onClick = { mostrarSoloCriticos = false },
                    modifier = Modifier.weight(1f)
                )
                FilterButton(
                    text = "⚠ Stock Crítico",
                    isSelected = mostrarSoloCriticos,
                    onClick = { mostrarSoloCriticos = true },
                    modifier = Modifier.weight(1f),
                    selectedColor = Color(0xFFB71C1C)
                )
            }

            Text(
                text = "${listaProductos.size} producto(s)",
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                items(listaProductos, key = { it.id }) { producto ->
                    ProductoCard(
                        producto = producto,
                        onClick = { navController.navigate("edicion/${producto.id}") }
                    )
                }
            }
        }
    }
}

@Composable
fun FilterButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    selectedColor: Color = Color(0xFF1A237E)
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(40.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) selectedColor else Color.White,
            contentColor = if (isSelected) Color.White else Color(0xFF424242)
        )
    ) {
        Text(text = text, fontSize = 13.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun ProductoCard(producto: Producto, onClick: () -> Unit) {
    val stockCritico = producto.stockActual < 5

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (stockCritico) {
                Box(
                    modifier = Modifier
                        .width(4.dp)
                        .height(60.dp)
                        .background(
                            color = if (producto.stockActual == 0) Color(0xFFB71C1C) else Color(0xFFE65100),
                            shape = RoundedCornerShape(4.dp)
                        )
                )
                Spacer(modifier = Modifier.width(12.dp))
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = producto.nombre,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color(0xFF212121)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Precio unitario: $${String.format("%.2f", producto.precio)}",
                    fontSize = 13.sp,
                    color = Color(0xFF616161)
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                if (stockCritico) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        tint = Color(0xFFE65100),
                        modifier = Modifier.size(14.dp)
                    )
                }
                // Stock en rojo si es crítico (requerimiento del examen)
                Text(
                    text = "${producto.stockActual}",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (stockCritico) Color(0xFFB71C1C) else Color(0xFF1A237E)
                )
                Text(
                    text = "unidades",
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            }
        }
    }
}