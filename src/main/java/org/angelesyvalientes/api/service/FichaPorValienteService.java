package org.angelesyvalientes.api.service;

import org.angelesyvalientes.api.dto.FichaValienteDTO;
import org.angelesyvalientes.api.dto.ProgramaMinimizadoDTO;
import org.angelesyvalientes.api.persistence.entity.Ficha;
import org.angelesyvalientes.api.persistence.entity.FichaPorValiente;
import org.angelesyvalientes.api.persistence.entity.Programa;
import org.angelesyvalientes.api.persistence.repository.FichaPorValienteRepository;
import org.angelesyvalientes.api.persistence.repository.FichaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FichaPorValienteService {

    private final FichaPorValienteRepository fichaPorValienteRepository;
    private final FichaRepository fichaRepository;

    public FichaPorValienteService(FichaPorValienteRepository fichaPorValienteRepository, FichaRepository fichaRepository) {
        this.fichaPorValienteRepository = fichaPorValienteRepository;
        this.fichaRepository =fichaRepository;
    }

    @Transactional(readOnly = true)
    public List<FichaPorValiente> findAll() {
        return fichaPorValienteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<FichaPorValiente> findById(int idFicha, int idValiente) {
        return fichaPorValienteRepository.findByIdFichaAndIdValiente(idFicha, idValiente);
    }

    @Transactional(readOnly = true)
    public List<FichaPorValiente> findByValiente(int idValiente) {
        return fichaPorValienteRepository.findByIdValiente(idValiente);
    }

    @Transactional(readOnly = true)
    public List<FichaPorValiente> findByFicha(int idFicha) {
        return fichaPorValienteRepository.findByIdFicha(idFicha);
    }

    @Transactional
    public FichaPorValiente crearFichaPorValiente(int idPersona, int idPrograma) {
        // Buscar la ficha con cod_ficha = 1 para el idPrograma dado
        Optional<Ficha> fichaOptional = fichaRepository.findByCodigoAndProgramaId(1, idPrograma);

        if (fichaOptional.isPresent()) {
            Ficha ficha = fichaOptional.get();
            FichaPorValiente nuevaRelacion = new FichaPorValiente();
            nuevaRelacion.setIdValiente(idPersona);
            nuevaRelacion.setIdFicha(ficha.getId()); // Usar el ID de la ficha encontrada
            nuevaRelacion.setFechaFinalizacion(null);
            return fichaPorValienteRepository.save(nuevaRelacion);
        } else {

            return null;
        }
    }




    @Transactional
    public FichaPorValiente update(int idFicha, int idValiente, LocalDate fechaFinalizacion) {
        Optional<FichaPorValiente> existing = fichaPorValienteRepository.findByIdFichaAndIdValiente(idFicha, idValiente);
        if (existing.isPresent()) {
            FichaPorValiente toUpdate = existing.get();
            toUpdate.setFechaFinalizacion(fechaFinalizacion);
            return fichaPorValienteRepository.save(toUpdate);
        }
        return null;
    }
    @Transactional
    public List<ProgramaMinimizadoDTO> obtenerProgramasPorValiente(int idValiente) {
        return fichaPorValienteRepository.findDistinctProgramaMinimizadoByValienteId(idValiente);
    }

    @Transactional
    public List<FichaValienteDTO> obtenerFichasPorProgramaYPersona(int idPrograma, int idPersona) {
        return fichaPorValienteRepository.findFichasAndValientesByProgramaAndPersona(idPrograma, idPersona);
    }


    @Transactional
    public FichaPorValiente updateAndCreateNext(int idFichaActual, int idValiente, LocalDate fechaFinalizacion) {
        // 1. Actualizar la fecha de finalización de la ficha actual
        Optional<FichaPorValiente> existing = fichaPorValienteRepository.findByIdFichaAndIdValiente(idFichaActual, idValiente);
        FichaPorValiente updated = null;
        if (existing.isPresent()) {
            FichaPorValiente toUpdate = existing.get();
            toUpdate.setFechaFinalizacion(fechaFinalizacion);
            updated = fichaPorValienteRepository.save(toUpdate);

            // 2. Buscar la ficha actual para obtener el idPrograma y codFicha
            Optional<Ficha> fichaActualOptional = fichaRepository.findById(idFichaActual);
            if (fichaActualOptional.isPresent()) {
                Ficha fichaActual = fichaActualOptional.get();
                int idPrograma = fichaActual.getPrograma().getId();
                int siguienteCodFicha = fichaActual.getCodigo() + 1;

                // 3. Buscar la siguiente ficha por codFicha e idPrograma
                Optional<Ficha> siguienteFichaOptional = fichaRepository.findByCodigoAndProgramaId(siguienteCodFicha, idPrograma);

                // 4. Si existe la siguiente ficha, crear la nueva relación FichaPorValiente
                if (siguienteFichaOptional.isPresent()) {
                    Ficha siguienteFicha = siguienteFichaOptional.get();
                    FichaPorValiente nuevaRelacion = new FichaPorValiente();
                    nuevaRelacion.setIdValiente(idValiente);
                    nuevaRelacion.setIdFicha(siguienteFicha.getId());
                    nuevaRelacion.setFechaFinalizacion(null);
                    fichaPorValienteRepository.save(nuevaRelacion); // Guardar la nueva relación
                }
            }
        }
        return updated; // Devolvemos la ficha actualizada (la primera)
    }
}