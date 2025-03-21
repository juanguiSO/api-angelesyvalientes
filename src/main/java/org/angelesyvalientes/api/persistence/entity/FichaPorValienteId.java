package org.angelesyvalientes.api.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FichaPorValienteId implements Serializable {
    private int idFicha;
    private int idValiente;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FichaPorValienteId that)) return false;
        return idFicha == that.idFicha && idValiente == that.idValiente;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFicha, idValiente);
    }
}
