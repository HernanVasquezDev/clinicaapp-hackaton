package co.generation.clinica;
import co.generation.clinica.datos.DatosCSV;
import co.generation.clinica.model.*;
import co.generation.clinica.service.ClinicaService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static ClinicaService servicio = new ClinicaService();
    public static Especialidad especialidad;
    public static void main(String[] args) {

        DatosCSV.cargar(servicio);

        while(true){
            mostrarMenu();

            try{
                int  opcion = Integer.parseInt(sc.nextLine());
                switch (opcion){

                    case 1 -> {
                        System.out.println("Cedula del paciente: ");
                        String cedula = sc.nextLine();

                        System.out.println("Nombre del paciente: ");
                        String nombre = sc.nextLine();

                        System.out.println("Apellido del paciente: ");
                        String apellido = sc.nextLine();

                        System.out.println("Telefono del paciente: ");
                        String telefono = sc.nextLine();

                        Paciente paciente = new Paciente(
                                cedula,
                                nombre,
                                apellido,
                                telefono
                        );

                        servicio.Registrarpaciente(paciente);
                    }

                    case 2 -> {
                        System.out.println("Nombre del Medico: ");
                        String nombre = sc.nextLine();
                        System.out.println("Apellido del medico");
                        String apellido = sc.nextLine();

                        System.out.println("""
                                
                                        Especialidad del medico:
                                
                                       1. GENERAL,
                                       2. PEDIATRA,
                                       3. CARDIOLOGIA,
                                       4. URGENCIAS
                                
                                """);
                        System.out.println("Seleccione una opcion: ");
                        int esp = Integer.parseInt(sc.nextLine());

                        Especialidad especialidad = switch (esp) {
                            case 1 -> Especialidad.GENERAL;
                            case 2 -> Especialidad.PEDIATRA;
                            case 3 -> Especialidad.CARDIOLOGIA;
                            case 4 -> Especialidad.URGENCIAS;
                            default -> throw new IllegalArgumentException("La especialidad no existe");
                        };
                        Medico medico = new Medico(
                                nombre,
                                apellido,
                                especialidad
                        );
                        servicio.registrarMedico(medico);
                    }
                    case 3 -> {
                        System.out.println("Cedula del paciente: ");
                        String cedula = sc.nextLine();
                        Paciente paciente = servicio.buscarPorCedula(cedula);
                        if(paciente == null){
                            System.out.println("Paciente no encontrado");
                            continue;
                        }

                        System.out.println("Nombre del medico: ");
                        String nombre = sc.nextLine();

                        System.out.println("Apellido del medico: ");
                        String apellido = sc.nextLine();

                        Medico medico =
                                servicio.buscarPorNombreApellido(
                                nombre,
                                apellido
                        );

                        if(medico == null){
                            System.out.println("Medico no encontrado");
                            continue;
                        }
                        System.out.println("Año: ");
                        int anio = Integer.parseInt(sc.nextLine());

                        System.out.println("Mes: ");
                        int mes = Integer.parseInt(sc.nextLine());

                        System.out.println("Dia: ");
                        int dia = Integer.parseInt(sc.nextLine());

                        System.out.println("Hora: ");
                        int hora = Integer.parseInt(sc.nextLine());

                        System.out.println("Minuto: ");
                        int minuto = Integer.parseInt(sc.nextLine());

                        LocalDateTime fechaHora =
                                LocalDateTime.of(
                                        anio,
                                        mes,
                                        dia,
                                        hora,
                                        minuto
                                );
                        Turno turno = new Turno(
                            paciente,
                            medico,
                            fechaHora
                        );


                        servicio.AsignarTurno(turno);
                    }

                    case 4 -> {

                        System.out.print("Año: ");
                        int anio = Integer.parseInt(sc.nextLine());

                        System.out.print("Mes: ");
                        int mes = Integer.parseInt(sc.nextLine());

                        System.out.print("Dia: ");
                        int dia = Integer.parseInt(sc.nextLine());

                        LocalDate fecha =
                                LocalDate.of(anio, mes, dia);
                        List<Turno> turnos =
                                servicio.listarTurnosDelDia(fecha);

                        if(turnos.isEmpty()){
                            System.out.print("No hay turnos");
                        } else {
                            turnos.forEach(System.out::println);
                        }
                    }
                    case 5 ->{

                        System.out.print("ID turno: ");

                        int id = Integer.parseInt(sc.nextLine());

                        servicio.cancelarTurno(id);
                    }

                    case 6 -> {
                        System.out.print("Nombre del medico: ");
                        String nombre = sc.nextLine();

                        System.out.print("Apellido del medico: ");
                        String apellido = sc.nextLine();

                        Medico medico = servicio.buscarPorNombreApellido(nombre, apellido);
                        if (medico == null) {
                            System.out.println("Medico no encontrado");
                            continue;
                        }
                        List<Turno> turnos =
                                servicio.buscarPorMedico(medico);
                        if (turnos.isEmpty()) {
                            System.out.println("No hay turnos");
                        } else {
                            turnos.forEach(System.out::println);
                        }
                    }
                    case 7 -> {
                        System.out.println("Cedula del paciente: ");
                        String cedula = sc.nextLine();

                        Paciente paciente = servicio.buscarPorCedula(cedula);

                        if(paciente == null){
                            System.out.println("Paciente no encontrado");
                            continue;
                        }
                        List<Turno> turnos =
                                servicio.buscarPorPaciente(paciente);
                        if(turnos.isEmpty()){
                            System.out.println("No hay turnos");
                        } else {
                            turnos.forEach(System.out::println);
                        }
                    }

                    case 8 -> {
                        System.out.println("Ingrese el id del turno: ");
                        int idTurno = Integer.parseInt(sc.nextLine());

                        System.out.println("Seleccione el nuevo estado: ");
                        EstadoTurno[] estados = EstadoTurno.values();

                        for (int i = 0; i < estados.length ; i++) {
                            System.out.println((i+1) + ". " + estados[i]);
                        }

                        System.out.println("Opcion: ");
                        int opcionEstado = Integer.parseInt(sc.nextLine());

                        if (opcionEstado < 1 || opcionEstado > estados.length) {
                            System.out.println("Opcion invalida!");
                        }
                        EstadoTurno nuevoEstado = estados[opcionEstado - 1];

                        servicio.cambiarEstadoTurno(idTurno, nuevoEstado);
                    }

                    case 9 -> {
                        servicio.listarPacientes();
                    }
                    case 10 -> {
                        servicio.listarMedicos();
                    }
                    case 0 -> {
                        DatosCSV.guardar(servicio);
                        System.out.println("Datos guardados....");
                        System.out.println("Hasta Pronto");
                        sc.close();
                        return;
                    }

                    default ->
                        System.out.println("Opción inválida.");
                }
            } catch(Exception e){
                System.out.println("Error: "  + e.getMessage());
            }
        }
    }

    private static void mostrarMenu(){

        System.out.println("||||||||||||||||||||||||||||||||||||||||||||");
        System.out.println("|                                          |");
        System.out.println("|           CLINICAAPP - MENÚ              |");
        System.out.println("|                                          |");
        System.out.println("|  1. Registrar paciente                   |");
        System.out.println("|  2. Registrar médico                     |");
        System.out.println("|  3. Asignar turno                        |");
        System.out.println("|  4. Listar turnos del día                |");
        System.out.println("|  5. Cancelar turno                       |");
        System.out.println("|  6. Ver turnos por médico                |");
        System.out.println("|  7. Ver turnos por paciente              |");
        System.out.println("|  8. Cambiar estado de turno              |");
        System.out.println("|  9. Listar pacientes                     |");
        System.out.println("| 10. Listar médicos                       |");
        System.out.println("|  0. Salir                                |");
        System.out.println("|                                          |");
        System.out.println("||||||||||||||||||||||||||||||||||||||||||||");

    }
}
