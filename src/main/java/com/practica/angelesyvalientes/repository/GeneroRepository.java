package com.practica.angelesyvalientes.repository;

import com.practica.angelesyvalientes.entity.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneroRepository  extends JpaRepository<Genero, Long> {


}
