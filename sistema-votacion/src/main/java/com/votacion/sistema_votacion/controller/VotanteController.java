package com.votacion.sistema_votacion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador encargado de gestionar las vistas relacionadas
 * con el registro de votantes.
 */
@Controller
public class VotanteController {

    /**
     * Muestra el formulario de registro de votantes.
     *
     * @return Nombre de la vista que contiene el formulario de registro.
     */
    @GetMapping("/votantes/registro")
    public String mostrarFormulario() {
        return "registro";
    }
}