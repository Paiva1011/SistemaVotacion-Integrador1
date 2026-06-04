package com.votacion.sistema_votacion.controller;

import com.votacion.sistema_votacion.model.Candidato;
import com.votacion.sistema_votacion.model.Eleccion;
import com.votacion.sistema_votacion.repository.CandidatoRepository;
import com.votacion.sistema_votacion.repository.EleccionRepository;
import com.votacion.sistema_votacion.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/resultados")
public class ResultadosController {

    private static final Logger log = LoggerFactory.getLogger(ResultadosController.class);

    @Autowired
    private EleccionRepository eleccionRepository;
    @Autowired
    private CandidatoRepository candidatoRepository;
    @Autowired
    private VotoRepository votoRepository;

    @GetMapping
    public String verResultados(Model model) {
        log.info("Accediendo al panel de resultados");
        List<Eleccion> elecciones = eleccionRepository.findAll();
        model.addAttribute("elecciones", elecciones);
        return "resultados";
    }

    @GetMapping("/{idEleccion}")
    public String resultadosPorEleccion(@PathVariable Long idEleccion, Model model) {
        log.info("Consultando resultados para elección ID: {}", idEleccion);
        Eleccion eleccion = eleccionRepository.findById(idEleccion).orElse(null);
        List<Candidato> candidatos = candidatoRepository.findAll();

        Map<Candidato, Long> conteo = new LinkedHashMap<>();
        for (Candidato c : candidatos) {
            long votos = votoRepository.findAll().stream()
                    .filter(v -> v.getCandidato() != null &&
                            v.getCandidato().getIdCandidato().equals(c.getIdCandidato())
                            && v.getEleccion().getIdEleccion().equals(idEleccion))
                    .count();
            conteo.put(c, votos);
            log.debug("Candidato {} {}: {} votos", c.getNombres(), c.getApellidos(), votos);
        }

        long totalVotos = votoRepository.findAll().stream()
                .filter(v -> v.getEleccion().getIdEleccion().equals(idEleccion))
                .count();

        log.info("Total de votos en elección {}: {}", idEleccion, totalVotos);
        model.addAttribute("eleccion", eleccion);
        model.addAttribute("conteo", conteo);
        model.addAttribute("totalVotos", totalVotos);
        return "resultados-detalle";
    }
}