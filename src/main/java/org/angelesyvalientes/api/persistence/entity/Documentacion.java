package org.angelesyvalientes.api.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "documentacion")
@Getter
@Setter
public class Documentacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nm_id_documentacion")
    private Integer idDocumentacion;

    @ManyToOne
    @JoinColumn(name = "nm_id_persona")
    private Persona persona;

    @Column(name = "tx_tipo_documentacion", length = 255)
    private String tipoDocumentacion;

    @Column(name = "tx_url_pdf", length = 45)
    private String urlPdf;

    @Column(name = "fe_fecha")
    private LocalDate fecha;
}