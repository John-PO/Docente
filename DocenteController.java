package com.estacio.ads2024.college.classes.control.controllers;

import com.estacio.ads2024.college.classes.control.entities.Docente;
import com.estacio.ads2024.college.classes.control.services.DocenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/docentes")
public class DocenteController {
    @Autowired
    private DocenteService docenteService;

    // Métodos para mapear requisiçes HTTP aqui
    /**
     * Lista todas as disciplinas cadastradas.
     */
    @GetMapping
    public String listarTodas(Model model) {
        ResponseEntity<List<Docente>> response = docenteService.listarDocentes();

        if (!response.getStatusCode().is2xxSuccessful()) {
            model.addAttribute("error", response.getBody());
        }
        model.addAttribute("docentes", response.getBody());
        return "docentes";
    }

    /**
     * Adiciona uma nova disciplina ao sistema.
     * 
     * @param docente objeto Disciplina que será adicionado
     */
    @PostMapping
    public String adicionarDocente(@ModelAttribute Docente docente, RedirectAttributes redirectAttrs) {
        ResponseEntity<Docente> response = docenteService.adicionarDocente(docente);
        if (!response.getStatusCode().is2xxSuccessful()) {
            redirectAttrs.addFlashAttribute("error", response.getBody());
        }
        return "redirect:/docentes";
    }

    /**
     * Exclui uma disciplina pelo ID.
     * 
     * @param id o ID da disciplina que será excluída
     */
    @DeleteMapping("/{id}")
    public String excluirDocente(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        ResponseEntity<String> response = docenteService.excluirDocente(id);
        if (!response.getStatusCode().is2xxSuccessful()) {
            redirectAttrs.addFlashAttribute("error", response.getBody());
        }
        return "redirect:/docentes";
    }

    /**
     * Atualiza uma disciplina existente com base no ID fornecido.
     * 
     * @param id                   o ID da disciplina a ser atualizada
     * @param disciplinaAtualizada objeto Disciplina com os novos dados
     * @return a disciplina atualizada ou null se não encontrada
     */
    @PutMapping("/{id}")
    public String atualizarDocente(@PathVariable Long id, @ModelAttribute Docente docente,
            RedirectAttributes redirectAttrs) {
        ResponseEntity<Docente> response = docenteService.atualizarDocente(id, docente);
        if (!response.getStatusCode().is2xxSuccessful()) {
            redirectAttrs.addFlashAttribute("error", "Falha ao atualizar a docente.");
        } else {
            redirectAttrs.addFlashAttribute("success", "Docente atualizada com sucesso.");
        }
        return "redirect:/docentes";
    }
}