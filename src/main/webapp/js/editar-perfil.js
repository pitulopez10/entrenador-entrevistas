document.addEventListener("DOMContentLoaded", function () {

    const inputFoto =
        document.getElementById("fotoPerfil");

    const previewFoto =
        document.getElementById("previewFoto");

    const placeholderFoto =
        document.getElementById("placeholderFoto");

    const formulario =
        document.getElementById("formEditarPerfil");

    const inputCv =
        document.getElementById("cv");

    const nombreCvSeleccionado =
        document.getElementById("nombreCvSeleccionado");


    const MAX_BYTES =
        5 * 1024 * 1024;


    const TIPOS_IMAGEN_PERMITIDOS = [
        "image/jpeg",
        "image/png",
        "image/webp"
    ];


    let previewUrl = null;


    // ==================================================
    // FOTO DE PERFIL
    // ==================================================

    if (inputFoto) {

        inputFoto.addEventListener(
            "change",
            function () {

                const archivo =
                    inputFoto.files[0];


                if (!archivo) {
                    return;
                }


                // =========================
                // VALIDAR TIPO
                // =========================

                if (!TIPOS_IMAGEN_PERMITIDOS.includes(
                    archivo.type
                )) {

                    alert(
                        "La imagen debe ser JPG, PNG o WEBP."
                    );

                    inputFoto.value = "";

                    return;
                }


                // =========================
                // VALIDAR TAMAÑO
                // =========================

                if (archivo.size > MAX_BYTES) {

                    alert(
                        "La imagen no puede superar los 5 MB."
                    );

                    inputFoto.value = "";

                    return;
                }


                // =========================
                // LIBERAR PREVIEW ANTERIOR
                // =========================

                if (previewUrl) {

                    URL.revokeObjectURL(
                        previewUrl
                    );
                }


                // =========================
                // CREAR PREVIEW
                // =========================

                previewUrl =
                    URL.createObjectURL(
                        archivo
                    );


                if (placeholderFoto) {

                    placeholderFoto.style.display =
                        "none";
                }


                if (previewFoto) {

                    previewFoto.src =
                        previewUrl;

                    previewFoto.style.display =
                        "block";
                }
            }
        );
    }


    // ==================================================
    // CURRÍCULUM PDF
    // ==================================================

    if (inputCv) {

        inputCv.addEventListener(
            "change",
            function () {

                const archivo =
                    inputCv.files[0];


                if (!archivo) {

                    if (nombreCvSeleccionado) {

                        nombreCvSeleccionado.textContent =
                            "PDF. Máximo 5 MB.";
                    }

                    return;
                }


                // =========================
                // VALIDAR EXTENSIÓN
                // =========================

                const nombreArchivo =
                    archivo.name.toLowerCase();


                if (!nombreArchivo.endsWith(".pdf")) {

                    alert(
                        "El currículum debe ser un archivo PDF."
                    );

                    inputCv.value = "";


                    if (nombreCvSeleccionado) {

                        nombreCvSeleccionado.textContent =
                            "PDF. Máximo 5 MB.";
                    }

                    return;
                }


                // =========================
                // VALIDAR TIPO MIME
                // =========================

                if (archivo.type &&
                    archivo.type !== "application/pdf") {

                    alert(
                        "El archivo seleccionado no es un PDF válido."
                    );

                    inputCv.value = "";


                    if (nombreCvSeleccionado) {

                        nombreCvSeleccionado.textContent =
                            "PDF. Máximo 5 MB.";
                    }

                    return;
                }


                // =========================
                // VALIDAR TAMAÑO
                // =========================

                if (archivo.size > MAX_BYTES) {

                    alert(
                        "El currículum no puede superar los 5 MB."
                    );

                    inputCv.value = "";


                    if (nombreCvSeleccionado) {

                        nombreCvSeleccionado.textContent =
                            "PDF. Máximo 5 MB.";
                    }

                    return;
                }


                // =========================
                // MOSTRAR NOMBRE
                // =========================

                if (nombreCvSeleccionado) {

                    nombreCvSeleccionado.textContent =
                        archivo.name;
                }
            }
        );
    }


    // ==================================================
    // VALIDACIÓN DEL FORMULARIO
    // ==================================================

    if (formulario) {

        formulario.addEventListener(
            "submit",
            function (event) {

                const nombre =
                    document
                        .getElementById("nombre")
                        .value
                        .trim();


                const mail =
                    document
                        .getElementById("mail")
                        .value
                        .trim();


                const fechaNacimiento =
                    document
                        .getElementById("fechaNacimiento")
                        .value;


                // =========================
                // NOMBRE
                // =========================

                if (nombre.length < 2) {

                    event.preventDefault();

                    alert(
                        "Ingresá un nombre válido."
                    );

                    return;
                }


                // =========================
                // CORREO
                // =========================

                if (!mail.includes("@")) {

                    event.preventDefault();

                    alert(
                        "Ingresá un correo electrónico válido."
                    );

                    return;
                }


                // =========================
                // FECHA DE NACIMIENTO
                // =========================

                if (fechaNacimiento) {

                    const fecha =
                        new Date(
                            fechaNacimiento + "T00:00:00"
                        );


                    const hoy =
                        new Date();


                    hoy.setHours(
                        0,
                        0,
                        0,
                        0
                    );


                    if (fecha > hoy) {

                        event.preventDefault();

                        alert(
                            "La fecha de nacimiento no puede ser futura."
                        );

                        return;
                    }
                }
            }
        );
    }


    // ==================================================
    // LIMPIAR MEMORIA
    // ==================================================

    window.addEventListener(
        "beforeunload",
        function () {

            if (previewUrl) {

                URL.revokeObjectURL(
                    previewUrl
                );
            }
        }
    );

});