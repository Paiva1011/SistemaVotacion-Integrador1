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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

@Controller
@RequestMapping("/candidatos")
public class CandidatoController {

    private static final Logger log = LoggerFactory.getLogger(CandidatoController.class);

    @Autowired
    private CandidatoRepository candidatoRepository;
    @Autowired
    private EleccionRepository eleccionRepository;
    @Autowired
    private PartidoPoliticoRepository partidoRepository;

    @GetMapping
    public String listarCandidatos(Model model) {
        log.info("Listando candidatos públicamente");
        List<Candidato> candidatos = candidatoRepository.findAllByOrderByApellidosAsc();
        model.addAttribute("candidatos", candidatos);
        return "candidatos";
    }

    @GetMapping("/admin")
    public String listarAdmin(HttpSession session, Model model) {
        if (session.getAttribute("adminLogueado") == null) {
            log.warn("Intento de acceso a candidatos admin sin sesión");
            return "redirect:/admin/login";
        }
        log.info("Administrador accedió a gestión de candidatos");
        model.addAttribute("candidatos", candidatoRepository.findAll());
        model.addAttribute("elecciones", eleccionRepository.findAll());
        model.addAttribute("partidos", partidoRepository.findAll());
        return "admin/candidatos";
    }

    @PostMapping("/guardar")
    public String guardar(@RequestParam String nombres,
            @RequestParam String apellidos,
            @RequestParam Long idPartido,
            @RequestParam Long idEleccion,
            @RequestParam(required = false) MultipartFile propuestasPdf,
            HttpSession session) throws IOException {

        if (session.getAttribute("adminLogueado") == null) {
            log.warn("Intento de guardar candidato sin sesión admin");
            return "redirect:/admin/login";
        }

        PartidoPolitico partido = partidoRepository.findById(idPartido).orElse(null);
        Eleccion eleccion = eleccionRepository.findById(idEleccion).orElse(null);

        String nombreArchivo = null;
        if (propuestasPdf != null && !propuestasPdf.isEmpty()) {
            nombreArchivo = System.currentTimeMillis() + "_" + propuestasPdf.getOriginalFilename();
            Path destino = Paths.get("uploads/" + nombreArchivo);
            Files.createDirectories(destino.getParent());
            Files.copy(propuestasPdf.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);
            log.info("PDF de propuestas guardado: {}", nombreArchivo);
        }

        Candidato candidato = new Candidato(nombres, apellidos, nombreArchivo, partido, eleccion);
        candidatoRepository.save(candidato);
        log.info("Candidato guardado: {} {}", nombres, apellidos);
        return "redirect:/candidatos/admin";
    }

    @GetMapping("/archivos/{filename}")
    @ResponseBody
    public ResponseEntity<Resource> verArchivo(@PathVariable String filename) throws Exception {
        log.info("Solicitud de archivo PDF: {}", filename);
        Path path = Paths.get("uploads/" + filename);
        Resource resource = new UrlResource(path.toUri());
        if (!resource.exists()) {
            log.warn("Archivo no encontrado: {}", filename);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .header("Content-Type", "application/pdf")
                .body(resource);
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("adminLogueado") == null) {
            log.warn("Intento de eliminar candidato sin sesión admin");
            return "redirect:/admin/login";
        }
        candidatoRepository.deleteById(id);
        log.info("Candidato eliminado con ID: {}", id);
        return "redirect:/candidatos/admin";
    }
}