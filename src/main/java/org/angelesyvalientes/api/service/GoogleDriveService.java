package org.angelesyvalientes.api.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import org.angelesyvalientes.api.security.Res;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Collections;

/**
 * Servicio de Spring que facilita la interacción con Google Drive,
 * específicamente para la carga de imágenes. Utiliza la API de Google Drive
 * y una cuenta de servicio para la autenticación.
 */
@Service
public class GoogleDriveService {

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String SERVICE_ACOUNT_KEY_PATH = getPathToGoodleCredentials();

    /**
     * Determina la ruta al archivo de credenciales de la cuenta de servicio de Google.
     * Busca el archivo 'ayv.json' en el directorio de trabajo actual de la aplicación.
     *
     * @return La ruta absoluta al archivo de credenciales.
     */
    private static String getPathToGoodleCredentials() {
        String currentDirectory = System.getProperty("user.dir");
        Path filePath = Paths.get(currentDirectory, "ayv.json");
        return filePath.toString();
    }

    /**
     * Sube una imagen a una carpeta específica en Google Drive.
     *
     * @param file El archivo de imagen a subir. Se espera que sea un archivo JPEG.
     * @return Un objeto {@link Res} que contiene el estado de la operación (código 200 para éxito,
     * código 500 para error), un mensaje descriptivo y la URL de la imagen cargada en Google Drive.
     * @throws GeneralSecurityException Si ocurre un error de seguridad al crear el servicio de Drive.
     * @throws IOException              Si ocurre un error de entrada/salida al interactuar con Google Drive
     * o al leer el archivo de credenciales.
     */
    public Res uploadImageToDrive(File file) throws GeneralSecurityException, IOException {
        Res res = new Res();

        try {
            // ID de la carpeta en Google Drive donde se guardarán las imágenes.
            String folderId = "1HK4WMYkuJqQnoMq6h3O28oqZQjhgsMcw";
            // Crea una instancia del servicio de Google Drive autenticado.
            Drive drive = createDriveService();
            // Define los metadatos del archivo que se va a crear en Drive.
            com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
            fileMetaData.setName(file.getName());
            fileMetaData.setParents(Collections.singletonList(folderId));
            // Crea el contenido multimedia del archivo. Se especifica el tipo MIME como "image/jpeg".
            FileContent mediaContent = new FileContent("image/jpeg", file);
            // Sube el archivo a Google Drive. Se solicitan solo el campo 'id' en la respuesta para eficiencia.
            com.google.api.services.drive.model.File uploadedFile = drive.files().create(fileMetaData, mediaContent)
                    .setFields("id").execute();
            // Construye la URL pública de la imagen en Google Drive utilizando su ID.
            String imageUrl = "https://drive.google.com/uc?export=view&id=" + uploadedFile.getId();
            System.out.println("IMAGE URL: " + imageUrl);
            // Elimina el archivo local después de la carga exitosa.
            file.delete();
            // Establece los atributos de la respuesta de éxito.
            res.setStatus(200);
            res.setMessage("Image Successfully Uploaded To Drive");
            res.setUrl(imageUrl);
        } catch (Exception e) {
            // Captura cualquier excepción que ocurra durante el proceso de carga.
            System.out.println(e.getMessage());
            // Establece los atributos de la respuesta de error.
            res.setStatus(500);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    /**
     * Crea y autentica un servicio de Google Drive utilizando las credenciales de la cuenta de servicio.
     *
     * @return Una instancia autenticada del servicio {@link Drive}.
     * @throws GeneralSecurityException Si ocurre un error de seguridad al configurar el transporte HTTP.
     * @throws IOException              Si ocurre un error al leer el archivo de credenciales.
     */
    private Drive createDriveService() throws GeneralSecurityException, IOException {
        // Crea una credencial de Google a partir del archivo de la cuenta de servicio.
        GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream(SERVICE_ACOUNT_KEY_PATH))
                // Define el alcance de la autorización. En este caso, se solicita acceso completo a Google Drive.
                .createScoped(Collections.singleton(DriveScopes.DRIVE));

        // Construye y devuelve el servicio de Google Drive.
        return new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                credential)
                .setApplicationName("AngelesyValientesAPI") // Opcional: Establece el nombre de la aplicación.
                .build();
    }
}