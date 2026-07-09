package com.votacion.sistema_votacion.controller;

import com.votacion.sistema_votacion.model.Votante;
import com.votacion.sistema_votacion.model.Otp;
import com.votacion.sistema_votacion.repository.VotanteRepository;
import com.votacion.sistema_votacion.repository.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/votante")
public class VotanteController {

    private static final Logger log = LoggerFactory.getLogger(VotanteController.class);

    @Autowired
    private VotanteRepository votanteRepository;
    @Autowired
    private OtpRepository otpRepository;

    @GetMapping("/login")
    public String mostrarLogin() {
        log.info("Mostrando página de login del votante");
        return "votante/login";
    }

    @PostMapping("/login")
    public String procesarDni(@RequestParam String dni,
            HttpSession session, Model model) {
        log.info("Intento de login con DNI: {}", dni);
        Votante votante = votanteRepository.findByDni(dni).orElse(null);
        if (votante == null) {
            log.warn("DNI no encontrado: {}", dni);
            model.addAttribute("error", "DNI no encontrado");
            return "votante/login";
        }
        String codigo = String.valueOf((int) (Math.random() * 900000) + 100000);
        Otp otp = new Otp(votante, codigo,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(5));
        otpRepository.save(otp);
        log.info("OTP generado para DNI {}: {}", dni, codigo);
        session.setAttribute("dniVotante", dni);
        model.addAttribute("mensaje", "Se envió el código OTP a tu celular");
        return "votante/verificar-otp";
    }

    @GetMapping("/verificar-otp")
    public String mostrarVerificarOtp() {
        return "votante/verificar-otp";
    }

    @PostMapping("/verificar-otp")
    public String procesarOtp(@RequestParam String codigo,
            HttpSession session, Model model) {
        String dni = (String) session.getAttribute("dniVotante");
        if (dni == null) {
            log.warn("Intento de verificar OTP sin sesión activa");
            return "redirect:/votante/login";
        }
        Votante votante = votanteRepository.findByDni(dni).orElse(null);
        if (votante == null) {
            log.warn("Votante no encontrado para DNI: {}", dni);
            return "redirect:/votante/login";
        }
        Otp otp = otpRepository.findByVotanteAndUsadoFalse(votante).orElse(null);
        if (otp == null || !otp.getCodigo().equals(codigo)) {
            log.warn("OTP incorrecto para DNI: {}", dni);
            model.addAttribute("error", "Código OTP incorrecto");
            return "votante/verificar-otp";
        }
        if (otp.getFechaExpiracion().isBefore(LocalDateTime.now())) {
            log.warn("OTP expirado para DNI: {}", dni);
            model.addAttribute("error", "El código OTP ha expirado");
            return "votante/verificar-otp";
        }
        otp.setUsado(true);
        otpRepository.save(otp);
        session.setAttribute("votanteLogueado", votante);
        log.info("Votante con DNI {} autenticado correctamente", dni);
        return "redirect:/voto/elecciones";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        log.info("Votante cerró sesión");
        session.invalidate();
        return "redirect:/votante/login";
    }
}