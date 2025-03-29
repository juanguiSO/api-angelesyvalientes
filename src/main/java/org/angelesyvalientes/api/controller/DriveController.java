package org.angelesyvalientes.api.controller;

import org.angelesyvalientes.api.service.GoogleDriveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/drive") // Agrega una ruta base para tus endpoints de Drive
public class DriveController {

    @Autowired
    private GoogleDriveService googleDriveService; // Inyecta tu servicio

    @PostMapping("/upload") // La ruta para la carga de archivos
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        System.out.println("uploadFile fue llamado. Archivo recibido: " + (file == null ? "Nulo" : file.getOriginalFilename()));
        try {
            String fileUrl = googleDriveService.uploadFile(file);
            return ResponseEntity.ok(fileUrl); // Retorna la URL del archivo
        } catch (IOException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al cargar el archivo: " + e.getMessage());
        }
    }
}