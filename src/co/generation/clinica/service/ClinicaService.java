package co.generation.clinica.service;
import co.generation.clinica.interfaces.Consultable;
import co.generation.clinica.model.*;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;

public class ClinicaService implements Consultable {

    private int generarIdPaciente(){
        return pacientes.stream()
                .mapToInt(Paciente::getId)
                .max()
                .orElse(0) + 1;
    }

    private int generarIdMedico(){
        return medicos.stream()
                .mapToInt(Medico::getId)
                .max()
                .orElse(0) + 1;
    }

    private int generarIdTurno(){
        return turnos.stream()
                .mapToInt(Turno::getId)
                .max()
                .orElse(0) + 1;
    }


    List<Paciente> pacientes = new ArrayList<>();
    List<Medico> medicos = new ArrayList<>();
    List<Turno> turnos = new ArrayList<>();

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public List<Medico> getMedicos() {
        return medicos;
    }

    public List<Turno> getTurnos() {
        return turnos;
    }

    //Pacientes format

    public void Registrarpaciente(Paciente p){

        if(!p.esValido()){
            System.out.println("Paciente no registrado.");
            return;
        }

        if(buscarPorCedula(p.getCedula()) !=null) {
            System.out.println("Ya existe un paciente con este documento.");
            return;
        }

        p.setId(generarIdPaciente());
        pacientes.add(p);

        System.out.println("Paciente registrado.");

    }

    public Paciente buscarPorCedula(String cedula){
        for(Paciente p : pacientes){
            if(p.getCedula().equals(cedula)){
                return p;
            }
        }
        return null;
    }


    public void listarPacientes(){
        if(pacientes.isEmpty()){
            System.out.println("No hay paciente registrados.");
            return;
        }
        List <Paciente> ordenados = new ArrayList<>(pacientes);

        ordenados.sort(Comparator
                .comparing(Paciente::getApellido)
                .thenComparing(Paciente::getNombre));
        for (Paciente p : ordenados) {
            System.out.println(p);
        }
    }

    //Medico
    public void registrarMedico(Medico m){
        if(!medicos.isEmpty()){
            System.out.println("Medico no registrado");
            return;
        }

        if(medicos.contains(m)){
            System.out.println("Medico existente");
            return;
        }

        m.setId(generarIdMedico());
        medicos.add(m);
        System.out.println("Medico registrado" + m);
    }
    public Medico buscarPorNombreApellido(String nombre, String apellido){
        for(Medico m : medicos){
            if(m.getNombre().equalsIgnoreCase(nombre) && m.getApellido().equalsIgnoreCase(apellido)){
                return m;
            }
        }
        return null;
    }

    public void listarMedicos(){
        if(medicos.isEmpty()){
            System.out.println("No hay medico registrados.");
            return;
        }

        List <Medico> ordenados = new ArrayList<>(medicos);

        ordenados.sort(Comparator
                .comparing(Medico::getEspecialidad)
                .thenComparing(Medico::getApellido));

        for (Medico m : ordenados) {
            System.out.println(m);
        }
    }

    //Turnos
    public void AsignarTurno(Turno t){

        if(t.getPaciente() == null || t.getMedico() == null){
            System.out.println("Paciente o Medico no registrado");
        }

        if(turnos.contains(t)){
            System.out.println("El medico ya tiene un turno asignado en este horario");
        }

        t.setId(generarIdTurno());
        turnos.add(t);
        System.out.println("Turno asignado con exito." + t);
    }
    public void cancelarTurno(int idTurno){

        Turno t = buscarTurnoPorId(idTurno);
        if(t == null){
            System.out.println("Turno no encontrado");
        }

        if(t.getEstado() != EstadoTurno.PENDIENTE ){
            System.out.println("no se puede cancelar un turno ya atendido");
        }
        t.setEstado(EstadoTurno.CANCELADO);
        System.out.println("turno cancelado.");
    }

    public void cambiarEstadoTurno(int idTurno, EstadoTurno nuevo){
        Turno t = buscarTurnoPorId(idTurno);

        if(t == null){
            System.out.println("Turno no encontrado");
        }
        t.setEstado(nuevo);
        System.out.println("Estado fue actualizado con exito: " + nuevo);
    }

    private Turno buscarTurnoPorId(int idTurno) {

        for(Turno t : turnos){
            if(t.getId() == idTurno){
                return t;
            }
        }
        return null;
    }

    //interfaces consultable
    @Override
    public List<Turno> buscarPorPaciente(Paciente paciente) {
        List<Turno> res = new ArrayList<>();

        for(Turno t : turnos){
            if(t.getPaciente().equals(paciente)){
                res.add(t);
                }
        }
        return res;
    }
    @Override
    public List<Turno> buscarPorMedico(Medico medico) {
        List<Turno> res = new ArrayList<>();

        for(Turno t : turnos){
            if(t.getMedico().equals(medico)){
                res.add(t);
            }
        }
        return res;
    }
    @Override
    public List<Turno> listarTurnosDelDia(LocalDate fecha) {
        List<Turno> res = new ArrayList<>();

        for(Turno t : turnos){
            if(t.getFechaHora().equals(fecha)){
                res.add(t);
            }
        }
        return res;
    }

}
