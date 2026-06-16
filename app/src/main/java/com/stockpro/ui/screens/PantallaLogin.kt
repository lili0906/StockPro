package com.stockpro.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun PantallaLogin(navController: NavController) {
    var nombreOperario by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    val botonHabilitado = nombreOperario.trim().length >= 3

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF1A237E), Color(0xFF283593))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(24.dp),
                color = Color.White.copy(alpha = 0.15f),
                modifier = Modifier.size(90.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(52.dp)
                    )
                }
            }

            Text(
                text = "Bienvenido a StockPro",
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Text(
                text = "Sistema de Gestión de Inventario",
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = nombreOperario,
                onValueChange = { nombreOperario = it },
                label = { Text("Nombre del Operario", color = Color.White.copy(alpha = 0.8f)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = Color(0xFF90CAF9),
                    unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                    cursorColor = Color(0xFF90CAF9)
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        if (botonHabilitado) {
                            navController.navigate("catalogo/${nombreOperario.trim()}")
                        }
                    }
                )
            )

            if (nombreOperario.isNotEmpty() && !botonHabilitado) {
                Text(
                    text = "El nombre debe tener al menos 3 caracteres",
                    color = Color(0xFFEF9A9A),
                    fontSize = 12.sp
                )
            }

            Button(
                onClick = {
                    keyboardController?.hide()
                    navController.navigate("catalogo/${nombreOperario.trim()}")
                },
                enabled = botonHabilitado,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF42A5F5),
                    disabledContainerColor = Color.White.copy(alpha = 0.2f)
                )
            ) {
                Text(
                    text = "Ingresar al Sistema",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (botonHabilitado) Color.White else Color.White.copy(alpha = 0.4f)
                )
            }
        }
    }
}