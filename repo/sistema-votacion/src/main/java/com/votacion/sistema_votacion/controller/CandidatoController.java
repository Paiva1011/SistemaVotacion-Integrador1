package com.votacion.sistema_votacion.controller;

import com.votacion.sistema_votacion.model.Candidato;
import com.votacion.sistema_votacion.model.Eleccion;
import com.votacion.sistema_votacion.model.PartidoPolitico;
import com.votacion.sistema_votacion.repository.CandidatoRepository;
import com.votacion.sistema_votacion.repository.EleccionRepository;
import com.votacion.sistema_votacion.repository.PartidoPoliticoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

@Controller
@RequestMapping("/candidatos")
public class CandidatoController {

    @Autowired
    private CandidatoRepository candidatoRepository;

    @Autowired
    private EleccionRepository eleccionRepository;

    @Autowired
    private PartidoPoliticoRepository partidoRepository;

    // ── Vista pública de candidatos
    @GetMapping
    public String listarCandidatos(Model model) {
        List<Candidato> candidatos = candidatoRepository.findAllByOrderByApellidosAsc();
        model.addAttribute("candidatos", candidatos);
        return "candidatos";
    }

    // ── Vista admin de candidatos
    @GetMapping("/admin")
    public String listarAdmin(HttpSession session, Model model) {
        if (session.getAttribute("adminLogueado") == null)
            return "redirect:/admin/login";

        model.addAttribute("candidatos", candidatoRepository.findAll());
        model.addAttribute("elecciones", eleccionRepository.findAll());
        model.addAttribute("partidos", partidoRepository.findAll());
        return "admin/candidatos";
    }

    // ── Guardar nuevo candidato
    @PostMapping("/guardar")
    public String guardar(@RequestParam String nombres,
            @RequestParam String apellidos,
            @RequestParam long idPartido,
            @RequestParam long idEleccion,
            @RequestParam(required = false) MultipartFile propuestasPdf,
            HttpSession session) throws IOException {

        if (session.getAttribute("adminLogueado") == null)
            return "redirect:/admin/login";

        PartidoPolitico partido = partidoRepository.findById(idPartido).orElse(null);
        Eleccion eleccion = eleccionRepository.findById(idEleccion).orElse(null);

        // Guardar el PDF en la carpeta uploads/
        String nombreArchivo = null;
        if (propuestasPdf != null && !propuestasPdf.isEmpty()) {
            nombreArchivo = System.currentTimeMillis() + "_" + propuestasPdf.getOriginalFilename();
            Path destino = Paths.get("uploads/" + nombreArchivo);
            Files.createDirectories(destino.getParent());
            Files.copy(propuestasPdf.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);
        }

        Candidato candidato = new Candidato(nombres, apellidos, nombreArchivo, partido, eleccion);
        candidatoRepository.save(candidato);
        return "redirect:/candidatos/admin";
    }

    // ── Servir archivos PDF
    @GetMapping("/archivos/{filename}")
    @ResponseBody
    public ResponseEntity<Resource> verArchivo(@PathVariable String filename) throws Exception {
        Path path = Paths.get("uploads/" + filename);
        Resource resource = new UrlResource(path.toUri());

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header("Content-Type", "application/pdf")
                .body(resource);
    }

    // Eliminar candidato
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable long id, HttpSession session) {
        if (session.getAttribute("adminLogueado") == null)
            return "redirect:/admin/login";

        candidatoRepository.deleteById(id);
        return "redirect:/candidatos/admin";
    }
}