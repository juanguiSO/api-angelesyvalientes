package org.angelesyvalientes.api.service;

import org.angelesyvalientes.api.persistence.entity.Familiar;
import org.angelesyvalientes.api.persistence.entity.TipoIdentificacion;
import org.angelesyvalientes.api.persistence.entity.Vivienda;
import org.angelesyvalientes.api.persistence.repository.FamiliaresRepository;
import org.angelesyvalientes.api.persistence.repository.TipoIdentificacionRepository;
import org.angelesyvalientes.api.persistence.repository.ViviendaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que gestiona las operaciones relacionadas con la entidad {@link Familiar}.
 * Proporciona métodos para obtener, crear, actualizar y eliminar familiares
 * de la base de datos a través del {@link FamiliaresRepository}.
 */
@Service
public class FamiliaresService {

    private final FamiliaresRepository familiaresRepository;
    private final TipoIdentificacionRepository tipoIdentificacionRepository;
    private final ViviendaRepository viviendaRepository;
    private static final Logger logger = LoggerFactory.getLogger(FamiliaresService.class);

    @Autowired
    public FamiliaresService(FamiliaresRepository familiaresRepository, TipoIdentificacionRepository tipoIdentificacionRepository, ViviendaRepository viviendaRepository) {
        this.familiaresRepository = familiaresRepository;
        this.tipoIdentificacionRepository = tipoIdentificacionRepository;
        this.viviendaRepository = viviendaRepository;
    }

    public List<Familiar> obtenerTodosLosFamiliares() {
        return familiaresRepository.findAll();
    }

    public Optional<Familiar> obtenerFamiliarPorId(int id) {
        return familiaresRepository.findById(id);
    }

    @Transactional
    public Familiar crearFamiliar(Familiar familiar) {
        logger.info("Iniciando proceso de creación de un nuevo Familiar.");
        logger.debug("Información del Familiar a guardar: {}", familiar);
        System.out.println("Nombre a guardar: " + familiar.getNombre());
        System.out.println("Número de identificación a guardar: " + familiar.getNumeroIdentificacion());
        System.out.println("ID de vivienda recibido: " + familiar.getIdVivienda());
        System.out.println("ID de tipo de identificación recibido: " + familiar.getTipoIdentificacionId());

        Optional<TipoIdentificacion> tipoIdentificacionOptional = tipoIdentificacionRepository.findById(familiar.getTipoIdentificacionId());
        Optional<Vivienda> viviendaOptional = viviendaRepository.findById(familiar.getIdVivienda());

        if (tipoIdentificacionOptional.isEmpty()) {
            logger.error("No se encontró TipoIdentificacion con ID: {}", familiar.getTipoIdentificacionId());
            throw new IllegalArgumentException("Tipo de identificación inválido.");
        }

        if (viviendaOptional.isEmpty()) {
            logger.error("No se encontró Vivienda con ID: {}", familiar.getIdVivienda());
            throw new IllegalArgumentException("Vivienda inválida.");
        }

        familiar.setTipoIdentificacion(tipoIdentificacionOptional.get());
        familiar.setVivienda(viviendaOptional.get());

        Familiar familiarGuardado = familiaresRepository.save(familiar);
        logger.info("Familiar creado exitosamente con ID: {}", familiarGuardado.getIdFamiliares());
        logger.debug("Información del Familiar guardado: {}", familiarGuardado);
        return familiarGuardado;
    }

    public Familiar actualizarFamiliar(int id, Familiar familiarActualizado) {
        Optional<Familiar> familiarExistente = familiaresRepository.findById(id);
        if (familiarExistente.isPresent()) {
            familiarActualizado.setIdFamiliar(id);
            return familiaresRepository.save(familiarActualizado);
        } else {
            return null;
        }
    }

    public void eliminarFamiliar(int id) {
        familiaresRepository.deleteById(id);
    }

    /**
     * Obtiene todos los familiares asociados a una vivienda específica.
     * 
     * @param idVivienda El ID de la vivienda para la cual se desean obtener los familiares
     * @return Lista de familiares asociados a la vivienda
     */
    public List<Familiar> obtenerFamiliaresPorIdVivienda(Integer idVivienda) {
        return familiaresRepository.findByIdVivienda(idVivienda);
    }
}