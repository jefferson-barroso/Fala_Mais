package com.example.fala.controller;

import com.example.fala.model.Feedback;
import com.example.fala.repository.FeedbackRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Cadastrar um novo feedback", description = "Criar um novo feedback no sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Feedback cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description =  "Dados inválidos!")
    })
    @PostMapping("/cadastrarFeedback")
    public ResponseEntity<Feedback> cadastrarFeedback(@RequestBody Feedback novoFeedback) {
        Feedback feedbackSalvo = feedbackRepository.save(novoFeedback);
        return ResponseEntity.status(HttpStatus.CREATED).body(feedbackSalvo);
    }

    @Operation(summary = "Listar todos os feedbacjs", description = "Retorna todos os feedbacks cadastrados.")
    @GetMapping("/listarTodosFeedbacks")
    public List<Feedback> listarTodosFeedbacks() {
        return feedbackRepository.findAll();
    }

    @Operation(summary = "Buscar feedback por id", description = "Busca os dados de um feedback por Id")
    @GetMapping("/buscarFeedback/{id}")
    public ResponseEntity<Feedback> buscarFeedbackPorId(@PathVariable Long id) {
        return feedbackRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Atualizar feedback por id", description = "Atualiza os dados de um feedback por Id")
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

    @Operation(summary = "Excluir feedback por id", description = "Exclui os dados de um feedback por Id")
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
