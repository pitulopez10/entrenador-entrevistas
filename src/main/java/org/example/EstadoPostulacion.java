package org.example;

public enum EstadoPostulacion {
    enviado,
    enRevision,
    seleccionado,
    rechazado;

    public static EstadoPostulacion desdeTexto(String texto) {
        if (texto == null || texto.isBlank()) {
            return enviado; // Valor por defecto
        }

        // Compara ignorando mayúsculas y minúsculas directamente con el texto original
        for (EstadoPostulacion estado : EstadoPostulacion.values()) {
            if (estado.name().equalsIgnoreCase(texto.trim())) {
                return estado;
            }
        }

        return enviado; // Valor por defecto si no coincide
    }
}