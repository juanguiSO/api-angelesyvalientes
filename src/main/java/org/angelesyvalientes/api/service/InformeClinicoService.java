package org.angelesyvalientes.api.service;

import org.angelesyvalientes.api.persistence.entity.InformeClinico;
import org.angelesyvalientes.api.persistence.repository.InformeClinicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que gestiona las operaciones relacionadas con la entidad {@link InformeClinico}.
 * Proporciona métodos para obtener, crear, actualizar y eliminar informes clínicos
 * de la base de datos a través del {@link InformeClinicoRepository}.
 */
@Service
public class InformeClinicoService {

    private final InformeClinicoRepository informeClinicoRepository;

    /**
     * Constructor de la clase {@code InformeClinicoService}.
     * Recibe una instancia de {@link InformeClinicoRepository} a través de la inyección de dependencias
     * para interactuar con la capa de persistencia.
     *
     * @param informeClinicoRepository El repositorio para acceder a los datos de los informes clínicos.
     */
    @Autowired
    public InformeClinicoService(InformeClinicoRepository informeClinicoRepository) {
        this.informeClinicoRepository = informeClinicoRepository;
    }

    /**
     * Obtiene un {@link InformeClinico} de la base de datos por su identificador único.
     * Utiliza el método {@code findById} del repositorio, que devuelve un {@link Optional}
     * para manejar el caso en que el informe clínico no sea encontrado.
     *
     * @param id El identificador único del informe clínico a buscar.
     * @return Un {@link Optional} que contiene el {@link InformeClinico} si se encuentra,
     * o un {@link Optional} vacío en caso contrario.
     */
    public Optional<InformeClinico> getInformeClinico(Long id) {
        return informeClinicoRepository.findById(id);
    }

    /**
     * Obtiene una lista con todos los {@link InformeClinico} almacenados en la base de datos.
     * Utiliza el método {@code findAll} del repositorio.
     *
     * @return Una {@link List} que contiene todos los informes clínicos encontrados.
     * Si no hay informes clínicos, la lista estará vacía.
     */
    public List<InformeClinico> getAllInformesClinicos() {
        return informeClinicoRepository.findAll();
    }

    /**
     * Guarda un nuevo {@link InformeClinico} en la base de datos.
     * Utiliza el método {@code save} del repositorio.
     *
     * @param informeClinico El objeto {@link InformeClinico} a guardar.
     * @return El objeto {@link InformeClinico} guardado, que puede incluir
     * identificadores generados por la base de datos.
     */
    public InformeClinico createInformeClinico(InformeClinico informeClinico) {
        return informeClinicoRepository.save(informeClinico);
    }

    /**
     * Actualiza la información de un {@link InformeClinico} existente en la base de datos.
     * Primero, busca el informe clínico por su ID. Si se encuentra, actualiza sus campos
     * con la información proporcionada en el {@code informeClinicoActualizado} y luego
     * guarda los cambios utilizando el método {@code save} del repositorio.
     * Si el informe clínico no se encuentra, lanza una {@link RuntimeException}.
     *
     * @param id                        El identificador único del informe clínico a actualizar.
     * @param informeClinicoActualizado El objeto {@link InformeClinico} con la información actualizada.
     * @return El objeto {@link InformeClinico} actualizado y guardado en la base de datos.
     * @throws RuntimeException Si no se encuentra un informe clínico con el ID proporcionado.
     */
    public InformeClinico updateInformeClinico(Long id, InformeClinico informeClinicoActualizado) {
        Optional<InformeClinico> informeClinicoExistente = informeClinicoRepository.findById(id);

        if (informeClinicoExistente.isPresent()) {
            InformeClinico informeClinico = informeClinicoExistente.get();

            // Actualizar los campos
            informeClinico.setPersona(informeClinicoActualizado.getPersona());
            informeClinico.setFecha(informeClinicoActualizado.getFecha());
            informeClinico.setTipoInforme(informeClinicoActualizado.getTipoInforme());
            informeClinico.setProfesional(informeClinicoActualizado.getProfesional());
            informeClinico.setUrlPdf(informeClinicoActualizado.getUrlPdf());

            return informeClinicoRepository.save(informeClinico);
        } else {
            throw new RuntimeException("InformeClinico con ID " + id + " no encontrado.");
        }
    }

    /**
     * Elimina un {@link InformeClinico} de la base de datos por su identificador único.
     * Primero, verifica si el informe clínico existe. Si existe, utiliza el método
     * {@code deleteById} del repositorio para eliminarlo.
     * Si el informe clínico no se encuentra, lanza una {@link RuntimeException}.
     *
     * @param id El identificador único del informe clínico a eliminar.
     * @throws RuntimeException Si no se encuentra un informe clínico con el ID proporcionado.
     */
    public void deleteInformeClinico(Long id) {
        Optional<InformeClinico> informeClinicoExistente = informeClinicoRepository.findById(id);

        if (informeClinicoExistente.isPresent()) {
            informeClinicoRepository.deleteById(id);
        } else {
            throw new RuntimeException("InformeClinico con ID " + id + " no encontrado.");
        }
    }
}