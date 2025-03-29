package org.angelesyvalientes.api.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.InputStreamContent; // Importación CORRECTA
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;  // Importante: Importar la clase Permission
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;


@Service
public class GoogleDriveService {

    private static final String APPLICATION_NAME = "AngelesyValientes";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    private static final String CREDENTIALS_FILE_PATH = "angelesyvalientes-c6c4d11e1a41.json"; //Relativa
    private Drive drive;

    @Autowired
    ResourcePatternResolver resourcePatternResolver;

    public GoogleDriveService() throws IOException, GeneralSecurityException {
        this.drive = getDriveService();
    }

    private Credential authorize() throws IOException, GeneralSecurityException { //Autorización para la cuenta de servicio
        try {
            FileInputStream fileInputStream = new FileInputStream(CREDENTIALS_FILE_PATH); // Usar una variable local
            System.out.println("FileInputStream creado exitosamente."); // Registrar si funciona
            InputStream in = fileInputStream;

            if (in == null) {
                System.err.println("¡InputStream 'in' es nulo después de FileInputStream!");
                throw new IOException("No se pudo leer el archivo de credenciales: FileInputStream devolvió nulo");
            }

            GoogleCredential credential = GoogleCredential.fromStream(in)
                    .createScoped(Collections.singletonList(DriveScopes.DRIVE_FILE));
            System.out.println("GoogleCredential creado exitosamente."); // Registrar si funciona
            return credential;
        } catch (IOException e) { // or catch (Exception e) if you are unsure.
            System.err.println("Error authorizing service account: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }


    private Drive getDriveService() throws IOException, GeneralSecurityException {
        try {
            String credentialsFilePath = CREDENTIALS_FILE_PATH;
            System.out.println("Intentando cargar client_secret.json desde: " + credentialsFilePath);
            NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            Credential credential = authorize();
            return new Drive.Builder(httpTransport, JSON_FACTORY, credential)
                    .setApplicationName(APPLICATION_NAME)
                    .build();

        } catch (IOException | GeneralSecurityException e) {
            System.err.println("Error al inicializar Google Drive service: " + e.getMessage());
            e.printStackTrace();
            throw e; //Re-throw para detener el inicio.
        }
    }

    public String uploadFile(MultipartFile file) throws IOException {
        System.out.println("GoogleDriveService.uploadFile() fue llamado. Nombre del archivo: " + file.getOriginalFilename());

        File fileMetadata = new File(); // Constructor sin argumentos
        fileMetadata.setName(file.getOriginalFilename()); // Establecer el nombre usando setName()

        InputStreamContent mediaContent = new InputStreamContent( //Usando la importacion correcta.
                file.getContentType(),
                new ByteArrayInputStream(file.getBytes()));

        System.out.println("Creando archivo en Google Drive...");

        File uploadedFile = drive.files().create(fileMetadata, mediaContent)
                .setFields("id, webViewLink")
                .execute();

        System.out.println("Archivo subido. ID: " + uploadedFile.getId() + ", URL: " + uploadedFile.getWebViewLink());

        // **Código para compartir con formacionciudanana@gmail.com**
        try {
            Permission permission = new Permission();
            permission.setType("user");
            permission.setRole("reader"); // O "writer" para dar permiso de edición
            permission.setEmailAddress("formacionciudanana@gmail.com");

            System.out.println("Compartiendo el archivo con formacionciudadana@gmail.com...");

            drive.permissions().create(uploadedFile.getId(), permission).execute();

            System.out.println("Archivo compartido exitosamente con formacionciudadana@gmail.com");
        } catch (IOException e) {
            System.err.println("Error al compartir el archivo con formacionciudadana@gmail.com: " + e.getMessage());
            e.printStackTrace();
        }

        // **Código para crear una copia en la carpeta de formacionciudadana@gmail.com**
        try {
            File fileMetadataCopy = new File();
            fileMetadataCopy.setName(file.getOriginalFilename());
            //Set this directory and replace YOUR_FOLDER_ID_HERE
            fileMetadataCopy.setParents(Collections.singletonList("https://drive.google.com/drive/u/0/folders/1EW6qU1-T3cAqXY_BZq6GNZOp07CfqwJb"));

            System.out.println("Creando una copia del archivo en la carpeta de formacionciudadana@gmail.com...");

            File copiedFile = drive.files().copy(uploadedFile.getId(), fileMetadataCopy)
                    .setFields("id, webViewLink")
                    .execute();

            System.out.println("Copia del archivo creada. ID: " + copiedFile.getId() + ", URL: " + copiedFile.getWebViewLink());

        } catch (IOException e) {
            System.err.println("Error al copiar el archivo en la carpeta de formacionciudadana@gmail.com: " + e.getMessage());
            e.printStackTrace();
        }

        return uploadedFile.getWebViewLink();
    }
}