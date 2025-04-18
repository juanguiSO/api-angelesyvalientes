package org.angelesyvalientes.api.persistence.repository;

import org.angelesyvalientes.api.persistence.entity.ClasificacionValiente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClasificacionValienteRepository extends JpaRepository<ClasificacionValiente, Long> {
    // JpaRepository ya proporciona métodos básicos.
    // Añade métodos personalizados si necesitas consultas específicas para ClasificacionValiente.
}