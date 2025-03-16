package com.practica.angelesyvalientes.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "persona")
@Inheritance(strategy = InheritanceType.JOINED)
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática del ID
    @Column(name = "nm_id_persona")
    private int nmIdPersona;

    @ManyToOne
    @JoinColumn(name = "nm_id_genero", nullable = false) // Relación con la clase Género
    private Genero genero;

    @ManyToOne
    @JoinColumn(name = "nm_tipo_identificacion", nullable = false) // Relación con TipoIdentificacion
    private TipoIdentificacion tipoIdentificacion;

    @Column(name = "tx_primer_nombre", nullable = false, length = 45)
    private String txPrimerNombre;

    @Column(name = "tx_segundo_nombre", length = 45)
    private String txSegundoNombre;

    @Column(name = "tx_primer_apellido", nullable = false, length = 45)
    private String txPrimerApellido;

    @Column(name = "tx_segundo_apellido", length = 45)
    private String txSegundoApellido;

    @Column(name = "tx_telefono", nullable = false, length = 10)
    private String txTelefono;

    @Column(name = "tx_correo", length = 45)
    private String txCorreo;

    @Column(name = "tx_numero_identificacion", length = 15)
    private String txNumeroIdentificacion;

    public Genero getGenero() {
        return genero;
    }

    public TipoIdentificacion getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public String getTxPrimerNombre() {
        return txPrimerNombre;
    }

    public String getTxSegundoNombre() {
        return txSegundoNombre;
    }

    public String getTxPrimerApellido() {
        return txPrimerApellido;
    }

    public String getTxSegundoApellido() {
        return txSegundoApellido;
    }

    public String getTxTelefono() {
        return txTelefono;
    }

    public String getTxCorreo() {
        return txCorreo;
    }

    public String getTxNumeroIdentificacion() {
        return txNumeroIdentificacion;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public void setTxCorreo(String txCorreo) {
        this.txCorreo = txCorreo;
    }

    public void setTxNumeroIdentificacion(String txNumeroIdentificacion) {
        this.txNumeroIdentificacion = txNumeroIdentificacion;
    }

    public void setTxSegundoNombre(String txSegundoNombre) {
        this.txSegundoNombre = txSegundoNombre;
    }

    public void setTxPrimerApellido(String txPrimerApellido) {
        this.txPrimerApellido = txPrimerApellido;
    }

    public void setTxPrimerNombre(String txPrimerNombre) {
        this.txPrimerNombre = txPrimerNombre;
    }

    public void setTxSegundoApellido(String txSegundoApellido) {
        this.txSegundoApellido = txSegundoApellido;
    }

    public void setTxTelefono(String txTelefono) {
        this.txTelefono = txTelefono;
    }
}
