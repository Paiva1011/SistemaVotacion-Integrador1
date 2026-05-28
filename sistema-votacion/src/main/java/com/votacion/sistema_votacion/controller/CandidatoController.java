package com.votacion.sistema_votacion.controller;

import com.votacion.sistema_votacion.model.Candidato;
import com.votacion.sistema_votacion.repository.CandidatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CandidatoController {

    @Autowired
    private CandidatoRepository candidatoRepository;

    @GetMapping("/candidatos")
    public String listarCandidatos(Model model) {
        List<Candidato> lista = candidatoRepository.findAllByOrderByApellidoAsc();
        model.addAttribute("candidatos", lista);
        return "candidatos";
    }
}