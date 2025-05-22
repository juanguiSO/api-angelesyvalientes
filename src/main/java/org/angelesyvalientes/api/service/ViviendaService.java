package org.angelesyvalientes.api.service;

import org.angelesyvalientes.api.persistence.entity.Vivienda;
import org.angelesyvalientes.api.persistence.repository.ViviendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que gestiona las operaciones relacionadas con la entidad {@link Vivienda}.
 * Proporciona métodos para obtener, crear, actualizar y eliminar viviendas
 * de la base de datos a través del {@link ViviendaRepository}.
 */
@Service
public class ViviendaService {

    /**
     * Repositorio para acceder a los datos de la entidad {@link Vivienda} en la base de datos.
     */
    @Autowired
    private ViviendaRepository viviendaRepository;

    /**
     * Obtiene una lista con todas las {@link Vivienda} almacenadas en la base de datos.
     * Utiliza el método {@code findAll} del repositorio.
     *
     * @return Una {@link List} que contiene todas las viviendas encontradas.
     * Si no hay viviendas, la lista estará vacía.
     */
    public List<Vivienda> obtenerTodasLasViviendas() {
        return viviendaRepository.findAll();
    }

    /**
     * Obtiene una {@link Vivienda} de la base de datos por su identificador único.
     * Utiliza el método {@code findById} del repositorio, que devuelve un {@link Optional}
     * para manejar el caso en que la vivienda no sea encontrada.
     *
     * @param id El identificador único de la vivienda a buscar.
     * @return Un {@link Optional} que contiene la {@link Vivienda} si se encuentra,
     * o un {@link Optional} vacío en caso contrario.
     */
    public Optional<Vivienda> obtenerViviendaPorId(Integer id) {
        return viviendaRepository.findById(id);
    }

    /**
     * Guarda una nueva {@link Vivienda} en la base de datos.
     * Utiliza el método {@code save} del repositorio.
     *
     * @param vivienda El objeto {@link Vivienda} a guardar.
     * @return El objeto {@link Vivienda} guardado, que puede incluir
     * identificadores generados por la base de datos.
     */
    public Vivienda crearVivienda(Vivienda vivienda) {
        return viviendaRepository.save(vivienda);
    }

    /**
     * Actualiza la información de una {@link Vivienda} existente en la base de datos.
     * Primero, busca la vivienda por su ID. Si se encuentra, actualiza sus campos
     * con la información proporcionada en el {@code viviendaActualizada} y luego
     * guarda los cambios utilizando el método {@code save} del repositorio.
     * Si la vivienda no se encuentra, devuelve {@code null} (se podría considerar
     * lanzar una excepción para un manejo de errores más explícito).
     *
     * @param id                  El identificador único de la vivienda a actualizar.
     * @param viviendaActualizada El objeto {@link Vivienda} con la información actualizada.
     * @return El objeto {@link Vivienda} actualizado y guardado en la base de datos,
     * o {@code null} si no se encuentra una vivienda con el ID proporcionado.
     */
    public Vivienda actualizarVivienda(Integer id, Vivienda viviendaActualizada) {
        Optional<Vivienda> viviendaExistente = viviendaRepository.findById(id);
        if (viviendaExistente.isPresent()) {
            viviendaActualizada.setId(id); // Asegura que el ID no cambie
            return viviendaRepository.save(viviendaActualizada);
        } else {
            return null; // O lanza una excepción, dependiendo de tu manejo de errores
        }
    }

    /**
     * Elimina una {@link Vivienda} de la base de datos por su identificador único.
     * Utiliza el método {@code deleteById} del repositorio.
     *
     * @param id El identificador único de la vivienda a eliminar.
     */
    public void eliminarVivienda(int id) {
        viviendaRepository.deleteById(id);
    }

    /**
     * Obtiene el número total de viviendas registradas.
     * @return El conteo total de viviendas.
     */
    public long countAllViviendas() {
        return viviendaRepository.count();
    }
}