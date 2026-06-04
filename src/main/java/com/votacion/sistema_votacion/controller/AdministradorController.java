package com.votacion.sistema_votacion.controller;

import com.votacion.sistema_votacion.model.Administrador;
import com.votacion.sistema_votacion.repository.AdministradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/admin")
public class AdministradorController {

    private static final Logger log = LoggerFactory.getLogger(AdministradorController.class);

    @Autowired
    private AdministradorRepository administradorRepository;

    @GetMapping("/login")
    public String mostrarLogin() {
        log.info("Mostrando página de login del administrador");
        return "admin/login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String usuario,
            @RequestParam String password,
            HttpSession session, Model model) {
        log.info("Intento de login de administrador con usuario: {}", usuario);
        Administrador admin = administradorRepository.findByUsuario(usuario).orElse(null);
        if (admin != null && admin.getPassword().equals(password)) {
            session.setAttribute("adminLogueado", admin);
            log.info("Administrador '{}' autenticado correctamente", usuario);
            return "redirect:/admin/dashboard";
        }
        log.warn("Login fallido para administrador: {}", usuario);
        model.addAttribute("error", "Usuario o contraseña incorrectos");
        return "admin/login";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (session.getAttribute("adminLogueado") == null) {
            log.warn("Intento de acceso al dashboard sin sesión activa");
            return "redirect:/admin/login";
        }
        log.info("Administrador accedió al dashboard");
        return "admin/dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        log.info("Administrador cerró sesión");
        session.invalidate();
        return "redirect:/admin/login";
    }
}