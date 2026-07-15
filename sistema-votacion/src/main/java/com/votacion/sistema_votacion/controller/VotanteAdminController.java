package com.votacion.sistema_votacion.controller;

import com.votacion.sistema_votacion.model.Votante;
import com.votacion.sistema_votacion.repository.VotanteRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/votantes")
public class VotanteAdminController {

    @Autowired
    private VotanteRepository votanteRepository;

    @GetMapping
    public String listar(HttpSession session, Model model) {
        if (session.getAttribute("adminLogueado") == null)
            return "redirect:/admin/login";

        model.addAttribute("votantes", votanteRepository.findAll());
        return "admin/votantes";
    }

    @PostMapping("/guardar")
    public String guardar(@RequestParam String dni,
            @RequestParam String nombres,
            @RequestParam String apellidos,
            @RequestParam String celular,
            HttpSession session, Model model) {
        if (session.getAttribute("adminLogueado") == null)
            return "redirect:/admin/login";

        // Verificar si el DNI ya existe
        if (votanteRepository.findByDni(dni).isPresent()) {
            model.addAttribute("votantes", votanteRepository.findAll());
            model.addAttribute("error", "Ya existe un votante con ese DNI");
            return "admin/votantes";
        }

        // Capitaliza la primera letra de cada palabra
        String nombresFormateados = StringUtils.capitalize(nombres.toLowerCase());
        String apellidosFormateados = StringUtils.capitalize(apellidos.toLowerCase());

        votanteRepository.save(new Votante(dni, nombresFormateados, apellidosFormateados, celular));
        return "redirect:/votantes";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("adminLogueado") == null)
            return "redirect:/admin/login";

        votanteRepository.deleteById(id);
        return "redirect:/votantes";
    }
}