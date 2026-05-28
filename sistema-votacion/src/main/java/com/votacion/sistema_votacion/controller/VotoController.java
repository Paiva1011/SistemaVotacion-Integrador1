package com.votacion.sistema_votacion.controller;

import com.votacion.sistema_votacion.repository.CandidatoRepository;
import com.votacion.sistema_votacion.service.VotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/votar")
public class VotoController {

    @Autowired
    private VotoService votoService;

    @Autowired
    private CandidatoRepository candidatoRepository;

    @GetMapping
    public String mostrarCandidatos(Model model) {
        model.addAttribute("candidatos", candidatoRepository.findAll());
        return "votar";
    }

    @PostMapping
    public String emitirVoto(@RequestParam Long votanteId,
                              @RequestParam Long candidatoId,
                              Model model) {
        String resultado = votoService.emitirVoto(votanteId, candidatoId);
        model.addAttribute("mensaje", resultado);
        model.addAttribute("candidatos", candidatoRepository.findAll());
        return "votar";
    }
}