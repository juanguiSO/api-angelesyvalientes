package org.angelesyvalientes.api.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "usuario")
@Getter
@Setter
public class Usuario {
    @Id
    @Column(name = "cd_usuario", nullable = false)
    private String cdUsuario;

    @OneToOne
    @JoinColumn(name = "nm_id_persona") // Relación con la clase Persona
    private Persona persona;

    @Column(name = "tx_contrasena", nullable = false, length = 100)
    private String txContrasena;

    @Column(name = "fe_creacion")
    private LocalDate feCreacion;

    @Column(name = "is_deleted")
    private boolean isDeleted = false; // Por defecto, no está eliminado

    public String getCdUsuario() {
        return cdUsuario;
    }

    public void setCdUsuario(String cdUsuario) {
        this.cdUsuario = cdUsuario;
    }

    public String getTxContrasena() {
        return txContrasena;
    }

    public void setTxContrasena(String txContrasena) {
        this.txContrasena = txContrasena;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public void setFeCreacion(LocalDate feCreacion) {
        this.feCreacion = feCreacion;
    }

    public Persona getPersona() {
        return persona;
    }

    public LocalDate getFeCreacion() {
        return feCreacion;
    }
}