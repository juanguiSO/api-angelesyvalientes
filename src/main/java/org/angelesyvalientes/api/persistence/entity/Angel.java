package org.angelesyvalientes.api.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad que representa a un ángel (donante) en el sistema.
 * Hereda de la entidad {@link Persona} y mapea la tabla "angel" en la base de datos.
 */
@Data
@Entity
@Table(name = "angel")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Angel extends Persona {

    /**
     * Relación muchos a uno con la entidad {@link Donacion},
     * representando la donación asociada a este ángel.
     * La columna de unión en la tabla "angel" es "nm_id_donacion".
     */
    @ManyToOne
    @JoinColumn(name = "nm_id_donacion")
    private Donacion donacion;

    /**
     * Profesión del ángel.
     */
    @Column(name = "tx_profesion")
    @Size(max = 100, message = "La profesión no debe exceder los 100 caracteres.")
    private String profesion;

    /**
     * URL de la galería de imágenes del ángel.
     */
    @Column(name = "tx_url_galeria")
    @Size(max = 255, message = "La URL de la galería no debe exceder los 255 caracteres.")
    private String urlGaleria;

    /**
     * Descripción del ángel, con una longitud máxima de 255 caracteres.
     */
    @Column(name = "tx_descripcion", length = 255)
    @Size(max = 255, message = "La descripción no debe exceder los 255 caracteres.")
    private String descripcion;

    /**
     * Rol específico del ángel dentro del sistema, con una longitud máxima de 45 caracteres.
     */
    @Column(name = "tx_rol_angel", length = 45)
    @NotBlank(message = "El rol del ángel es obligatorio.")
    @Size(max = 45, message = "El rol del ángel no debe exceder los 45 caracteres.")
    private String rolAngel;

    /**
     * Establece la descripción del ángel.
     *
     * @param descripcion La nueva descripción del ángel.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Establece la donación asociada al ángel.
     *
     * @param donacion La entidad {@link Donacion} a asociar con el ángel.
     */
    public void setDonacion(Donacion donacion) {
        this.donacion = donacion;
    }

    /**
     * Establece el rol específico del ángel.
     *
     * @param rolAngel El nuevo rol del ángel.
     */
    public void setRolAngel(String rolAngel) {
        this.rolAngel = rolAngel;
    }

    /**
     * Establece la URL de la galería de imágenes del ángel.
     *
     * @param urlGaleria La nueva URL de la galería del ángel.
     */
    public void setUrlGaleria(String urlGaleria) {
        this.urlGaleria = urlGaleria;
    }

    /**
     * Establece la profesión del ángel.
     *
     * @param profesion La nueva profesión del ángel.
     */
    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    /**
     * Obtiene el rol específico del ángel.
     *
     * @return El rol del ángel.
     */
    public String getRolAngel() {
        return rolAngel;
    }

    /**
     * Obtiene la donación asociada al ángel.
     *
     * @return La entidad {@link Donacion} asociada al ángel.
     */
    public Donacion getDonacion() {
        return donacion;
    }

    /**
     * Obtiene la descripción del ángel.
     *
     * @return La descripción del ángel.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Obtiene la profesión del ángel.
     *
     * @return La profesión del ángel.
     */
    public String getProfesion() {
        return profesion;
    }

    /**
     * Obtiene la URL de la galería de imágenes del ángel.
     *
     * @return La URL de la galería del ángel.
     */
    public String getUrlGaleria() {
        return urlGaleria;
    }
}