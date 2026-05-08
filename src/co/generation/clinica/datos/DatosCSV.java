package co.generation.clinica.datos;

import co.generation.clinica.model.*;
import co.generation.clinica.service.ClinicaService;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DatosCSV {

    private static final String DIR = "datos/";
    private static final String F_PACIENTES = DIR + "pacientes.csv";
    private static final String F_MEDICOS = DIR + "medicos.csv";
    private static final String F_TURNOS = DIR + "turnos.csv";

    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void cargar(ClinicaService servicio) {
        new File(DIR).mkdirs();

        cargarPacientes(servicio);
        cargarMedicos(servicio);
        cargarTurnos(servicio);
    }

    private static void cargarPacientes(ClinicaService servicio) {

        File archivo = new File(F_PACIENTES);

        if (!archivo.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {

            String linea;

            while ((linea = br.readLine()) != null) {

                if (linea.isBlank()) {
                    continue;
                }

                // formato:
                // id,cedula,nombre,apellido,telefono

                String[] p = linea.split(",", -1);

                Paciente paciente = new Paciente(
                        Integer.parseInt(p[0].trim()),
                        p[1].trim(),
                        p[2].trim(),
                        p[3].trim(),
                        p[4].trim()
                );

                servicio.getPacientes().add(paciente);
            }

        } catch (IOException e) {
            System.out.println("Error al cargar pacientes: " + e.getMessage());
        }
    }

    private static void cargarMedicos(ClinicaService servicio) {

        File archivo = new File(F_MEDICOS);

        if (!archivo.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {

            String linea;

            while ((linea = br.readLine()) != null) {

                if (linea.isBlank()) {
                    continue;
                }

                // formato:
                // id,nombre,apellido,especialidad

                String[] p = linea.split(",", -1);

                Medico medico = new Medico(
                        Integer.parseInt(p[0].trim()),
                        p[1].trim(),
                        p[2].trim(),
                        Especialidad.valueOf(p[3].trim())
                );

                servicio.getMedicos().add(medico);
            }

        } catch (IOException e) {
            System.out.println("Error al cargar médicos: " + e.getMessage());
        }
    }

    private static void cargarTurnos(ClinicaService servicio) {

        File archivo = new File(F_TURNOS);

        if (!archivo.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {

            String linea;

            while ((linea = br.readLine()) != null) {

                if (linea.isBlank()) {
                    continue;
                }

                // formato:
                // id,cedulaPaciente,nombreMedico,apellidoMedico,fechaHora,estado

                String[] p = linea.split(",", -1);

                Paciente paciente =
                        servicio.buscarPorCedula(p[1].trim());

                Medico medico =
                        servicio.buscarPorNombreApellido(
                                p[2].trim(),
                                p[3].trim()
                        );

                if (paciente == null || medico == null) {
                    continue;
                }

                Turno turno = new Turno(
                        Integer.parseInt(p[0].trim()),
                        paciente,
                        medico,
                        LocalDateTime.parse(p[4].trim(), FMT),
                        EstadoTurno.valueOf(p[5].trim())
                );

                servicio.getTurnos().add(turno);
            }

        } catch (IOException e) {
            System.out.println("Error al cargar turnos: " + e.getMessage());
        }
    }
    public static void guardar(ClinicaService servicio) {

        new File(DIR).mkdirs();

        guardarPacientes(servicio.getPacientes());
        guardarMedicos(servicio.getMedicos());
        guardarTurnos(servicio.getTurnos());
    }

    private static void guardarPacientes(List<Paciente> lista) {

        try (PrintWriter pw =
                     new PrintWriter(new FileWriter(F_PACIENTES))) {

            for (Paciente p : lista) {

                pw.println(
                        p.getId() + "," +
                                p.getCedula() + "," +
                                p.getNombre() + "," +
                                p.getApellido() + "," +
                                p.getTelefono()
                );
            }

        } catch (IOException e) {
            System.out.println(
                    "Error al guardar pacientes: " + e.getMessage()
            );
        }
    }

    private static void guardarMedicos(List<Medico> lista) {

        try (PrintWriter pw =
                     new PrintWriter(new FileWriter(F_MEDICOS))) {

            for (Medico m : lista) {

                pw.println(
                        m.getId() + "," +
                                m.getNombre() + "," +
                                m.getApellido() + "," +
                                m.getEspecialidad()
                );
            }

        } catch (IOException e) {
            System.out.println(
                    "Error al guardar médicos: " + e.getMessage()
            );
        }
    }

    private static void guardarTurnos(List<Turno> lista) {

        try (PrintWriter pw =
                     new PrintWriter(new FileWriter(F_TURNOS))) {

            for (Turno t : lista) {

                pw.println(
                        t.getId() + "," +
                                t.getPaciente().getCedula() + "," +
                                t.getMedico().getNombre() + "," +
                                t.getMedico().getApellido() + "," +
                                t.getFechaHora().format(FMT) + "," +
                                t.getEstado()
                );
            }

        } catch (IOException e) {
            System.out.println(
                    "Error al guardar turnos: " + e.getMessage()
            );
        }
    }
}