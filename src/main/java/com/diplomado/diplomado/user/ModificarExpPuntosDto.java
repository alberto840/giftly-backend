package com.diplomado.diplomado.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO para modificar exp y puntos de un usuario.
 * Usar valores positivos para sumar y negativos para restar.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModificarExpPuntosDto {
    private BigDecimal deltaExp;
    private Integer deltaPuntos;
}
