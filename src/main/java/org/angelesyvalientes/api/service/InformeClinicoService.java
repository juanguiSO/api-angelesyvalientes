package org.angelesyvalientes.api.service;

import org.angelesyvalientes.api.persistence.entity.InformeClinico;
import org.angelesyvalientes.api.persistence.repository.InformeClinicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InformeClinicoService {

    private final InformeClinicoRepository informeClinicoRepository;

    @Autowired
    public InformeClinicoService(InformeClinicoRepository informeClinicoRepository) {
        this.informeClinicoRepository = informeClinicoRepository;
    }

    // Obtener un InformeClinico por su ID
    public Optional<InformeClinico> getInformeClinico(Long id) {
        return informeClinicoRepository.findById(id);
    }

    // Obtener todos los InformesClinicos
    public List<InformeClinico> getAllInformesClinicos() {
        return informeClinicoRepository.findAll();
    }

    // Guardar un nuevo InformeClinico
    public InformeClinico createInformeClinico(InformeClinico informeClinico) {
        return informeClinicoRepository.save(informeClinico);
    }

    // Actualizar un InformeClinico existente
    public InformeClinico updateInformeClinico(Long id, InformeClinico informeClinicoActualizado) {
        Optional<InformeClinico> informeClinicoExistente = informeClinicoRepository.findById(id);

        if (informeClinicoExistente.isPresent()) {
            InformeClinico informeClinico = informeClinicoExistente.get();

            // Actualizar los campos
            informeClinico.setPersona(informeClinicoActualizado.getPersona());
            informeClinico.setFecha(informeClinicoActualizado.getFecha());
            informeClinico.setTipoInforme(informeClinicoActualizado.getTipoInforme());
            informeClinico.setProfesional(informeClinicoActualizado.getProfesional());
            informeClinico.setUrlPdf(informeClinicoActualizado.getUrlPdf());

            return informeClinicoRepository.save(informeClinico);
        } else {
            throw new RuntimeException("InformeClinico con ID " + id + " no encontrado.");
        }
    }

    // Eliminar un InformeClinico por su ID
    public void deleteInformeClinico(Long id) {
        Optional<InformeClinico> informeClinicoExistente = informeClinicoRepository.findById(id);

        if (informeClinicoExistente.isPresent()) {
            informeClinicoRepository.deleteById(id);
        } else {
            throw new RuntimeException("InformeClinico con ID " + id + " no encontrado.");
        }
    }
}