package org.angelesyvalientes.api.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidad que representa a un acompañante en el sistema.
 * Mapea la tabla "acompanante" en la base de datos.
 */
@Data
@Entity
@Table(name = "acompanante")
@Setter
@Getter
public class Acompanante {

    /**
     * Identificador único del acompañante, generado automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nm_id_acompanante")
    private int idAcompanante;

    /**
     * Relación muchos a uno con la entidad {@link Persona},
     * representando la persona a la que acompaña este registro.
     * La columna de unión en la tabla "acompanante" es "nm_id_persona".
     * Se requiere que la persona asociada no sea nula.
     */
    @ManyToOne
    @JoinColumn(name = "nm_id_persona")
    @NotNull(message = "La persona acompañante no puede ser nula.")
    private Persona persona;

    /**
     * Nombre del acompañante.
     * Se requiere que el nombre no esté en blanco y tenga una longitud máxima de 45 caracteres.
     */
    @Column(name = "tx_nombre_acompanante", length = 45)
    @NotBlank(message = "El nombre del acompañante no puede estar en blanco.")
    @Size(max = 45, message = "El nombre del acompañante no puede exceder los 45 caracteres.")
    private String nombreAcompanante;

    /**
     * Número de teléfono del acompañante.
     * Se requiere que el teléfono no esté en blanco y tenga una longitud máxima de 20 caracteres.
     */
    @Column(name = "tx_telefono_acompanante", length = 20)
    @NotBlank(message = "El teléfono del acompañante no puede estar en blanco.")
    @Size(max = 10, message = "El teléfono del acompañante no puede exceder los 10 caracteres.")
    private String telefonoAcompanante;

    /**
     * Obtiene el identificador único del acompañante.
     *
     * @return El ID del acompañante.
     */
    public int getIdAcompanante() {
        return idAcompanante;
    }

    /**
     * Obtiene la persona asociada a este acompañante.
     *
     * @return La entidad {@link Persona} del acompañante.
     */
    public Persona getPersona() {
        return persona;
    }

    /**
     * Obtiene el nombre del acompañante.
     *
     * @return El nombre del acompañante.
     */
    public String getNombreAcompanante() {
        return nombreAcompanante;
    }

    /**
     * Obtiene el número de teléfono del acompañante.
     *
     * @return El número de teléfono del acompañante.
     */
    public String getTelefonoAcompanante() {
        return telefonoAcompanante;
    }

    /**
     * Establece el identificador único del acompañante.
     *
     * @param idAcompanante El nuevo ID del acompañante.
     */
    public void setIdAcompanante(int idAcompanante) {
        this.idAcompanante = idAcompanante;
    }

    /**
     * Establece el nombre del acompañante.
     *
     * @param nombreAcompanante El nuevo nombre del acompañante.
     */
    public void setNombreAcompanante(String nombreAcompanante) {
        this.nombreAcompanante = nombreAcompanante;
    }

    /**
     * Establece la persona asociada a este acompañante.
     *
     * @param persona La entidad {@link Persona} a asociar con el acompañante.
     */
    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    /**
     * Establece el número de teléfono del acompañante.
     *
     * @param telefonoAcompanante El nuevo número de teléfono del acompañante.
     */
    public void setTelefonoAcompanante(String telefonoAcompanante) {
        this.telefonoAcompanante = telefonoAcompanante;
    }
}