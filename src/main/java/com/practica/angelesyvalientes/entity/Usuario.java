package com.practica.angelesyvalientes.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Entity
@Table(name="usuario")

public class Usuario {
    @Id
    @Column(name = "cd_usuario")
    private String cdUsuario;

    @OneToOne
    @JoinColumn(name = "nm_id_persona", nullable = false) // Relación con la clase Persona
    private Persona persona;

    @Getter
    @Setter
    @Column(name = "tx_contrasena", nullable = false, length = 100)
    private String txContrasena;

    @Column(name = "fe_creacion", nullable = false)
    private LocalDate feCreacion;

    @Column(name = "is_deleted")
    private boolean isDeleted = false; // Por defecto, no está eliminado

    public String getCdUsuario() {
        return cdUsuario;
    }

    public void setCdUsuario(String cdUsuario) {
        this.cdUsuario = cdUsuario;
    }

    public Persona getPersona() {
        return this.persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
    public String getTxContrasena() {
        return this.txContrasena;
    }

    public void setTxContrasena(String txContrasena) {
        this.txContrasena = txContrasena;
    }

    public LocalDate getFeCreacion(){
        return this.feCreacion;
    }

    public void setFeCreacion(LocalDate feCreacion){
       this.feCreacion = feCreacion;
    }


}

