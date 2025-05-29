package com.tienda.ejemplo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoCantidad {
    private String codigoProducto;
    private Integer cantidad;
}
