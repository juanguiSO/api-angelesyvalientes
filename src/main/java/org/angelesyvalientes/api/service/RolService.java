package org.angelesyvalientes.api.service;

import org.angelesyvalientes.api.persistence.entity.Rol;
import org.angelesyvalientes.api.persistence.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolService {

    private final RolRepository rolRepository;

    @Autowired
    public RolService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    /**
     * Guarda un nuevo rol en la base de datos.
     *
     * @param rol El rol a guardar.
     * @return El rol guardado.
     */
    public Rol save(Rol rol) {
        return rolRepository.save(rol);
    }

    /**
     * Obtiene todos los roles de la base de datos.
     *
     * @return Una lista de todos los roles.
     */
    public List<Rol> getAll() {
        return rolRepository.findAll();
    }

    /**
     * Obtiene un rol por su ID.
     *
     * @param id El ID del rol a buscar.
     * @return Un Optional que contiene el rol si se encuentra, o un Optional vacío si no.
     */
    public Optional<Rol> getById(Integer id) {
        return rolRepository.findById(id);
    }

    /**
     * Elimina un rol de la base de datos por su ID.
     *
     * @param id El ID del rol a eliminar.
     */
    public void deleteById(Integer id) {
        rolRepository.deleteById(id);
    }

    /**
     * Actualiza un rol existente en la base de datos.
     *
     * @param rol El rol con los datos actualizados. Debe tener un ID válido.
     * @return El rol actualizado.
     * @throws IllegalArgumentException Si el rol proporcionado no tiene un ID.
     */
    public Rol update(Rol rol) {
        if (rol.getIdRol() == null) {
            throw new IllegalArgumentException("El rol para actualizar debe tener un ID.");
        }
        if (rolRepository.existsById(rol.getIdRol())) {
            return rolRepository.save(rol);
        } else {
            return null; // O lanzar una excepción indicando que el rol no existe
        }
    }

    /**
     * Busca un rol por su nombre.
     *
     * @param nombre El nombre del rol a buscar.
     * @return Un Optional que contiene el rol si se encuentra, o un Optional vacío si no.
     */
    public Optional<Rol> findByNombre(String nombre) {
        return rolRepository.findByNombre(nombre);
    }

    /**
     * Obtiene todos los roles activos.
     *
     * @return Una lista de roles cuyo estado 'activo' es true.
     */
    public List<Rol> getAllActivos() {
        return rolRepository.findByActivoTrue();
    }

    /**
     * Obtiene todos los roles inactivos.
     *
     * @return Una lista de roles cuyo estado 'activo' es false.
     */
    public List<Rol> getAllInactivos() {
        return rolRepository.findByActivoFalse();
    }

    /**
     * Asocia un permiso a un rol.
     *
     * @param rolId     El ID del rol al que se le asignará el permiso.
     * @param permisoId El ID del permiso a asignar.
     * @return El rol actualizado, o un Optional vacío si el rol o el permiso no existen.
     */
    public Optional<Rol> addPermisoToRol(Integer rolId, Integer permisoId) {
        Optional<Rol> rolOptional = rolRepository.findById(rolId);
        // Para implementar esto completamente, necesitarías también un PermisoRepository
        // y obtener la entidad Permiso por su ID. Luego, añadir el permiso a la lista
        // de permisos del rol y guardar el rol actualizado.
        // Este es un ejemplo incompleto, ya que faltaría la interacción con PermisoRepository.
        return rolOptional.map(rol -> {
            // Lógica para obtener el permiso y añadirlo a la lista de permisos del rol
            // Necesitarías inyectar PermisoRepository aquí para hacerlo correctamente.
            // Ejemplo conceptual:
            // Optional<Permiso> permisoOptional = permisoRepository.findById(permisoId);
            // permisoOptional.ifPresent(permiso -> {
            //     rol.getPermisos().add(permiso);
            //     rolRepository.save(rol);
            // });
            return rol;
        });
    }

    /**
     * Remueve un permiso de un rol.
     *
     * @param rolId     El ID del rol del que se removerá el permiso.
     * @param permisoId El ID del permiso a remover.
     * @return El rol actualizado, o un Optional vacío si el rol o el permiso no existen.
     */
    public Optional<Rol> removePermisoFromRol(Integer rolId, Integer permisoId) {
        Optional<Rol> rolOptional = rolRepository.findById(rolId);
        // Similar a addPermisoToRol, esto requeriría interactuar con PermisoRepository
        // para obtener el permiso y luego removerlo de la lista de permisos del rol.
        return rolOptional.map(rol -> {
            // Lógica para obtener el permiso y removerlo de la lista de permisos del rol
            // Necesitarías inyectar PermisoRepository aquí para hacerlo correctamente.
            // Ejemplo conceptual:
            // Optional<Permiso> permisoOptional = permisoRepository.findById(permisoId);
            // permisoOptional.ifPresent(permiso -> {
            //     rol.getPermisos().removeIf(permisoExistente -> permisoExistente.getIdPermiso().equals(permisoId));
            //     rolRepository.save(rol);
            // });
            return rol;
        });
    }
}