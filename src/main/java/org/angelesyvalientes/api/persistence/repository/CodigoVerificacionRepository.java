package org.angelesyvalientes.api.persistence.repository;

import org.angelesyvalientes.api.persistence.entity.CodigoVerificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface CodigoVerificacionRepository extends JpaRepository<CodigoVerificacion, Long> {
    Optional<CodigoVerificacion> findByCdUsuarioAndCodigoAndFechaExpiracionAfter(String cdUsuario, String codigo, LocalDateTime now);
    Optional<CodigoVerificacion> findByCdUsuario(String cdUsuario);
}