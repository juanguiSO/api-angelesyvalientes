package org.angelesyvalientes.api.service;

import org.angelesyvalientes.api.persistence.entity.Angel;
import org.angelesyvalientes.api.persistence.repository.AngelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que gestiona las operaciones relacionadas con la entidad {@link Angel}.
 * Proporciona métodos para obtener, crear, actualizar y eliminar ángeles
 * de la base de datos a través del {@link AngelRepository}.
 */
@Service
public class AngelService {

    private final AngelRepository angelRepository;

    /**
     * Constructor de la clase {@code AngelService}.
     * Recibe una instancia de {@link AngelRepository} a través de la inyección de dependencias
     * para interactuar con la capa de persistencia.
     *
     * @param angelRepository El repositorio para acceder a los datos de los ángeles.
     */
    @Autowired
    public AngelService(AngelRepository angelRepository) {
        this.angelRepository = angelRepository;
    }

    /**
     * Obtiene una lista con todos los {@link Angel} almacenados en la base de datos.
     * Utiliza el método {@code findAll} del repositorio.
     *
     * @return Una {@link List} que contiene todos los ángeles encontrados.
     * Si no hay ángeles, la lista estará vacía.
     */
    public List<Angel> getAllAngeles() {
        return angelRepository.findAll();
    }

    /**
     * Obtiene un {@link Angel} de la base de datos por su identificador único.
     * Utiliza el método {@code findById} del repositorio, que devuelve un {@link Optional}
     * para manejar el caso en que el ángel no sea encontrado.
     *
     * @param id El identificador único del ángel a buscar.
     * @return Un {@link Optional} que contiene el {@link Angel} si se encuentra,
     * o un {@link Optional} vacío en caso contrario.
     */
    public Optional<Angel> getAngelById(Long id) {
        return angelRepository.findById(id);
    }

    /**
     * Guarda un nuevo {@link Angel} en la base de datos.
     * Utiliza el método {@code save} del repositorio.
     *
     * @param angel El objeto {@link Angel} a guardar.
     * @return El objeto {@link Angel} guardado, que puede incluir
     * identificadores generados por la base de datos.
     */
    public Angel saveAngel(Angel angel) {
        return angelRepository.save(angel);
    }

    /**
     * Elimina un {@link Angel} de la base de datos por su identificador único.
     * Utiliza el método {@code deleteById} del repositorio.
     *
     * @param id El identificador único del ángel a eliminar.
     */
    public void deleteAngel(Long id) {
        angelRepository.deleteById(id);
    }

    /**
     * Actualiza la información de un {@link Angel} existente en la base de datos.
     * Primero, busca el ángel por su ID. Si se encuentra, actualiza los campos proporcionados
     * en el {@code angelDetails} y luego guarda los cambios utilizando el método {@code save}
     * del repositorio. Si el ángel no se encuentra, devuelve {@code null} (se podría considerar
     * lanzar una excepción para un manejo de errores más explícito).
     *
     * @param id          El identificador único del ángel a actualizar.
     * @param angelDetails El objeto {@link Angel} que contiene los detalles actualizados.
     * @return El objeto {@link Angel} actualizado y guardado en la base de datos,
     * o {@code null} si no se encuentra un ángel con el ID proporcionado.
     */
    public Angel updateAngel(Long id, Angel angelDetails) {
        Optional<Angel> angelOptional = angelRepository.findById(id);

        if (angelOptional.isPresent()) {
            Angel angel = angelOptional.get();

            // Actualizar los campos si los nuevos valores no son nulos
            if (angelDetails.getDonacion() != null) {
                angel.setDonacion(angelDetails.getDonacion());
            }
            if (angelDetails.getProfesion() != null) {
                angel.setProfesion(angelDetails.getProfesion());
            }
            if (angelDetails.getUrlGaleria() != null) {
                angel.setUrlGaleria(angelDetails.getUrlGaleria());
            }
            if (angelDetails.getDescripcion() != null) {
                angel.setDescripcion(angelDetails.getDescripcion());
            }
            if (angelDetails.getRolAngel() != null) {
                angel.setRolAngel(angelDetails.getRolAngel());
            }

            return angelRepository.save(angel);
        } else {
            return null; // O lanza una excepción, según tu manejo de errores
        }
    }
}