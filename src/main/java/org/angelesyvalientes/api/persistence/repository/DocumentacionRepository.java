package org.angelesyvalientes.api.persistence.repository;

import org.angelesyvalientes.api.persistence.entity.Documentacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentacionRepository extends JpaRepository<Documentacion, Long> {

}
