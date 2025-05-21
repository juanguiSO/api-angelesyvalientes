package org.angelesyvalientes.api.persistence.repository;

import org.angelesyvalientes.api.dto.FichaValienteDTO;
import org.angelesyvalientes.api.dto.ProgramaMinimizadoDTO;
import org.angelesyvalientes.api.persistence.entity.Ficha;
import org.angelesyvalientes.api.persistence.entity.FichaPorValiente;
import org.angelesyvalientes.api.persistence.entity.FichaPorValienteId;
import org.angelesyvalientes.api.persistence.entity.Programa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FichaPorValienteRepository extends JpaRepository<FichaPorValiente, FichaPorValienteId> {

    // Métodos personalizados si son necesarios
    List<FichaPorValiente> findByIdValiente(int idValiente);

    List<FichaPorValiente> findByIdFicha(int idFicha);

    Optional<FichaPorValiente> findByIdFichaAndIdValiente(int idFicha, int idValiente);



    @Query("SELECT DISTINCT new org.angelesyvalientes.api.dto.ProgramaMinimizadoDTO(p.id, p.nombre, p.matriculado) FROM Programa p " +
            "JOIN Ficha f ON p.id = f.programa.id " +
            "JOIN FichaPorValiente fxv ON f.id = fxv.idFicha " +
            "WHERE fxv.idValiente = :idPersona")
    List<ProgramaMinimizadoDTO> findDistinctProgramaMinimizadoByValienteId(@Param("idPersona") int idPersona);

    @Query("SELECT new org.angelesyvalientes.api.dto.FichaValienteDTO(f.id, f.nombre, f.urlRecurso, f.codigo, fxv.fechaFinalizacion) " +
            "FROM Ficha f " +
            "JOIN FichaPorValiente fxv ON f.id = fxv.idFicha " +
            "WHERE f.programa.id = :idPrograma AND fxv.idValiente = :idPersona")
    List<FichaValienteDTO> findFichasAndValientesByProgramaAndPersona(
            @Param("idPrograma") int idPrograma,
            @Param("idPersona") int idPersona
    );
}