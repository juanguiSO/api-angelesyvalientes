package org.angelesyvalientes.api.service;



import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;

import com.google.api.client.http.FileContent;

import com.google.api.client.json.JsonFactory;

import com.google.api.client.json.gson.GsonFactory;

import com.google.api.services.drive.Drive;

import com.google.api.services.drive.DriveScopes;

import com.google.api.services.drive.model.FileList;

import org.angelesyvalientes.api.persistence.entity.Documentacion;

import org.angelesyvalientes.api.persistence.entity.Ficha;

import org.angelesyvalientes.api.persistence.entity.InformeClinico;

import org.angelesyvalientes.api.security.Res;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;


import java.io.ByteArrayOutputStream;
import java.io.File;

import java.io.FileInputStream;

import java.io.IOException;

import java.security.GeneralSecurityException;

import java.util.Collections;

import java.util.List;



/**

 * Servicio de Spring para la carga de archivos en Google Drive, organizándolos por ID de persona o programa.

 */

@Service

public class GoogleDriveService {



    private static final Logger logger = LoggerFactory.getLogger(GoogleDriveService.class);

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

// ID de la carpeta raíz, ahora como constante estática

    private static final String ROOT_FOLDER_ID = "1HK4WMYkuJqQnoMq6h3O28oqZQjhgsMcw";



    @Value("${GOOGLE_APPLICATION_CREDENTIALS}")

    private String GOOGLE_CREDENTIALS_PATH;



    /**

     * Crea y autentica el servicio de Google Drive utilizando credenciales de la cuenta de servicio.

     * @return Una instancia autenticada del servicio {@link Drive}.

     * @throws GeneralSecurityException Si ocurre un error de seguridad.

     * @throws IOException Si ocurre un problema de lectura de credenciales.

     */

