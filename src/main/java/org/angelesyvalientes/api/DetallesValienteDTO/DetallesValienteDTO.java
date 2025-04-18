package org.angelesyvalientes.api.DetallesValienteDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;



public record DetallesValienteDTO(
        @NotNull LocalDate fechaNacimiento,
        @NotNull Long grupoPoblacionalId,
        @NotNull Long clasificacionValienteId,
        @NotNull Long viviendaId,

        // Campos opcionales
        String tallaCalzado,
        String tallaCamisa,
        String tallaPantalon,
        String nombreResponsable,
        String parentescoResponsable,
        String telefonoResponsable,
        String urlGaleria,
        Boolean poblacionConflictoArmado,
        Boolean poblacionMigrante,
        Boolean poblacionJoven,
        Boolean poblacionMujer,
        Boolean poblacionLgtbiq,
        Boolean activo
) {}