package co.generation.clinica.model;

import java.time.LocalDateTime;

public class Turno {
    private int id;
    private Paciente paciente;
    private Medico medico;
    private LocalDateTime fechaHora;
    private EstadoTurno estado;

    //constructor CSV
    public Turno(Paciente paciente, Medico medico, LocalDateTime fechaHora) {
        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.fechaHora = fechaHora;
        this.estado = estado != null ? estado : EstadoTurno.PENDIENTE;
    }
    //constructor consola
    public Turno(Paciente paciente, Medico medico, LocalDateTime fechaHora, EstadoTurno estado) {
        this.paciente = paciente;
        this.medico = medico;
        this.fechaHora = fechaHora;
        this.estado = EstadoTurno.PENDIENTE;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        if(paciente == null){
            throw new IllegalArgumentException("El campo paciente no puede estar vacio.");
        }
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        if(medico == null){
            throw new IllegalArgumentException("El campo medico no puede estar vacio.");
        }
            this.medico = medico;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        if(fechaHora == null){
            throw new IllegalArgumentException("El campo fechaHora no puede estar vacio.");
        }
        this.fechaHora = fechaHora;
    }

    public EstadoTurno getEstado() {
        return estado;
    }

    public void setEstado(EstadoTurno estado) {
        if(estado == null){
            throw new IllegalArgumentException("El campo estado no puede estar vacio.");
        }
        this.estado = estado;
    }
    //esta asignacion nos permite verificar que el medico no este asignado dos veces al mismo turno
    public boolean esMismoHorario(Turno otro){
        return medico.equals(otro.getMedico())
                && fechaHora.equals(otro.getFechaHora());
    }

    @Override
    //controlar que el medico no este asignado dos veces al mismo turno en la misma hora.
    public boolean equals(Object obj) {
        if(this == obj)return true;
        if(!(obj instanceof Turno))return false;

        Turno other = (Turno) obj;

        return this.medico.equals(other.medico)
            && this.fechaHora.equals(other.fechaHora);
    }

    @Override
    public String toString() {
        return "[" + estado + "]"
        + paciente + "| -"
               + "-|" + medico.getNombre() + " " + medico.getApellido()
        + "(" + medico.getEspecialidad() + ") |" + fechaHora;
    }
}
