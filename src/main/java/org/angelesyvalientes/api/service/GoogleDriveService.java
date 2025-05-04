package org.angelesyvalientes.api.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.FileList;
import org.angelesyvalientes.api.security.Res;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

/**
 * Servicio de Spring para la carga de archivos en Google Drive, organizándolos por ID de persona.
 */
@Service
public class GoogleDriveService {

    private static final Logger logger = LoggerFactory.getLogger(GoogleDriveService.class);
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String SERVICE_ACCOUNT_KEY_PATH = getPathToGoogleCredentials();
    private static final String ROOT_FOLDER_ID = "1HK4WMYkuJqQnoMq6h3O28oqZQjhgsMcw"; // ID de la carpeta raíz

    /**
     * Obtiene la ruta al archivo de credenciales de la cuenta de servicio de Google.
     * @return La ruta absoluta al archivo de credenciales.
     */
    private static String getPathToGoogleCredentials() {
        return Paths.get(System.getProperty("user.dir"), "ayv.json").toString();
    }

    /**
     * Sube un PDF a la carpeta "Documentación" dentro de la carpeta de la persona y guarda solo el ID.
     * @param file El archivo PDF a subir.
     * @param idPersona El ID de la persona asociada.
     * @param tipoDocumentacion Tipo de documento a registrar.
     * @param documentacionService Servicio para actualizar el ID del documento en la BD.
     * @return Respuesta con estado y ID del archivo en Google Drive.
     * @throws GeneralSecurityException Si ocurre un problema de autenticación.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    public Res uploadPdfToDrive(File file, Long idPersona, String tipoDocumentacion, DocumentacionService documentacionService)
            throws GeneralSecurityException, IOException {
        Res res = new Res();

        if (!file.exists() || !file.isFile() || !file.getName().endsWith(".pdf")) {
            res.setStatus(400);
            res.setMessage("El archivo no es un PDF válido.");
            return res;
        }

        // **Validar si la persona existe antes de intentar subir el archivo**
        if (!documentacionService.existePersona(idPersona)) {
            res.setStatus(400);
            res.setMessage("No se encontró la persona con ID: " + idPersona);
            return res;
        }

        try {
            Drive drive = createDriveService();
            String documentFolderId = findOrCreateDocumentFolder(drive, idPersona.toString());

            if (documentFolderId == null) {
                res.setStatus(500);
                res.setMessage("Error al crear o encontrar la carpeta de documentación.");
                return res;
            }

            // Subir el archivo PDF
            com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
            fileMetaData.setName(file.getName());
            fileMetaData.setParents(Collections.singletonList(documentFolderId));
            FileContent mediaContent = new FileContent("application/pdf", file);

            com.google.api.services.drive.model.File uploadedFile = drive.files().create(fileMetaData, mediaContent)
                    .setFields("id").execute();

            if (uploadedFile != null && uploadedFile.getId() != null) {
                String fileId = uploadedFile.getId();
                logger.info("PDF subido correctamente con ID: {}", fileId);

                res.setStatus(200);
                res.setMessage("PDF subido exitosamente a la carpeta de documentación.");
                res.setUrl(fileId); // Ahora la URL en la respuesta es el ID

                file.delete(); // Solo eliminar si la subida fue exitosa

                // Guardar el ID del PDF en Documentacion asociada a la persona y tipo de documento
                documentacionService.guardarIdDocumento(idPersona, fileId, tipoDocumentacion);
            }
        } catch (Exception e) {
            logger.error("Error al subir el PDF: {}", e.getMessage());
            res.setStatus(500);
            res.setMessage("Error al subir el PDF: " + e.getMessage());
        }
        return res;
    }
    /**
     * Busca o crea la carpeta "Documentación" dentro de la carpeta de la persona en Google Drive.
     * @param drive Servicio de Google Drive autenticado.
     * @param idPersona ID de la persona.
     * @return El ID de la carpeta de documentación.
     * @throws IOException Si ocurre un problema de acceso a Drive.
     */
    private String findOrCreateDocumentFolder(Drive drive, String idPersona) throws IOException {
        String personFolderId = findOrCreatePersonFolder(drive, idPersona);

        if (personFolderId == null) {
            return null;
        }

        FileList result = drive.files().list()
                .setQ("mimeType='application/vnd.google-apps.folder' and name='Documentación' and '"
                        + personFolderId + "' in parents and trashed=false")
                .setFields("files(id)")
                .execute();

        List<com.google.api.services.drive.model.File> folders = result.getFiles();

        if (!folders.isEmpty()) {
            return folders.get(0).getId();
        } else {
            com.google.api.services.drive.model.File fileMetadata = new com.google.api.services.drive.model.File();
            fileMetadata.setName("Documentación");
            fileMetadata.setMimeType("application/vnd.google-apps.folder");
            fileMetadata.setParents(Collections.singletonList(personFolderId));

            return drive.files().create(fileMetadata)
                    .setFields("id")
                    .execute().getId();
        }
    }

