package org.angelesyvalientes.api.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Entidad que representa a un valiente (beneficiario) en el sistema.
 * Hereda de la entidad {@link Persona} y mapea la tabla "valiente" en la base de datos.
 */
@Entity
@Table(name = "valiente")
@Getter
@Setter
@NoArgsConstructor
public class Valiente extends Persona {

    /**
     * Fecha de nacimiento del valiente. No puede ser nula y debe ser en el pasado.
     */
    @Column(name = "fe_nacimiento", nullable = false)
    @NotNull(message = "La fecha de nacimiento es obligatoria.")
    @Past(message = "La fecha de nacimiento debe ser en el pasado.")
    private LocalDate fechaNacimiento;

    /**
     * Talla de camisa del valiente (opcional).
     */
    @Column(name = "tx_talla_camisa")
    @Size(max = 10, message = "La talla de camisa no debe exceder los 10 caracteres.")
    private String tallaCamisa;

    /**
     * Talla de pantalón del valiente (opcional).
     */
    @Column(name = "tx_talla_pantalon")
    @Size(max = 10, message = "La talla de pantalón no debe exceder los 10 caracteres.")
    private String tallaPantalon;

    /**
     * Talla de calzado del valiente (opcional).
     */
    @Column(name = "tx_talla_calzado")
    @Size(max = 10, message = "La talla de calzado no debe exceder los 10 caracteres.")
    private String tallaCalzado;

    /**
     * Nombre del responsable del valiente (opcional).
     */
    @Column(name = "tx_nombre_responsable")
    @Size(max = 100, message = "El nombre del responsable no debe exceder los 100 caracteres.")
    private String nombreResponsable;

    /**
     * Parentesco del responsable con el valiente (opcional).
     */
    @Column(name = "tx_parentesco_responsable")
    @Size(max = 45, message = "El parentesco del responsable no debe exceder los 45 caracteres.")
    private String parentescoResponsable;

    /**
     * Teléfono del responsable del valiente (opcional).
     */
    @Column(name = "tx_telefono_responsable")
    @Size(max = 10, message = "El teléfono del responsable no debe exceder los 10 caracteres.")
    private String telefonoResponsable;

    /**
     * URL de la galería de imágenes del valiente (opcional).
     */
    @Column(name = "tx_url_galeria")
    @Size(max = 255, message = "La URL de la galería no debe exceder los 255 caracteres.")
    private String urlGaleria;

    /**
     * Indica si el valiente pertenece a la población afectada por el conflicto armado.
     */
    @Column(name = "bo_personas_conflicto_armado")
    private boolean poblacionConflictoArmado;

    /**
     * Indica si el valiente pertenece a la población migrante.
     */
    @Column(name = "bo_poblacion_migrante")
    private boolean poblacionMigrante;

    /**
     * Indica si el valiente pertenece a la población joven.
     */
    @Column(name = "bo_poblacion_jovenes")
    private boolean poblacionJoven;

    /**
     * Indica si el valiente pertenece a la población de mujeres.
     */
    @Column(name = "bo_poblacion_mujeres")
    private boolean poblacionMujer;

    /**
     * Indica si el valiente pertenece a la población LGTBIQ+.
     */
    @Column(name = "bo_poblacion_lgtbiq")
    private boolean poblacionLgtbiq;

    /**
     * Relación muchos a uno con la entidad {@link GrupoPoblacional},
     * indicando el grupo poblacional al que pertenece el valiente. No puede ser nulo.
     * La columna de unión en la tabla "valiente" es "nm_id_grupo_poblacional".
     */
    @ManyToOne
    @JoinColumn(name = "nm_id_grupo_poblacional", nullable = false)
    @NotNull(message = "El grupo poblacional es obligatorio.")
    private GrupoPoblacional grupoPoblacional;

