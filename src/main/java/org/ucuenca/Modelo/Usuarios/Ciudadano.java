package org.ucuenca.Modelo.Usuarios;

import org.ucuenca.Modelo.Documentos.Cedula;
import org.ucuenca.Modelo.Documentos.Plantillas.DocumentoLegal;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class Ciudadano implements Serializable {
    /**
     * Separados por espacio
     */
    String nombres;
    /**
     * Separados por espacio
     */
    String apellidos;
    String ocupacion;
    Sexo sexo;

    /**
     * Año / Mes / Día
     */
    LocalDate fechaNacimiento;

    EstadoCivil estadoCivil;

    boolean vivo;

    int edad;

    Provincia provinciaNacimiento;

    Ciudadano madre;
    Ciudadano padre;

    TipoSangre tipoSangre;

    Cedula cedula;

    Collection<DocumentoLegal> documentosAsociados;


    //TODO Borrar estos constructores
    public Ciudadano(boolean vivo) {
        this.vivo = vivo;
    }

    public Ciudadano(EstadoCivil estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public Ciudadano(
            String nombres, String apellidos,
            String ocupacion, Sexo sexo,
            LocalDate fechaNacimiento, EstadoCivil estadoCivil,
            boolean vivo, int edad,
            Provincia provinciaNacimiento, Cedula cedula) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.ocupacion = ocupacion;
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
        this.estadoCivil = estadoCivil;
        this.vivo = vivo;
        this.edad = edad;
        this.provinciaNacimiento = provinciaNacimiento;
        this.cedula = cedula;
        this.documentosAsociados = new ArrayList<>();
    }

    public Ciudadano(String nombres, String apellidos, String ocupacion, Sexo sexo, LocalDate fechaNacimiento, EstadoCivil estadoCivil, boolean vivo, int edad, Provincia provinciaNacimiento, Ciudadano madre, Ciudadano padre, TipoSangre tipoSangre, Cedula cedula) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.ocupacion = ocupacion;
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
        this.estadoCivil = estadoCivil;
        this.vivo = vivo;
        this.edad = edad;
        this.provinciaNacimiento = provinciaNacimiento;
        this.madre = madre;
        this.padre = padre;
        this.tipoSangre = tipoSangre;
        this.cedula = cedula;
        this.documentosAsociados = new ArrayList<>();
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public EstadoCivil getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(EstadoCivil estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public boolean estaVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    public Provincia getProvinciaNacimiento() {
        return provinciaNacimiento;
    }

    public void setProvinciaNacimiento(Provincia provinciaNacimiento) {
        this.provinciaNacimiento = provinciaNacimiento;
    }

    public Ciudadano getMadre() {
        return madre;
    }

    public void setMadre(Ciudadano madre) {
        this.madre = madre;
    }

    public Ciudadano getPadre() {
        return padre;
    }

    public void setPadre(Ciudadano padre) {
        this.padre = padre;
    }

    public TipoSangre getTipoSangre() {
        return tipoSangre;
    }

    public void setTipoSangre(TipoSangre tipoSangre) {
        this.tipoSangre = tipoSangre;
    }

    public Cedula getCedula() {
        return cedula;
    }

    public void setCedula(Cedula cedula) {
        this.cedula = cedula;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public Collection<DocumentoLegal> getDocumentosAsociados() {
        return documentosAsociados;
    }

    public void setDocumentosAsociados(Collection<DocumentoLegal> documentosAsociados) {
        this.documentosAsociados = documentosAsociados;
    }


    /**
     * Formato: Cédula Nombre Apellido*/
    @Override
    public String toString() {
        return this.getCedula().getIdUnico() + " : " +
                this.getNombres().split(" ")[0] + " " +
                this.getApellidos().split(" ")[0];
    }
}
