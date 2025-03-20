package org.angelesyvalientes.api.persistence.repository;



import org.angelesyvalientes.api.persistence.entity.TipoIdentificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoIdentificacionRepository  extends JpaRepository<TipoIdentificacion, Long> {

}


