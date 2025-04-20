package org.angelesyvalientes.api.service;

import org.angelesyvalientes.api.persistence.entity.GrupoEtnico;
import org.angelesyvalientes.api.persistence.repository.GrupoEtnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GrupoEtnicoService {

    private final GrupoEtnicoRepository grupoEtnicoRepository;

    @Autowired
    public GrupoEtnicoService(GrupoEtnicoRepository grupoEtnicoRepository) {
        this.grupoEtnicoRepository = grupoEtnicoRepository;
    }

    public List<GrupoEtnico> getAllGruposEtnicos() {
        return grupoEtnicoRepository.findAll();
    }

    public Optional<GrupoEtnico> getGrupoEtnicoById(Integer id) {
        return grupoEtnicoRepository.findById(id);
    }

    public GrupoEtnico saveGrupoEtnico(GrupoEtnico grupoEtnico) {
        return grupoEtnicoRepository.save(grupoEtnico);
    }

    public void deleteGrupoEtnico(Integer id) {
        grupoEtnicoRepository.deleteById(id);
    }
}