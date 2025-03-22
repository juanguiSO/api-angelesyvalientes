package org.angelesyvalientes.api.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "informe_clinico")

public class InformeClinico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nm_id_informe_clinico")
    private Integer idInformeClinico;

    @ManyToOne
    @JoinColumn(name = "nm_id_persona")
    private Persona persona;

    @Column(name = "fe_fecha")
    private LocalDate fecha;

    @Column(name = "tx_tipo_informe", length = 255)
    private String tipoInforme;

    @Column(name = "tx_profesional", length = 45)
    private String profesional;

    @Column(name = "tx_url_pdf", length = 45)
    private String urlPdf;
}