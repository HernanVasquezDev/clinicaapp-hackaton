package co.generation.clinica.model;

import co.generation.clinica.interfaces.Registrable;

public class Paciente implements Registrable {

    private int id;
    private String cedula;
    private String nombre;
    private String apellido;
    private String telefono;

    //constructor registrar desde el menú esto se usa par avalidar y evitar espacios en blaco con trim
    public Paciente(String cedula, String nombre, String apellido, String telefono) {
        setCedula(cedula);
        setNombre(nombre);
        setApellido(apellido);
        setTelefono(telefono);
    }

    public Paciente(int id, String cedula, String nombre, String apellido, String telefono) {
        this.id = id;
        setCedula(cedula);
        setNombre(nombre);
        setApellido(apellido);
        setTelefono(telefono);
    }

    public int getId() { return id; }
    public String getCedula() { return cedula; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getTelefono() { return telefono; }

    //este set es el de validacion por id
    public void setId(int id) {
        this.id = id;
    }

    public void setCedula(String cedula) {
        if (cedula == null || cedula.trim().isEmpty()) {
            throw new IllegalArgumentException("la cedula no puede estar vacia.");
        }
        this.cedula = cedula.trim();
//aqui no debe estar el espacio en blacno = trim
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("el nombre no puede estar vacio.");
        }
        this.nombre = nombre.trim();
    }

    public void setApellido(String apellido) {
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new IllegalArgumentException("el apellido no puede estar vacio.");
        }
        this.apellido = apellido.trim();
    }

    public void setTelefono(String telefono) {
        if (telefono == null || !telefono.matches("^[0-9]{7,10}$")) {
            throw new IllegalArgumentException(
                    "el telefono debe contener solo digitos.");
        }
        this.telefono = telefono;
    }

    @Override
    public String getDatosRegistro() {
        return this.toString();
    }

    @Override
    public boolean esValido() {
        return cedula != null && !cedula.trim().isEmpty()
                && nombre != null && !nombre.trim().isEmpty()
                && apellido != null && !apellido.trim().isEmpty()
                && telefono != null && telefono.matches("^[0-9]{7,10}$");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Paciente)) return false;
        Paciente other = (Paciente) obj;
        return this.cedula.equals(other.cedula);
    }

    @Override
    public int hashCode() {
        return cedula != null ? cedula.hashCode() : 0;
    }

    @Override
    public String toString() {
        return nombre + " " + apellido + " - " + cedula + " - " + telefono;
    }
}