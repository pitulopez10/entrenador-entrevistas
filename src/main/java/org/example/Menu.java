package org.example;

import java.sql.SQLException;
import java.util.Scanner;

public class Menu {

    // Instanciamos los DAOs que utilizará la interfaz
    private final PostulanteDAO postulanteDAO = new PostulanteDAO();
    private final AdministradorDAO adminDAO = new AdministradorDAO();
    private final EmpresaDAO empresaDAO = new EmpresaDAO();

    private boolean isAdminAutenticado = false;

    public void iniciar() {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean salirPrincipal = false;

            while (!salirPrincipal) {
                System.out.println("             MENU PRINCIPAL             ");
                System.out.println("1. Panel de ADMINISTRADOR");
                System.out.println("2. Panel de EMPRESA (Próximamente)");
                System.out.println("3. Panel de POSTULANTE (Próximamente)");
                System.out.println("0. Salir del Sistema");
                System.out.print("Seleccione una opción: ");

                int opcionPrincipal = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer

                switch (opcionPrincipal) {
                    case 1:
                        // Si no está validado, le pide credenciales primero
                        if (!isAdminAutenticado) {
                            validarAdministrador(scanner);
                        }
                        // Si la validación pasó con éxito, abre su panel
                        if (isAdminAutenticado) {
                            menuAdministrador(scanner, postulanteDAO);
                        }
                        break;
                    case 2:
                        System.out.println("\nMódulo de Empresas en desarrollo...");
                        break;
                    case 3:
                        System.out.println("\n[Info] Módulo de Postulantes en desarrollo...");
                        break;
                    case 0:
                        salirPrincipal = true;
                        System.out.println("\nCerrando el sistema.");
                        break;
                    default:
                        System.out.println("\nError: Opción no válida del Menú Principal.");
                }
            }
        } catch (Exception e) {
            System.out.println("Ocurrió un error general en la aplicación: " + e.getMessage());
        }
    }

    private void validarAdministrador(Scanner scanner) {
        System.out.println("\n--------------------------------------------------");
        System.out.println("[VALIDACION DE ADMINISTRADOR]");
        System.out.println("--------------------------------------------------");
        System.out.print("Usuario: ");
        String usuario = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        try {
            if (adminDAO.validarCredenciales(usuario, password)) {
                isAdminAutenticado = true;
                System.out.println("¡Validación exitosa! Bienvenido, Administrador Central.");
            } else {
                System.out.println("Error: Credenciales incorrectas. Acceso denegado.");
            }
        } catch (SQLException e) {
            System.out.println("Error: No se pudo procesar la validación: " + e.getMessage());
        }
    }

    private void menuAdministrador(Scanner scanner, PostulanteDAO postulanteDAO) {
        boolean salirAdmin = false;

        while (!salirAdmin) {
            System.out.println("\n       PANEL DE CONTROL: ADMINISTRADOR   ");
            System.out.println("1. Registrarse(agregar otro admin)");
            System.out.println("2. Cerrar sesión");
            System.out.println("3. Listar postulantes");
            System.out.println("4. Bloquear/desbloquear postulante");
            System.out.println("5. Modificar postulante");
            System.out.println("6. Eliminar postulante");
            System.out.println("7. Bloquear/desbloquear empresa");
            System.out.println("8. Eliminar empresas");
            System.out.println("9. Listar empresas");
            System.out.println("10. Modificar empresa");
            System.out.println("11. Listar ofertas");
            System.out.println("12. Eliminar ofertas");
            System.out.println("13. Listar Postulaciones");
            System.out.println("14. Eliminar postulacion");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione un Caso de Uso: ");

            int opcionAdmin = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            try {
                switch (opcionAdmin) {
                    case 1:
                        System.out.println("\n Registrar Administrador...");
                        break;
                    case 2:
                        System.out.println("\n Cerrando sesión de Administrador...");
                        isAdminAutenticado = false;
                        salirAdmin = true;
                        break;
                    case 3:
                        System.out.println("\n Listar todos los postulantes...");
                        break;
                    case 4:
                        System.out.println("\n Bloquear/Desbloquear postulante...");
                        break;
                    case 5:
                        System.out.println("\n MODIFICAR POSTULANTE");
                        ejecutarModificarPostulante(scanner, postulanteDAO);
                        break;
                    case 6:
                        System.out.println("\n Eliminar postulante...");
                        break;
                    case 7:
                        System.out.println("\n BLOQUEAR / DESBLOQUEAR EMPRESA");
                        ejecutarBloquearDesbloquearEmpresa(scanner);
                        break;
                    case 8:
                        System.out.println("\n Eliminar empresa por RUT...");
                        break;
                    case 9:
                        System.out.println("\n Listar todas las empresas...");
                        break;
                    case 10:
                        System.out.println("\n Modificar datos de empresa...");
                        break;
                    case 11:
                        System.out.println("\n Listar ofertas laborales...");
                        break;
                    case 12:
                        System.out.println("\n Eliminar ofertas laborales...");
                        break;
                    case 13:
                        System.out.println("\n Listar postulaciones hechas...");
                        break;
                    case 14:
                        System.out.println("\n Eliminar postulación...");
                        break;
                    case 0:
                        salirAdmin = true;
                        System.out.println("\nSaliendo del Panel de Administrador...");
                        break;
                    default:
                        System.out.println("\nError: Opción administrativa no válida.");
                }
            } catch (SQLException e) {
                System.out.println("\nError: Ocurrió un problema ejecutando la opción: " + e.getMessage());
            }
        }
    }

    private void ejecutarModificarPostulante(Scanner scanner, PostulanteDAO postulanteDAO) throws SQLException {
        System.out.print("Ingrese la CI del postulante que desea modificar: ");
        int ciBuscada = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        Postulante encontrado = postulanteDAO.buscarPorId(ciBuscada);

        if (encontrado != null) {
            System.out.println("Postulante encontrado: " + encontrado.getNombre());

            System.out.print("Nueva Localidad (" + encontrado.getLocalidad() + "): ");
            String nuevaLocalidad = scanner.nextLine();
            encontrado.setLocalidad(nuevaLocalidad);

            System.out.print("Nuevos Datos de Estudio (" + encontrado.getDatosEstudio() + "): ");
            String nuevosEstudios = scanner.nextLine();
            encontrado.setDatosEstudio(nuevosEstudios);

            System.out.print("Nuevos Datos de Experiencia (" + encontrado.getDatosExperiencia() + "): ");
            String nuevaExperiencia = scanner.nextLine();
            encontrado.setDatosExperiencia(nuevaExperiencia);

            System.out.print("Nuevo Teléfono (" + encontrado.getTelefono() + "): ");
            int nuevoTelefono = scanner.nextInt();
            scanner.nextLine(); //Limpia el buffer
            encontrado.setTelefono(nuevoTelefono);

            System.out.println("\n Guardando cambios en MariaDB...");
            postulanteDAO.modificar(encontrado);
            System.out.println("El registro ha sido modificado correctamente");
        } else {
            System.out.println("Error: No existe ningún postulante registrado con la CI " + ciBuscada);
        }
    }
    private void ejecutarBloquearDesbloquearEmpresa(Scanner scanner) throws SQLException {
        System.out.print("Ingrese el RUT de la empresa que desea gestionar: ");
        String RUTBuscado = scanner.nextLine();

        // Buscamos si la empresa existe en la BD
        Empresa empresa = empresaDAO.buscarPorId(RUTBuscado);

        if (empresa != null) {
            System.out.println("Empresa encontrada: " + empresa.getNombre());

            // Si está bloqueada, le ofrecemos desbloquear
            if (empresa.isBloqueado()) {
                System.out.println("Estado actual:BLOQUEADA");
                System.out.print("¿Desea DESBLOQUEAR a esta empresa? (S/N): ");
                String respuesta = scanner.nextLine().trim().toUpperCase();

                if (respuesta.equals("S")) {
                    empresaDAO.desbloquear(empresa.getRut());
                    System.out.println("La empresa ha sido desbloqueada correctamente.");
                } else {
                    System.out.println("Operación cancelada. La empresa sigue bloqueada.");
                }
            }
            // Si está activa, le ofrecemos BLOQUEAR
            else {
                System.out.println("Estado actual: DESBLOQUEADA");
                System.out.print("¿Desea bloquear a esta empresa? (S/N): ");
                String respuesta = scanner.nextLine().trim().toUpperCase();

                if (respuesta.equals("S")) {
                    empresaDAO.bloquear(empresa.getRut());
                    System.out.println("La empresa ha sido bloqueada correctamente.");
                } else {
                    System.out.println("Operación cancelada. La empresa sigue activa.");
                }
            }
        } else {
            System.out.println("Error: No existe ninguna empresa registrada con el RUT: " + RUTBuscado);
        }
    }
}