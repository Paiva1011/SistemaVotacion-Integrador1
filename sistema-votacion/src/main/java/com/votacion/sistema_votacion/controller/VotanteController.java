package com.votacion.sistema_votacion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VotanteController {

    @GetMapping("/votantes/registro")
    public String mostrarFormulario() {
        return "registro";
    }
}