package org.angelesyvalientes.api.DetallesValienteDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record ValienteRequest(
        @NotNull Long idPersona,
        @Valid DetallesValienteDTO detallesValiente

) {}