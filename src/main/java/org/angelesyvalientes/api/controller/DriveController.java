package org.angelesyvalientes.api.controller;

import org.angelesyvalientes.api.security.Res;
import org.angelesyvalientes.api.service.GoogleDriveService;
import org.angelesyvalientes.api.service.PersonaService; // Importar el servicio PersonaService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
public class DriveController {

    @Autowired
    private GoogleDriveService googleDriveService;

    @Autowired
    private PersonaService personaService; // Inyectar el servicio PersonaService

    @PostMapping("/uploadToGoogleDrive")
    public Object handleFileUpload(@RequestParam("image") MultipartFile file, @RequestParam("idPersona") Long idPersona) throws IOException, GeneralSecurityException { //Recibimos el id de la persona y aseguramos que es Long.
        if (file.isEmpty()) {
            return "FIle is empty";
        }
        File tempFile = File.createTempFile("temp", null);
        file.transferTo(tempFile);
        Res res = googleDriveService.uploadImageToDrive(tempFile);
        System.out.println(res);
        personaService.actualizarUrlFoto(idPersona, res.getUrl()); // Actualizar la URL en la base de datos
        return res;
    }
}