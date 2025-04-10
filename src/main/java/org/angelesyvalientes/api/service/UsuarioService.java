package org.angelesyvalientes.api.service;

import org.angelesyvalientes.api.persistence.entity.Usuario;
import org.angelesyvalientes.api.persistence.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // Importa el PasswordEncoder
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que gestiona las operaciones relacionadas con la entidad {@link Usuario}.
 * Proporciona métodos para obtener, crear, actualizar y eliminar usuarios
 * de la base de datos a través del {@link UsuarioRepository}.
 */
@Service
public class UsuarioService {

    /**
     * Repositorio para acceder a los datos de la entidad {@link Usuario} en la base de datos.
     */
    @Autowired
    UsuarioRepository usuarioRepository;

    /**
     * Codificador de contraseñas para encriptar la nueva contraseña.
     */
    @Autowired
    PasswordEncoder passwordEncoder; // Necesitas inyectar el PasswordEncoder

    /**
     * Obtiene un {@link Usuario} de la base de datos por su identificador único.
     * Utiliza el método {@code findById} del repositorio, que devuelve un {@link Optional}
     * para manejar el caso en que el usuario no sea encontrado.
     *
     * @param id El identificador único del usuario a buscar.
     * @return Un {@link Optional} que contiene el {@link Usuario} si se encuentra,
     * o un {@link Optional} vacío en caso contrario.
     */
    public Optional<Usuario> getUsuario(Long id) {
        return usuarioRepository.findById(id);
    }

    /**
     * Obtiene un {@link Usuario} de la base de datos por su código de usuario.
     * Utiliza el método {@code findByCdUsuario} del repositorio, que devuelve un {@link Optional}
     * para manejar el caso en que el usuario no sea encontrado.
     *
     * @param cdUsuario El código único del usuario a buscar.
     * @return Un {@link Optional} que contiene el {@link Usuario} si se encuentra,
     * o un {@link Optional} vacío en caso contrario.
     */
    public Optional<Usuario> getUsuarioByCdUsuario(String cdUsuario) {
        return usuarioRepository.findByCdUsuario(cdUsuario);
    }

    /**
     * Obtiene una lista con todos los {@link Usuario} almacenados en la base de datos.
     * Utiliza el método {@code findAll} del repositorio.
     *
     * @return Una {@link List} que contiene todos los usuarios encontrados.
     * Si no hay usuarios, la lista estará vacía.
     */
    public List<Usuario> getUsuarios() {
        return usuarioRepository.findAll();
    }

    /**
     * Guarda un nuevo {@link Usuario} en la base de datos.
     * Utiliza el método {@code save} del repositorio.
     *
     * @param usuario El objeto {@link Usuario} a guardar.
     * @return El objeto {@link Usuario} guardado, que puede incluir
     * identificadores generados por la base de datos.
     */
    public Usuario saveUsuario(Usuario usuario) {
        usuario.setTxContrasena(passwordEncoder.encode(usuario.getTxContrasena())); // Encriptar la contraseña al guardar
        return usuarioRepository.save(usuario);
    }

    /**
     * Actualiza la información de un {@link Usuario} existente en la base de datos.
     * Primero, busca el usuario por su ID. Si se encuentra, actualiza sus campos
     * con la información proporcionada en el {@code usuarioActualizado} y luego
     * guarda los cambios utilizando el método {@code save} del repositorio.
     * Si el usuario no se encuentra, lanza una {@link RuntimeException}.
     *
     * @param id              El identificador único del usuario a actualizar.
     * @param usuarioActualizado El objeto {@link Usuario} con la información actualizada.
     * @return El objeto {@link Usuario} actualizado y guardado en la base de datos.
     * @throws RuntimeException Si no se encuentra un usuario con el ID proporcionado.
     */
    public Usuario updateUsuario(Long id, Usuario usuarioActualizado) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);

        if (usuarioExistente.isPresent()) {
            Usuario usuario = usuarioExistente.get();

            // Actualizar los campos
            usuario.setPersona(usuarioActualizado.getPersona());
            usuario.setTxContrasena(usuarioActualizado.getTxContrasena());
            usuario.setFeCreacion(usuarioActualizado.getFeCreacion());

            return usuarioRepository.save(usuario);
        } else {
            throw new RuntimeException("Usuario con ID " + id + " no encontrado.");
        }
    }

    /**
     * Actualiza la contraseña de un {@link Usuario} existente en la base de datos
     * buscando por su código de usuario.
     *
     * @param cdUsuario     El código único del usuario cuya contraseña se va a actualizar.
     * @param nuevaContrasena La nueva contraseña sin encriptar.
     * @return `true` si la contraseña se actualizó correctamente, `false` si el usuario no se encontró.
     */
    public boolean updatePassword(String cdUsuario, String nuevaContrasena) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findByCdUsuario(cdUsuario);

        if (usuarioExistente.isPresent()) {
            Usuario usuario = usuarioExistente.get();
            usuario.setTxContrasena(passwordEncoder.encode(nuevaContrasena)); // Encriptar la nueva contraseña
            usuarioRepository.save(usuario);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Elimina un {@link Usuario} de la base de datos por su identificador único.
     * Primero, verifica si el usuario existe. Si existe, utiliza el método
     * {@code deleteById} del repositorio para eliminarlo.
     * Si el usuario no se encuentra, lanza una {@link RuntimeException}.
     *
     * @param id El identificador único del usuario a eliminar.
     * @throws RuntimeException Si no se encuentra un usuario con el ID proporcionado.
     */
    public void deleteUsuario(Long id) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);

        if (usuarioExistente.isPresent()) {
            usuarioRepository.deleteById(id); // Elimina el registro de la base de datos
        } else {
            throw new RuntimeException("Usuario con ID " + id + " no encontrado.");
        }
    }
}