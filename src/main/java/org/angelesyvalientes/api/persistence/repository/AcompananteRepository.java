package org.angelesyvalientes.api.persistence.repository;


import org.angelesyvalientes.api.persistence.entity.Acompanante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcompananteRepository extends JpaRepository<Acompanante, Long>  {

}

