package org.example;

public class Main {

    public static void main(String[] args) {

//        PostulanteDAO postulanteDAO = new PostulanteDAO();

//        try {
//
//            // LISTAR TODOS
//            List<Postulante> postulantes = postulanteDAO.listar();
//
//            System.out.println("POSTULANTES:");
//
//            for (Postulante postulante : postulantes) {
//                System.out.println(
//                        postulante.getCi() + " - " +
//                                postulante.getNombre() + " - " +
//                                postulante.getMail() + " - " +
//                                postulante.getGenero() + " - " +
//                                "Bloqueado: " + postulante.isBloqueado()
//                );
//            }
//
//            System.out.println();
//
//            // BUSCAR POR CI
//            int ciBuscada = 12345672;
//
//            Postulante encontrado = postulanteDAO.buscarPorId(ciBuscada);
//
//            if (encontrado != null) {
//                System.out.println("POSTULANTE ENCONTRADO:");
//                System.out.println("CI: " + encontrado.getCi());
//                System.out.println("Nombre: " + encontrado.getNombre());
//                System.out.println("Mail: " + encontrado.getMail());
//                System.out.println("Género: " + encontrado.getGenero());
//                System.out.println("Bloqueado: " + encontrado.isBloqueado());
//            } else {
//                System.out.println("No existe un postulante con CI " + ciBuscada);
//            }
//
//        } catch (SQLException e) {
//            System.out.println("Error al acceder a la base de datos:");
//            System.out.println(e.getMessage());
//        }

        Menu menu = new Menu();

        menu.iniciar();
    }
}