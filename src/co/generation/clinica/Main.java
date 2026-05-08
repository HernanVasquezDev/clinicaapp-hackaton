package co.generation.clinica;
import co.generation.clinica.datos.DatosCSV;
import co.generation.clinica.model.*;
import co.generation.clinica.service.ClinicaService;

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

                    case 1:
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
                        break;
                    case 2:
                        System.out.println("Nombre del Medico: ");
                        String nombreMedico = sc.nextLine();
                        System.out.println("Apellido del medico");
                        String apellidoMedico = sc.nextLine();

                        System.out.println("""
                                  
                                Especialidad del medico:
                                
                               1. GENERAL,
                               2. PEDIATRA,
                               3. CARDIOLOGIA,
                               4. URGENCIAS
                                
                        """);
                        System.out.println("Seleccione una opcion: ");
                        int esp = Integer.parseInt(sc.nextLine());

                        Especialidad especialidad = switch (esp){
                            case 1 -> Especialidad.GENERAL;
                            case 2 -> Especialidad.PEDIATRA;
                            case 3 -> Especialidad.CARDIOLOGIA;
                            case 4 -> Especialidad.URGENCIAS;
                            default -> throw new IllegalArgumentException("La especialidad no existe");
                        };
                        Medico medico = new Medico(
                                nombreMedico,
                                apellidoMedico,
                                especialidad
                        );
                        servicio.registrarMedico(medico);
                        break;
                    case 3:
                        System.out.println("Cedula del paciente: ");
                        String Cedula = sc.nextLine();
                        Paciente searchpaciente = servicio.buscarPorCedula(Cedula);
                        if(searchpaciente == null){
                            System.out.println("Paciente no encontrado");
                            continue;
                        }

                        System.out.println("Apellido del medico: ");
                        String searchApellidoMedico = sc.nextLine();

                        Medico searchmedico =
                                servicio.buscarPorNombreApellido(
                                apellidoMedico
                        );


                        servicio.AsignarTurno();
                        break;
                    case 4:
                        servicio.listarTurnosDelDia();
                        break;
                    case 5:
                        servicio.cancelarTurno();
                        break;
                    case 6:
                        servicio.buscarPorMedico();
                        break;
                    case 7:
                        servicio.buscarPorPaciente();
                        break;
                    case 8:
                        servicio.cambiarEstadoTurno();
                        break;
                    case 9:
                        servicio.listarPacientes();
                        break;
                    case 10:
                        servicio.listarMedicos();
                        break;
                    case 0:
                        break;
                    default:
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
