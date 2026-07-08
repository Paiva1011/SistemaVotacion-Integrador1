package com.votacion.sistema_votacion.controller;

import com.votacion.sistema_votacion.model.*;
import com.votacion.sistema_votacion.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.util.*;

@Controller
@RequestMapping("/resultados")
public class ResultadosController {

    @Autowired
    private EleccionRepository eleccionRepository;
    @Autowired
    private CandidatoRepository candidatoRepository;
    @Autowired
    private VotoRepository votoRepository;

    // Panel admin — para ver todas las elecciones con sus votos
    @GetMapping("/admin")
    public String resultadosAdmin(HttpSession session, Model model) {
        if (session.getAttribute("adminLogueado") == null)
            return "redirect:/admin/login";

        model.addAttribute("elecciones", eleccionRepository.findAll());
        return "admin/resultados";
    }

    // Resultados de una elección específica (admin)
    @GetMapping("/admin/{idEleccion}")
    public String detalleAdmin(@PathVariable Long idEleccion, HttpSession session, Model model) {
        if (session.getAttribute("adminLogueado") == null)
            return "redirect:/admin/login";

        return cargarResultados(idEleccion, model, "admin/resultados-detalle");
    }

    // Resultados publicos - solo para elecciones publicadas
    @GetMapping
    public String resultadosPublicos(Model model) {
        List<Eleccion> publicadas = eleccionRepository.findByPublicadaTrue();
        model.addAttribute("elecciones", publicadas);
        return "resultados";
    }

    // Detalle publico de una elección
    @GetMapping("/{idEleccion}")
    public String detallePublico(@PathVariable Long idEleccion, Model model) {
        Eleccion eleccion = eleccionRepository.findById(idEleccion).orElse(null);
        if (eleccion == null || !eleccion.isPublicada())
            return "redirect:/resultados";

        return cargarResultados(idEleccion, model, "resultados-detalle");
    }

    // Metodo para cargar datos de los resultados
    private String cargarResultados(Long idEleccion, Model model, String vista) {
        Eleccion eleccion = eleccionRepository.findById(idEleccion).orElse(null);

        List<Candidato> candidatos = candidatoRepository.findAll()
                .stream()
                .filter(c -> c.getEleccion().getIdEleccion().equals(idEleccion))
                .toList();

        long totalVotos = votoRepository.countByEleccion(eleccion);

        // Construye mapa con votos y porcentaje por candidato
        List<Map<String, Object>> datos = new ArrayList<>();
        for (Candidato c : candidatos) {
            long votos = votoRepository.countByCandidatoAndEleccion(c, eleccion);
            double porcentaje = totalVotos > 0 ? (votos * 100.0 / totalVotos) : 0;

            Map<String, Object> fila = new LinkedHashMap<>();
            fila.put("candidato", c);
            fila.put("votos", votos);
            fila.put("porcentaje", String.format("%.1f", porcentaje));
            datos.add(fila);
        }

        // Votos en blanco
        long votosBlanco = votoRepository.findAll().stream()
                .filter(v -> v.getEleccion().getIdEleccion().equals(idEleccion)
                        && v.getCandidato() == null)
                .count();
        if (votosBlanco > 0) {
            double porcentaje = totalVotos > 0 ? (votosBlanco * 100.0 / totalVotos) : 0;
            Map<String, Object> blanco = new LinkedHashMap<>();
            blanco.put("candidato", null);
            blanco.put("votos", votosBlanco);
            blanco.put("porcentaje", String.format("%.1f", porcentaje));
            datos.add(blanco);
        }

        // Ordena los votos de mayor a menor
        datos.sort((a, b) -> Long.compare((Long) b.get("votos"), (Long) a.get("votos")));

        model.addAttribute("eleccion", eleccion);
        model.addAttribute("datos", datos);
        model.addAttribute("totalVotos", totalVotos);
        return vista;
    }
}