package org.angelesyvalientes.api.DetallesValienteDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;



public record DetallesValienteDTO(
        @NotNull(message = "El ID de persona es requerido")
        Long idPersona,

        @NotNull(message = "La fecha de nacimiento es requerida")
        @Past(message = "La fecha debe ser en el pasado")
        LocalDate fechaNacimiento,

        @NotNull(message = "El grupo poblacional es requerido")
        Integer grupoEtnicoId,

        @NotNull(message = "La clasificación es requerida")
        Long clasificacionValienteId,

        @NotNull(message = "La vivienda es requerida")
        Long viviendaId,

        // Campos opcionales
        @Size(max = 10, message = "La talla no debe exceder 10 caracteres")
        String tallaCalzado,

        @Size(max = 10, message = "La talla no debe exceder 10 caracteres")
        String tallaCamisa,

        @Size(max = 10, message = "La talla no debe exceder 10 caracteres")
        String tallaPantalon,

        @Size(max = 100, message = "El nombre no debe exceder 100 caracteres")
        String nombreResponsable,

        @Size(max = 45, message = "El parentesco no debe exceder 45 caracteres")
        String parentescoResponsable,

        @Size(max = 10, message = "El teléfono no debe exceder 10 caracteres")
        String telefonoResponsable,

        @Size(max = 255, message = "La URL no debe exceder 255 caracteres")
        String urlGaleria,

         //Boolean poblacionConflictoArmado,
        //Boolean poblacionMigrante,
       //Boolean poblacionJoven,
      //Boolean poblacionMujer,
        Boolean poblacionLgtbiq,
        Boolean activo
) {}