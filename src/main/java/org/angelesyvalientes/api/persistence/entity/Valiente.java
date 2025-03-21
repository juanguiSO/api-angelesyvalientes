package org.angelesyvalientes.api.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "valiente")
@Getter
@Setter
@NoArgsConstructor
public class Valiente extends Persona {

    @Column(name = "fe_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "tx_talla_camisa")
    private String tallaCamisa;

    @Column(name = "tx_talla_pantalon")
    private String tallaPantalon;

    @Column(name = "tx_talla_calzado")
    private String tallaCalzado;

    @Column(name = "tx_nombre_responsable")
    private String nombreResponsable;

    @Column(name = "tx_parentesco_responsable")
    private String parentescoResponsable;

    @Column(name = "tx_telefono_responsable")
    private String telefonoResponsable;

    @Column(name = "tx_url_galeria")
    private String urlGaleria;

    @Column(name = "bo_personas_conflicto_armado")
    private boolean poblacionConflictoArmado;

    @Column(name = "bo_poblacion_migrante")
    private boolean poblacionMigrante;

    @Column(name = "bo_poblacion_jovenes")
    private boolean poblacionJoven;

    @Column(name = "bo_poblacion_mujeres")
    private boolean poblacionMujer;

    @Column(name = "bo_poblacion_lgtbiq")
    private boolean poblacionLgtbiq;

    @ManyToOne
    @JoinColumn(name = "nm_id_grupo_poblacional", nullable = false)
    private GrupoPoblacional grupoPoblacional;

    @ManyToOne
    @JoinColumn(name = "nm_id_clasificacion_valiente", nullable = false)
    private ClasificacionValiente clasificacionValiente;

    @ManyToOne
    @JoinColumn(name = "nm_id_vivienda", nullable = false)
    private Vivienda vivienda;
}
