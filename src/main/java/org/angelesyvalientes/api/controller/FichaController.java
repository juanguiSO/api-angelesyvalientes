package org.angelesyvalientes.api.controller;

import org.angelesyvalientes.api.persistence.entity.Ficha;
import org.angelesyvalientes.api.service.FichaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fichas")
public class FichaController {

    private final FichaService fichaService;

    @Autowired
    public FichaController(FichaService fichaService) {
        this.fichaService = fichaService;
    }

    @GetMapping
    public List<Ficha> listarFichas() {
        return fichaService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ficha> obtenerFicha(@PathVariable int id) {
        return fichaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Ficha> crearFicha(@RequestBody Ficha ficha) {
        Ficha nuevaFicha = fichaService.guardar(ficha);
        return ResponseEntity.ok(nuevaFicha);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ficha> actualizarFicha(@PathVariable int id, @RequestBody Ficha ficha) {
        return fichaService.obtenerPorId(id)
                .map(fichaExistente -> {
                    ficha.setId(id);
                    Ficha actualizada = fichaService.guardar(ficha);
                    return ResponseEntity.ok(actualizada);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFicha(@PathVariable int id) {
        if (fichaService.obtenerPorId(id).isPresent()) {
            fichaService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
