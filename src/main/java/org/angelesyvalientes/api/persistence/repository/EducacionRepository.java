package org.angelesyvalientes.api.persistence.repository;


import org.angelesyvalientes.api.persistence.entity.Educacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducacionRepository extends JpaRepository<Educacion, Long> {
}
