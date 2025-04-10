package org.angelesyvalientes.api.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Entidad que representa una vivienda en el sistema.
 * Mapea la tabla "vivienda" en la base de datos.
 */
@Data
@Entity
@Table(name = "vivienda")
public class Vivienda {
    /**
     * Identificador único de la vivienda, generado automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nm_id_vivienda")
    private int id;

    /**
     * Dirección de la vivienda. No puede ser nula y tiene una longitud máxima de 255 caracteres.
     */
    @Column(name = "tx_direccion")
    @NotBlank(message = "La dirección de la vivienda es obligatoria.")
    @Size(max = 255, message = "La dirección de la vivienda no debe exceder los 255 caracteres.")
    private String direccion;

    /**
     * Indica si la vivienda tiene acceso a agua potable.
     */
    @Column(name = "bo_agua")
    private boolean agua;

    /**
     * Indica si la vivienda tiene acceso a electricidad.
     */
    @Column(name = "bo_luz")
    private boolean luz;

    /**
     * Indica si la vivienda tiene acceso a gas natural.
     */
    @Column(name = "bo_gas")
    private boolean gas;

    /**
     * Indica si la vivienda tiene acceso a internet.
     */
    @Column(name = "bo_internet")
    private boolean internet;

    /**
     * Indica si la vivienda tiene acceso a alcantarillado.
     */
    @Column(name = "bo_alcantarillado")
    private boolean alcantarillado;

    /**
     * Establece el identificador único de la vivienda.
     *
     * @param id El ID de la vivienda.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Establece si la vivienda tiene acceso a agua potable.
     *
     * @param agua `true` si tiene acceso, `false` en caso contrario.
     */
    public void setAgua(boolean agua) {
        this.agua = agua;
    }

    /**
     * Establece si la vivienda tiene acceso a alcantarillado.
     *
     * @param alcantarillado `true` si tiene acceso, `false` en caso contrario.
     */
    public void setAlcantarillado(boolean alcantarillado) {
        this.alcantarillado = alcantarillado;
    }

    /**
     * Establece si la vivienda tiene acceso a gas natural.
     *
     * @param gas `true` si tiene acceso, `false` en caso contrario.
     */
    public void setGas(boolean gas) {
        this.gas = gas;
    }

    /**
     * Establece si la vivienda tiene acceso a internet.
     *
     * @param internet `true` si tiene acceso, `false` en caso contrario.
     */
    public void setInternet(boolean internet) {
        this.internet = internet;
    }

    /**
     * Establece si la vivienda tiene acceso a electricidad.
     *
     * @param luz `true` si tiene acceso, `false` en caso contrario.
     */
    public void setLuz(boolean luz) {
        this.luz = luz;
    }

    /**
     * Establece la dirección de la vivienda.
     *
     * @param direccion La dirección de la vivienda.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene el identificador único de la vivienda.
     *
     * @return El ID de la vivienda.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene la dirección de la vivienda.
     *
     * @return La dirección de la vivienda.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Indica si la vivienda tiene acceso a agua potable.
     *
     * @return `true` si tiene acceso, `false` en caso contrario.
     */
    public boolean isAgua() {
        return agua;
    }

    /**
     * Indica si la vivienda tiene acceso a electricidad.
     *
     * @return `true` si tiene acceso, `false` en caso contrario.
     */
    public boolean isLuz() {
        return luz;
    }

    /**
     * Indica si la vivienda tiene acceso a gas natural.
     *
     * @return `true` si tiene acceso, `false` en caso contrario.
     */
    public boolean isGas() {
        return gas;
    }

    /**
     * Indica si la vivienda tiene acceso a internet.
     *
     * @return `true` si tiene acceso, `false` en caso contrario.
     */
    public boolean isInternet() {
        return internet;
    }

    /**
     * Indica si la vivienda tiene acceso a alcantarillado.
     *
     * @return `true` si tiene acceso, `false` en caso contrario.
     */
    public boolean isAlcantarillado() {
        return alcantarillado;
    }
}