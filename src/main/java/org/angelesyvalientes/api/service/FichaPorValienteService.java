package org.angelesyvalientes.api.service;

import org.angelesyvalientes.api.dto.FichaValienteDTO;
import org.angelesyvalientes.api.dto.MesProgramaDTO;
import org.angelesyvalientes.api.dto.ProgramaMinimizadoDTO;
import org.angelesyvalientes.api.dto.ValienteConFichasDTO;
import org.angelesyvalientes.api.persistence.entity.Ficha;
import org.angelesyvalientes.api.persistence.entity.FichaPorValiente;
import org.angelesyvalientes.api.persistence.entity.Programa;
import org.angelesyvalientes.api.persistence.repository.FichaPorValienteRepository;
import org.angelesyvalientes.api.persistence.repository.FichaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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

    @Transactional(readOnly = true)
    public List<MesProgramaDTO> getEstadisticasMensuales() {
        // Obtener todas las fichas con fecha de finalización
        List<FichaPorValiente> fichas = fichaPorValienteRepository.findAllByFechaFinalizacionNotNull();
        
        // Agrupar por mes y programa
        Map<String, Map<String, Integer>> stats = new HashMap<>();
        
        fichas.forEach(ficha -> {
            LocalDate fecha = ficha.getFechaFinalizacion();
            String mes = getMesAbreviado(fecha.getMonthValue());
            
            // Obtener el nombre del programa
            String programa = fichaRepository.findById(ficha.getIdFicha())
                    .map(Ficha::getPrograma)
                    .map(Programa::getNombre)
                    .orElse("Desconocido");
            
            // Inicializar el mes si no existe
            stats.putIfAbsent(mes, new HashMap<>());
            
            // Incrementar el contador para el programa
            stats.get(mes).merge(programa, 1, Integer::sum);
        });
        
        // Convertir a la estructura deseada
        return stats.entrySet().stream()
                .map(entry -> new MesProgramaDTO(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(MesProgramaDTO::mes))
                .collect(Collectors.toList());
    }

    private String getMesAbreviado(int mes) {
        String[] meses = {"Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"};
        return meses[mes - 1];
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
    public List<ProgramaMinimizadoDTO> obtenerProgramasPorValiente(int idValiente) {
        return fichaPorValienteRepository.findDistinctProgramaMinimizadoByValienteId(idValiente);
    }

    @Transactional
    public List<FichaValienteDTO> obtenerFichasPorProgramaYPersona(int idPrograma, int idPersona) {
        return fichaPorValienteRepository.findFichasAndValientesByProgramaAndPersona(idPrograma, idPersona);
    }

    @Transactional(readOnly = true)
    public List<ValienteConFichasDTO> obtenerValientesConFichasFinalizadasPorPrograma(int idPrograma) {
        List<ValienteConFichasDTO> valientes = fichaPorValienteRepository.findValientesConFichasFinalizadasPorPrograma(idPrograma);
        
        // For each ValienteConFichasDTO, populate the fichasFinalizadas list
        for (ValienteConFichasDTO valiente : valientes) {
            List<FichaValienteDTO> fichas = fichaPorValienteRepository.findFichasAndValientesByProgramaAndPersona(
                idPrograma, 
                valiente.getIdValiente()
            );
            valiente.setFichasFinalizadas(fichas);
        }
        
        return valientes;
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