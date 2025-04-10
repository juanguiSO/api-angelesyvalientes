package org.angelesyvalientes.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.angelesyvalientes.api.security.Res;
import org.angelesyvalientes.api.service.GoogleDriveService;
import org.angelesyvalientes.api.service.PersonaService; // Importar el servicio PersonaService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * Controlador REST para la carga de archivos a Google Drive.
 * Expone un endpoint para recibir un archivo de imagen a través de una solicitud multipart/form-data,
 * subirlo a Google Drive utilizando el servicio {@link GoogleDriveService}, y opcionalmente
 * actualizar la URL de la foto de perfil de una persona utilizando {@link PersonaService}.
 * La API está etiquetada como "Carga a drive" en la documentación de Swagger.
 */
@Tag(name = "Carga  a drive")
@RestController
public class DriveController {

    private final GoogleDriveService googleDriveService;
    private final PersonaService personaService; // Inyectar el servicio PersonaService

    /**
     * Constructor de la clase {@code DriveController}.
     * Recibe instancias de {@link GoogleDriveService} y {@link PersonaService}
     * a través de la inyección de dependencias.
     *
     * @param googleDriveService El servicio para interactuar con Google Drive.
     * @param personaService     El servicio para la gestión de personas.
     */
    @Autowired
    public DriveController(GoogleDriveService googleDriveService, PersonaService personaService) {
        this.googleDriveService = googleDriveService;
        this.personaService = personaService;
    }

    /**
     * Endpoint para la carga de un archivo de imagen a Google Drive.
     * Recibe un archivo multipart como parámetro de la solicitud con el nombre "image".
     * Opcionalmente, puede recibir el ID de una persona ("idPersona") para actualizar su foto de perfil.
     * Sube el archivo a Google Drive y retorna la respuesta del servicio, que incluye la URL del archivo en Drive.
     * Si se proporciona un ID de persona, también actualiza la URL de la foto de perfil en la base de datos.
     *
     * @param file      El archivo de imagen a subir, enviado como multipart/form-data con el nombre "image".
     * @param idPersona (Opcional) El ID de la persona cuya foto de perfil se actualizará. Debe ser de tipo Long.
     * @return Una respuesta {@link ResponseEntity} con el resultado de la operación de carga, incluyendo la URL
     * del archivo en Google Drive, o un mensaje de error si la carga falla o el archivo está vacío.
     * @throws IOException              Si ocurre un error de entrada/salida al procesar el archivo.
     * @throws GeneralSecurityException Si ocurre un error de seguridad al interactuar con Google Drive.
     */
    @Operation(summary = "Cargar un archivo al drive  ")
    @PostMapping("/uploadToGoogleDrive")
    public ResponseEntity<?> handleFileUpload(@RequestParam("image") MultipartFile file,
                                              @RequestParam(value = "idPersona", required = false) Long idPersona) throws IOException, GeneralSecurityException {
        if (file.isEmpty()) {
            return new ResponseEntity<>("El archivo está vacío", HttpStatus.BAD_REQUEST);
        }

        File tempFile = File.createTempFile("temp", null);
        file.transferTo(tempFile);
        Res res = googleDriveService.uploadImageToDrive(tempFile);
        System.out.println(res);

        // Actualizar la URL de la foto de perfil si se proporciona el ID de la persona
        if (idPersona != null && res.getUrl() != null) {
            try {
                personaService.actualizarUrlFoto(idPersona, res.getUrl());
                return new ResponseEntity<>(res, HttpStatus.OK);
            } catch (RuntimeException e) {
                // Si no se encuentra la persona, aún retornamos la URL cargada, pero podríamos loggear el error.
                System.err.println("Error al actualizar la foto de perfil de la persona con ID " + idPersona + ": " + e.getMessage());
                return new ResponseEntity<>(res, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}