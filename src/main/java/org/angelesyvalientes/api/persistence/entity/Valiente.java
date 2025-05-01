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
@PrimaryKeyJoinColumn(name = "nm_id_persona")
@Getter
@Setter
@NoArgsConstructor

public class Valiente extends Persona {


    @Override
    public String toString() {
        return "Valiente{" +
                "nmIdPersona=" + getNmIdPersona() +
                ", fechaNacimiento=" + fechaNacimiento +
                ", tallaCamisa='" + tallaCamisa + '\'' +
                ", tallaPantalon='" + tallaPantalon + '\'' +
                ", tallaCalzado='" + tallaCalzado + '\'' +
                ", nombreResponsable='" + nombreResponsable + '\'' +
                ", parentescoResponsable='" + parentescoResponsable + '\'' +
                ", telefonoResponsable='" + telefonoResponsable + '\'' +
                ", urlGaleria='" + urlGaleria + '\'' +
                ", poblacionLgtbiq=" + poblacionLgtbiq +
                ", activo=" + activo +
                ", grupoEtnico=" + (grupoEtnico != null ? grupoEtnico.getId() : null) + // Mostrar ID del grupo étnico
                ", clasificacionValiente=" + (clasificacionValiente != null ? clasificacionValiente.getId() : null) + // Mostrar ID de la clasificación
                ", vivienda=" + (vivienda != null ? vivienda.getId() : null) + // Mostrar ID de la vivienda
                '}';
    }



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

    @Column(name = "bo_personas_conflicto_armado")
    private boolean poblacionConflictoArmado;

    /**
     * Indica si el valiente pertenece a la población migrante.

    @Column(name = "bo_poblacion_migrante")
    private boolean poblacionMigrante;

    /**
     * Indica si el valiente pertenece a la población joven.

    @Column(name = "bo_poblacion_jovenes")
    private boolean poblacionJoven;

    /**
     * Indica si el valiente pertenece a la población de mujeres.

    @Column(name = "bo_poblacion_mujeres")
    private boolean poblacionMujer;

    /**
     * Indica si el valiente pertenece a la población LGTBIQ+.
     */
    @Column(name = "bo_poblacion_lgtbiq")
    private boolean poblacionLgtbiq;

    /**
     * Relación muchos a uno con la entidad {@link GrupoEtnico},
     * indicando el grupo poblacional al que pertenece el valiente. No puede ser nulo.
     * La columna de unión en la tabla "valiente" es "nm_id_grupo_poblacional".
     */
    @ManyToOne
    @JoinColumn(name = "nm_id_grupo_etnico", nullable = false)
    @NotNull(message = "El grupo etnico es obligatorio.")
    private GrupoEtnico grupoEtnico;

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
    @JoinColumn(name = "nm_id_vivienda")
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

    public @NotNull(message = "La fecha de nacimiento es obligatoria.") @Past(message = "La fecha de nacimiento debe ser en el pasado.") LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public @Size(max = 10, message = "La talla de calzado no debe exceder los 10 caracteres.") String getTallaCalzado() {
        return tallaCalzado;
    }

    public @Size(max = 45, message = "El parentesco del responsable no debe exceder los 45 caracteres.") String getParentescoResponsable() {
        return parentescoResponsable;
    }

    public @Size(max = 10, message = "La talla de camisa no debe exceder los 10 caracteres.") String getTallaCamisa() {
        return tallaCamisa;
    }

    public @Size(max = 10, message = "La talla de pantalón no debe exceder los 10 caracteres.") String getTallaPantalon() {
        return tallaPantalon;
    }

    public @Size(max = 100, message = "El nombre del responsable no debe exceder los 100 caracteres.") String getNombreResponsable() {
        return nombreResponsable;
    }

    @Override
    public String getTxPrimerApellido() {
        return super.getTxPrimerApellido();
    }

    @Override
    public String getTxNumeroIdentificacion() {
        return super.getTxNumeroIdentificacion();
    }

    public @Size(max = 255, message = "La URL de la galería no debe exceder los 255 caracteres.") String getUrlGaleria() {
        return urlGaleria;
    }

    @Override
    public String getUrlFoto() {
        return super.getUrlFoto();
    }

