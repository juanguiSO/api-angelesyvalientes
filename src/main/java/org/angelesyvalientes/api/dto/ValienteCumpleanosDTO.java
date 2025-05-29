package org.angelesyvalientes.api.dto;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValienteCumpleanosDTO {

    private int idPersona;
    private String segundoNombre;
    private String primerNombre;
    private String primerApellido;
    private String segundoApellido;
    private LocalDate fechaNacimiento;


    public ValienteCumpleanosDTO(int idPersona, String primerNombre, String segundoNombre,
                                 String primerApellido, String segundoApellido, LocalDate fechaNacimiento) {
        this.idPersona = idPersona;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.fechaNacimiento = fechaNacimiento;
    }


    public String getPrimerNombre() {
        return primerNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }
}