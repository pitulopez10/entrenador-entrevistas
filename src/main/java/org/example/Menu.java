package org.example;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.List;

public class Menu {

    // Instanciamos los DAOs que utilizará la interfaz
    private final PostulanteDAO postulanteDAO = new PostulanteDAO();
    private final AdministradorDAO adminDAO = new AdministradorDAO();
    private final EmpresaDAO empresaDAO = new EmpresaDAO();
    private final OfertaLaboralDAO ofertaLaboralDAO = new OfertaLaboralDAO();

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
                            iniciarSesionAdministrador(scanner);
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

    private void iniciarSesionAdministrador(Scanner scanner) {
        System.out.println("\n------------------------------");
        System.out.println("INICIAR SESIÓN");
        System.out.println("------------------------------");

        System.out.print("Usuario: ");
        String usuario = scanner.nextLine().trim();

        System.out.print("Contraseña: ");
        String password = scanner.nextLine();

        if (usuario.isEmpty() || password.isEmpty()) {
            System.out.println("Error: El usuario y la contraseña son obligatorios.");
            return;
        }

        try {
            if (adminDAO.validarCredenciales(usuario, password)) {
                isAdminAutenticado = true;

                System.out.println(
                        "Inicio de sesión exitoso. Bienvenido " + usuario + "."
                );

            } else {
                System.out.println(
                        "Error: Usuario o contraseña incorrectos."
                );
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error al acceder a la base de datos: " + e.getMessage()
            );
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
                        System.out.println("\n Registrar nuevo administrador: ");
                        ejecutarRegistrarAdmin(scanner);
                        break;
                    case 2:
                        System.out.println("\n Cerrando sesión de Administrador...");
                        isAdminAutenticado = false;
                        salirAdmin = true;
                        break;
                    case 3:
                        System.out.println("\n Listar todos los postulantes...");
                        ejecutarListarPostulantes();
                        break;
                    case 4:
                        System.out.println("\n Bloquear/Desbloquear postulante...");
                        ejecutarBloqDesbloqPostulante(scanner);
                        break;
                    case 5:
                        System.out.println("\n Modificar postulante");
                        ejecutarModificarPostulante(scanner, postulanteDAO);
                        break;
                    case 6:
                        System.out.println("\n Eliminar postulante...");
                        break;
                    case 7:
                        System.out.println("\n Bloquear / desbloquear empresa");
                        ejecutarBloquearDesbloquearEmpresa(scanner);
                        break;
                    case 8:
                        System.out.println("\n Eliminar empresa por RUT");
                        ejecutarEliminarEmpresa(scanner);
                        break;
                    case 9:
                        ejecutarListarEmpresas();
                        break;
                    case 10:
                        System.out.println("\n Modificar empresa");
                        ejecutarModificarEmpresa(scanner);
                        break;
                    case 11:
                        ejecutarListarOfertas();
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
                        System.out.println("\n Saliendo del Panel de Administrador...");
                        break;
                    default:
                        System.out.println("\n Error: Opción administrativa no válida.");
                }
            } catch (SQLException e) {
                System.out.println("\nError: Ocurrió un problema ejecutando la opción: " + e.getMessage());
            }
        }
    }

    private void ejecutarRegistrarAdmin(Scanner scanner) throws SQLException {

        System.out.print("Ingrese el nombre de usuario: ");
        String usuario = scanner.nextLine().trim();

        System.out.print("Ingrese la contraseña: ");
        String password = scanner.nextLine().trim();

        //Que no este vacio el campo
        if (usuario.isEmpty() || password.isEmpty()) {
            System.out.println("Error: Los campos no pueden estar vacíos.");
            return;
        }

        if (adminDAO.buscarPorId(usuario) != null) {
            System.out.println("Error: El usuario '" + usuario + "' ya existe. Elija otro nombre.");
            return;
        }

        // 3. Instanciamos y guardamos
        Administrador nuevoAdmin = new Administrador();
        nuevoAdmin.setUsuario(usuario);
        nuevoAdmin.setPassword(password);

        try {
            adminDAO.agregar(nuevoAdmin);
            System.out.println("Administrador registrado correctamente.");
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // Código de error de MariaDB para PK duplicada
                System.out.println("Error: El nombre de usuario ya se encuentra registrado.");
            } else {
                throw e;
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

    private void ejecutarModificarEmpresa(Scanner scanner) throws SQLException {

        // LISTAR EMPRESAS DISPONIBLES
        List<Empresa> empresas = empresaDAO.listar();

        if (empresas.isEmpty()) {
            System.out.println("No hay empresas registradas.");
            return;
        }

        System.out.println("\n------------------------------");
        System.out.println("EMPRESAS REGISTRADAS");
        System.out.println("------------------------------");

        for (Empresa empresa : empresas) {
            System.out.println("RUT: " + empresa.getRut());
            System.out.println("Nombre: " + empresa.getNombre());
            System.out.println("Mail: " + empresa.getMail());
            System.out.println("------------------------------");
        }

        // PEDIR EMPRESA A MODIFICAR
        System.out.print("\nIngrese el RUT de la empresa que desea modificar: ");
        String rutBuscado = scanner.nextLine().trim();

        Empresa empresa = empresaDAO.buscarPorId(rutBuscado);

        if (empresa == null) {
            System.out.println(
                    "Error: No existe ninguna empresa registrada con el RUT: "
                            + rutBuscado
            );
            return;
        }

        System.out.println("\nEmpresa seleccionada: " + empresa.getNombre());

        // NOMBRE
        System.out.print("Nuevo nombre (" + empresa.getNombre() + "): ");
        String nuevoNombre = scanner.nextLine().trim();

        if (!nuevoNombre.isEmpty()) {
            empresa.setNombre(nuevoNombre);
        }

        // MAIL
        System.out.print("Nuevo mail (" + empresa.getMail() + "): ");
        String nuevoMail = scanner.nextLine().trim();

        if (!nuevoMail.isEmpty()) {
            empresa.setMail(nuevoMail);
        }

        // DESCRIPCIÓN
        System.out.print(
                "Nueva descripción (" + empresa.getDescripcion() + "): "
        );
        String nuevaDescripcion = scanner.nextLine().trim();

        if (!nuevaDescripcion.isEmpty()) {
            empresa.setDescripcion(nuevaDescripcion);
        }

        // SITIO WEB
        System.out.print(
                "Nuevo sitio web (" + empresa.getSitioWeb() + "): "
        );
        String nuevoSitioWeb = scanner.nextLine().trim();

        if (!nuevoSitioWeb.isEmpty()) {
            empresa.setSitioWeb(nuevoSitioWeb);
        }

        // LOGO
        System.out.print(
                "Nuevo logo (" + empresa.getLogo() + "): "
        );
        String nuevoLogo = scanner.nextLine().trim();

        if (!nuevoLogo.isEmpty()) {
            empresa.setLogo(nuevoLogo);
        }

        // TELÉFONO
        System.out.print(
                "Nuevo teléfono (" + empresa.getTelefono() + "): "
        );
        String telefonoIngresado = scanner.nextLine().trim();

        if (!telefonoIngresado.isEmpty()) {

            try {

                int nuevoTelefono = Integer.parseInt(telefonoIngresado);

                if (nuevoTelefono <= 0) {
                    System.out.println(
                            "Error: El teléfono debe ser mayor a 0."
                    );
                    return;
                }

                empresa.setTelefono(nuevoTelefono);

            } catch (NumberFormatException e) {

                System.out.println(
                        "Error: El teléfono debe ser un número válido."
                );

                return;
            }
        }

        // GUARDAR CAMBIOS
        empresaDAO.modificar(empresa);

        System.out.println("\nEmpresa modificada correctamente.");
    }
    private void ejecutarEliminarEmpresa(Scanner scanner) throws SQLException {
        System.out.print("Ingrese el RUT de la empresa que desea eliminar: ");
        String rutBuscado = scanner.nextLine().trim();

        Empresa empresa = empresaDAO.buscarPorId(rutBuscado);

        if (empresa != null) {
            System.out.println("Empresa encontrada: " + empresa.getNombre() + " (RUT: " + empresa.getRut() + ")");
            System.out.print("¿Está seguro que desea eliminar permanentemente esta empresa? (S/N): ");
            String confirmacion = scanner.nextLine().trim().toUpperCase();

            if (confirmacion.equals("S")) {
                empresaDAO.eliminar(empresa.getRut());
                System.out.println("La empresa ha sido eliminada correctamente del registro");
            } else {
                System.out.println("Operación cancelada. No se han realizado cambios.");
            }
        } else {
            System.out.println("Error: No existe ninguna empresa registrada con el RUT: " + rutBuscado);
        }
    }
    private void ejecutarListarEmpresas() throws SQLException {
        List<Empresa> empresas = empresaDAO.listar();

        if (empresas.isEmpty()) {
            System.out.println("No hay empresas registradas en el sistema.");
        } else {
            System.out.println("LISTADO DE EMPRESAS REGISTRADAS");
            for (Empresa emp : empresas) {
                String estado = emp.isBloqueado() ? "Bloqueada" : "Activa";

                System.out.println("----------------------------------------");
                System.out.println("RUT:          " + emp.getRut());
                System.out.println("Nombre:       " + emp.getNombre());
                System.out.println("Email:        " + emp.getMail());
                System.out.println("Teléfono:     " + emp.getTelefono());
                System.out.println("Sitio Web:    " + emp.getSitioWeb());
                System.out.println("Estado:       " + estado);
            }
            System.out.println("----------------------------------------");
            System.out.println("Total de registros: " + empresas.size());
        }
    }
    private void ejecutarListarPostulantes() throws SQLException {
        List<Postulante> postulantes = postulanteDAO.listar();

        if (postulantes.isEmpty()) {
            System.out.println("No hay postulantes registrados en el sistema.");
        } else {
            System.out.println("LISTADO DE POSTULANTES REGISTRADOS");
            for (Postulante postulante : postulantes) {
                String estado = postulante.isBloqueado() ? "Bloqueado" : "Activo";

                System.out.println("----------------------------------------");
                System.out.println("CI:           " + postulante.getCi());
                System.out.println("Nombre:       " + postulante.getNombre());
                System.out.println("Email:        " + postulante.getMail());
                System.out.println("Teléfono:     " + postulante.getTelefono());
                System.out.println("Localidad:    " + postulante.getLocalidad());
                System.out.println("Estado:       " + estado);
            }
            System.out.println("----------------------------------------");
            System.out.println("Total de registros: " + postulantes.size());
        }
    }

    private void ejecutarBloqDesbloqPostulante (Scanner scanner) throws SQLException{
        ejecutarListarPostulantes();
        if (postulanteDAO.listar().isEmpty()){
            return;
        }
        System.out.print("\nIngrese la CI del postulante que desea gestionar: ");
        int ciBuscada = scanner.nextInt();
        scanner.nextLine(); // limpiar buffer

        Postulante postulante = postulanteDAO.buscarPorId(ciBuscada);

        if (postulante != null) {
            System.out.println("Postulante encontrado: " + postulante.getNombre());

            if (postulante.isBloqueado()) {
                System.out.println("Estado actual: BLOQUEADO");
                System.out.print("¿Desea DESBLOQUEAR a este postulante? (S/N): ");
                String respuesta = scanner.nextLine().trim().toUpperCase();

                if (respuesta.equals("S")) {
                    postulanteDAO.desbloquear(postulante.getCi());
                    System.out.println("El postulante ha sido desbloqueado correctamente.");
                } else {
                    System.out.println("Operación cancelada. El postulante sigue bloqueado.");
                }
            } else {
                System.out.println("Estado actual: ACTIVO");
                System.out.print("¿Desea bloquear a este postulante? (S/N): ");
                String respuesta = scanner.nextLine().trim().toUpperCase();

                if (respuesta.equals("S")) {
                    postulanteDAO.bloquear(postulante.getCi());
                    System.out.println("El postulante ha sido bloqueado correctamente.");
                } else {
                    System.out.println("Operación cancelada. El postulante sigue activo.");
                }
            }
        } else {
            System.out.println("Error: No existe ningún postulante registrado con la CI " + ciBuscada);
        }
    }

    private void ejecutarListarOfertas() throws SQLException {

        List<OfertaLaboral> ofertas = ofertaLaboralDAO.listar();

        System.out.println("\n------------------------------------------");
        System.out.println("          OFERTAS LABORALES");
        System.out.println("------------------------------------------");

        if (ofertas.isEmpty()) {
            System.out.println("No hay ofertas laborales registradas.");
            return;
        }

        for (OfertaLaboral oferta : ofertas) {

            System.out.println("ID: " + oferta.getId());
            System.out.println("Título: " + oferta.getTitulo());
            System.out.println("Descripción: " + oferta.getDescripcion());
            System.out.println("Requisitos: " + oferta.getRequisitos());
            System.out.println("Fecha publicación: " + oferta.getFechaPublicacion());
            System.out.println("Fecha cierre: " + oferta.getFechaCierre());
            System.out.println("Estado: " + oferta.getEstado());

            if (oferta.getEmpresa() != null) {
                System.out.println(
                        "Empresa: " +
                                oferta.getEmpresa().getNombre() +
                                " - RUT: " +
                                oferta.getEmpresa().getRut()
                );
            }

            System.out.println("------------------------------------------");
        }
    }
}