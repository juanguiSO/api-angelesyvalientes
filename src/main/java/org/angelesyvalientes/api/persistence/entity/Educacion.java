package org.angelesyvalientes.api.persistence.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Entidad que representa la información educativa de una persona en el sistema.
 * Mapea la tabla "educacion" en la base de datos.
 */
@Data
@Entity
@Table(name = "educacion")
public class Educacion {

    /**
     * Identificador único del registro de educación, generado automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nm_id_educacion")
    private int idEducacion;

    /**
     * Relación muchos a uno con la entidad {@link Persona},
     * indicando a qué persona pertenece esta información educativa.
     * La columna de unión en la tabla "educacion" es "nm_id_persona".
     */
    @ManyToOne
    @JoinColumn(name = "nm_id_persona")
    @NotNull(message = "La persona asociada a la educación es obligatoria.")
    private Persona persona;

    /**
     * Nombre de la institución educativa.
     * La longitud máxima permitida para este campo es de 255 caracteres.
     */
    @Column(name = "tx_institucion", length = 255)
    @NotBlank(message = "El nombre de la institución es obligatorio.")
    @Size(max = 255, message = "El nombre de la institución no debe exceder los 255 caracteres.")
    private String institucion;

    /**
     * Nivel de educación alcanzado (ej. primaria, secundaria, universitario).
     * La longitud máxima permitida para este campo es de 45 caracteres.
     */
    @Column(name = "tx_nivel", length = 45)
    @NotBlank(message = "El nivel de educación es obligatorio.")
    @Size(max = 45, message = "El nivel de educación no debe exceder los 45 caracteres.")
    private String nivel;

    /**
     * Establece la persona a la que pertenece esta información educativa.
     *
     * @param persona La entidad {@link Persona} asociada a la educación.
     */
    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    /**
     * Establece el nombre de la institución educativa.
     *
     * @param institucion El nombre de la institución.
     */
    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    /**
     * Establece el identificador único del registro de educación.
     *
     * @param idEducacion El ID del registro de educación.
     */
    public void setIdEducacion(int idEducacion) {
        this.idEducacion = idEducacion;
    }

    /**
     * Establece el nivel de educación alcanzado.
     *
     * @param nivel El nivel de educación.
     */
    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    /**
     * Obtiene la persona asociada a esta información educativa.
     *
     * @return La entidad {@link Persona} de la educación.
     */
    public Persona getPersona() {
        return persona;
    }

    /**
     * Obtiene el identificador único del registro de educación.
     *
     * @return El ID del registro de educación.
     */
    public int getIdEducacion() {
        return idEducacion;
    }

    /**
     * Obtiene el nombre de la institución educativa.
     *
     * @return El nombre de la institución.
     */
    public String getInstitucion() {
        return institucion;
    }

    /**
     * Obtiene el nivel de educación alcanzado.
     *
     * @return El nivel de educación.
     */
    public String getNivel() {
        return nivel;
    }
}