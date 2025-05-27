package org.angelesyvalientes.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.angelesyvalientes.api.dto.FichaValienteDTO;
import org.angelesyvalientes.api.dto.MesProgramaDTO;
import org.angelesyvalientes.api.dto.ProgramaMinimizadoDTO;
import org.angelesyvalientes.api.dto.ValienteConFichasDTO;
import org.angelesyvalientes.api.persistence.entity.FichaPorValiente;
import org.angelesyvalientes.api.service.FichaPorValienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Fichas Por Valiente", description = "Controlador para gestionar la relación entre Fichas y Valientes")
@RestController
@RequestMapping("/api/fichas-por-valiente")
public class FichaPorValienteController {

    private final FichaPorValienteService fichaPorValienteService;

    public FichaPorValienteController(FichaPorValienteService fichaPorValienteService) {
        this.fichaPorValienteService = fichaPorValienteService;
    }

    @Operation(summary = "Obtener todas las relaciones Ficha-Valiente")
    @GetMapping
    public ResponseEntity<List<FichaPorValiente>> getAll() {
        List<FichaPorValiente> fichas = fichaPorValienteService.findAll();
        return new ResponseEntity<>(fichas, HttpStatus.OK);
    }

    @Operation(summary = "Obtener una relación Ficha-Valiente por ID de Ficha e ID de Valiente")
    @GetMapping("/{idFicha}/{idValiente}")
    public ResponseEntity<FichaPorValiente> getById(
            @PathVariable int idFicha,
            @PathVariable int idValiente) {
        return fichaPorValienteService.findById(idFicha, idValiente)
                .map(ficha -> new ResponseEntity<>(ficha, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Obtener las relaciones Ficha-Valiente de un Valiente")
    @GetMapping("/valiente/{idValiente}")
    public ResponseEntity<List<FichaPorValiente>> getByValiente(@PathVariable int idValiente) {
        List<FichaPorValiente> fichas = fichaPorValienteService.findByValiente(idValiente);
        return new ResponseEntity<>(fichas, HttpStatus.OK);
    }

    @Operation(summary = "Obtener las relaciones Ficha-Valiente de una Ficha")
    @GetMapping("/ficha/{idFicha}")
    public ResponseEntity<List<FichaPorValiente>> getByFicha(@PathVariable int idFicha) {
        List<FichaPorValiente> fichas = fichaPorValienteService.findByFicha(idFicha);
        return new ResponseEntity<>(fichas, HttpStatus.OK);
    }

    @Operation(summary = "Obtener los Programas de un Valiente")
    @GetMapping("/{idValiente}/programas")
    public ResponseEntity<List<ProgramaMinimizadoDTO>> obtenerProgramasPorValiente(@PathVariable int idValiente) {
        List<ProgramaMinimizadoDTO> programas = fichaPorValienteService.obtenerProgramasPorValiente(idValiente);
        return ResponseEntity.ok(programas);
    }

    @Operation(summary = "Obtener las Fichas de un Valiente en un Programa")
    @GetMapping("/programas/{idPrograma}/personas/{idPersona}/fichas")
    public ResponseEntity<List<FichaValienteDTO>> obtenerFichasPorProgramaYPersona(
            @PathVariable int idPrograma,
            @PathVariable int idPersona
    ) {
        List<FichaValienteDTO> fichas = fichaPorValienteService.obtenerFichasPorProgramaYPersona(idPrograma, idPersona);
        return ResponseEntity.ok(fichas);
    }

    @Operation(summary = "Obtener Valientes con Fichas Finalizadas por Programa")
    @GetMapping("/valientes/programa/{idPrograma}")
    public ResponseEntity<List<ValienteConFichasDTO>> obtenerValientesConFichasFinalizadasPorPrograma(
            @PathVariable int idPrograma
    ) {
        List<ValienteConFichasDTO> valientes = fichaPorValienteService.obtenerValientesConFichasFinalizadasPorPrograma(idPrograma);
        return ResponseEntity.ok(valientes);
    }

    @Operation(summary = "Crear una nueva relación Ficha-Valiente")
    @PostMapping("/crear")
    public ResponseEntity<FichaPorValiente> crearFichaPorValiente(
            @RequestParam int idPersona,
            @RequestParam int idPrograma
    ) {
        FichaPorValiente nuevaRelacion = fichaPorValienteService.crearFichaPorValiente(idPersona, idPrograma);
        if (nuevaRelacion != null) {
            return new ResponseEntity<>(nuevaRelacion, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Actualizar la fecha de finalización de una Ficha-Valiente y crear la siguiente")
    @PutMapping("/{idFicha}/{idValiente}")
    public ResponseEntity<FichaPorValiente> updateAndCreateNext(
            @PathVariable int idFicha,
            @PathVariable int idValiente,
            @RequestParam LocalDate fechaFinalizacion) {
        FichaPorValiente updated = fichaPorValienteService.updateAndCreateNext(idFicha, idValiente, fechaFinalizacion);
        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Obtener estadísticas mensuales por programa")
    @GetMapping("/estadisticas-mensuales")
    public ResponseEntity<List<MesProgramaDTO>> getEstadisticasMensuales() {
        return new ResponseEntity<>(fichaPorValienteService.getEstadisticasMensuales(), HttpStatus.OK);
    }
}