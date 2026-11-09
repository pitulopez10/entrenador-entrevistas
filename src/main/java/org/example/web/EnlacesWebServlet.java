package org.example.web;

import java.net.URI;

final class EnlacesWebServlet {
    private EnlacesWebServlet() { }

    static boolean esHttp(String valor) {
        if (valor == null || valor.isBlank() || valor.chars().anyMatch(Character::isISOControl)) return false;
        try {
            URI uri = URI.create(valor.trim());
            return ("http".equalsIgnoreCase(uri.getScheme())
                    || "https".equalsIgnoreCase(uri.getScheme()))
                    && uri.getHost() != null && uri.getUserInfo() == null;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
