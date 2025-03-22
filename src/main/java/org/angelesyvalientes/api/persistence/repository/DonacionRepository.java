package org.angelesyvalientes.api.persistence.repository;

import org.angelesyvalientes.api.persistence.entity.Donacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonacionRepository extends JpaRepository<Donacion, Long>  {
}
