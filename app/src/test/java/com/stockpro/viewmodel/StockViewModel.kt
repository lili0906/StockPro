package com.stockpro.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.stockpro.model.Producto

class StockViewModel : ViewModel() {

    val productos = mutableStateListOf(
        Producto(1, "Tornillos Hexagonales", "Tornillos de acero inoxidable M8x30mm, ideales para estructuras metálicas.", 0.25, 320),
        Producto(2, "Pintura Epóxica Gris", "Pintura industrial de alta resistencia para pisos y paredes de bodega.", 45.99, 3),
        Producto(3, "Cinta de Embalaje", "Cinta adhesiva transparente reforzada, rollo de 50m x 48mm.", 2.75, 4),
        Producto(4, "Pallet de Madera", "Pallet estándar 120x80cm, capacidad de carga hasta 1000kg.", 18.50, 42),
        Producto(5, "Guantes de Nitrilo", "Guantes desechables talla M, caja de 100 unidades.", 12.00, 0),
        Producto(6, "Etiquetas de Código de Barras", "Etiquetas adhesivas blancas 50x30mm, compatibles con impresoras térmicas.", 5.40, 2)
    )

    fun obtenerProducto(id: Int): Producto? {
        return productos.find { it.id == id }
    }

    fun actualizarStock(id: Int, nuevaCantidad: Int) {
        val index = productos.indexOfFirst { it.id == id }
        if (index != -1) {
            productos[index] = productos[index].copy(stockActual = nuevaCantidad)
        }
    }

    fun calcularValorTotalInventario(): Double {
        return productos.sumOf { it.precio * it.stockActual }
    }

    fun obtenerProductosEnRiesgo(): List<Producto> {
        return productos.filter { it.stockActual < 5 }
    }

    fun contarProductosSinStock(): Int {
        return productos.count { it.stockActual == 0 }
    }
}