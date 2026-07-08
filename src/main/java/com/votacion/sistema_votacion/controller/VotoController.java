package com.votacion.sistema_votacion.controller;

import com.votacion.sistema_votacion.model.*;
import com.votacion.sistema_votacion.repository.*;
import com.votacion.sistema_votacion.service.VotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/voto")
public class VotoController {

    @Autowired private CandidatoRepository candidatoRepository;
    @Autowired private EleccionRepository eleccionRepository;
    @Autowired private ComprobanteRepository comprobanteRepository;
    @Autowired private VotoService votoService;

    // ── Lista de elecciones activas ────────────────────────────
    @GetMapping("/elecciones")
    public String mostrarElecciones(HttpSession session, Model model) {
        if (session.getAttribute("votanteLogueado") == null)
            return "redirect:/votante/login";

        Votante votante = (Votante) session.getAttribute("votanteLogueado");
        List<Eleccion> elecciones = eleccionRepository.findByEstado("ACTIVA");

        model.addAttribute("elecciones", elecciones);
        model.addAttribute("votanteLogueado", votante);
        return "votante/elecciones";
    }

    // ── Candidatos por elección ────────────────────────────────
    @GetMapping("/candidatos/{idEleccion}")
    public String mostrarCandidatos(@PathVariable long idEleccion,
            HttpSession session, Model model) {

        if (session.getAttribute("votanteLogueado") == null)
            return "redirect:/votante/login";

        Votante votante = (Votante) session.getAttribute("votanteLogueado");
        Eleccion eleccion = eleccionRepository.findById(idEleccion).orElse(null);

        if (eleccion == null) {
            model.addAttribute("error", "La elección no existe.");
            return "redirect:/voto/elecciones";
        }

        // Verifica si ya votó en esta elección
        if (votoService.yaVoto(votante, eleccion)) {
            model.addAttribute("elecciones", eleccionRepository.findByEstado("ACTIVA"));
            model.addAttribute("votanteLogueado", votante);
            model.addAttribute("errorVoto", "Ya emitiste tu voto en el proceso: "
                    + eleccion.getNombre() + ". No puedes votar dos veces.");
            return "votante/elecciones";
        }

        List<Candidato> candidatos = candidatoRepository.findAll()
                .stream()
                .filter(c -> c.getEleccion().getIdEleccion().equals(idEleccion))
                .collect(java.util.stream.Collectors.toList());

        model.addAttribute("eleccion", eleccion);
        model.addAttribute("candidatos", candidatos);
        return "votante/candidatos";
    }

    // ── Procesar voto ──────────────────────────────────────────
    @PostMapping("/emitir")
    public String emitirVoto(@RequestParam long idCandidato,
            @RequestParam long idEleccion,
            HttpSession session, Model model) {

        Votante votante = (Votante) session.getAttribute("votanteLogueado");
        if (votante == null)
            return "redirect:/votante/login";

        Eleccion eleccion = eleccionRepository.findById(idEleccion).orElse(null);

        if (eleccion == null) {
            model.addAttribute("error", "La elección no existe.");
            return "redirect:/voto/elecciones";
        }

        if (votoService.yaVoto(votante, eleccion)) {
            List<Candidato> candidatos = candidatoRepository.findAll()
                    .stream()
                    .filter(c -> c.getEleccion().getIdEleccion().equals(idEleccion))
                    .collect(java.util.stream.Collectors.toList());

            model.addAttribute("error", "Ya emitiste tu voto en esta elección");
            model.addAttribute("eleccion", eleccion);
            model.addAttribute("candidatos", candidatos);
            return "votante/candidatos";
        }

        // Si es voto en blanco (id = 0), candidato queda null
        Candidato candidato = null;
        if (idCandidato != 0) {
            candidato = candidatoRepository.findById(idCandidato).orElse(null);
        }

        Voto voto = votoService.guardarVoto(votante, candidato, eleccion);

        String codigoVerificacion = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        Comprobante comprobante = new Comprobante(voto, codigoVerificacion,
                LocalDateTime.now(), "EMITIDO");
        comprobanteRepository.save(comprobante);

        session.setAttribute("ultimoComprobante", comprobante);
        return "redirect:/voto/comprobante";
    }

    // ── Ver comprobante ────────────────────────────────────────
    @GetMapping("/comprobante")
    public String verComprobante(HttpSession session, Model model) {
        Comprobante comprobante = (Comprobante) session.getAttribute("ultimoComprobante");
        if (comprobante == null)
            return "redirect:/votante/login";

        model.addAttribute("comprobante", comprobante);
        return "votante/comprobante";
    }
}