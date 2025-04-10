package org.angelesyvalientes.api.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Entidad que representa un permiso o autorización dentro del sistema.
 * Mapea la tabla "permiso" en la base de datos.
 */
@Entity
@Table(name = "permiso")
@Getter
@Setter
@NoArgsConstructor
public class Permiso {

    /**
     * Identificador único del permiso, generado automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_permiso")
    private Integer idPermiso;

    /**
     * Código que identifica la opción o funcionalidad a la que se aplica este permiso.
     * No puede ser nulo y tiene una longitud máxima de 45 caracteres.
     */
    @Column(name = "cd_opcion", length = 45, nullable = false)
    @NotBlank(message = "El código de la opción es obligatorio.")
    @Size(max = 45, message = "El código de la opción no debe exceder los 45 caracteres.")
    private String codigoOpcion;

    /**
     * Código que identifica la operación específica permitida por este permiso.
     * No puede ser nulo y tiene una longitud máxima de 45 caracteres.
     */
    @Column(name = "cd_operacion", length = 45, nullable = false)
    @NotBlank(message = "El código de la operación es obligatorio.")
    @Size(max = 45, message = "El código de la operación no debe exceder los 45 caracteres.")
    private String codigoOperacion;

    /**
     * Relación muchos a muchos con la entidad {@link Rol},
     * indicando los roles que tienen asignado este permiso.
     * El atributo `mappedBy` indica que la gestión de esta relación se realiza
     * en la propiedad "permisos" de la entidad {@link Rol}.
     */
    @ManyToMany(mappedBy = "permisos")
    private List<Rol> roles;

    /**
     * Indica si el permiso está activo (true) o inactivo (false).
     * Por defecto, su valor es true.
     */
    @Column(name = "bo_activo", nullable = false, columnDefinition = "boolean default true")
    private boolean activo = true;

    /**
     * Obtiene el código de la opción asociada a este permiso.
     *
     * @return El código de la opción.
     */
    public String getCodigoOpcion() {
        return codigoOpcion;
    }

    /**
     * Obtiene el código de la operación permitida por este permiso.
     *
     * @return El código de la operación.
     */
    public String getCodigoOperacion() {
        return codigoOperacion;
    }

    /**
     * Obtiene el identificador único del permiso.
     *
     * @return El ID del permiso.
     */
    public Integer getIdPermiso() {
        return idPermiso;
    }

    /**
     * Obtiene la lista de roles que tienen asignado este permiso.
     *
     * @return La lista de entidades {@link Rol}.
     */
    public List<Rol> getRoles() {
        return roles;
    }

    /**
     * Establece el código de la opción asociada a este permiso.
     *
     * @param codigoOpcion El nuevo código de la opción.
     */
    public void setCodigoOpcion(String codigoOpcion) {
        this.codigoOpcion = codigoOpcion;
    }

    /**
     * Establece el código de la operación permitida por este permiso.
     *
     * @param codigoOperacion El nuevo código de la operación.
     */
    public void setCodigoOperacion(String codigoOperacion) {
        this.codigoOperacion = codigoOperacion;
    }

    /**
     * Establece la lista de roles que tienen asignado este permiso.
     *
     * @param roles La lista de entidades {@link Rol} a asignar.
     */
    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    /**
     * Establece el identificador único del permiso.
     *
     * @param idPermiso El nuevo ID del permiso.
     */
    public void setIdPermiso(Integer idPermiso) {
        this.idPermiso = idPermiso;
    }
}