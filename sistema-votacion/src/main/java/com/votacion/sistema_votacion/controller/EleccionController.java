package com.votacion.sistema_votacion.controller;

import com.votacion.sistema_votacion.model.Eleccion;
import com.votacion.sistema_votacion.repository.EleccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/elecciones")
public class EleccionController {

    @Autowired
    private EleccionRepository eleccionRepository;

    // Listar elecciones (admin)
    @GetMapping
    public String listar(HttpSession session, Model model) {
        if (session.getAttribute("adminLogueado") == null)
            return "redirect:/admin/login";

        model.addAttribute("elecciones", eleccionRepository.findAll());
        return "admin/elecciones";
    }

    // Guardar nueva elección
    @PostMapping("/guardar")
    public String guardar(@RequestParam String nombre,
            @RequestParam String descripcion,
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin,
            @RequestParam String estado,
            HttpSession session) {
        if (session.getAttribute("adminLogueado") == null)
            return "redirect:/admin/login";

        Eleccion eleccion = new Eleccion(
                nombre, descripcion,
                LocalDateTime.parse(fechaInicio),
                LocalDateTime.parse(fechaFin),
                estado);
        eleccionRepository.save(eleccion);
        return "redirect:/elecciones";
    }

    // Cambiar estado de elección
    @GetMapping("/estado/{id}/{estado}")
    public String cambiarEstado(@PathVariable long id,
            @PathVariable String estado,
            HttpSession session) {
        if (session.getAttribute("adminLogueado") == null)
            return "redirect:/admin/login";

        eleccionRepository.findById(id).ifPresent(e -> {
            e.setEstado(estado);
            eleccionRepository.save(e);
        });
        return "redirect:/elecciones";
    }

    // Eliminar elección
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable long id, HttpSession session) {
        if (session.getAttribute("adminLogueado") == null)
            return "redirect:/admin/login";

        eleccionRepository.deleteById(id);
        return "redirect:/elecciones";
    }

    // Publicar resultados de una elección
    @GetMapping("/publicar/{id}")
    public String publicar(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("adminLogueado") == null)
            return "redirect:/admin/login";

        eleccionRepository.findById(id).ifPresent(e -> {
            e.setPublicada(true);
            eleccionRepository.save(e);
        });
        return "redirect:/elecciones";
    }
}