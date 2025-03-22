package org.angelesyvalientes.api.persistence.repository;

import org.angelesyvalientes.api.persistence.entity.InformeClinico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InformeClinicoRepository  extends JpaRepository<InformeClinico, Long> {
}