    /**
     * Relación muchos a uno con la entidad {@link ClasificacionValiente},
     * indicando la clasificación del valiente. No puede ser nulo.
     * La columna de unión en la tabla "valiente" es "nm_id_clasificacion_valiente".
     */
    @ManyToOne
    @JoinColumn(name = "nm_id_clasificacion_valiente", nullable = false)
    @NotNull(message = "La clasificación del valiente es obligatoria.")
    private ClasificacionValiente clasificacionValiente;

    /**
     * Relación muchos a uno con la entidad {@link Vivienda},
     * indicando la vivienda a la que está asociado el valiente. No puede ser nulo.
     * La columna de unión en la tabla "valiente" es "nm_id_vivienda".
     */
    @ManyToOne
    @JoinColumn(name = "nm_id_vivienda", nullable = false)
    @NotNull(message = "La vivienda es obligatoria.")
    private Vivienda vivienda;

    /**
     * Indica si el valiente está activo (true) o inactivo (false).
     * Por defecto, su valor es true.
     */
    @Column(name = "bo_activo", nullable = false, columnDefinition = "boolean default true")
    private boolean activo = true;

    /**
     * Establece la URL de la galería de imágenes del valiente.
     *
     * @param urlGaleria La URL de la galería.
     */
    public void setUrlGaleria(String urlGaleria) {
        this.urlGaleria = urlGaleria;
    }

    /**
     * Establece el estado de actividad del valiente.
     *
     * @param activo `true` si el valiente está activo, `false` en caso contrario.
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    /**
     * Establece la fecha de nacimiento del valiente.
     *
     * @param fechaNacimiento La fecha de nacimiento.
     */
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Establece el nombre del responsable del valiente.
     *
     * @param nombreResponsable El nombre del responsable.
     */
    public void setNombreResponsable(String nombreResponsable) {
        this.nombreResponsable = nombreResponsable;
    }

    /**
     * Establece la talla de calzado del valiente.
     *
     * @param tallaCalzado La talla de calzado.
     */
    public void setTallaCalzado(String tallaCalzado) {
        this.tallaCalzado = tallaCalzado;
    }

    /**
     * Establece la talla de camisa del valiente.
     *
     * @param tallaCamisa La talla de camisa.
     */
    public void setTallaCamisa(String tallaCamisa) {
        this.tallaCamisa = tallaCamisa;
    }

    /**
     * Establece la talla de pantalón del valiente.
     *
     * @param tallaPantalon La talla de pantalón.
     */
    public void setTallaPantalon(String tallaPantalon) {
        this.tallaPantalon = tallaPantalon;
    }

    /**
     * Establece el parentesco del responsable con el valiente.
     *
     * @param parentescoResponsable El parentesco del responsable.
     */
    public void setParentescoResponsable(String parentescoResponsable) {
        this.parentescoResponsable = parentescoResponsable;
    }

    /**
     * Establece el teléfono del responsable del valiente.
     *
     * @param telefonoResponsable El teléfono del responsable.
     */
    public void setTelefonoResponsable(String telefonoResponsable) {
        this.telefonoResponsable = telefonoResponsable;
    }

    /**
     * Establece el grupo poblacional al que pertenece el valiente.
     *
     * @param grupoPoblacional El grupo poblacional.
     */
    public void setGrupoPoblacional(GrupoPoblacional grupoPoblacional) {
        this.grupoPoblacional = grupoPoblacional;
    }

    /**
     * Establece la clasificación del valiente.
     *
     * @param clasificacionValiente La clasificación del valiente.
     */
    public void setClasificacionValiente(ClasificacionValiente clasificacionValiente) {
        this.clasificacionValiente = clasificacionValiente;
    }

    /**
     * Establece la vivienda a la que está asociado el valiente.
     *
     * @param vivienda La vivienda del valiente.
     */
    public void setVivienda(Vivienda vivienda) {
        this.vivienda = vivienda;
    }

    /**
     * Verifica si el valiente está activo.
     *
     * @return `true` si el valiente está activo, `false` en caso contrario.
     */
    public boolean isActivo() {
        return activo;
    }
}