package co.generation.clinica.model;

import co.generation.clinica.interfaces.Registrable;

public class Medico implements Registrable {

    private int id;
    private String nombre;
    private String apellido;
    private Especialidad especialidad;

    public Medico(int i, String nombre, String apellido, Especialidad especialidad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
    }

    public Medico(String nombre, String apellido, Especialidad especialidad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id no valido!");
        } else {
            this.id = id;
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede se nulo ni vacio!");
        } else {
            this.nombre = nombre;
        }
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede se nulo ni vacio!");
        } else {
            this.apellido = apellido;
        }
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        if(especialidad == null) {
            throw new IllegalArgumentException("La especialidad no puede ser nula!");
        } else {
            this.especialidad = especialidad;
        }
    }

    @Override
    public String getDatosRegistro(){
        return "Dr." + nombre + " " + apellido + " - " + especialidad;
    }

    @Override
    public boolean esValido(){
        return nombre != null && apellido != null && especialidad != null;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Medico medico = (Medico) object;
        return id == medico.id && java.util.Objects.equals(nombre, medico.nombre) && java.util.Objects.equals(apellido, medico.apellido) && java.util.Objects.equals(especialidad, medico.especialidad);
    }

    @Override
    public String toString() {
        return "Medico{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", especialidad=" + especialidad +
                '}';
    }
}
