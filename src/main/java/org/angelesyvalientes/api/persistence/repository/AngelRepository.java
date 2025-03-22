package org.angelesyvalientes.api.persistence.repository;


import org.angelesyvalientes.api.persistence.entity.Angel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AngelRepository extends JpaRepository<Angel, Long> {

}