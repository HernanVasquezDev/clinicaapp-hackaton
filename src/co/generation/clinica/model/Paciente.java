package co.generation.clinica.model;

import co.generation.clinica.interfaces.Registrable;

public class Paciente implements Registrable {

    private int id;
    private String cedula;
    private String nombre;
    private String apellido;
    private String telefono;

    //constructor csv
    public Paciente(int id, String cedula, String nombre, String apellido, String telefono) {
        this(cedula, nombre, apellido, telefono);
        this.id = id;
    }

    //constructor modulo
    public Paciente(String cedula, String nombre, String apellido, String telefono) {
        setCedula(cedula);
        setNombre(nombre);
        setApellido(apellido);
        setTelefono(telefono);
    }

    public int getId() {
        return id;
    }

    public String getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCedula(String cedula) {

        if (cedula == null || cedula.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "La cédula no puede estar vacía."
            );
        }

        this.cedula = cedula.trim();
    }

    public void setNombre(String nombre) {

        if (nombre == null || nombre.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "El nombre no puede estar vacío."
            );
        }

        this.nombre = nombre.trim();
    }

    public void setApellido(String apellido) {

        if (apellido == null || apellido.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "El apellido no puede estar vacío."
            );
        }

        this.apellido = apellido.trim();
    }

    public void setTelefono(String telefono) {

        if (telefono == null ||
                !telefono.matches("^[0-9]{7,10}$")) {

            throw new IllegalArgumentException(
                    "El teléfono debe contener entre 7 y 10 dígitos."
            );
        }

        this.telefono = telefono.trim();
    }

    @Override
    public String getDatosRegistro() {

        return toString();
    }

    @Override
    public boolean esValido() {

        return cedula != null
                && !cedula.isBlank()
                && nombre != null
                && !nombre.isBlank()
                && apellido != null
                && !apellido.isBlank()
                && telefono != null
                && telefono.matches("^[0-9]{7,10}$");
    }


    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (!(obj instanceof Paciente)) return false;

        Paciente other = (Paciente) obj;

        return cedula.equals(other.cedula);
    }

    @Override
    public int hashCode() {

        return cedula.hashCode();
    }

    @Override
    public String toString() {

        return nombre + " "
                + apellido + " - "
                + cedula + " - "
                + telefono;
    }
}