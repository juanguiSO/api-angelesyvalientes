package org.angelesyvalientes.api.persistence.repository;


import org.angelesyvalientes.api.persistence.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository  extends JpaRepository<Persona, Long> {

}