    public @Size(max = 10, message = "El teléfono del responsable no debe exceder los 10 caracteres.") String getTelefonoResponsable() {
        return telefonoResponsable;
    }

    @Override
    public Genero getGenero() {
        return super.getGenero();
    }

    @Override
    public int getNmIdPersona() {
        return super.getNmIdPersona();
    }

    @Override
    public String getTxCorreo() {
        return super.getTxCorreo();
    }

    public @NotNull(message = "El grupo poblacional es obligatorio.") GrupoEtnico getGrupoEtnico() {
        return grupoEtnico;
    }

    public @NotNull(message = "La vivienda es obligatoria.") Vivienda getVivienda() {
        return vivienda;
    }

    @Override
    public String getTxSegundoNombre() {
        return super.getTxSegundoNombre();
    }

    public @NotNull(message = "La clasificación del valiente es obligatoria.") ClasificacionValiente getClasificacionValiente() {
        return clasificacionValiente;
    }

    @Override
    public String getTxPrimerNombre() {
        return super.getTxPrimerNombre();
    }
    public boolean isPoblacionLgtbiq() {
        return poblacionLgtbiq;
    }
/**
    public boolean isPoblacionConflictoArmado() {
        return poblacionConflictoArmado;
    }

    public boolean isPoblacionJoven() {
        return poblacionJoven;
    }



    public boolean isPoblacionMigrante() {
        return poblacionMigrante;
    }

    public boolean isPoblacionMujer() {
        return poblacionMujer;
    }*/

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

    @Override
    public TipoIdentificacion getTipoIdentificacion() {
        return super.getTipoIdentificacion();
    }

    @Override
    public String getTxSegundoApellido() {
        return super.getTxSegundoApellido();
    }

    @Override
    public String getTxTelefono() {
        return super.getTxTelefono();
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
     * @param grupoEtnico El grupo poblacional.
     */
    public void setGrupoEtnico(GrupoEtnico grupoEtnico) {
        this.grupoEtnico = grupoEtnico;
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
    @Override
    public void setGenero(Genero genero) {
        super.setGenero(genero);
    }

    /**
     * Verifica si el valiente está activo.
     *
     * @return `true` si el valiente está activo, `false` en caso contrario.
     */
    public boolean isActivo() {
        return activo;
    }


    /**
    public void setPoblacionConflictoArmado(boolean poblacionConflictoArmado) {
        this.poblacionConflictoArmado = poblacionConflictoArmado;
    }
     public void setPoblacionMigrante(boolean poblacionMigrante) {
     this.poblacionMigrante = poblacionMigrante;
     }

     public void setPoblacionMujer(boolean poblacionMujer) {
     this.poblacionMujer = poblacionMujer;
     }

    public void setPoblacionJoven(boolean poblacionJoven) {
        this.poblacionJoven = poblacionJoven;
    }*/

    @Override
    public void setTxSegundoApellido(String txSegundoApellido) {
        super.setTxSegundoApellido(txSegundoApellido);
    }

    @Override
    public void setNmIdPersona(int nmIdPersona) {
        super.setNmIdPersona(nmIdPersona);
    }

    public void setPoblacionLgtbiq(boolean poblacionLgtbiq) {
        this.poblacionLgtbiq = poblacionLgtbiq;
    }



    @Override
    public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion) {
        super.setTipoIdentificacion(tipoIdentificacion);
    }

    @Override
    public void setTxCorreo(String txCorreo) {
        super.setTxCorreo(txCorreo);
    }


    @Override
    public void setTxNumeroIdentificacion(String txNumeroIdentificacion) {
        super.setTxNumeroIdentificacion(txNumeroIdentificacion);
    }

    @Override
    public void setTxPrimerApellido(String txPrimerApellido) {
        super.setTxPrimerApellido(txPrimerApellido);
    }

    @Override
    public void setTxPrimerNombre(String txPrimerNombre) {
        super.setTxPrimerNombre(txPrimerNombre);
    }

    @Override
    public void setTxSegundoNombre(String txSegundoNombre) {
        super.setTxSegundoNombre(txSegundoNombre);
    }

    @Override
    public void setTxTelefono(String txTelefono) {
        super.setTxTelefono(txTelefono);
    }

    @Override
    public void setUrlFoto(String urlFoto) {
        super.setUrlFoto(urlFoto);
    }


}