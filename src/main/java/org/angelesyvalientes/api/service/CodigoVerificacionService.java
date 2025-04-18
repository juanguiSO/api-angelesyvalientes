package org.angelesyvalientes.api.service;

import org.angelesyvalientes.api.persistence.entity.CodigoVerificacion;
import org.angelesyvalientes.api.persistence.repository.CodigoVerificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class CodigoVerificacionService {

    @Autowired
    private CodigoVerificacionRepository codigoVerificacionRepository;

    private static final int CODIGO_LENGTH = 4;
    private static final int EXPIRATION_MINUTES = 10; // Tiempo de expiración del código

    public String generarCodigo() {
        Random random = new Random();
        int codigoNumerico = 1000 + random.nextInt(9000);
        return String.valueOf(codigoNumerico);
    }

    public void guardarCodigo(String cdUsuario, String codigo) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationDate = now.plusMinutes(EXPIRATION_MINUTES);

        CodigoVerificacion codigoVerificacion = new CodigoVerificacion();
        codigoVerificacion.setCdUsuario(cdUsuario);
        codigoVerificacion.setCodigo(codigo);
        codigoVerificacion.setFechaCreacion(now);
        codigoVerificacion.setFechaExpiracion(expirationDate);

        // Eliminar cualquier código anterior para este usuario
        codigoVerificacionRepository.findByCdUsuario(cdUsuario).ifPresent(codigoVerificacionRepository::delete);

        codigoVerificacionRepository.save(codigoVerificacion);
    }

    public boolean esCodigoValido(String cdUsuario, String codigo) {
        Optional<CodigoVerificacion> codigoVerificacionOpt =
                codigoVerificacionRepository.findByCdUsuarioAndCodigoAndFechaExpiracionAfter(
                        cdUsuario, codigo, LocalDateTime.now());
        return codigoVerificacionOpt.isPresent();
    }

    public void eliminarCodigo(String cdUsuario) {
        codigoVerificacionRepository.findByCdUsuario(cdUsuario).ifPresent(codigoVerificacionRepository::delete);
    }
}