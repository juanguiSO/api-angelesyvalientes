package org.angelesyvalientes.api.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "acompanante")
@Setter
@Getter
public class Acompanante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nm_id_acompanante")
    private int idAcompanante;

    @ManyToOne
    @JoinColumn(name = "nm_id_persona")
    private Persona persona;

    @Column(name = "tx_nombre_acompanante", length = 45)
    private String nombreAcompanante;

    @Column(name = "tx_telefono_acompanante", length = 20) // Ajusta la longitud según sea necesario
    private String telefonoAcompanante;
}