    private Drive createDriveService() throws GeneralSecurityException, IOException {

        logger.info("Ruta JSON de credenciales: {}", GOOGLE_CREDENTIALS_PATH);

        try (FileInputStream fis = new FileInputStream(GOOGLE_CREDENTIALS_PATH)) {

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

     * @param drive Servicio de Google Drive autenticado.

     * @param folderName El nombre de la carpeta (que será el ID de la persona).

     * @return El ID de la carpeta de la persona, o null si ocurre un error al crearla.

     * @throws IOException Si ocurre un error al interactuar con Google Drive.

     */

    private String findOrCreatePersonFolder(Drive drive, String folderName) throws IOException {

        FileList result = drive.files().list()

                .setQ("mimeType='application/vnd.google-apps.folder' and name='" + folderName + "' and '" + ROOT_FOLDER_ID + "' in parents and trashed=false")

                .setFields("files(id)")

                .execute();

        List<com.google.api.services.drive.model.File> folders = result.getFiles();

        if (!folders.isEmpty()) {

            return folders.get(0).getId();

        } else {

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



    /**

     * Busca o crea la carpeta raíz "Programas" dentro de ROOT_FOLDER_ID.

     * @param drive Servicio de Google Drive autenticado.

     * @return El ID de la carpeta "Programas".

     * @throws IOException Si ocurre un error al interactuar con Google Drive.

     */

    private String findOrCreateProgramasRootFolder(Drive drive) throws IOException {

        String programasRootFolderName = "Programas";

        String programasRootFolderId = null;



        FileList result = drive.files().list()

                .setQ("mimeType='application/vnd.google-apps.folder' and name='" + programasRootFolderName + "' and '" + ROOT_FOLDER_ID + "' in parents and trashed=false")

                .setFields("files(id)")

                .execute();



        List<com.google.api.services.drive.model.File> folders = result.getFiles();



        if (!folders.isEmpty()) {

            programasRootFolderId = folders.get(0).getId();

            logger.info("Carpeta raíz 'Programas' existente encontrada con ID: {}", programasRootFolderId);

        } else {

            logger.warn("No se encontró la carpeta raíz 'Programas'. Creando nueva carpeta.");

            com.google.api.services.drive.model.File fileMetadata = new com.google.api.services.drive.model.File();

            fileMetadata.setName(programasRootFolderName);

            fileMetadata.setMimeType("application/vnd.google-apps.folder");

            fileMetadata.setParents(Collections.singletonList(ROOT_FOLDER_ID)); // Crear bajo tu ROOT_FOLDER_ID base



            com.google.api.services.drive.model.File createdFolder = drive.files().create(fileMetadata)

                    .setFields("id")

                    .execute();

            if (createdFolder == null || createdFolder.getId() == null) {

                logger.error("Error crítico: No se pudo crear la carpeta raíz 'Programas'.");

                throw new IOException("Fallo al crear la carpeta raíz 'Programas'.");

            }

            programasRootFolderId = createdFolder.getId();

            logger.info("Carpeta raíz 'Programas' creada con ID: {}", programasRootFolderId);

        }

        return programasRootFolderId;

    }
    /**

     * Busca o crea la carpeta del programa (por su nombre) dentro de la carpeta "Programas",

     * y luego busca o crea la carpeta "Fichas" dentro de esa carpeta del programa.

     *

     * @param drive Servicio de Google Drive autenticado.

     * @param programaNombre El nombre del programa (ej. "Matemáticas").

     * @return El ID de la carpeta "Fichas" dentro de la carpeta del programa.

     * @throws IOException Si ocurre un error al interactuar con Google Drive.

     */

    private String findOrCreateFichaRecursoFolder(Drive drive, String programaNombre) throws IOException {

        String fichasFolderId = null;

        String programaFolderId = null;



        try {

// 1. Obtener/Crear la carpeta raíz "Programas"

            String programasRootId = findOrCreateProgramasRootFolder(drive);

            if (programasRootId == null) {

                throw new IOException("No se pudo obtener/crear la carpeta raíz 'Programas'.");

            }



// 2. Buscar o crear la carpeta del programa (por nombre) dentro de la carpeta "Programas"

            String programaFolderQuery = "mimeType='application/vnd.google-apps.folder' and name='" + programaNombre + "' and '" + programasRootId + "' in parents and trashed=false";

            com.google.api.services.drive.model.FileList programaFiles = drive.files().list()

                    .setQ(programaFolderQuery)

                    .setFields("files(id)")

                    .execute();



            if (programaFiles.getFiles().isEmpty()) {

                logger.warn("No se encontró la carpeta del programa con nombre: {}. Creando nueva carpeta.", programaNombre);

                com.google.api.services.drive.model.File programaFolderMetadata = new com.google.api.services.drive.model.File();

                programaFolderMetadata.setName(programaNombre);

                programaFolderMetadata.setMimeType("application/vnd.google-apps.folder");

                programaFolderMetadata.setParents(Collections.singletonList(programasRootId)); // Crear bajo la carpeta "Programas"



                com.google.api.services.drive.model.File createdProgramaFolder = drive.files().create(programaFolderMetadata)

                        .setFields("id")

                        .execute();

                if (createdProgramaFolder == null || createdProgramaFolder.getId() == null) {

                    logger.error("Error crítico: No se pudo crear la carpeta del programa con nombre: {}", programaNombre);

                    throw new IOException("Fallo al crear la carpeta del programa: " + programaNombre);

                }

                programaFolderId = createdProgramaFolder.getId();

                logger.info("Carpeta del programa '{}' creada con ID: {}", programaNombre, programaFolderId);

            } else {

                programaFolderId = programaFiles.getFiles().get(0).getId();

                logger.info("Carpeta del programa '{}' existente encontrada con ID: {}", programaNombre, programaFolderId);

            }



// A este punto, tenemos el programaFolderId (existente o recién creado)

// 3. Buscar o crear la carpeta "Fichas" dentro de la carpeta del programa

            String fichasFolderQuery = "mimeType='application/vnd.google-apps.folder' and name='Fichas' and '" + programaFolderId + "' in parents and trashed=false";

            com.google.api.services.drive.model.FileList fichasFiles = drive.files().list()

                    .setQ(fichasFolderQuery)

                    .setFields("files(id)")

                    .execute();



            if (fichasFiles.getFiles().isEmpty()) {

                logger.warn("No se encontró la carpeta 'Fichas' dentro del programa '{}'. Creando nueva carpeta.", programaNombre);

                com.google.api.services.drive.model.File fileMetadata = new com.google.api.services.drive.model.File();

                fileMetadata.setName("Fichas");

                fileMetadata.setMimeType("application/vnd.google-apps.folder");

                fileMetadata.setParents(Collections.singletonList(programaFolderId));

                com.google.api.services.drive.model.File createdFichasFolder = drive.files().create(fileMetadata)

                        .setFields("id")

                        .execute();

                if (createdFichasFolder == null || createdFichasFolder.getId() == null) {

                    logger.error("Error crítico: No se pudo crear la carpeta 'Fichas' dentro de la carpeta del programa '{}'.", programaNombre);

                    throw new IOException("Fallo al crear la carpeta 'Fichas'.");

                }

                fichasFolderId = createdFichasFolder.getId();

                logger.info("Carpeta 'Fichas' creada con ID: {}", fichasFolderId);

            } else {

                fichasFolderId = fichasFiles.getFiles().get(0).getId();

                logger.info("Carpeta 'Fichas' existente encontrada con ID: {}", fichasFolderId);

            }

        } catch (IOException e) {

            logger.error("Error al buscar o crear la estructura de carpetas para Ficha (Programas -> [Nombre del Programa] -> Fichas): {}", e.getMessage(), e);

            throw e;

        }

        return fichasFolderId;

    }



    /**

     * Sube un archivo PDF a la carpeta "Documentación" dentro de la carpeta de la persona y guarda solo el ID.

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

                res.setUrl(fileId);



                file.delete();



                documentacionService.guardarIdDocumento(idPersona, fileId, tipoDocumentacion);

            }

        } catch (Exception e) {

            logger.error("Error al subir el PDF de Documentación: {}", e.getMessage());

            res.setStatus(500);

            res.setMessage("Error al subir el PDF de Documentación: " + e.getMessage());

        }

        return res;

    }



    /**

     * Sube una imagen a Google Drive dentro de una carpeta con el ID de la persona y guarda solo el ID.

     * Si la carpeta no existe, se crea. Si existe, se utiliza la carpeta existente.

     *

     * @param file El archivo de imagen a subir. Se espera que sea un archivo JPEG.

     * @param idPersona El ID de la persona para la cual se está subiendo la foto.

     * @return Un objeto {@link Res} que contiene el estado de la operación, un mensaje

     * descriptivo y el ID de la imagen cargada en Google Drive.

     * @throws GeneralSecurityException Si ocurre un error de seguridad al crear el servicio de Drive.

     * @throws IOException Si ocurre un error de entrada/salida al interactuar con Google Drive

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

            // Buscar imágenes existentes en la carpeta
            FileList existingFiles = drive.files().list()
                    .setQ("'" + personFolderId + "' in parents and mimeType contains 'image/'")
                    .setFields("files(id)")
                    .execute();

            // Si hay una imagen existente, eliminarla
            if (!existingFiles.getFiles().isEmpty()) {
                String existingImageId = existingFiles.getFiles().get(0).getId();
                drive.files().delete(existingImageId).execute();
                logger.info("Imagen anterior eliminada: {}", existingImageId);
            }

            // Subir la nueva imagen
            com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
            fileMetaData.setName(file.getName());
            fileMetaData.setMimeType("image/jpeg");
            fileMetaData.setParents(Collections.singletonList(personFolderId));
            FileContent mediaContent = new FileContent("image/jpeg", file);

            com.google.api.services.drive.model.File uploadedFile = drive.files().create(fileMetaData, mediaContent)
                    .setFields("id").execute();
            String imageId = uploadedFile.getId();
            logger.info("Nueva imagen subida: {}", imageId);
            file.delete();

            res.setStatus(200);
            res.setMessage("Imagen de perfil actualizada correctamente.");
            res.setUrl(imageId);

        } catch (Exception e) {
            logger.error("Error al subir la imagen: {}", e.getMessage());
            res.setStatus(500);
            res.setMessage(e.getMessage());
        }

        return res;
    }



    public Res uploadInformeClinicoPdf(File file, Long idPersona, Long idInformeClinico,

                                       InformeClinico informeClinico) throws GeneralSecurityException, IOException {

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

                res.setUrl(fileId);

                file.delete();



                informeClinico.setUrlPdf(fileId);

            }

        } catch (Exception e) {

            logger.error("Error al subir el informe clínico PDF: {}", e.getMessage());

            res.setStatus(500);

            res.setMessage("Error al subir el informe clínico PDF: " + e.getMessage());

        }

        return res;

    }





    public Res uploadDocumentacionPdf(File file, Long idPersona, Long idDocumentacion,

                                      Documentacion documentacion) throws GeneralSecurityException, IOException {

        Res res = new Res();

        if (!file.exists() || !file.isFile() || !file.getName().endsWith(".pdf")) {

            res.setStatus(400);

            res.setMessage("El archivo no es un PDF válido.");

            return res;

        }

        try {

            Drive drive = createDriveService();

            String documentacionFolderId = findOrCreateDocumentFolder(drive, idPersona.toString());

            if (documentacionFolderId == null) {

                res.setStatus(500);

                res.setMessage("Error al crear o encontrar la carpeta de documentación.");

                return res;

            }

            com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();

            fileMetaData.setName(file.getName());

            fileMetaData.setParents(Collections.singletonList(documentacionFolderId));

            FileContent mediaContent = new FileContent("application/pdf", file);

            com.google.api.services.drive.model.File uploadedFile = drive.files().create(fileMetaData, mediaContent)

                    .setFields("id").execute();

            if (uploadedFile != null && uploadedFile.getId() != null) {

                String fileId = uploadedFile.getId();

                logger.info("Documento PDF subido correctamente con ID: {}", fileId);

                res.setStatus(200);

                res.setMessage("Documento PDF subido exitosamente.");

                res.setUrl(fileId);

                file.delete();



                documentacion.setUrlPdf(fileId);

            }



        } catch (Exception e) {

            logger.error("Error al subir el documento PDF: {}", e.getMessage());

            res.setStatus(500);

            res.setMessage("Error al subir el documento PDF: " + e.getMessage());

        }

        return res;

    }



    /**

     * Sube un archivo PDF a Google Drive para un recurso de Ficha, organizándolo por el nombre del programa.

     * Guarda la URL de vista web del archivo en la entidad Ficha. Incluye lógica de eliminación de archivo

     * anterior si existe un conflicto por nombre o si el código de la ficha cambia.

     *

     * @param file El archivo PDF a subir.

     * @param programaNombre El nombre del programa asociado a la Ficha, para la organización en carpetas.

     * @param ficha La entidad Ficha asociada.

     * @return Un objeto Res con el estado y la URL del archivo subido, o un error.

     * @throws GeneralSecurityException Si hay un error de seguridad al acceder a Google Drive.

     * @throws IOException Si hay un error de E/S al leer el archivo o interactuar con Google Drive.

     */

    public Res uploadFichaRecursoPdf(File file, String programaNombre, Ficha ficha) throws GeneralSecurityException, IOException {

        Res res = new Res();

        if (!file.exists() || !file.isFile() || !file.getName().endsWith(".pdf")) {

            res.setStatus(400);

            res.setMessage("El archivo no es un PDF válido.");

            logger.warn("Intento de subir archivo no PDF o no válido para Ficha: {}", file.getName());

            return res;

        }



        try {

            Drive drive = createDriveService();

            String fichaRecursoFolderId = findOrCreateFichaRecursoFolder(drive, programaNombre);

            if (fichaRecursoFolderId == null) {

                res.setStatus(500);

                res.setMessage("Error al crear o encontrar la carpeta de recursos de fichas.");

                logger.error("No se pudo obtener el ID de la carpeta de recursos de fichas para el programa: {}", programaNombre);

                return res;

            }



// Construir el nombre del archivo basado en el código de la ficha

            String newFileName = ficha.getCodigo() + ".pdf";



// Verificar si ya existe un archivo con el mismo nombre en la misma carpeta

            FileList existingFiles = drive.files().list()

                    .setQ("name='" + newFileName + "' and '" + fichaRecursoFolderId + "' in parents and trashed=false")

                    .setFields("files(id)")

                    .execute();



            if (existingFiles.getFiles() != null && !existingFiles.getFiles().isEmpty()) {

// Si existe, eliminar el archivo anterior para sobreescribir con el nuevo

                String existingFileId = existingFiles.getFiles().get(0).getId();

                drive.files().delete(existingFileId).execute();

                logger.info("Archivo existente de Ficha con nombre '{}' eliminado para sobreescritura. ID: {}", newFileName, existingFileId);

            }



// Subir el nuevo archivo PDF

            com.google.api.services.drive.model.File fileMetadata = new com.google.api.services.drive.model.File();

            fileMetadata.setName(newFileName);

            fileMetadata.setParents(Collections.singletonList(fichaRecursoFolderId));

            FileContent mediaContent = new FileContent("application/pdf", file);

            com.google.api.services.drive.model.File uploadedFile = drive.files().create(fileMetadata, mediaContent)

                    .setFields("id, webViewLink") // Aunque pedimos webViewLink, no lo usaremos para guardar

                    .execute();



            if (uploadedFile != null && uploadedFile.getId() != null) {

                String fileId = uploadedFile.getId(); // <--- ¡OBTENEMOS EL ID PURO!



// ELIMINAMOS TODA LA LÓGICA DE EXTRACCIÓN Y RECONSTRUCCIÓN DE LA URL

// Simplemente guardamos el ID puro.

                String urlToSaveInDb = fileId; // <--- ¡ESTE ES EL CAMBIO CLAVE AQUÍ!



                logger.info("Archivo PDF de Ficha subido correctamente. ID: {}, URL Guardada: {}", fileId, urlToSaveInDb);

                res.setStatus(200);

                res.setMessage("Archivo PDF de Ficha subido exitosamente.");

                res.setUrl(urlToSaveInDb); // Guardamos solo el ID en la URL de la ficha



            } else {

                logger.error("Error al subir el archivo PDF de la Ficha: No se obtuvo ID o URL.");

                res.setStatus(500);

                res.setMessage("Error al subir el archivo PDF de la Ficha: No se obtuvo ID o URL del archivo.");

            }



        } catch (Exception e) {

            logger.error("Error al subir el archivo PDF de la Ficha: {}", e.getMessage(), e);

            res.setStatus(500);

            res.setMessage("Error al subir el archivo PDF de la Ficha: " + e.getMessage());

        } finally {

// Asegúrate de que el archivo temporal se borre

            if (file != null && file.exists()) {

                file.delete();

                logger.info("Archivo temporal de Ficha eliminado: {}", file.getAbsolutePath());

            }

        }

        return res;

    }



    /**

     * Elimina un archivo de Google Drive dado su ID.

     * Este método es público para ser llamado desde FichaService.

     * @param fileId El ID del archivo a eliminar.

     * @throws GeneralSecurityException Si hay un error de seguridad al acceder a Google Drive.

     * @throws IOException Si hay un error de E/S al interactuar con Google Drive.

     */

    public void deleteFile(String fileId) throws GeneralSecurityException, IOException {

        if (fileId == null || fileId.trim().isEmpty()) {

            logger.warn("Intento de eliminar archivo de Google Drive con ID nulo o vacío. No se realizará la operación.");

            return;

        }

        try {

            Drive drive = createDriveService();

            drive.files().delete(fileId).execute();

            logger.info("Archivo con ID '{}' eliminado exitosamente de Google Drive.", fileId);

        } catch (IOException e) {

            logger.error("Error al eliminar el archivo con ID '{}' de Google Drive: {}", fileId, e.getMessage(), e);

            throw e;

        }

    }



    /**

     * Extrae el ID de un archivo de Google Drive a partir de la URL de vista web guardada en la base de datos para Fichas.

     * Asume que la URL *puede ser* el ID puro, o formatos antiguos con '/view?usp=drivesdk' o URLs completas de Drive.

     * Este método es público para ser llamado desde FichaService.

     * @param url La URL completa o parcial del recurso en Google Drive.

     * @return El ID del archivo, o null si no se puede extraer.

     */

    public String extractFileIdFromUrl(String url) {

        if (url == null || url.isEmpty()) {

            return null;

        }



// Caso 1: URL con el sufijo que antes guardabas (e.g., "ID/view?usp=drivesdk")

// Esta es la primera comprobación para compatibilidad con datos ya existentes

        if (url.contains("/view?usp=drivesdk")) {

            int endIndex = url.indexOf("/view?usp=drivesdk");

// Asegurarse de que el sufijo es exactamente al final de la URL o seguido de otros parámetros

            if (endIndex != -1 && url.length() >= endIndex + "/view?usp=drivesdk".length() &&

                    (url.length() == endIndex + "/view?usp=drivesdk".length() || url.charAt(endIndex + "/view?usp=drivesdk".length()) == '?')) {

                return url.substring(0, endIndex);

            }

        }



// Caso 2: URL completa de Google Drive (como la devuelve Drive a veces, e.g., "https://drive.google.com/file/d/ID/view")

        if (url.contains("/file/d/")) {
            int startIndex = url.indexOf("/file/d/") + "/file/d/".length();
            int endIndex = url.indexOf("/", startIndex); // Busca la siguiente barra después del ID
            if (endIndex == -1) { // Si no hay barra, busca el '?' (parámetros de query)
                endIndex = url.indexOf("?", startIndex);
                if (endIndex == -1) { // Si no hay '?', el ID va hasta el final de la cadena
                    endIndex = url.length();
                }
            }

// Asegúrate de que los índices son válidos
            if (startIndex < url.length() && endIndex >= startIndex) {
                return url.substring(startIndex, endIndex);
            }

        }



// Caso 3: Asumir que la 'url' ya es el ID puro (después de implementar la nueva lógica de guardado)
// Si no se encontró ningún patrón de URL conocido, asumimos que el string ya es el ID.
// Puedes añadir aquí una validación más robusta si el ID de Drive tiene una longitud o patrón específico.
        logger.debug("extractFileIdFromUrl: No se detectó patrón de URL conocido. Asumiendo que '{}' es el ID puro.", url);
        return url;
    }


    /**
     * Obtiene los metadatos de un archivo de Google Drive dado su ID.
     * Útil para obtener el nombre y el tipo MIME del archivo.
     *
     * @param fileId El ID del archivo de Google Drive.
     * @return Un objeto {@link com.google.api.services.drive.model.File} con los metadatos del archivo.
     * @throws GeneralSecurityException Si hay un error de seguridad al acceder a Google Drive.
     * @throws IOException Si hay un error de E/S al interactuar con Google Drive.
     */
    public com.google.api.services.drive.model.File getFileMetadata(String fileId) throws GeneralSecurityException, IOException {
        if (fileId == null || fileId.trim().isEmpty()) {
            logger.warn("Intento de obtener metadatos de archivo de Google Drive con ID nulo o vacío.");
            return null;
        }
        try {
            Drive drive = createDriveService();
            // Solicitamos los campos id, name y mimeType
            return drive.files().get(fileId).setFields("id, name, mimeType").execute();
        } catch (IOException e) {
            logger.error("Error al obtener metadatos del archivo con ID '{}' de Google Drive: {}", fileId, e.getMessage(), e);
            throw e;
        }
    }
    /**
     * Descarga un archivo de Google Drive dado su ID.
     *
     * @param fileId El ID del archivo de Google Drive a descargar.
     * @return Un array de bytes que representa el contenido del archivo.
     * @throws GeneralSecurityException Si hay un error de seguridad al acceder a Google Drive.
     * @throws IOException Si hay un error de E/S al interactuar con Google Drive.
     */
    public byte[] downloadFile(String fileId) throws GeneralSecurityException, IOException {
        if (fileId == null || fileId.trim().isEmpty()) {
            logger.warn("Intento de descargar archivo de Google Drive con ID nulo o vacío. No se realizará la operación.");
            return null; // O lanzar una excepción IllegalArgumentException
        }
        try {
            Drive drive = createDriveService();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            drive.files().get(fileId).executeMediaAndDownloadTo(outputStream);
            logger.info("Archivo con ID '{}' descargado exitosamente de Google Drive. Tamaño: {} bytes", fileId, outputStream.size());
            return outputStream.toByteArray();
        } catch (IOException e) {
            logger.error("Error al descargar el archivo con ID '{}' de Google Drive: {}", fileId, e.getMessage(), e);
            throw e; // Relanza la excepción para que el controlador la maneje
        }
    }


}