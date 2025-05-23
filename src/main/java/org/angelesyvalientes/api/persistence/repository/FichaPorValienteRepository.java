package org.angelesyvalientes.api.persistence.repository;

import org.angelesyvalientes.api.dto.FichaValienteDTO;
import org.angelesyvalientes.api.dto.ProgramaMinimizadoDTO;
import org.angelesyvalientes.api.dto.ValienteConFichasDTO;
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

    @Query("""
        SELECT DISTINCT new org.angelesyvalientes.api.dto.ValienteConFichasDTO(
            v.nmIdPersona,
            v.txPrimerNombre,
            v.txSegundoNombre,
            v.txPrimerApellido,
            v.txSegundoApellido,
            v.fechaNacimiento,
            v.txTelefono,
            v.txCorreo
        )
        FROM Valiente v
        JOIN FichaPorValiente fpv ON v.nmIdPersona = fpv.idValiente
        JOIN Ficha f ON fpv.idFicha = f.id
        WHERE f.programa.id = :idPrograma AND fpv.fechaFinalizacion IS NOT NULL
    """)
    List<ValienteConFichasDTO> findValientesConFichasFinalizadasPorPrograma(@Param("idPrograma") int idPrograma);

    @Query("""
        SELECT DISTINCT new org.angelesyvalientes.api.dto.ProgramaMinimizadoDTO(
            p.id,
            p.nombre,
            CASE
                WHEN COUNT(fxv.idFicha) > 0 THEN true
                ELSE false
            END
        )
        FROM Programa p
        LEFT JOIN Ficha f ON f.programa.id = p.id
        LEFT JOIN FichaPorValiente fxv ON fxv.idFicha = f.id
        WHERE fxv.idValiente = :idPersona OR fxv.idValiente IS NULL
        GROUP BY p.id, p.nombre
    """)
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