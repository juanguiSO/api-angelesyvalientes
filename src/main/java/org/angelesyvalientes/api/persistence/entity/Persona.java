package org.angelesyvalientes.api.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidad base que representa a una persona en el sistema.
 * Utiliza la estrategia de herencia JOINED, lo que significa que los atributos
 * específicos de las subclases se almacenan en tablas separadas unidas por la
 * clave primaria. Mapea la tabla "persona" en la base de datos.
 */
@Data
@Entity
@Table(name = "persona")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public class Persona {
    /**
     * Identificador único de la persona, generado automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nm_id_persona")
    private int nmIdPersona;

    /**
     * Relación muchos a uno con la entidad {@link Genero},
     * indicando el género de la persona. No puede ser nulo.
     * La columna de unión en la tabla "persona" es "nm_id_genero".
     */
    @ManyToOne
    @JoinColumn(name = "nm_id_genero", nullable = false)
    @NotNull(message = "El género es obligatorio.")
    private Genero genero;

    /**
     * Relación muchos a uno con la entidad {@link TipoIdentificacion},
     * indicando el tipo de identificación de la persona. No puede ser nulo.
     * La columna de unión en la tabla "persona" es "nm_tipo_identificacion".
     */
    @ManyToOne
    @JoinColumn(name = "nm_tipo_identificacion", nullable = false)
    @NotNull(message = "El tipo de identificación es obligatorio.")
    private TipoIdentificacion tipoIdentificacion;

    /**
     * Primer nombre de la persona. No puede ser nulo y tiene una longitud máxima de 45 caracteres.
     */
    @Column(name = "tx_primer_nombre", nullable = false, length = 45)
    @NotBlank(message = "El primer nombre es obligatorio.")
    @Size(max = 45, message = "El primer nombre no debe exceder los 45 caracteres.")
    private String txPrimerNombre;

    /**
     * Segundo nombre de la persona (opcional). Tiene una longitud máxima de 45 caracteres.
     */
    @Column(name = "tx_segundo_nombre", length = 45)
    @Size(max = 45, message = "El segundo nombre no debe exceder los 45 caracteres.")
    private String txSegundoNombre;

    /**
     * Primer apellido de la persona. No puede ser nulo y tiene una longitud máxima de 45 caracteres.
     */
    @Column(name = "tx_primer_apellido", nullable = false, length = 45)
    @NotBlank(message = "El primer apellido es obligatorio.")
    @Size(max = 45, message = "El primer apellido no debe exceder los 45 caracteres.")
    private String txPrimerApellido;

    /**
     * Segundo apellido de la persona (opcional). Tiene una longitud máxima de 45 caracteres.
     */
    @Column(name = "tx_segundo_apellido", length = 45)
    @Size(max = 45, message = "El segundo apellido no debe exceder los 45 caracteres.")
    private String txSegundoApellido;

    /**
     * Número de teléfono de la persona. No puede ser nulo y tiene una longitud máxima de 10 caracteres.
     */
    @Column(name = "tx_telefono", nullable = false, length = 10)
    @NotBlank(message = "El número de teléfono es obligatorio.")
    @Size(max = 10, message = "El número de teléfono no debe exceder los 10 caracteres.")
    private String txTelefono;

    /**
     * Correo electrónico de la persona (opcional). Tiene una longitud máxima de 45 caracteres.
     */
    @Column(name = "tx_correo", length = 45)
    @Size(max = 45, message = "El correo electrónico no debe exceder los 45 caracteres.")
    @Email(message = "El correo electrónico debe tener un formato válido.")
    private String txCorreo;

    /**
     * Número de identificación de la persona. Tiene una longitud máxima de 15 caracteres.
     */
    @Column(name = "tx_numero_identificacion", length = 15)
    @Size(max = 15, message = "El número de identificación no debe exceder los 15 caracteres.")
    private String txNumeroIdentificacion;

    /**
     * URL de la foto de perfil de la persona (opcional).
     */
    @Column(name = "tx_url_foto_perfil")
    private String urlFoto;

    /**
     * Establece la URL de la foto de perfil de la persona.
     *
     * @param urlFoto La URL de la foto de perfil.
     */
    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    /**
     * Obtiene la URL de la foto de perfil de la persona.
     *
     * @return La URL de la foto de perfil.
     */
    public String getUrlFoto() {
        return urlFoto;
    }

    /**
     * Obtiene el número de identificación de la persona.
     *
     * @return El número de identificación.
     */
    public String getTxNumeroIdentificacion() {
        return txNumeroIdentificacion;
    }

    /**
     * Obtiene el correo electrónico de la persona.
     *
     * @return El correo electrónico.
     */
    public String getTxCorreo() {
        return txCorreo;
    }

    /**
     * Obtiene el número de teléfono de la persona.
     *
     * @return El número de teléfono.
     */
    public String getTxTelefono() {
        return txTelefono;
    }

    /**
     * Obtiene el identificador único de la persona.
     *
     * @return El ID de la persona.
     */
    public int getNmIdPersona() {
        return nmIdPersona;
    }

    /**
     * Obtiene el primer apellido de la persona.
     *
     * @return El primer apellido.
     */
    public String getTxPrimerApellido() {
        return txPrimerApellido;
    }

    /**
     * Obtiene el segundo nombre de la persona.
     *
     * @return El segundo nombre.
     */
    public String getTxSegundoNombre() {
        return txSegundoNombre;
    }

    /**
     * Obtiene el segundo apellido de la persona.
     *
     * @return El segundo apellido.
     */
    public String getTxSegundoApellido() {
        return txSegundoApellido;
    }

    /**
     * Obtiene el primer nombre de la persona.
     *
     * @return El primer nombre.
     */
    public String getTxPrimerNombre() {
        return txPrimerNombre;
    }

    /**
     * Obtiene el tipo de identificación de la persona.
     *
     * @return La entidad {@link TipoIdentificacion}.
     */
    public TipoIdentificacion getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * Obtiene el género de la persona.
     *
     * @return La entidad {@link Genero}.
     */
    public Genero getGenero() {
        return genero;
    }

    /**
     * Establece el número de teléfono de la persona.
     *
     * @param txTelefono El número de teléfono.
     */
    public void setTxTelefono(String txTelefono) {
        this.txTelefono = txTelefono;
    }

    /**
     * Establece el segundo apellido de la persona.
     *
     * @param txSegundoApellido El segundo apellido.
     */
    public void setTxSegundoApellido(String txSegundoApellido) {
        this.txSegundoApellido = txSegundoApellido;
    }

    /**
     * Establece el correo electrónico de la persona.
     *
     * @param txCorreo El correo electrónico.
     */
    public void setTxCorreo(String txCorreo) {
        this.txCorreo = txCorreo;
    }

    /**
     * Establece el identificador único de la persona.
     *
     * @param nmIdPersona El ID de la persona.
     */
    public void setNmIdPersona(int nmIdPersona) {
        this.nmIdPersona = nmIdPersona;
    }

    /**
     * Establece el primer apellido de la persona.
     *
     * @param txPrimerApellido El primer apellido.
     */
    public void setTxPrimerApellido(String txPrimerApellido) {
        this.txPrimerApellido = txPrimerApellido;
    }

    /**
     * Establece el primer nombre de la persona.
     *
     * @param txPrimerNombre El primer nombre.
     */
    public void setTxPrimerNombre(String txPrimerNombre) {
        this.txPrimerNombre = txPrimerNombre;
    }

    /**
     * Establece el número de identificación de la persona.
     *
     * @param txNumeroIdentificacion El número de identificación.
     */
    public void setTxNumeroIdentificacion(String txNumeroIdentificacion) {
        this.txNumeroIdentificacion = txNumeroIdentificacion;
    }

    /**
     * Establece el segundo nombre de la persona.
     *
     * @param txSegundoNombre El segundo nombre.
     */
    public void setTxSegundoNombre(String txSegundoNombre) {
        this.txSegundoNombre = txSegundoNombre;
    }

    /**
     * Establece el tipo de identificación de la persona.
     *
     * @param tipoIdentificacion La entidad {@link TipoIdentificacion}.
     */
    public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * Establece el género de la persona.
     *
     * @param genero La entidad {@link Genero}.
     */
    public void setGenero(Genero genero) {
        this.genero = genero;
    }
}