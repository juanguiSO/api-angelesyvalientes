package org.angelesyvalientes.api.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * Entidad que representa a un usuario en el sistema.
 * Mapea la tabla "usuario" en la base de datos.
 */
@Data
@Entity
@Table(name = "usuario")
@Getter
@Setter
public class Usuario {

    /**
     * Código único del usuario, que actúa como clave primaria.
     */
    @Id
    @Column(name = "cd_usuario", nullable = false)
    @NotBlank(message = "El código de usuario es obligatorio.")
    @Size(max = 255, message = "El código de usuario no debe exceder los 255 caracteres.")
    private String cdUsuario;

    /**
     * Relación uno a uno con la entidad {@link Persona}, representando la información personal del usuario.
     * La columna de unión en la tabla "usuario" es "nm_id_persona".
     */
    @OneToOne
    @JoinColumn(name = "nm_id_persona")
    @NotNull(message = "La información de la persona es obligatoria.")
    private Persona persona;

    /**
     * Contraseña del usuario, no nula y con una longitud máxima de 100 caracteres.
     */
    @Column(name = "tx_contrasena", nullable = false, length = 100)
    @NotBlank(message = "La contraseña es obligatoria.")
    @Size(max = 100, message = "La contraseña no debe exceder los 100 caracteres.")
    private String txContrasena;

    /**
     * Fecha de creación del usuario.
     */
    @Column(name = "fe_creacion")
    @PastOrPresent(message = "La fecha de creación no puede ser en el futuro.")
    private LocalDate feCreacion;

    /**
     * Indica si el usuario ha sido eliminado lógicamente (no borrado de la base de datos).
     * Por defecto, su valor es false.
     */
    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    /**
     * Relación muchos a muchos con la entidad {@link Rol}, representando los roles asignados al usuario.
     * Se utiliza una tabla de unión llamada "usuario_rol" para gestionar esta relación.
     */
    @ManyToOne
    @JoinColumn(name = "id_rol")
    private Rol rol;


    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    /**
     * Obtiene el código único del usuario.
     *
     * @return El código del usuario.
     */
    public String getCdUsuario() {
        return cdUsuario;
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return La contraseña del usuario.
     */
    public String getTxContrasena() {
        return txContrasena;
    }

    /**
     * Establece el código único del usuario.
     *
     * @param cdUsuario El nuevo código del usuario.
     */
    public void setCdUsuario(String cdUsuario) {
        this.cdUsuario = cdUsuario;
    }

    /**
     * Establece la contraseña del usuario.
     *
     * @param txContrasena La nueva contraseña del usuario.
     */
    public void setTxContrasena(String txContrasena) {
        this.txContrasena = txContrasena;
    }

    /**
     * Obtiene la información personal del usuario.
     *
     * @return La entidad {@link Persona} asociada al usuario.
     */
    public Persona getPersona() {
        return persona;
    }

    /**
     * Obtiene la fecha de creación del usuario.
     *
     * @return La fecha de creación del usuario.
     */
    public LocalDate getFeCreacion() {
        return feCreacion;
    }

    /**
     * Establece la información personal del usuario.
     *
     * @param persona La entidad {@link Persona} a asociar con el usuario.
     */
    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    /**
     * Establece la fecha de creación del usuario.
     *
     * @param feCreacion La nueva fecha de creación del usuario.
     */
    public void setFeCreacion(LocalDate feCreacion) {
        this.feCreacion = feCreacion;
    }

    /**
     * Obtiene la lista de roles asignados al usuario.
     *
     * @return La lista de roles del usuario.
     */

    /**
     * Obtiene el estado de eliminación lógica del usuario.
     *
     * @return `true` si el usuario está eliminado lógicamente, `false` en caso contrario.
     */
    public boolean isDeleted() {
        return isDeleted;
    }

    /**
     * Establece el estado de eliminación lógica del usuario.
     *
     * @param deleted El nuevo estado de eliminación lógica del usuario.
     */
    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}