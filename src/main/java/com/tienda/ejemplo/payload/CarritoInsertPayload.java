package com.tienda.ejemplo.payload;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarritoInsertPayload {
    private String rut;
    private List<ProductoCantidad> productos;
}
