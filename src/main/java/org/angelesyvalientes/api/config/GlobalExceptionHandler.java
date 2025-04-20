package org.angelesyvalientes.api.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.dao.DataIntegrityViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Maneja errores de concurrencia (optimistic locking)
    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<ErrorResponse> handleOptimisticLocking(Exception ex) {
        ErrorResponse error = new ErrorResponse(
                "CONFLICTO_VERSION",
                "El registro fue modificado por otro usuario. Por favor, recargue los datos.",
                HttpStatus.CONFLICT.value()
        );
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    // Maneja errores de integridad de datos (claves foráneas, constraints)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(Exception ex) {
        ErrorResponse error = new ErrorResponse(
                "ERROR_BD",
                "Error de integridad de datos. Verifique la información proporcionada.",
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Clase interna para la estructura de la respuesta de error
    private static class ErrorResponse {
        private String codigo;
        private String mensaje;
        private int status;

        public ErrorResponse(String codigo, String mensaje, int status) {
            this.codigo = codigo;
            this.mensaje = mensaje;
            this.status = status;
        }

        // Getters (necesarios para la serialización JSON)
        public String getCodigo() { return codigo; }
        public String getMensaje() { return mensaje; }
        public int getStatus() { return status; }
    }
}