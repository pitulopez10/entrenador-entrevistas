package org.example;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Menu {

    // DAOs
    private final PostulanteDAO postulanteDAO = new PostulanteDAO();
    private final AdministradorDAO adminDAO = new AdministradorDAO();
    private final EmpresaDAO empresaDAO = new EmpresaDAO();
    private final OfertaLaboralDAO ofertaLaboralDAO = new OfertaLaboralDAO();
    private final PostulacionDAO postulacionDAO = new PostulacionDAO();

    private boolean isAdminAutenticado = false;
    private Postulante postulanteAutenticado = null;

    public void iniciar() {

        try (Scanner scanner = new Scanner(System.in)) {

            boolean salirPrincipal = false;

            while (!salirPrincipal) {

                System.out.println("\n========================================");
                System.out.println("             MENU PRINCIPAL");
                System.out.println("========================================");
                System.out.println("1. Panel de ADMINISTRADOR");
                System.out.println("2. Panel de EMPRESA (Próximamente)");
                System.out.println("3. Panel de POSTULANTE");
                System.out.println("0. Salir del Sistema");
                System.out.print("Seleccione una opción: ");

                int opcionPrincipal = scanner.nextInt();
                scanner.nextLine();

                switch (opcionPrincipal) {

                    case 1:

                        if (!isAdminAutenticado) {
                            iniciarSesionAdministrador(scanner);
                        }

                        if (isAdminAutenticado) {
                            menuAdministrador(scanner);
                        }

                        break;

                    case 2:
                        System.out.println("\nMódulo de Empresas en desarrollo...");
                        break;

                    case 3:
                        menuAccesoPostulante(scanner);
                        break;

                    case 0:
                        salirPrincipal = true;
                        System.out.println("\nCerrando el sistema.");
                        break;

                    default:
                        System.out.println(
                                "\nError: Opción no válida del Menú Principal."
                        );
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Ocurrió un error general en la aplicación: "
                            + e.getMessage()
            );
        }
    }

    // ============================================================
    // INICIAR SESIÓN ADMINISTRADOR
    // ============================================================

    private void iniciarSesionAdministrador(Scanner scanner) {

        System.out.println("\n------------------------------");
        System.out.println("INICIAR SESIÓN");
        System.out.println("------------------------------");

        System.out.print("Usuario: ");
        String usuario = scanner.nextLine().trim();

        System.out.print("Contraseña: ");
        String password = scanner.nextLine();

        if (usuario.isEmpty() || password.isEmpty()) {

            System.out.println(
                    "Error: El usuario y la contraseña son obligatorios."
            );

            return;
        }

        try {

            if (adminDAO.validarCredenciales(usuario, password)) {

                isAdminAutenticado = true;

                System.out.println(
                        "Inicio de sesión exitoso. Bienvenido "
                                + usuario + "."
                );

            } else {

                System.out.println(
                        "Error: Usuario o contraseña incorrectos."
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al acceder a la base de datos: "
                            + e.getMessage()
            );
        }
    }

    // ============================================================
    // MENÚ ADMINISTRADOR
    // ============================================================

    private void menuAdministrador(Scanner scanner) {

        boolean salirAdmin = false;

        while (!salirAdmin) {

            System.out.println("\n========================================");
            System.out.println("       PANEL DE CONTROL: ADMINISTRADOR");
            System.out.println("========================================");
            System.out.println("1. Registrar nuevo administrador");
            System.out.println("2. Gestión de postulantes");
            System.out.println("3. Gestión de empresas");
            System.out.println("4. Gestión de ofertas laborales");
            System.out.println("5. Gestión de postulaciones");
            System.out.println("6. Cerrar sesión");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");

            int opcionAdmin = scanner.nextInt();
            scanner.nextLine();

            try {

                switch (opcionAdmin) {

                    case 1:
                        ejecutarRegistrarAdmin(scanner);
                        break;

                    case 2:
                        menuPostulantes(scanner);
                        break;

                    case 3:
                        menuEmpresas(scanner);
                        break;

                    case 4:
                        menuOfertasLaborales(scanner);
                        break;

                    case 5:
                        menuPostulaciones(scanner);
                        break;

                    case 6:
                        System.out.println(
                                "\nCerrando sesión de Administrador..."
                        );

                        isAdminAutenticado = false;
                        salirAdmin = true;
                        break;

                    case 0:
                        salirAdmin = true;

                        System.out.println(
                                "\nVolviendo al Menú Principal..."
                        );

                        break;

                    default:
                        System.out.println(
                                "\nError: Opción administrativa no válida."
                        );
                }

            } catch (SQLException e) {

                System.out.println(
                        "\nError: Ocurrió un problema ejecutando la opción: "
                                + e.getMessage()
                );
            }
        }
    }

    // ============================================================
    // SUBMENÚ POSTULANTES
    // ============================================================

    private void menuPostulantes(Scanner scanner) throws SQLException {

        boolean volver = false;

        while (!volver) {

            System.out.println("\n========================================");
            System.out.println("          GESTIÓN DE POSTULANTES");
            System.out.println("========================================");
            System.out.println("1. Listar postulantes");
            System.out.println("2. Bloquear/desbloquear postulante");
            System.out.println("3. Modificar postulante");
            System.out.println("4. Consultar postulaciones de un postulante");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {

                case 1:
                    ejecutarListarPostulantes();
                    break;

                case 2:
                    ejecutarBloqDesbloqPostulante(scanner);
                    break;

                case 3:
                    ejecutarModificarPostulante(scanner, postulanteDAO);
                    break;

                case 4:
                    ejecutarConsultarPostulacionUsu(scanner);
                    break;

                case 0:
                    volver = true;
                    break;

                default:
                    System.out.println(
                            "Error: Opción no válida."
                    );
            }
        }
    }

    // ============================================================
    // SUBMENÚ EMPRESAS
    // ============================================================

    private void menuEmpresas(Scanner scanner) throws SQLException {

        boolean volver = false;

        while (!volver) {

            System.out.println("\n========================================");
            System.out.println("            GESTIÓN DE EMPRESAS");
            System.out.println("========================================");
            System.out.println("1. Listar empresas");
            System.out.println("2. Modificar empresa");
            System.out.println("3. Bloquear/desbloquear empresa");
            System.out.println("4. Eliminar empresa");
            System.out.println("5. Consultar ofertas de una empresa");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {

                case 1:
                    ejecutarListarEmpresas();
                    break;

                case 2:
                    ejecutarModificarEmpresa(scanner);
                    break;

                case 3:
                    ejecutarBloquearDesbloquearEmpresa(scanner);
                    break;

                case 4:
                    ejecutarEliminarEmpresa(scanner);
                    break;

                case 5:
                    ejecutarConsultarOfertasEmp(scanner);
                    break;

                case 0:
                    volver = true;
                    break;

                default:
                    System.out.println(
                            "Error: Opción no válida."
                    );
            }
        }
    }

    // ============================================================
    // SUBMENÚ OFERTAS LABORALES
    // ============================================================

    private void menuOfertasLaborales(Scanner scanner)
            throws SQLException {

        boolean volver = false;

        while (!volver) {

            System.out.println("\n========================================");
            System.out.println("        GESTIÓN DE OFERTAS LABORALES");
            System.out.println("========================================");
            System.out.println("1. Listar ofertas laborales");
            System.out.println("2. Eliminar oferta laboral");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {

                case 1:
                    ejecutarListarOfertas();
                    break;

                case 2:
                    ejecutarEliminarOferta(scanner);
                    break;

                case 0:
                    volver = true;
                    break;

                default:
                    System.out.println(
                            "Error: Opción no válida."
                    );
            }
        }
    }

    // ============================================================
    // SUBMENÚ POSTULACIONES
    // ============================================================

    private void menuPostulaciones(Scanner scanner)
            throws SQLException {

        boolean volver = false;

        while (!volver) {

            System.out.println("\n========================================");
            System.out.println("          GESTIÓN DE POSTULACIONES");
            System.out.println("========================================");
            System.out.println("1. Listar postulaciones");
            System.out.println("2. Eliminar postulación");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {

                case 1:
                    ejecutarListarPostulaciones();
                    break;

                case 2:
                    ejecutarEliminarPostulacion(scanner);
                    break;

                case 0:
                    volver = true;
                    break;

                default:
                    System.out.println(
                            "Error: Opción no válida."
                    );
            }
        }
    }

    // ============================================================
    // REGISTRAR ADMINISTRADOR
    // ============================================================

    private void ejecutarRegistrarAdmin(Scanner scanner)
            throws SQLException {

        System.out.print("Ingrese el nombre de usuario: ");
        String usuario = scanner.nextLine().trim();

        System.out.print("Ingrese la contraseña: ");
        String password = scanner.nextLine().trim();

        if (usuario.isEmpty() || password.isEmpty()) {

            System.out.println(
                    "Error: Los campos no pueden estar vacíos."
            );

            return;
        }

        if (adminDAO.buscarPorId(usuario) != null) {

            System.out.println(
                    "Error: El usuario '" + usuario
                            + "' ya existe. Elija otro nombre."
            );

            return;
        }

        Administrador nuevoAdmin = new Administrador();

        nuevoAdmin.setUsuario(usuario);
        nuevoAdmin.setPassword(password);

        try {

            adminDAO.agregar(nuevoAdmin);

            System.out.println(
                    "Administrador registrado correctamente."
            );

        } catch (SQLException e) {

            if (e.getErrorCode() == 1062) {

                System.out.println(
                        "Error: El nombre de usuario ya se encuentra registrado."
                );

            } else {
                throw e;
            }
        }
    }

    // ============================================================
    // MODIFICAR POSTULANTE
    // ============================================================

    private void ejecutarModificarPostulante(
            Scanner scanner,
            PostulanteDAO postulanteDAO
    ) throws SQLException {
        ejecutarListarPostulantes();

        System.out.print("Ingrese la CI del postulante que desea modificar: ");
        String ciIngresada = scanner.nextLine().trim();

        int ciBuscada;
        try {
            ciBuscada = Integer.parseInt(ciIngresada);
        } catch (NumberFormatException e) {
            System.out.println("Error: La CI debe ser un número entero válido.");
            return;
        }

        Postulante encontrado = postulanteDAO.buscarPorId(ciBuscada);

        if (encontrado == null) {
            System.out.println("Error: No existe ningún postulante registrado con la CI " + ciBuscada);
            return;
        }

        System.out.println("\n--- MODIFICANDO POSTULANTE: " + encontrado.getNombre() + " ---");
        System.out.println("(Nota: Deje el campo vacío y presione Enter para mantener la información actual)\n");

        System.out.print("Nueva Localidad (" + encontrado.getLocalidad() + "): ");
        String nuevaLocalidad = scanner.nextLine().trim();
        if (!nuevaLocalidad.isBlank()) {
            encontrado.setLocalidad(nuevaLocalidad);
        }

        System.out.print("Nuevos Datos de Estudio (" + encontrado.getDatosEstudio() + "): ");
        String nuevosEstudios = scanner.nextLine().trim();
        if (!nuevosEstudios.isBlank()) {
            encontrado.setDatosEstudio(nuevosEstudios);
        }

        System.out.print("Nuevos Datos de Experiencia (" + encontrado.getDatosExperiencia() + "): ");
        String nuevaExperiencia = scanner.nextLine().trim();
        if (!nuevaExperiencia.isBlank()) {
            encontrado.setDatosExperiencia(nuevaExperiencia);
        }

        System.out.print("Nuevo Teléfono (" + encontrado.getTelefono() + "): ");
        String telefonoIngresado = scanner.nextLine().trim();
        if (!telefonoIngresado.isEmpty()) {
            try {
                int nuevoTelefono = Integer.parseInt(telefonoIngresado);
                encontrado.setTelefono(nuevoTelefono);
            } catch (NumberFormatException e) {
                System.out.println("Error: El teléfono debe ser numérico. Modificación cancelada.");
                return;
            }
        }

        System.out.print("\n¿Desea guardar los cambios realizados? (S/N): ");
        String confirmacion = scanner.nextLine().trim().toUpperCase();

        if (confirmacion.equals("S")) {
            postulanteDAO.modificar(encontrado);
            System.out.println("El registro ha sido modificado correctamente en la base de datos");
        } else {
            System.out.println("Modificación cancelada. No se aplicaron cambios al postulante.");
        }
    }

    // ============================================================
    // BLOQUEAR / DESBLOQUEAR POSTULANTE
    // ============================================================

    private void ejecutarBloqDesbloqPostulante(
            Scanner scanner
    ) throws SQLException {

        ejecutarListarPostulantes();

        if (postulanteDAO.listar().isEmpty()) {
            return;
        }

        System.out.print(
                "\nIngrese la CI del postulante que desea gestionar: "
        );

        int ciBuscada = scanner.nextInt();
        scanner.nextLine();

        Postulante postulante =
                postulanteDAO.buscarPorId(ciBuscada);

        if (postulante != null) {

            System.out.println(
                    "Postulante encontrado: "
                            + postulante.getNombre()
            );

            if (postulante.isBloqueado()) {

                System.out.println(
                        "Estado actual: BLOQUEADO"
                );

                System.out.print(
                        "¿Desea DESBLOQUEAR a este postulante? (S/N): "
                );

                String respuesta =
                        scanner.nextLine()
                                .trim()
                                .toUpperCase();

                if (respuesta.equals("S")) {

                    postulanteDAO.desbloquear(
                            postulante.getCi()
                    );

                    System.out.println(
                            "El postulante ha sido desbloqueado correctamente."
                    );

                } else {

                    System.out.println(
                            "Operación cancelada. El postulante sigue bloqueado."
                    );
                }

            } else {

                System.out.println(
                        "Estado actual: ACTIVO"
                );

                System.out.print(
                        "¿Desea bloquear a este postulante? (S/N): "
                );

                String respuesta =
                        scanner.nextLine()
                                .trim()
                                .toUpperCase();

                if (respuesta.equals("S")) {

                    postulanteDAO.bloquear(
                            postulante.getCi()
                    );

                    System.out.println(
                            "El postulante ha sido bloqueado correctamente."
                    );

                } else {

                    System.out.println(
                            "Operación cancelada. El postulante sigue activo."
                    );
                }
            }

        } else {

            System.out.println(
                    "Error: No existe ningún postulante registrado con la CI "
                            + ciBuscada
            );
        }
    }

    // ============================================================
    // LISTAR POSTULANTES
    // ============================================================

    private void ejecutarListarPostulantes()
            throws SQLException {

        List<Postulante> postulantes =
                postulanteDAO.listar();

        if (postulantes.isEmpty()) {

            System.out.println(
                    "No hay postulantes registrados en el sistema."
            );

            return;
        }

        System.out.println(
                "\nLISTADO DE POSTULANTES REGISTRADOS"
        );

        for (Postulante postulante : postulantes) {

            String estado =
                    postulante.isBloqueado()
                            ? "Bloqueado"
                            : "Activo";

            System.out.println(
                    "----------------------------------------"
            );

            System.out.println(
                    "CI:           "
                            + postulante.getCi()
            );

            System.out.println(
                    "Nombre:       "
                            + postulante.getNombre()
            );

            System.out.println(
                    "Email:        "
                            + postulante.getMail()
            );

            System.out.println(
                    "Teléfono:     "
                            + postulante.getTelefono()
            );

            System.out.println(
                    "Localidad:    "
                            + postulante.getLocalidad()
            );

            System.out.println(
                    "Estado:       "
                            + estado
            );
        }

        System.out.println(
                "----------------------------------------"
        );

        System.out.println(
                "Total de registros: "
                        + postulantes.size()
        );
    }

    // ============================================================
    // BLOQUEAR / DESBLOQUEAR EMPRESA
    // ============================================================

    private void ejecutarBloquearDesbloquearEmpresa(
            Scanner scanner
    ) throws SQLException {

        List<Empresa> empresas = empresaDAO.listar();

        if (empresas.isEmpty()) {
            System.out.println("No hay empresas registradas.");
            return;
        }

        System.out.println("\nEmpresas registradas:");
        for (Empresa emp : empresas) {
            System.out.println("RUT: " + emp.getRut() + " - " + emp.getNombre());
        }

        System.out.print(
                "\nIngrese el RUT de la empresa que desea gestionar: "
        );

        System.out.print(
                "Ingrese el RUT de la empresa que desea gestionar: "
        );

        String rutBuscado =
                scanner.nextLine().trim();

        Empresa empresa =
                empresaDAO.buscarPorId(rutBuscado);

        if (empresa != null) {

            System.out.println(
                    "Empresa encontrada: "
                            + empresa.getNombre()
            );

            if (empresa.isBloqueado()) {

                System.out.println(
                        "Estado actual: BLOQUEADA"
                );

                System.out.print(
                        "¿Desea DESBLOQUEAR a esta empresa? (S/N): "
                );

                String respuesta =
                        scanner.nextLine()
                                .trim()
                                .toUpperCase();

                if (respuesta.equals("S")) {

                    empresaDAO.desbloquear(
                            empresa.getRut()
                    );

                    System.out.println(
                            "La empresa ha sido desbloqueada correctamente."
                    );

                } else {

                    System.out.println(
                            "Operación cancelada. La empresa sigue bloqueada."
                    );
                }

            } else {

                System.out.println(
                        "Estado actual: DESBLOQUEADA"
                );

                System.out.print(
                        "¿Desea bloquear a esta empresa? (S/N): "
                );

                String respuesta =
                        scanner.nextLine()
                                .trim()
                                .toUpperCase();

                if (respuesta.equals("S")) {

                    empresaDAO.bloquear(
                            empresa.getRut()
                    );

                    System.out.println(
                            "La empresa ha sido bloqueada correctamente."
                    );

                } else {

                    System.out.println(
                            "Operación cancelada. La empresa sigue activa."
                    );
                }
            }

        } else {

            System.out.println(
                    "Error: No existe ninguna empresa registrada con el RUT: "
                            + rutBuscado
            );
        }
    }

    // ============================================================
    // MODIFICAR EMPRESA
    // ============================================================

    private void ejecutarModificarEmpresa(
            Scanner scanner
    ) throws SQLException {

        List<Empresa> empresas =
                empresaDAO.listar();

        if (empresas.isEmpty()) {

            System.out.println(
                    "No hay empresas registradas."
            );

            return;
        }

        System.out.println(
                "\n------------------------------"
        );

        System.out.println(
                "EMPRESAS REGISTRADAS"
        );

        System.out.println(
                "------------------------------"
        );

        for (Empresa empresa : empresas) {

            System.out.println(
                    "RUT: " + empresa.getRut()
            );

            System.out.println(
                    "Nombre: " + empresa.getNombre()
            );

            System.out.println(
                    "Mail: " + empresa.getMail()
            );

            System.out.println(
                    "------------------------------"
            );
        }

        System.out.print(
                "\nIngrese el RUT de la empresa que desea modificar: "
        );

        String rutBuscado =
                scanner.nextLine().trim();

        Empresa empresa =
                empresaDAO.buscarPorId(rutBuscado);

        if (empresa == null) {

            System.out.println(
                    "Error: No existe ninguna empresa registrada con el RUT: "
                            + rutBuscado
            );

            return;
        }

        System.out.println(
                "\nEmpresa seleccionada: "
                        + empresa.getNombre()
        );

        System.out.print(
                "Nuevo nombre ("
                        + empresa.getNombre()
                        + "): "
        );

        String nuevoNombre =
                scanner.nextLine().trim();

        if (!nuevoNombre.isEmpty()) {
            empresa.setNombre(nuevoNombre);
        }

        System.out.print(
                "Nuevo mail ("
                        + empresa.getMail()
                        + "): "
        );

        String nuevoMail =
                scanner.nextLine().trim();

        if (!nuevoMail.isEmpty()) {
            empresa.setMail(nuevoMail);
        }

        System.out.print(
                "Nueva descripción ("
                        + empresa.getDescripcion()
                        + "): "
        );

        String nuevaDescripcion =
                scanner.nextLine().trim();

        if (!nuevaDescripcion.isEmpty()) {
            empresa.setDescripcion(nuevaDescripcion);
        }

        System.out.print(
                "Nuevo sitio web ("
                        + empresa.getSitioWeb()
                        + "): "
        );

        String nuevoSitioWeb =
                scanner.nextLine().trim();

        if (!nuevoSitioWeb.isEmpty()) {
            empresa.setSitioWeb(nuevoSitioWeb);
        }

        System.out.print(
                "Nuevo logo ("
                        + empresa.getLogo()
                        + "): "
        );

        String nuevoLogo =
                scanner.nextLine().trim();

        if (!nuevoLogo.isEmpty()) {
            empresa.setLogo(nuevoLogo);
        }

        System.out.print(
                "Nuevo teléfono ("
                        + empresa.getTelefono()
                        + "): "
        );

        String telefonoIngresado =
                scanner.nextLine().trim();

        if (!telefonoIngresado.isEmpty()) {

            try {

                int nuevoTelefono =
                        Integer.parseInt(
                                telefonoIngresado
                        );

                if (nuevoTelefono <= 0) {

                    System.out.println(
                            "Error: El teléfono debe ser mayor a 0."
                    );

                    return;
                }

                empresa.setTelefono(
                        nuevoTelefono
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Error: El teléfono debe ser un número válido."
                );

                return;
            }
        }

        empresaDAO.modificar(empresa);

        System.out.println(
                "\nEmpresa modificada correctamente."
        );
    }

    // ============================================================
    // ELIMINAR EMPRESA
    // ============================================================

    private void ejecutarEliminarEmpresa(
            Scanner scanner
    ) throws SQLException {

        System.out.print(
                "Ingrese el RUT de la empresa que desea eliminar: "
        );

        String rutBuscado =
                scanner.nextLine().trim();

        Empresa empresa =
                empresaDAO.buscarPorId(rutBuscado);

        if (empresa != null) {

            System.out.println(
                    "Empresa encontrada: "
                            + empresa.getNombre()
                            + " (RUT: "
                            + empresa.getRut()
                            + ")"
            );

            System.out.print(
                    "¿Está seguro que desea eliminar permanentemente esta empresa? (S/N): "
            );

            String confirmacion =
                    scanner.nextLine()
                            .trim()
                            .toUpperCase();

            if (confirmacion.equals("S")) {

                empresaDAO.eliminar(
                        empresa.getRut()
                );

                System.out.println(
                        "La empresa ha sido eliminada correctamente del registro."
                );

            } else {

                System.out.println(
                        "Operación cancelada. No se han realizado cambios."
                );
            }

        } else {

            System.out.println(
                    "Error: No existe ninguna empresa registrada con el RUT: "
                            + rutBuscado
            );
        }
    }

    // ============================================================
    // LISTAR EMPRESAS
    // ============================================================

    private void ejecutarListarEmpresas()
            throws SQLException {

        List<Empresa> empresas = empresaDAO.listar();

        if (empresas.isEmpty()) {

            System.out.println(
                    "No hay empresas registradas en el sistema."
            );

            return;
        }

        System.out.println(
                "\nLISTADO DE EMPRESAS REGISTRADAS"
        );

        for (Empresa emp : empresas) {

            String estado =
                    emp.isBloqueado()
                            ? "Bloqueada"
                            : "Activa";

            System.out.println(
                    "----------------------------------------"
            );

            System.out.println(
                    "RUT:          "
                            + emp.getRut()
            );

            System.out.println(
                    "Nombre:       "
                            + emp.getNombre()
            );

            System.out.println(
                    "Email:        "
                            + emp.getMail()
            );

            System.out.println(
                    "Teléfono:     "
                            + emp.getTelefono()
            );

            System.out.println(
                    "Sitio Web:    "
                            + emp.getSitioWeb()
            );

            System.out.println(
                    "Estado:       "
                            + estado
            );
        }

        System.out.println(
                "----------------------------------------"
        );

        System.out.println(
                "Total de registros: "
                        + empresas.size()
        );
    }

    // ============================================================
    // LISTAR OFERTAS
    // ============================================================

    private void ejecutarListarOfertas()
            throws SQLException {

        List<OfertaLaboral> ofertas =
                ofertaLaboralDAO.listar();

        System.out.println(
                "\n------------------------------------------"
        );

        System.out.println(
                "          OFERTAS LABORALES"
        );

        System.out.println(
                "------------------------------------------"
        );

        if (ofertas.isEmpty()) {

            System.out.println(
                    "No hay ofertas laborales registradas."
            );

            return;
        }

        for (OfertaLaboral oferta : ofertas) {

            System.out.println(
                    "ID: " + oferta.getId()
            );

            System.out.println(
                    "Título: " + oferta.getTitulo()
            );

            System.out.println(
                    "Descripción: "
                            + oferta.getDescripcion()
            );

            System.out.println(
                    "Requisitos: "
                            + oferta.getRequisitos()
            );

            System.out.println(
                    "Fecha publicación: "
                            + oferta.getFechaPublicacion()
            );

            System.out.println(
                    "Fecha cierre: "
                            + oferta.getFechaCierre()
            );

            System.out.println(
                    "Estado: "
                            + oferta.getEstado()
            );

            if (oferta.getEmpresa() != null) {

                System.out.println(
                        "Empresa: "
                                + oferta.getEmpresa().getNombre()
                                + " - RUT: "
                                + oferta.getEmpresa().getRut()
                );
            }

            System.out.println(
                    "------------------------------------------"
            );
        }
    }

    // ============================================================
    // ELIMINAR OFERTA
    // ============================================================

    private void ejecutarEliminarOferta(
            Scanner scanner
    ) throws SQLException {

        ejecutarListarOfertas();

        if (ofertaLaboralDAO.listar().isEmpty()) {
            return;
        }

        System.out.print(
                "\nIngrese el ID de la oferta que desea eliminar: "
        );

        int idBuscado =
                scanner.nextInt();

        scanner.nextLine();

        OfertaLaboral oferta =
                ofertaLaboralDAO.buscarPorId(
                        idBuscado
                );

        if (oferta != null) {

            System.out.println(
                    "Oferta encontrada: "
                            + oferta.getTitulo()
                            + " (ID: "
                            + oferta.getId()
                            + ")"
            );

            System.out.print(
                    "¿Está seguro que desea eliminar permanentemente esta oferta? (S/N): "
            );

            String confirmacion =
                    scanner.nextLine()
                            .trim()
                            .toUpperCase();

            if (confirmacion.equals("S")) {

                ofertaLaboralDAO.eliminar(
                        oferta.getId()
                );

                System.out.println(
                        "La oferta ha sido eliminada correctamente, junto con sus postulaciones asociadas."
                );

            } else {

                System.out.println(
                        "Operación cancelada. No se han realizado cambios."
                );
            }

        } else {

            System.out.println(
                    "Error: No existe ninguna oferta registrada con el ID "
                            + idBuscado
            );
        }
    }

    // ============================================================
    // CONSULTAR OFERTAS DE EMPRESA
    // ============================================================

    private void ejecutarConsultarOfertasEmp(
            Scanner scanner
    ) throws SQLException {

        List<Empresa> empresas =
                empresaDAO.listar();

        if (empresas.isEmpty()) {

            System.out.println(
                    "No hay empresas registradas."
            );

            return;
        }

        System.out.println(
                "\nEmpresas registradas:"
        );

        for (Empresa empresa : empresas) {

            System.out.println(
                    "RUT: "
                            + empresa.getRut()
                            + " - "
                            + empresa.getNombre()
            );
        }

        System.out.print(
                "\nIngrese el RUT de la empresa que desea consultar: "
        );

        String rutBuscado =
                scanner.nextLine().trim();

        Empresa empresa =
                empresaDAO.buscarPorId(
                        rutBuscado
                );

        if (empresa == null) {

            System.out.println(
                    "Error: No existe ninguna empresa registrada con el RUT: "
                            + rutBuscado
            );

            return;
        }

        System.out.println(
                "\nEmpresa: "
                        + empresa.getNombre()
                        + " (RUT: "
                        + empresa.getRut()
                        + ")"
        );

        List<OfertaLaboral> ofertas =
                ofertaLaboralDAO.listarPorEmpresa(
                        rutBuscado
                );

        if (ofertas.isEmpty()) {

            System.out.println(
                    "Esta empresa no tiene ofertas publicadas."
            );

            return;
        }

        System.out.println(
                "\nOfertas publicadas:"
        );

        for (OfertaLaboral oferta : ofertas) {

            System.out.println(
                    "----------------------------------------"
            );

            System.out.println(
                    "ID:                 "
                            + oferta.getId()
            );

            System.out.println(
                    "Título:             "
                            + oferta.getTitulo()
            );

            System.out.println(
                    "Estado:             "
                            + oferta.getEstado()
            );

            System.out.println(
                    "Fecha publicación:  "
                            + oferta.getFechaPublicacion()
            );

            System.out.println(
                    "Fecha cierre:       "
                            + oferta.getFechaCierre()
            );
        }

        System.out.println(
                "----------------------------------------"
        );
    }

    // ============================================================
    // LISTAR POSTULACIONES
    // ============================================================

    private void ejecutarListarPostulaciones()
            throws SQLException {

        List<Postulacion> postulaciones =
                postulacionDAO.listar();

        System.out.println(
                "\n------------------------------------------"
        );

        System.out.println(
                "              POSTULACIONES"
        );

        System.out.println(
                "------------------------------------------"
        );

        if (postulaciones.isEmpty()) {

            System.out.println(
                    "No hay postulaciones registradas."
            );

            return;
        }

        for (Postulacion postulacion : postulaciones) {

            System.out.println(
                    "ID postulación: "
                            + postulacion.getId()
            );

            System.out.println(
                    "Fecha: "
                            + postulacion.getFechaPostulacion()
            );

            System.out.println(
                    "Estado: "
                            + postulacion.getEstado()
            );

            System.out.println(
                    "Mensaje: "
                            + postulacion.getMensaje()
            );

            System.out.println(
                    "Postulante: "
                            + postulacion.getPostulante().getNombre()
                            + " - CI: "
                            + postulacion.getPostulante().getCi()
            );

            System.out.println(
                    "Oferta: "
                            + postulacion.getOfertaLaboral().getTitulo()
                            + " - ID: "
                            + postulacion.getOfertaLaboral().getId()
            );

            System.out.println(
                    "------------------------------------------"
            );
        }
    }

    // ============================================================
    // ELIMINAR POSTULACIÓN
    // ============================================================

    private void ejecutarEliminarPostulacion(
            Scanner scanner
    ) throws SQLException {

        System.out.println(
                "\n------------------------------------------"
        );

        System.out.println(
                "          ELIMINAR POSTULACIÓN"
        );

        System.out.println(
                "------------------------------------------"
        );

        List<Postulacion> postulaciones =
                postulacionDAO.listar();

        if (postulaciones.isEmpty()) {

            System.out.println(
                    "No hay postulaciones registradas."
            );

            return;
        }

        for (Postulacion postulacion : postulaciones) {

            System.out.println(
                    "ID: "
                            + postulacion.getId()
            );

            System.out.println(
                    "Postulante: "
                            + postulacion.getPostulante().getNombre()
                            + " - CI: "
                            + postulacion.getPostulante().getCi()
            );

            System.out.println(
                    "Oferta: "
                            + postulacion.getOfertaLaboral().getTitulo()
                            + " - ID: "
                            + postulacion.getOfertaLaboral().getId()
            );

            System.out.println(
                    "Estado: "
                            + postulacion.getEstado()
            );

            System.out.println(
                    "------------------------------------------"
            );
        }

        System.out.print(
                "\nIngrese el ID de la postulación que desea eliminar: "
        );

        String idIngresado =
                scanner.nextLine().trim();

        int id;

        try {

            id =
                    Integer.parseInt(
                            idIngresado
                    );

        } catch (NumberFormatException e) {

            System.out.println(
                    "Error: El ID debe ser un número."
            );

            return;
        }

        Postulacion postulacion =
                postulacionDAO.buscarPorId(
                        id
                );

        if (postulacion == null) {

            System.out.println(
                    "Error: No existe una postulación con ID "
                            + id
                            + "."
            );

            return;
        }

        System.out.println(
                "\nPostulación seleccionada:"
        );

        System.out.println(
                "ID: "
                        + postulacion.getId()
        );

        System.out.println(
                "Postulante: "
                        + postulacion.getPostulante().getNombre()
        );

        System.out.println(
                "Oferta: "
                        + postulacion.getOfertaLaboral().getTitulo()
        );

        System.out.print(
                "\n¿Confirma que desea eliminarla? (S/N): "
        );

        String confirmacion =
                scanner.nextLine().trim();

        if (!confirmacion.equalsIgnoreCase("S")) {

            System.out.println(
                    "Eliminación cancelada."
            );

            return;
        }

        postulacionDAO.eliminar(id);

        System.out.println(
                "Postulación eliminada correctamente."
        );
    }

    // ============================================================
    // CONSULTAR POSTULACIONES DE UN POSTULANTE
    // ============================================================

    private void ejecutarConsultarPostulacionUsu(
            Scanner scanner
    ) throws SQLException {

        List<Postulante> postulantes =
                postulanteDAO.listar();

        if (postulantes.isEmpty()) {

            System.out.println(
                    "No hay postulantes registrados."
            );

            return;
        }

        System.out.println(
                "\nPostulantes registrados:"
        );

        for (Postulante postulante : postulantes) {

            System.out.println(
                    "CI: "
                            + postulante.getCi()
                            + " - "
                            + postulante.getNombre()
            );
        }

        System.out.print(
                "\nIngrese la CI del postulante que desea consultar: "
        );

        int ciBuscada =
                scanner.nextInt();

        scanner.nextLine();

        Postulante postulante =
                postulanteDAO.buscarPorId(
                        ciBuscada
                );

        if (postulante == null) {

            System.out.println(
                    "Error: No existe ningún postulante registrado con la CI "
                            + ciBuscada
            );

            return;
        }

        System.out.println(
                "\nPostulante: "
                        + postulante.getNombre()
                        + " (CI: "
                        + postulante.getCi()
                        + ")"
        );

        List<Postulacion> postulaciones =
                postulacionDAO.listarPorPostulante(
                        ciBuscada
                );

        if (postulaciones.isEmpty()) {

            System.out.println(
                    "Este postulante no registra postulaciones."
            );

            return;
        }

        System.out.println(
                "\nPostulaciones realizadas:"
        );

        for (Postulacion postulacion : postulaciones) {

            System.out.println(
                    "----------------------------------------"
            );

            System.out.println(
                    "Oferta:   "
                            + postulacion.getOfertaLaboral().getTitulo()
            );

            System.out.println(
                    "Empresa:  "
                            + postulacion.getOfertaLaboral()
                            .getEmpresa()
                            .getNombre()
            );

            System.out.println(
                    "Fecha:    "
                            + postulacion.getFechaPostulacion()
            );

            System.out.println(
                    "Estado:   "
                            + postulacion.getEstado()
            );

            System.out.println(
                    "Mensaje:  "
                            + postulacion.getMensaje()
            );
        }

        System.out.println("----------------------------------------");

    }
    private void menuAccesoPostulante(Scanner scanner) {

        boolean volver = false;

        while (!volver) {

            System.out.println("\n========================================");
            System.out.println("              POSTULANTE");
            System.out.println("========================================");
            System.out.println("1. Registrarse");
            System.out.println("2. Iniciar sesión");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");

            String opcion = scanner.nextLine().trim();

            try {

                switch (opcion) {

                    case "1":
                        ejecutarRegistrarPostulante(scanner);
                        break;

                    case "2":
                        ejecutarIniciarSesionPostulante(scanner);

                        if (postulanteAutenticado != null) {
                            menuPostulante(scanner);
                        }

                        break;

                    case "0":
                        volver = true;
                        break;

                    default:
                        System.out.println("Error: Opción no válida.");
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error al acceder a la base de datos: "
                                + e.getMessage()
                );
            }
        }
    }

    private void menuPostulante(Scanner scanner) {

        boolean cerrarSesion = false;

        while (!cerrarSesion && postulanteAutenticado != null) {

            System.out.println("\n========================================");
            System.out.println("           PANEL DE POSTULANTE");
            System.out.println("========================================");
            System.out.println(
                    "Bienvenido/a, "
                            + postulanteAutenticado.getNombre()
            );
            System.out.println("----------------------------------------");
            System.out.println("1. Completar información personal");
            System.out.println("2. Agregar estudios");
            System.out.println("3. Cerrar sesión");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");

            String opcion = scanner.nextLine().trim();

            try {

                switch (opcion) {

                    case "1":
                        ejecutarCompletarInformacionPersonal(scanner);
                        break;

                    case "2":
                        ejecutarAgregarEstudios(scanner);
                        break;

                    case "3":
                        ejecutarCerrarSesionPostulante();
                        cerrarSesion = true;
                        break;

                    case "0":
                        cerrarSesion = true;
                        break;

                    default:
                        System.out.println("Error: Opción no válida.");
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error al acceder a la base de datos: "
                                + e.getMessage()
                );
            }
        }
    }


    private void ejecutarIniciarSesionPostulante(
            Scanner scanner
    ) throws SQLException {

        System.out.println("\n========================================");
        System.out.println("      INICIAR SESIÓN - POSTULANTE");
        System.out.println("========================================");

        System.out.print("Mail: ");
        String mail =
                scanner.nextLine().trim();

        System.out.print("Contraseña: ");
        String password =
                scanner.nextLine();

        if (mail.isEmpty() || password.isEmpty()) {

            System.out.println(
                    "Error: El mail y la contraseña son obligatorios."
            );

            return;
        }

        Postulante postulante =
                postulanteDAO.iniciarSesion(
                        mail,
                        password
                );

        if (postulante == null) {

            System.out.println(
                    "Error: Mail o contraseña incorrectos."
            );

            return;
        }

        if (postulante.isBloqueado()) {

            System.out.println(
                    "Error: Su usuario se encuentra bloqueado."
            );

            return;
        }

        postulanteAutenticado = postulante;

        System.out.println("\nInicio de sesión exitoso.");

        System.out.println(
                "Bienvenido/a "
                        + postulanteAutenticado.getNombre()
                        + "."
        );

        mostrarDatosPostulante(postulanteAutenticado);
    }

    private void ejecutarCompletarInformacionPersonal(
            Scanner scanner
    ) throws SQLException {

        if (postulanteAutenticado == null) {

            System.out.println(
                    "Error: Debe iniciar sesión."
            );

            return;
        }

        System.out.println("\n========================================");
        System.out.println("     COMPLETAR INFORMACIÓN PERSONAL");
        System.out.println("========================================");

        // TELÉFONO
        System.out.print("Teléfono: ");
        String telefonoIngresado =
                scanner.nextLine().trim();

        if (!telefonoIngresado.isEmpty()) {

            try {

                int telefono =
                        Integer.parseInt(
                                telefonoIngresado
                        );

                if (telefono <= 0) {

                    System.out.println(
                            "Error: El teléfono debe ser mayor a 0."
                    );

                    return;
                }

                postulanteAutenticado.setTelefono(
                        telefono
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Error: El teléfono debe ser numérico."
                );

                return;
            }
        }

        // DESCRIPCIÓN
        System.out.print(
                "Descripción personal: "
        );

        String descripcion =
                scanner.nextLine().trim();

        if (!descripcion.isEmpty()) {

            postulanteAutenticado.setDescripcion(
                    descripcion
            );
        }

        // FOTO
        System.out.print(
                "Ruta o URL de foto de perfil: "
        );

        String foto =
                scanner.nextLine().trim();

        if (!foto.isEmpty()) {

            postulanteAutenticado.setFotoPerfil(
                    foto
            );
        }

        // CV
        System.out.print(
                "Ruta o URL del CV: "
        );

        String cv =
                scanner.nextLine().trim();

        if (!cv.isEmpty()) {

            postulanteAutenticado.setCv(
                    cv
            );
        }

        // FECHA NACIMIENTO
        System.out.print(
                "Fecha de nacimiento (AAAA-MM-DD): "
        );

        String fechaIngresada =
                scanner.nextLine().trim();

        if (!fechaIngresada.isEmpty()) {

            try {

                postulanteAutenticado.setFechaNacimiento(
                        java.time.LocalDate.parse(
                                fechaIngresada
                        )
                );

            } catch (
                    java.time.format.DateTimeParseException e
            ) {

                System.out.println(
                        "Error: La fecha debe tener formato AAAA-MM-DD."
                );

                return;
            }
        }

        // LOCALIDAD
        System.out.print("Localidad: ");

        String localidad =
                scanner.nextLine().trim();

        if (!localidad.isEmpty()) {

            postulanteAutenticado.setLocalidad(
                    localidad
            );
        }

        // GUARDAMOS
        postulanteDAO.modificar(
                postulanteAutenticado
        );

        System.out.println(
                "\nInformación personal guardada correctamente."
        );
    }

    private void ejecutarRegistrarPostulante(Scanner scanner) throws SQLException {

        System.out.println("\n========================================");
        System.out.println("        REGISTRO DE POSTULANTE");
        System.out.println("========================================");

        System.out.print("Ingrese su CI: ");
        String ciIngresada = scanner.nextLine().trim();

        int ci;

        try {
            ci = Integer.parseInt(ciIngresada);
        } catch (NumberFormatException e) {
            System.out.println("Error: La CI debe ser numérica.");
            return;
        }

        if (postulanteDAO.buscarPorId(ci) != null) {
            System.out.println("Error: Ya existe un postulante con esa CI.");
            return;
        }

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();

        System.out.print("Mail: ");
        String mail = scanner.nextLine().trim();

        if (mail.isEmpty()) {
            System.out.println("Error: El mail es obligatorio.");
            return;
        }

        if (postulanteDAO.buscarPorMail(mail) != null) {
            System.out.println("Error: El mail ingresado ya está en uso.");
            return;
        }

        String password = leerPassword(scanner).trim();
        if (password.isEmpty()) {
            System.out.println("Error: La contraseña es obligatoria.");
            return;
        }

        if (nombre.isEmpty() || mail.isEmpty() || password.isEmpty()) {
            System.out.println("Error: Los campos son obligatorios.");
            return;
        }

        System.out.println("Género:");
        System.out.println("1. Masculino");
        System.out.println("2. Femenino");
        System.out.print("Seleccione una opción: ");

        String opcionGenero = scanner.nextLine().trim();

        DTGenero genero;

        switch (opcionGenero) {

            case "1":
                genero = DTGenero.Masculino;
                break;

            case "2":
                genero = DTGenero.Femenino;
                break;

            default:
                System.out.println("Error: Opción de género no válida.");
                return;
        }

        Postulante nuevo = new Postulante();

        nuevo.setCi(ci);
        nuevo.setNombre(nombre);
        nuevo.setMail(mail);
        nuevo.setPassword(password);
        nuevo.setFechaRegistro(java.time.LocalDate.now());
        nuevo.setGenero(genero);
        nuevo.setTelefono(0);
        nuevo.setBloqueado(false);

        postulanteDAO.agregar(nuevo);

        System.out.println("Postulante registrado correctamente.");
    }

    private void ejecutarAgregarEstudios(Scanner scanner) throws SQLException {

        if (postulanteAutenticado == null) {
            System.out.println("Error: Debe iniciar sesión.");
            return;
        }

        System.out.println("\n========================================");
        System.out.println("             AGREGAR ESTUDIOS");
        System.out.println("========================================");

        String estudiosActuales =
                postulanteAutenticado.getDatosEstudio();

        if (estudiosActuales != null && !estudiosActuales.isBlank()) {

            System.out.println("Estudios actuales:");
            System.out.println(estudiosActuales);
        }

        System.out.print("Ingrese el nuevo estudio: ");
        String nuevoEstudio = scanner.nextLine().trim();

        if (nuevoEstudio.isEmpty()) {
            System.out.println("Error: Debe ingresar un estudio.");
            return;
        }

        if (estudiosActuales == null || estudiosActuales.isBlank()) {

            postulanteAutenticado.setDatosEstudio(
                    nuevoEstudio
            );

        } else {

            postulanteAutenticado.setDatosEstudio(
                    estudiosActuales + " | " + nuevoEstudio
            );
        }

        postulanteDAO.modificar(
                postulanteAutenticado
        );

        System.out.println(
                "Estudio agregado correctamente."
        );
    }

    private void ejecutarCerrarSesionPostulante() {

        if (postulanteAutenticado == null) {
            return;
        }

        System.out.println(
                "Cerrando sesión de "
                        + postulanteAutenticado.getNombre()
                        + "..."
        );

        postulanteAutenticado = null;

        System.out.println(
                "Sesión cerrada correctamente."
        );
    }
    private String leerPassword(Scanner scanner) {

        java.io.Console console = System.console();

        if (console != null) {
            char[] passwordChars = console.readPassword("Contraseña: ");
            return new String(passwordChars);
        }

        // Respaldo para IntelliJ
        System.out.print("Contraseña: ");
        return scanner.nextLine();
    }
    private void mostrarDatosPostulante(Postulante postulante) {

        System.out.println("\n========================================");
        System.out.println("               MI CV DIGITAL");
        System.out.println("========================================");

        System.out.println("CI: " + postulante.getCi());

        System.out.println(
                "Nombre: " + valorOIncompleto(postulante.getNombre())
        );

        System.out.println(
                "Mail: " + valorOIncompleto(postulante.getMail())
        );

        System.out.println(
                "Fecha de registro: "
                        + (postulante.getFechaRegistro() != null
                        ? postulante.getFechaRegistro()
                        : "No completado")
        );

        System.out.println(
                "Teléfono: "
                        + (postulante.getTelefono() > 0
                        ? postulante.getTelefono()
                        : "No completado")
        );

        System.out.println(
                "Descripción: "
                        + valorOIncompleto(postulante.getDescripcion())
        );

        System.out.println(
                "Foto de perfil: "
                        + valorOIncompleto(postulante.getFotoPerfil())
        );

        System.out.println(
                "CV: "
                        + valorOIncompleto(postulante.getCv())
        );

        System.out.println(
                "Fecha de nacimiento: "
                        + (postulante.getFechaNacimiento() != null
                        ? postulante.getFechaNacimiento()
                        : "No completado")
        );

        System.out.println(
                "Género: "
                        + (postulante.getGenero() != null
                        ? postulante.getGenero()
                        : "No completado")
        );

        System.out.println(
                "Localidad: "
                        + valorOIncompleto(postulante.getLocalidad())
        );

        System.out.println(
                "Estudios: "
                        + valorOIncompleto(postulante.getDatosEstudio())
        );

        System.out.println(
                "Experiencia: "
                        + valorOIncompleto(postulante.getDatosExperiencia())
        );

        System.out.println(
                "Estado: "
                        + (postulante.isBloqueado()
                        ? "Bloqueado"
                        : "Activo")
        );

        System.out.println("========================================");
    }

    private String valorOIncompleto(String valor) {

        if (valor == null || valor.isBlank()) {
            return "No completado";
        }

        return valor;
    }












}

