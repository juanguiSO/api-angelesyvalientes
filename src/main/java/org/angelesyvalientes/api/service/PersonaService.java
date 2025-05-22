package org.angelesyvalientes.api.service;

import org.angelesyvalientes.api.persistence.entity.Persona;
import org.angelesyvalientes.api.persistence.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que gestiona las operaciones relacionadas con la entidad {@link Persona}.
 * Proporciona métodos para obtener, crear, actualizar y eliminar personas
 * de la base de datos a través del {@link PersonaRepository}. También incluye
 * un método específico para actualizar la URL de la foto de perfil de una persona.
 */
@Service
public class PersonaService {

    private final PersonaRepository personaRepository;

    /**
     * Constructor de la clase {@code PersonaService}.
     * Recibe una instancia de {@link PersonaRepository} a través de la inyección de dependencias
     * para interactuar con la capa de persistencia.
     *
     * @param personaRepository El repositorio para acceder a los datos de las personas.
     */
    @Autowired
    public PersonaService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    /**
     * Obtiene una {@link Persona} de la base de datos por su identificador único.
     * Utiliza el método {@code findById} del repositorio, que devuelve un {@link Optional}
     * para manejar el caso en que la persona no sea encontrada.
     *
     * @param id El identificador único de la persona a buscar.
     * @return Un {@link Optional} que contiene la {@link Persona} si se encuentra,
     * o un {@link Optional} vacío en caso contrario.
     */
    public Optional<Persona> getPersona(Integer id) {
        return personaRepository.findById(id);
    }

    /**
     * Obtiene una lista con todas las {@link Persona} almacenadas en la base de datos.
     * Utiliza el método {@code findAll} del repositorio.
     *
     * @return Una {@link List} que contiene todas las personas encontradas.
     * Si no hay personas, la lista estará vacía.
     */
    public List<Persona> getAllPersonas() {
        return personaRepository.findAll();
    }

    /**
     * Guarda una nueva {@link Persona} en la base de datos.
     * Utiliza el método {@code save} del repositorio.
     *
     * @param persona El objeto {@link Persona} a guardar.
     * @return El objeto {@link Persona} guardado, que puede incluir
     * identificadores generados por la base de datos.
     */
    public Persona createPersona(Persona persona) {
        return personaRepository.save(persona);
    }

    /**
     * Actualiza la información de una {@link Persona} existente en la base de datos.
     * Primero, busca la persona por su ID. Si se encuentra, actualiza sus campos
     * con la información proporcionada en el {@code personaActualizada} y luego
     * guarda los cambios utilizando el método {@code save} del repositorio.
     * Si la persona no se encuentra, lanza una {@link RuntimeException}.
     *
     * @param id                  El identificador único de la persona a actualizar.
     * @param personaActualizada El objeto {@link Persona} con la información actualizada.
     * @return El objeto {@link Persona} actualizado y guardado en la base de datos.
     * @throws RuntimeException Si no se encuentra una persona con el ID proporcionado.
     */
    public Persona updatePersona(Integer id, Persona personaActualizada) {
        Optional<Persona> personaExistente = personaRepository.findById(id);

        if (personaExistente.isPresent()) {
            Persona persona = personaExistente.get();

            // Actualizar los campos
            persona.setGenero(personaActualizada.getGenero());
            persona.setTipoIdentificacion(personaActualizada.getTipoIdentificacion());
            persona.setTxPrimerNombre(personaActualizada.getTxPrimerNombre());
            persona.setTxSegundoNombre(personaActualizada.getTxSegundoNombre());
            persona.setTxPrimerApellido(personaActualizada.getTxPrimerApellido());
            persona.setTxSegundoApellido(personaActualizada.getTxSegundoApellido());
            persona.setTxTelefono(personaActualizada.getTxTelefono());
            persona.setTxCorreo(personaActualizada.getTxCorreo());
            persona.setTxNumeroIdentificacion(personaActualizada.getTxNumeroIdentificacion());

            return personaRepository.save(persona);
        } else {
            throw new RuntimeException("Persona con ID " + id + " no encontrada.");
        }
    }

    /**
     * Elimina una {@link Persona} de la base de datos por su identificador único.
     * Primero, verifica si la persona existe. Si existe, utiliza el método
     * {@code deleteById} del repositorio para eliminarla.
     * Si la persona no se encuentra, lanza una {@link RuntimeException}.
     *
     * @param id El identificador único de la persona a eliminar.
     * @throws RuntimeException Si no se encuentra una persona con el ID proporcionado.
     */
    public void deletePersona(Integer id) {
        Optional<Persona> personaExistente = personaRepository.findById(id);

        if (personaExistente.isPresent()) {
            personaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Persona con ID " + id + " no encontrada.");
        }
    }

    /**
     * Actualiza la URL de la foto de perfil de una {@link Persona} existente en la base de datos.
     * Primero, busca la persona por su ID. Si se encuentra, actualiza el campo {@code urlFoto}
     * con la nueva URL proporcionada y luego guarda los cambios utilizando el método {@code save}
     * del repositorio. Si la persona no se encuentra, lanza una {@link RuntimeException}.
     *
     * @param idPersona El identificador único de la persona cuya URL de foto se va a actualizar.
     * @param fileId   El id de la foto de perfil.
     * @throws RuntimeException Si no se encuentra una persona con el ID proporcionado.
     */
    public void actualizarUrlFoto(Integer idPersona, String fileId) {
        Optional<Persona> personaExistente = personaRepository.findById(idPersona);

        if (personaExistente.isPresent()) {
            Persona persona = personaExistente.get();
            persona.setUrlFoto(fileId); // Guardamos el ID del archivo
            personaRepository.save(persona);
        } else {
            throw new RuntimeException("Persona con ID " + idPersona + " no encontrada.");
        }
    }
}