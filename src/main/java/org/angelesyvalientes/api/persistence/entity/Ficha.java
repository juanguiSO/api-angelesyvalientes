package org.angelesyvalientes.api.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidad que representa una ficha de información o recurso dentro de un programa.
 * Mapea la tabla "ficha" en la base de datos.
 */
@Entity
@Table(name = "ficha")
@Getter
@Setter
public class Ficha {
    /**
     * Identificador único de la ficha.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // <--- ¡AÑADIR ESTO!
    @Column(name = "nm_id_ficha")
    private Integer id; // <--- ¡CAMBIAR A Integer!


    /**
     * Nombre o tema de la ficha.
     * La longitud máxima permitida para este campo es de 45 caracteres.
     */
    @Column(name = "tx_tema", length = 45)
    @NotBlank(message = "El nombre de la ficha es obligatorio.")
    @Size(max = 45, message = "El nombre de la ficha no debe exceder los 45 caracteres.")
    private String nombre;

    /**
     * URL o ubicación del recurso asociado a esta ficha.
     * La longitud máxima permitida para este campo es de 200 caracteres.
     */
    @Column(name = "tx_url_recurso", length = 200)
    @Size(max = 200, message = "La URL del recurso no debe exceder los 200 caracteres.")
    private String urlRecurso;

    /**
     * Código identificador único de la ficha dentro del sistema.
     */
    @Column(name = "cod_ficha")
    @NotNull(message = "El código de la ficha es obligatorio.")
    private Integer codigo; // <--- CAMBIAR A Integer (recomendado si puede ser nulo o si no tiene un significado fijo cuando no está establecido)

    /**
     * Relación muchos a uno con la entidad {@link Programa},
     * indicando a qué programa pertenece esta ficha.
     * La columna de unión en la tabla "ficha" es "nm_id_programa" y no puede ser nula.
     */
    @ManyToOne
    @JoinColumn(name = "nm_id_programa", nullable = false)
    @NotNull(message = "El programa asociado a la ficha es obligatorio.")
    private Programa programa;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCodigo(@NotNull(message = "El código de la ficha es obligatorio.") Integer codigo) {
        this.codigo = codigo;
    }

    public @NotNull(message = "El código de la ficha es obligatorio.") Integer getCodigo() {
        return codigo;
    }

    public Integer getId() {
        return id;
    }

    /**
     * Establece el nombre o tema de la ficha.
     *
     * @param nombre El nombre de la ficha.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Establece el programa al que pertenece esta ficha.
     *
     * @param programa La entidad {@link Programa} asociada a la ficha.
     */
    public void setPrograma(Programa programa) {
        this.programa = programa;
    }

    /**
     * Establece la URL del recurso asociado a la ficha.
     *
     * @param urlRecurso La URL del recurso.
     */
    public void setUrlRecurso(String urlRecurso) {
        this.urlRecurso = urlRecurso;
    }


    /**
     * Obtiene el programa al que pertenece esta ficha.
     *
     * @return La entidad {@link Programa} asociada a la ficha.
     */
    public Programa getPrograma() {
        return programa;
    }

    /**
     * Obtiene el nombre o tema de la ficha.
     *
     * @return El nombre de la ficha.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene la URL del recurso asociado a la ficha.
     *
     * @return La URL del recurso.
     */
    public String getUrlRecurso() {
        return urlRecurso;
    }
}