package org.angelesyvalientes.api.persistence.repository;

import org.angelesyvalientes.api.persistence.entity.GrupoEtnico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoEtnicoRepository extends JpaRepository<GrupoEtnico, Integer> {
    // JpaRepository ya proporciona métodos básicos como findById, findAll, save, delete, etc.
    // Puedes añadir métodos personalizados si necesitas consultas específicas para GrupoPoblacional.
}