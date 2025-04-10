package org.angelesyvalientes.api.service;

import org.angelesyvalientes.api.persistence.entity.Familiar;
import org.angelesyvalientes.api.persistence.repository.FamiliaresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que gestiona las operaciones relacionadas con la entidad {@link Familiar}.
 * Proporciona métodos para obtener, crear, actualizar y eliminar familiares
 * de la base de datos a través del {@link FamiliaresRepository}.
 */
@Service
public class FamiliaresService {

    /**
     * Repositorio para acceder a los datos de la entidad {@link Familiar} en la base de datos.
     */
    @Autowired
    private FamiliaresRepository familiaresRepository;

    /**
     * Obtiene una lista con todos los {@link Familiar} almacenados en la base de datos.
     * Utiliza el método {@code findAll} del repositorio.
     *
     * @return Una {@link List} que contiene todos los familiares encontrados.
     * Si no hay familiares, la lista estará vacía.
     */
    public List<Familiar> obtenerTodosLosFamiliares() {
        return familiaresRepository.findAll();
    }

    /**
     * Obtiene un {@link Familiar} de la base de datos por su identificador único.
     * Utiliza el método {@code findById} del repositorio, que devuelve un {@link Optional}
     * para manejar el caso en que el familiar no sea encontrado.
     *
     * @param id El identificador único del familiar a buscar.
     * @return Un {@link Optional} que contiene el {@link Familiar} si se encuentra,
     * o un {@link Optional} vacío en caso contrario.
     */
    public Optional<Familiar> obtenerFamiliarPorId(int id) {
        return familiaresRepository.findById(id);
    }

    /**
     * Guarda un nuevo {@link Familiar} en la base de datos.
     * Utiliza el método {@code save} del repositorio.
     *
     * @param familiares El objeto {@link Familiar} a guardar.
     * @return El objeto {@link Familiar} guardado, que puede incluir
     * identificadores generados por la base de datos.
     */
    public Familiar crearFamiliar(Familiar familiares) {
        return familiaresRepository.save(familiares);
    }

    /**
     * Actualiza la información de un {@link Familiar} existente en la base de datos.
     * Primero, busca el familiar por su ID. Si se encuentra, actualiza sus campos
     * con la información proporcionada en el {@code familiarActualizado} y luego
     * guarda los cambios utilizando el método {@code save} del repositorio.
     * Si el familiar no se encuentra, devuelve {@code null} (se podría considerar
     * lanzar una excepción para un manejo de errores más explícito).
     *
     * @param id                  El identificador único del familiar a actualizar.
     * @param familiarActualizado El objeto {@link Familiar} con la información actualizada.
     * @return El objeto {@link Familiar} actualizado y guardado en la base de datos,
     * o {@code null} si no se encuentra un familiar con el ID proporcionado.
     */
    public Familiar actualizarFamiliar(int id, Familiar familiarActualizado) {
        Optional<Familiar> familiarExistente = familiaresRepository.findById(id);
        if (familiarExistente.isPresent()) {
            familiarActualizado.setIdFamiliar(id); // Asegura que el ID no cambie
            return familiaresRepository.save(familiarActualizado);
        } else {
            return null; // O lanza una excepción, dependiendo de tu manejo de errores
        }
    }

    /**
     * Elimina un {@link Familiar} de la base de datos por su identificador único.
     * Utiliza el método {@code deleteById} del repositorio.
     *
     * @param id El identificador único del familiar a eliminar.
     */
    public void eliminarFamiliar(int id) {
        familiaresRepository.deleteById(id);
    }

    /**
     * Puedes agregar métodos personalizados aquí para implementar lógica de negocio
     * específica relacionada con la entidad {@link Familiar}. Por ejemplo, métodos
     * para buscar familiares por ciertos criterios, realizar operaciones complejas, etc.
     */
    // Puedes agregar métodos personalizados aquí para lógica de negocio específica
}