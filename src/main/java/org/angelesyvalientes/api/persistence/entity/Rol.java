package org.angelesyvalientes.api.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Entidad que representa un rol de usuario en el sistema.
 * Mapea la tabla "rol" en la base de datos.
 */
@Entity
@Table(name = "rol")
@Getter
@Setter
@NoArgsConstructor
public class Rol {

    /**
     * Identificador único del rol, generado automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Integer idRol;

    /**
     * Nombre del rol. No puede ser nulo, debe ser único y tiene una longitud máxima de 255 caracteres.
     */
    @Column(name = "tx_rol", length = 255, nullable = false, unique = true)
    @NotBlank(message = "El nombre del rol es obligatorio.")
    @Size(max = 255, message = "El nombre del rol no debe exceder los 255 caracteres.")
    private String nombre;

    /**
     * Relación muchos a muchos con la entidad {@link Usuario},
     * indicando los usuarios que tienen asignado este rol.
     * El atributo `mappedBy` indica que la gestión de esta relación se realiza
     * en la propiedad "roles" de la entidad {@link Usuario}.
     */
    @OneToMany(mappedBy = "rol")
    private List<Usuario> usuarios;

    /**
     * Relación muchos a muchos con la entidad {@link Permiso},
     * indicando los permisos asociados a este rol.
     * Se utiliza una tabla de unión llamada "rol_permiso" para gestionar esta relación.
     */
    @ManyToMany
    @JoinTable(
            name = "rol_permiso",
            joinColumns = @JoinColumn(name = "id_rol"),
            inverseJoinColumns = @JoinColumn(name = "id_permiso")
    )
    private List<Permiso> permisos;

    /**
     * Indica si el rol está activo (true) o inactivo (false).
     * Por defecto, su valor es true.
     */
    @Column(name = "bo_activo", nullable = false, columnDefinition = "boolean default true")
    private boolean activo = true;

    /**
     * Obtiene el nombre del rol.
     *
     * @return El nombre del rol.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el identificador único del rol.
     *
     * @return El ID del rol.
     */
    public Integer getIdRol() {
        return idRol;
    }

    /**
     * Establece el nombre del rol.
     *
     * @param nombre El nombre del rol.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Establece el identificador único del rol.
     *
     * @param idRol El ID del rol.
     */
    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    /**
     * Obtiene la lista de usuarios asociados a este rol.
     *
     * @return La lista de usuarios.
     */
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    /**
     * Establece la lista de usuarios asociados a este rol.
     *
     * @param usuarios La lista de usuarios a asignar a este rol.
     */
    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    /**
     * Obtiene la lista de permisos asociados a este rol.
     *
     * @return La lista de permisos.
     */
    public List<Permiso> getPermisos() {
        return permisos;
    }

    /**
     * Establece la lista de permisos asociados a este rol.
     *
     * @param permisos La lista de permisos a asignar a este rol.
     */
    public void setPermisos(List<Permiso> permisos) {
        this.permisos = permisos;
    }

    /**
     * Obtiene el estado de activación del rol.
     *
     * @return `true` si el rol está activo, `false` en caso contrario.
     */
    public boolean isActivo() {
        return activo;
    }

    /**
     * Establece el estado de activación del rol.
     *
     * @param activo El nuevo estado de activación del rol.
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}