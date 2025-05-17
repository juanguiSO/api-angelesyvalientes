package org.angelesyvalientes.api.dto;

import org.angelesyvalientes.api.persistence.entity.Ficha;

public record FichaDTO(
        int id,
        String nombre,
        String urlRecurso,
        int codigo
) {
    public static FichaDTO fromEntity(Ficha ficha) {
        return new FichaDTO(
                ficha.getId(),
                ficha.getNombre(),
                ficha.getUrlRecurso(),
                ficha.getCodigo()
        );
    }
}