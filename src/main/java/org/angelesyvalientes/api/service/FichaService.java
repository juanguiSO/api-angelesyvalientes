package org.angelesyvalientes.api.service;

import org.angelesyvalientes.api.persistence.entity.Ficha;
import org.angelesyvalientes.api.persistence.repository.FichaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FichaService {

    private final FichaRepository fichaRepository;

    @Autowired
    public FichaService(FichaRepository fichaRepository) {
        this.fichaRepository = fichaRepository;
    }

    public List<Ficha> obtenerTodas() {
        return fichaRepository.findAll();
    }

    public Optional<Ficha> obtenerPorId(int id) {
        return fichaRepository.findById(id);
    }

    public Ficha guardar(Ficha ficha) {
        return fichaRepository.save(ficha);
    }

    public void eliminar(int id) {
        fichaRepository.deleteById(id);
    }
}
