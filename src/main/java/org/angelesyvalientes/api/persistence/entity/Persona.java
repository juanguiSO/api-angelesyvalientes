package org.angelesyvalientes.api.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "persona")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
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
}
