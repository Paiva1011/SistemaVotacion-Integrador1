package com.votacion.sistema_votacion.controller;

import com.votacion.sistema_votacion.model.PartidoPolitico;
import com.votacion.sistema_votacion.repository.PartidoPoliticoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/partidos")
public class PartidoPoliticoController {

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
        return "redirect:/partidos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("adminLogueado") == null)
            return "redirect:/admin/login";

        partidoRepository.deleteById(id);
        return "redirect:/partidos";
    }
}