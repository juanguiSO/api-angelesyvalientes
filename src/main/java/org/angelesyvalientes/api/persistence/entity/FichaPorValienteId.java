package org.angelesyvalientes.api.persistence.entity;

import java.io.Serializable;
import java.util.Objects;

public class FichaPorValienteId implements Serializable {
    private int idFicha;
    private int idValiente;

    public FichaPorValienteId() {}

    public FichaPorValienteId(int idFicha, int idValiente) {
        this.idFicha = idFicha;
        this.idValiente = idValiente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FichaPorValienteId that = (FichaPorValienteId) o;
        return idFicha == that.idFicha && idValiente == that.idValiente;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFicha, idValiente);
    }
}