    /**
     * Crea y autentica el servicio de Google Drive utilizando credenciales de la cuenta de servicio.
     * @return Una instancia autenticada del servicio {@link Drive}.
     * @throws GeneralSecurityException Si ocurre un error de seguridad.
     * @throws IOException Si ocurre un problema de lectura de credenciales.
     */
    private Drive createDriveService() throws GeneralSecurityException, IOException {
        try (FileInputStream fis = new FileInputStream(SERVICE_ACCOUNT_KEY_PATH)) {
            GoogleCredential credential = GoogleCredential.fromStream(fis)
                    .createScoped(Collections.singleton(DriveScopes.DRIVE));

            return new Drive.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JSON_FACTORY,
                    credential)
                    .setApplicationName("AngelesyValientesAPI")
                    .build();
        }
    }
    /**
     * Busca una carpeta con el nombre del ID de la persona dentro de la carpeta raíz.
     * Si no existe, la crea.
     *
     * @param drive         Servicio de Google Drive autenticado.
     * @param folderName El nombre de la carpeta (que será el ID de la persona).
     * @return El ID de la carpeta de la persona, o null si ocurre un error al crearla.
     * @throws IOException Si ocurre un error al interactuar con Google Drive.
     */
    private String findOrCreatePersonFolder(Drive drive, String folderName) throws IOException {
        // Buscar si ya existe una carpeta con el nombre del ID de la persona
        FileList result = drive.files().list()
                .setQ("mimeType='application/vnd.google-apps.folder' and name='" + folderName + "' and '" + ROOT_FOLDER_ID + "' in parents and trashed=false")
                .setFields("files(id)")
                .execute();
        List<com.google.api.services.drive.model.File> folders = result.getFiles();
        if (!folders.isEmpty()) {
            // Si la carpeta existe, retornar su ID
            return folders.get(0).getId();
        } else {
            // Si la carpeta no existe, crearla
            com.google.api.services.drive.model.File fileMetadata = new com.google.api.services.drive.model.File();
            fileMetadata.setName(folderName);
            fileMetadata.setMimeType("application/vnd.google-apps.folder");
            fileMetadata.setParents(Collections.singletonList(ROOT_FOLDER_ID));
            com.google.api.services.drive.model.File folder = drive.files().create(fileMetadata)
                    .setFields("id")
                    .execute();
            return folder.getId();
        }
    }

    /**
     * Sube una imagen a Google Drive dentro de una carpeta con el ID de la persona y guarda solo el ID.
     * Si la carpeta no existe, se crea. Si existe, se utiliza la carpeta existente.
     *
     * @param file     El archivo de imagen a subir. Se espera que sea un archivo JPEG.
     * @param idPersona El ID de la persona para la cual se está subiendo la foto.
     * @return Un objeto {@link Res} que contiene el estado de la operación, un mensaje
     * descriptivo y el ID de la imagen cargada en Google Drive.
     * @throws GeneralSecurityException Si ocurre un error de seguridad al crear el servicio de Drive.
     * @throws IOException             Si ocurre un error de entrada/salida al interactuar con Google Drive
     * o al leer el archivo de credenciales.
     */
    public Res uploadImageToDrive(File file, Long idPersona) throws GeneralSecurityException, IOException {
        Res res = new Res();

        try {
            Drive drive = createDriveService();
            String personFolderId = findOrCreatePersonFolder(drive, idPersona.toString());

            if (personFolderId == null) {
                res.setStatus(500);
                res.setMessage("Error al crear o encontrar la carpeta para la persona con ID: " + idPersona);
                return res;
            }

            com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
            fileMetaData.setName(file.getName());
            fileMetaData.setParents(Collections.singletonList(personFolderId));
            FileContent mediaContent = new FileContent("image/jpeg", file);

            com.google.api.services.drive.model.File uploadedFile = drive.files().create(fileMetaData, mediaContent)
                    .setFields("id").execute();
            String imageId = uploadedFile.getId();
            System.out.println("IMAGE ID: " + imageId);
            file.delete();

            res.setStatus(200);
            res.setMessage("Imagen subida exitosamente a la carpeta de la persona con ID: " + idPersona);
            res.setUrl(imageId); // Ahora la URL en la respuesta es el ID

        } catch (Exception e) {
            System.out.println(e.getMessage());
            res.setStatus(500);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    /**
     * Busca o crea la carpeta "informeclinico" dentro de la carpeta de la persona en Google Drive.
     * @param drive Servicio de Google Drive autenticado.
     * @param idPersona ID de la persona.
     * @return El ID de la carpeta de informe clínico.
     * @throws IOException Si ocurre un problema de acceso a Drive.
     */
    private String findOrCreateInformeClinicoFolder(Drive drive, String idPersona) throws IOException {
        String personFolderId = findOrCreatePersonFolder(drive, idPersona);

        if (personFolderId == null) {
            return null;
        }

        FileList result = drive.files().list()
                .setQ("mimeType='application/vnd.google-apps.folder' and name='informeclinico' and '"
                        + personFolderId + "' in parents and trashed=false")
                .setFields("files(id)")
                .execute();

        List<com.google.api.services.drive.model.File> folders = result.getFiles();

        if (!folders.isEmpty()) {
            return folders.get(0).getId();
        } else {
            com.google.api.services.drive.model.File fileMetadata = new com.google.api.services.drive.model.File();
            fileMetadata.setName("informeclinico");
            fileMetadata.setMimeType("application/vnd.google-apps.folder");
            fileMetadata.setParents(Collections.singletonList(personFolderId));

            return drive.files().create(fileMetadata)
                    .setFields("id")
                    .execute().getId();
        }
    }
    public Res uploadInformeClinicoPdf(File file, Long idPersona, Long idInformeClinico, // Nuevo parámetro
                                       InformeClinicoService informeClinicoService) throws GeneralSecurityException, IOException {
        Res res = new Res();
        if (!file.exists() || !file.isFile() || !file.getName().endsWith(".pdf")) {
            res.setStatus(400);
            res.setMessage("El archivo no es un PDF válido.");
            return res;
        }
        try {
            Drive drive = createDriveService();
            String informeClinicoFolderId = findOrCreateInformeClinicoFolder(drive, idPersona.toString());
            if (informeClinicoFolderId == null) {
                res.setStatus(500);
                res.setMessage("Error al crear o encontrar la carpeta de informe clínico.");
                return res;
            }
            // Subir el archivo PDF
            com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
            fileMetaData.setName(file.getName());
            fileMetaData.setParents(Collections.singletonList(informeClinicoFolderId));
            FileContent mediaContent = new FileContent("application/pdf", file);
            com.google.api.services.drive.model.File uploadedFile = drive.files().create(fileMetaData, mediaContent)
                    .setFields("id").execute();
            if (uploadedFile != null && uploadedFile.getId() != null) {
                String fileId = uploadedFile.getId();
                logger.info("Informe clínico PDF subido correctamente con ID: {}", fileId);
                res.setStatus(200);
                res.setMessage("Informe clínico PDF subido exitosamente.");
                res.setUrl(fileId); // Ahora la URL en la respuesta es el ID
                file.delete(); // Solo eliminar si la subida fue exitosa
                // Guardar el ID del PDF en la tabla informeclinico asociada a la persona
                informeClinicoService.guardarUrlPdf(idPersona, fileId, idInformeClinico); // Pasar el idInformeClinico
            }
        } catch (Exception e) {
            logger.error("Error al subir el informe clínico PDF: {}", e.getMessage());
            res.setStatus(500);
            res.setMessage("Error al subir el informe clínico PDF: " + e.getMessage());
        }
        return res;
    }


}