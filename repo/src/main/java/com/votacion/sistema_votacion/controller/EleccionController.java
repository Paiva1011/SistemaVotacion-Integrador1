package com.votacion.sistema_votacion.controller;

import com.votacion.sistema_votacion.model.Eleccion;
import com.votacion.sistema_votacion.repository.EleccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/elecciones")
public class EleccionController {

    private static final Logger log = LoggerFactory.getLogger(EleccionController.class);

    @Autowired
    private EleccionRepository eleccionRepository;

    @GetMapping
    public String listar(HttpSession session, Model model) {
        if (session.getAttribute("adminLogueado") == null) {
            log.warn("Intento de acceso a elecciones sin sesión admin");
            return "redirect:/admin/login";
        }
        log.info("Administrador accedió a la lista de elecciones");
        model.addAttribute("elecciones", eleccionRepository.findAll());
        return "admin/elecciones";
    }

    @PostMapping("/guardar")
    public String guardar(@RequestParam String nombre,
            @RequestParam String descripcion,
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin,
            @RequestParam String estado,
            HttpSession session) {
        if (session.getAttribute("adminLogueado") == null) {
            log.warn("Intento de guardar elección sin sesión admin");
            return "redirect:/admin/login";
        }
        Eleccion eleccion = new Eleccion(
                nombre, descripcion,
                LocalDateTime.parse(fechaInicio),
                LocalDateTime.parse(fechaFin),
                estado);
        eleccionRepository.save(eleccion);
        log.info("Elección guardada: {} con estado {}", nombre, estado);
        return "redirect:/elecciones";
    }

    @GetMapping("/estado/{id}/{estado}")
    public String cambiarEstado(@PathVariable Long id,
            @PathVariable String estado,
            HttpSession session) {
        if (session.getAttribute("adminLogueado") == null) {
            log.warn("Intento de cambiar estado de elección sin sesión admin");
            return "redirect:/admin/login";
        }
        eleccionRepository.findById(id).ifPresent(e -> {
            e.setEstado(estado);
            eleccionRepository.save(e);
            log.info("Estado de elección ID {} cambiado a {}", id, estado);
        });
        return "redirect:/elecciones";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("adminLogueado") == null) {
            log.warn("Intento de eliminar elección sin sesión admin");
            return "redirect:/admin/login";
        }
        eleccionRepository.deleteById(id);
        log.info("Elección eliminada con ID: {}", id);
        return "redirect:/elecciones";
    }
}