package com.estacio.ads2024.college.classes.control.services;

import com.estacio.ads2024.college.classes.control.entities.Docente;
import com.estacio.ads2024.college.classes.control.repositories.DocenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DocenteService {
    @Autowired
    private DocenteRepository docenteRepository;

    /**
     * Cria uma nova disciplina no banco de dados.
     * 
     * @param docente a disciplina a ser criada
     * @return a disciplina criada
     */
    public ResponseEntity<Docente> adicionarDocente(Docente docente) {
        try {
            Docente novoDocente = docenteRepository.save(docente);
            return ResponseEntity.ok(novoDocente);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Busca uma disciplina pelo ID.
     * 
     * @param id o ID da disciplina
     * @return a disciplina encontrada ou null se não encontrada
     */
    public ResponseEntity<Docente> buscarDocente(Long id) {
        return docenteRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    /**
     * Remove uma disciplina do banco de dados pelo ID.
     * 
     * @param id o ID da disciplina a ser removida
     */
    public ResponseEntity<String> excluirDocente(Long id) {
        try {
            if (!docenteRepository.existsById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Docente não encontrada.");
            }
            docenteRepository.deleteById(id);
            return ResponseEntity.ok("Docente excluída com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao excluir a docente.");
        }
    }

    /**
     * Retorna todas as disciplinas.
     * 
     * @return a lista de disciplinas encontradas
     */
    public ResponseEntity<List<Docente>> listarDocentes() {
        try {
            List<Docente> docentes = docenteRepository.findAll();
            return ResponseEntity.ok(docentes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Atualiza o nome de uma disciplina.
     * 
     * @param disciplina a disciplina a ser atualizada
     * @return a disciplina atualizada
     */
    public ResponseEntity<Docente> atualizarDocente(Long id, Docente docente) {
        try {
            return docenteRepository.findById(id)
                    .map(docenteEncontrada -> {
                        docenteEncontrada.setNome(docente.getNome());
                        return ResponseEntity.ok(docenteRepository.save(docenteEncontrada));
                    })
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}