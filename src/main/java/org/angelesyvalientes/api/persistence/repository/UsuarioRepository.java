package org.angelesyvalientes.api.persistence.repository;

import org.angelesyvalientes.api.persistence.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository <Usuario, Long> {
    // Busca el usuario basado en el correo de Persona
    @Query("SELECT u FROM Usuario u WHERE u.persona.txCorreo = :correo")
    Optional<Usuario> findByPersonaCorreo(@Param("correo") String correo);

    // Busca el usuario basado en el Coidgo del Usuario
    @Query("SELECT u FROM Usuario u WHERE u.cdUsuario = :usuario")
    Optional<Usuario> findByCodigo(@Param("usuario") String usuario);

    boolean existsByCdUsuario(String cdUsuario);
}
