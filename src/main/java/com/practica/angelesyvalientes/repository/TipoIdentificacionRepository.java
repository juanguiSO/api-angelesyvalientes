package com.practica.angelesyvalientes.repository;



import com.practica.angelesyvalientes.entity.TipoIdentificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoIdentificacionRepository  extends JpaRepository<TipoIdentificacion, Long> {

}


