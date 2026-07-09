package com.votacion.sistema_votacion.controller;

import com.votacion.sistema_votacion.model.*;
import com.votacion.sistema_votacion.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/voto")
public class VotoController {

    private static final Logger log = LoggerFactory.getLogger(VotoController.class);

    @Autowired private VotoRepository votoRepository;
    @Autowired private CandidatoRepository candidatoRepository;
    @Autowired private EleccionRepository eleccionRepository;
    @Autowired private ComprobanteRepository comprobanteRepository;

    @GetMapping("/elecciones")
    public String mostrarElecciones(HttpSession session, Model model) {
        if (session.getAttribute("votanteLogueado") == null) {
            log.warn("Intento de acceso a elecciones sin sesión activa");
            return "redirect:/votante/login";
        }
        Votante votante = (Votante) session.getAttribute("votanteLogueado");
        List<Eleccion> elecciones = eleccionRepository.findByEstado("ACTIVA");
        log.info("Votante {} accedió a la lista de elecciones activas", votante.getDni());
        model.addAttribute("elecciones", elecciones);
        model.addAttribute("votanteLogueado", votante);
        return "votante/elecciones";
    }

    @GetMapping("/candidatos/{idEleccion}")
    public String mostrarCandidatos(@PathVariable Long idEleccion,
            HttpSession session, Model model) {
        if (session.getAttribute("votanteLogueado") == null) {
            log.warn("Intento de acceso a candidatos sin sesión activa");
            return "redirect:/votante/login";
        }
        log.info("Mostrando candidatos para elección ID: {}", idEleccion);
        Eleccion eleccion = eleccionRepository.findById(idEleccion).orElse(null);
        List<Candidato> candidatos = candidatoRepository.findAll()
                .stream()
                .filter(c -> c.getEleccion().getIdEleccion().equals(idEleccion))
                .collect(java.util.stream.Collectors.toList());
        model.addAttribute("eleccion", eleccion);
        model.addAttribute("candidatos", candidatos);
        return "votante/candidatos";
    }

    @PostMapping("/emitir")
    public String emitirVoto(@RequestParam Long idCandidato,
            @RequestParam Long idEleccion,
            HttpSession session, Model model) {

        Votante votante = (Votante) session.getAttribute("votanteLogueado");
        if (votante == null) {
            log.warn("Intento de voto sin sesión activa");
            return "redirect:/votante/login";
        }

        Eleccion eleccion = eleccionRepository.findById(idEleccion).orElse(null);

        if (votoRepository.findByVotanteAndEleccion(votante, eleccion).isPresent()) {
            log.warn("Votante {} intentó votar dos veces en elección {}", votante.getDni(), idEleccion);
            List<Candidato> candidatos = candidatoRepository.findAll()
                    .stream()
                    .filter(c -> c.getEleccion().getIdEleccion().equals(idEleccion))
                    .collect(java.util.stream.Collectors.toList());
            model.addAttribute("eleccion", eleccion);
            model.addAttribute("candidatos", candidatos);
            model.addAttribute("error", "Ya emitiste tu voto en esta elección");
            return "votante/candidatos";
        }

        Candidato candidato = null;
        if (idCandidato != 0) {
            candidato = candidatoRepository.findById(idCandidato).orElse(null);
        }

        Voto voto = new Voto(votante, candidato, eleccion, LocalDateTime.now());
        votoRepository.save(voto);
        log.info("Voto emitido por votante {} en elección {}", votante.getDni(), idEleccion);

        String codigoVerificacion = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        Comprobante comprobante = new Comprobante(voto, codigoVerificacion,
                LocalDateTime.now(), "EMITIDO");
        comprobanteRepository.save(comprobante);
        log.info("Comprobante generado con código: {}", codigoVerificacion);

        session.setAttribute("ultimoComprobante", comprobante);
        return "redirect:/voto/comprobante";
    }

    @GetMapping("/comprobante")
    public String verComprobante(HttpSession session, Model model) {
        Comprobante comprobante = (Comprobante) session.getAttribute("ultimoComprobante");
        if (comprobante == null) {
            log.warn("Intento de acceso a comprobante sin voto registrado");
            return "redirect:/votante/login";
        }
        log.info("Mostrando comprobante de voto");
        model.addAttribute("comprobante", comprobante);
        return "votante/comprobante";
    }
}