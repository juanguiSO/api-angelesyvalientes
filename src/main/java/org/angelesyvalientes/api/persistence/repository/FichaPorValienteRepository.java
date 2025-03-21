package org.angelesyvalientes.api.persistence.repository;

import org.angelesyvalientes.api.persistence.entity.FichaPorValiente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FichaPorValienteRepository extends JpaRepository<FichaPorValiente, Long> {

}
