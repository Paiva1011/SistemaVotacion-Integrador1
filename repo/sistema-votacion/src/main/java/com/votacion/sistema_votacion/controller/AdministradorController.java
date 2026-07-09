package com.votacion.sistema_votacion.controller;

import com.votacion.sistema_votacion.model.Administrador;
import com.votacion.sistema_votacion.repository.AdministradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdministradorController {

    @Autowired
    private AdministradorRepository administradorRepository;

    // Mostrar login admin
    @GetMapping("/login")
    public String mostrarLogin() {
        return "admin/login";
    }

    // Procesar login admin
    @PostMapping("/login")
    public String procesarLogin(@RequestParam String usuario,
            @RequestParam String password,
            HttpSession session, Model model) {
        Administrador admin = administradorRepository.findByUsuario(usuario).orElse(null);

        if (admin != null && admin.getPassword().equals(password)) {
            session.setAttribute("adminLogueado", admin);
            return "redirect:/admin/dashboard";
        }

        model.addAttribute("error", "Usuario o contraseña incorrectos");
        return "admin/login";
    }

    // Dashboard principal del admin
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (session.getAttribute("adminLogueado") == null) {
            return "redirect:/admin/login";
        }
        return "admin/dashboard";
    }

    // Cerrar sesión
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login";
    }
}