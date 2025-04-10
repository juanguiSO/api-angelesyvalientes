package org.angelesyvalientes.api.service;

import org.angelesyvalientes.api.persistence.entity.Permiso;
import org.angelesyvalientes.api.persistence.repository.PermisoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que gestiona las operaciones relacionadas con la entidad {@link Permiso}.
 * Proporciona métodos para guardar, obtener, actualizar y eliminar permisos
 * de la base de datos a través del {@link PermisoRepository}. También incluye
 * métodos para buscar permisos por diferentes criterios.
 */
@Service
public class PermisoService {

    private final PermisoRepository permisoRepository;

    /**
     * Constructor de la clase {@code PermisoService}.
     * Recibe una instancia de {@link PermisoRepository} a través de la inyección de dependencias
     * para interactuar con la capa de persistencia.
     *
     * @param permisoRepository El repositorio para acceder a los datos de los permisos.
     */
    @Autowired
    public PermisoService(PermisoRepository permisoRepository) {
        this.permisoRepository = permisoRepository;
    }

    /**
     * Guarda un nuevo {@link Permiso} en la base de datos.
     * Utiliza el método {@code save} del repositorio.
     *
     * @param permiso El objeto {@link Permiso} a guardar.
     * @return El objeto {@link Permiso} guardado, que puede incluir
     * identificadores generados por la base de datos.
     */
    public Permiso save(Permiso permiso) {
        return permisoRepository.save(permiso);
    }

    /**
     * Obtiene una lista con todos los {@link Permiso} almacenados en la base de datos.
     * Utiliza el método {@code findAll} del repositorio.
     *
     * @return Una {@link List} que contiene todos los permisos encontrados.
     * Si no hay permisos, la lista estará vacía.
     */
    public List<Permiso> getAll() {
        return permisoRepository.findAll();
    }

    /**
     * Obtiene un {@link Permiso} de la base de datos por su identificador único.
     * Utiliza el método {@code findById} del repositorio, que devuelve un {@link Optional}
     * para manejar el caso en que el permiso no sea encontrado.
     *
     * @param id El identificador único del permiso a buscar.
     * @return Un {@link Optional} que contiene el {@link Permiso} si se encuentra,
     * o un {@link Optional} vacío en caso contrario.
     */
    public Optional<Permiso> getById(Integer id) {
        return permisoRepository.findById(id);
    }

    /**
     * Elimina un {@link Permiso} de la base de datos por su identificador único.
     * Utiliza el método {@code deleteById} del repositorio.
     *
     * @param id El identificador único del permiso a eliminar.
     */
    public void deleteById(Integer id) {
        permisoRepository.deleteById(id);
    }

    /**
     * Actualiza la información de un {@link Permiso} existente en la base de datos.
     * Primero, verifica si el permiso proporcionado tiene un ID. Luego, verifica si
     * existe un permiso con ese ID en la base de datos. Si ambos son verdaderos,
     * actualiza el permiso utilizando el método {@code save} del repositorio.
     * Si el permiso no tiene ID o no existe, devuelve {@code null} (se podría considerar
     * lanzar una excepción para un manejo de errores más explícito).
     *
     * @param permiso El objeto {@link Permiso} con la información actualizada.
     * Debe tener un ID válido para poder ser actualizado.
     * @return El objeto {@link Permiso} actualizado y guardado en la base de datos,
     * o {@code null} si el permiso no tiene ID o no existe.
     * @throws IllegalArgumentException Si el permiso proporcionado no tiene un ID.
     */
    public Permiso update(Permiso permiso) {
        if (permiso.getIdPermiso() == null) {
            throw new IllegalArgumentException("El permiso para actualizar debe tener un ID.");
        }
        if (permisoRepository.existsById(permiso.getIdPermiso())) {
            return permisoRepository.save(permiso);
        } else {
            return null; // O lanzar una excepción indicando que el permiso no existe
        }
    }

    /**
     * Busca permisos en la base de datos por su código de opción.
     * Utiliza el método {@code findByCodigoOpcion} del repositorio.
     *
     * @param codigoOpcion El código de opción a buscar.
     * @return Una {@link List} de permisos cuyo código de opción coincide con el proporcionado.
     */
    public List<Permiso> findByCodigoOpcion(String codigoOpcion) {
        return permisoRepository.findByCodigoOpcion(codigoOpcion);
    }

    /**
     * Busca permisos en la base de datos por su código de operación.
     * Utiliza el método {@code findByCodigoOperacion} del repositorio.
     *
     * @param codigoOperacion El código de operación a buscar.
     * @return Una {@link List} de permisos cuyo código de operación coincide con el proporcionado.
     */
    public List<Permiso> findByCodigoOperacion(String codigoOperacion) {
        return permisoRepository.findByCodigoOperacion(codigoOperacion);
    }

    /**
     * Busca un permiso en la base de datos por su código de opción y código de operación.
     * Utiliza el método {@code findByCodigoOpcionAndCodigoOperacion} del repositorio.
     *
     * @param codigoOpcion  El código de opción a buscar.
     * @param codigoOperacion El código de operación a buscar.
     * @return Un {@link Optional} que contiene el {@link Permiso} si se encuentra
     * un permiso con ambos códigos coincidentes, o un {@link Optional} vacío en caso contrario.
     */
    public Optional<Permiso> findByCodigoOpcionAndCodigoOperacion(String codigoOpcion, String codigoOperacion) {
        return permisoRepository.findByCodigoOpcionAndCodigoOperacion(codigoOpcion, codigoOperacion);
    }

    /**
     * Obtiene todos los permisos cuyo estado 'activo' es verdadero.
     * Utiliza el método {@code findByActivoTrue} del repositorio.
     *
     * @return Una {@link List} de permisos que están activos.
     */
    public List<Permiso> getAllActivos() {
        return permisoRepository.findByActivoTrue();
    }

    /**
     * Obtiene todos los permisos cuyo estado 'activo' es falso.
     * Utiliza el método {@code findByActivoFalse} del repositorio.
     *
     * @return Una {@link List} de permisos que están inactivos.
     */
    public List<Permiso> getAllInactivos() {
        return permisoRepository.findByActivoFalse();
    }
}