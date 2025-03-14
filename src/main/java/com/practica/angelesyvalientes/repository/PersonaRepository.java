package com.practica.angelesyvalientes.repository;


import com.practica.angelesyvalientes.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository  extends JpaRepository<Persona, Long> {

}
