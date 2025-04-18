package org.angelesyvalientes.api.DetallesValienteDTO;

public class ActualizarContrasenaRequestDTO {
    private String cdUsuario;
    private String codigoVerificacion;
    private String nuevaContrasena;

    public String getCdUsuario() {
        return cdUsuario;
    }

    public String getCodigoVerificacion() {
        return codigoVerificacion;
    }

    public String getNuevaContrasena() {
        return nuevaContrasena;
    }

    public void setCdUsuario(String cdUsuario) {
        this.cdUsuario = cdUsuario;
    }

    public void setCodigoVerificacion(String codigoVerificacion) {
        this.codigoVerificacion = codigoVerificacion;
    }

    public void setNuevaContrasena(String nuevaContrasena) {
        this.nuevaContrasena = nuevaContrasena;
    }
}
