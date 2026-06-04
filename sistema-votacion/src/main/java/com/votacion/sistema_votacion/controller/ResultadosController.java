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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/resultados")
public class ResultadosController {

    @Autowired
    private EleccionRepository eleccionRepository;
    @Autowired
    private CandidatoRepository candidatoRepository;
    @Autowired
    private VotoRepository votoRepository;

    // Panel de resultados
    @GetMapping
    public String verResultados(Model model) {
        List<Eleccion> elecciones = eleccionRepository.findAll();
        model.addAttribute("elecciones", elecciones);
        return "resultados";
    }

    // Resultados por elección
    @GetMapping("/{idEleccion}")
    public String resultadosPorEleccion(@PathVariable Long idEleccion, Model model) {
        Eleccion eleccion = eleccionRepository.findById(idEleccion).orElse(null);
        List<Candidato> candidatos = candidatoRepository.findAll();

        // Contar votos por candidato
        Map<Candidato, Long> conteo = new LinkedHashMap<>();
        for (Candidato c : candidatos) {
            long votos = votoRepository.findAll().stream()
                    .filter(v -> v.getCandidato().getIdCandidato().equals(c.getIdCandidato())
                            && v.getEleccion().getIdEleccion().equals(idEleccion))
                    .count();
            conteo.put(c, votos);
        }

        model.addAttribute("eleccion", eleccion);
        model.addAttribute("conteo", conteo);
        model.addAttribute("totalVotos", votoRepository.findAll().stream()
                .filter(v -> v.getEleccion().getIdEleccion().equals(idEleccion))
                .count());
        return "resultados-detalle";
    }
}