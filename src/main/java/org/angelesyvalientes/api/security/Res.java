package org.angelesyvalientes.api.security;

import lombok.Data;

/**
 * Clase simple de transferencia de datos (DTO) utilizada para representar
 * una respuesta genérica con información sobre el estado, un mensaje y una URL opcional.
 * Esta clase se utiliza a menudo para estandarizar las respuestas de la API,
 * especialmente en casos de éxito o error donde se necesita comunicar información adicional
 * al cliente.
 */
@Data
public class Res {

    /**
     * Código de estado numérico que indica el resultado de la operación.
     * Por ejemplo, podría ser un código de estado HTTP personalizado o un código interno
     * de la aplicación.
     */
    private int status;

    /**
     * Mensaje de texto descriptivo que proporciona más detalles sobre el resultado
     * de la operación. Puede ser un mensaje de éxito, un mensaje de error o cualquier
     * otra información relevante.
     */
    private String message;

    /**
     * URL opcional que puede estar relacionada con la respuesta.
     * Por ejemplo, podría ser la URL de un recurso recién creado, la URL de un error
     * para más detalles o cualquier otra URL relevante para el contexto de la respuesta.
     */
    private String url;

    /**
     * Obtiene el código de estado de la respuesta.
     *
     * @return El código de estado.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Obtiene el mensaje de la respuesta.
     *
     * @return El mensaje.
     */
    public int getStatus() {
        return status;
    }

    /**
     * Obtiene la URL asociada a la respuesta (si existe).
     *
     * @return La URL.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Establece el mensaje de la respuesta.
     *
     * @param message El mensaje a establecer.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Establece el código de estado de la respuesta.
     *
     * @param status El código de estado a establecer.
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Establece la URL asociada a la respuesta.
     *
     * @param url La URL a establecer.
     */
    public void setUrl(String url) {
        this.url = url;
    }
}