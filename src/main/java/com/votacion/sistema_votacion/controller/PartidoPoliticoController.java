package com.votacion.sistema_votacion.controller;

import com.votacion.sistema_votacion.model.PartidoPolitico;
import com.votacion.sistema_votacion.repository.PartidoPoliticoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/partidos")
public class PartidoPoliticoController {
    private static final Logger log = LoggerFactory.getLogger(PartidoPoliticoController.class);

    @Autowired
    private PartidoPoliticoRepository partidoRepository;

    @GetMapping
    public String listar(HttpSession session, Model model) {
        if (session.getAttribute("adminLogueado") == null)
            return "redirect:/admin/login";

        model.addAttribute("partidos", partidoRepository.findAll());
        return "admin/partidos";
    }

    @PostMapping("/guardar")
    public String guardar(@RequestParam String nombre,
            @RequestParam String simbolo,
            HttpSession session) {
        if (session.getAttribute("adminLogueado") == null)
            return "redirect:/admin/login";

        partidoRepository.save(new PartidoPolitico(nombre, simbolo));
        log.info("Partido político registrado: {}", nombre);
        return "redirect:/partidos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("adminLogueado") == null)
            return "redirect:/admin/login";

        partidoRepository.deleteById(id);
        log.info("Partido político eliminado - ID: {}", id);
        return "redirect:/partidos";
    }
}