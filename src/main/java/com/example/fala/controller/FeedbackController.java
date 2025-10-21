package com.example.fala.controller;

import com.example.fala.model.Feedback;
import com.example.fala.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    @Autowired
    private  FeedbackRepository feedbackRepository;

    @PostMapping("/cadastrarFeedback")
    public ResponseEntity<Feedback> cadastrarFeedback(@RequestBody Feedback novoFeedback) {
        Feedback feedbackSalvo = feedbackRepository.save(novoFeedback);
        return ResponseEntity.status(HttpStatus.CREATED).body(feedbackSalvo);
    }

    @GetMapping("/listarTodosFeedbacks")
    public List<Feedback> listarTodos() {
        return feedbackRepository.findAll();
    }

    @GetMapping("/buscarFeedback/{id}")
    public ResponseEntity<Feedback> buscarPorId(@PathVariable Long id) {
        return feedbackRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/atualizarFeedback/{id}")
    public ResponseEntity<Feedback> atualizarFeedback(
            @PathVariable Long id,
            @RequestBody Feedback feedbackAtualizado) {

        return feedbackRepository.findById(id)
                .map(feedbackExistente -> {
                    BeanUtils.copyProperties(feedbackAtualizado, feedbackExistente, "idFeedback", "usuario", "dataEnvio");
                    Feedback salvo = feedbackRepository.save(feedbackExistente);
                    return ResponseEntity.ok(salvo);
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/deletarFeedback/{id}")
    public ResponseEntity<Void> excluirFeedback(@PathVariable Long id) {
        if (feedbackRepository.existsById(id)) {
            feedbackRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
