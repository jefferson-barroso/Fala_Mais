package com.example.fala.controller;

import com.example.fala.dto.FeedbackRegistroDTO;
import com.example.fala.dto.FeedbackResponseDTO;
import com.example.fala.dto.UsuarioResponseDTO;
import com.example.fala.enums.Status;
import com.example.fala.model.Feedback;
import com.example.fala.model.Usuario;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    @Autowired
    private  FeedbackRepository feedbackRepository;

    private UsuarioResponseDTO toUsuarioResponseDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setRole(usuario.getRole());
        return dto;
    }

    private FeedbackResponseDTO toFeedbackResponseDTO(Feedback feedback){
        FeedbackResponseDTO dto = new FeedbackResponseDTO();
        dto.setIdFeedback(feedback.getIdFeedback());
        dto.setTitulo(feedback.getTitulo());
        dto.setDescricao(feedback.getDescricao());
        dto.setCategoria(feedback.getCategoria());
        dto.setAnonimo(feedback.getAnonimo());
        dto.setDataEnvio(feedback.getDataEnvio());
        dto.setStatus(feedback.getStatus());
        dto.setResposta(feedback.getResposta());
        if (Boolean.FALSE.equals(feedback.getAnonimo()) && feedback.getUsuario() != null) {
            dto.setAutor(toUsuarioResponseDTO(feedback.getUsuario()));
        } else {
            dto.setAutor(null);
        }
        return dto;
    }



    @Operation(summary = "Cadastrar um novo feedback", description = "Criar um novo feedback no sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Feedback cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description =  "Dados inválidos!")
    })
    @PostMapping("/cadastrarFeedback")
    public ResponseEntity<FeedbackResponseDTO> cadastrarFeedback(@RequestBody FeedbackRegistroDTO novoRegistro) { // AJUSTE 4: Recebe DTO e Retorna DTO

        Feedback novoFeedback = new Feedback();

        novoFeedback.setTitulo(novoRegistro.getTitulo());
        novoFeedback.setDescricao(novoRegistro.getDescricao());
        novoFeedback.setCategoria(novoRegistro.getCategoria());
        novoFeedback.setAnonimo(novoRegistro.getAnonimo() != null ? novoRegistro.getAnonimo() : false); // Garante que não seja null
        novoFeedback.setDataEnvio(LocalDateTime.now());
        novoFeedback.setStatus(Status.NOVO);
        Feedback feedbackSalvo = feedbackRepository.save(novoFeedback);
        return ResponseEntity.status(HttpStatus.CREATED).body(toFeedbackResponseDTO(feedbackSalvo));

    }

    @Operation(summary = "Listar todos os feedbacjs", description = "Retorna todos os feedbacks cadastrados.")
    @GetMapping("/listarTodosFeedbacks")
    public List<FeedbackResponseDTO> listarTodosFeedbacks() {
        return feedbackRepository.findAll().stream().map(this::toFeedbackResponseDTO).collect(Collectors.toList());
    }

    @Operation(summary = "Buscar feedback por id", description = "Busca os dados de um feedback por Id")
    @GetMapping("/buscarFeedback/{id}")
    public ResponseEntity<FeedbackResponseDTO> buscarFeedbackPorId(@PathVariable Long id) {
        return feedbackRepository.findById(id)
                .map(this::toFeedbackResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Atualizar feedback por id", description = "Atualiza os dados de um feedback por Id")
    @PutMapping("/atualizarFeedback/{id}")
    public ResponseEntity<FeedbackResponseDTO> atualizarFeedback(
                                                                  @PathVariable Long id,
                                                                  @RequestBody FeedbackRegistroDTO feedbackAtualizadoDTO) { // Recebe DTO

        return feedbackRepository.findById(id)
                .map(feedbackExistente -> {
                    feedbackExistente.setTitulo(feedbackAtualizadoDTO.getTitulo());
                    feedbackExistente.setDescricao(feedbackAtualizadoDTO.getDescricao());
                    feedbackExistente.setCategoria(feedbackAtualizadoDTO.getCategoria());
                    feedbackExistente.setAnonimo(feedbackAtualizadoDTO.getAnonimo());
                    Feedback salvo = feedbackRepository.save(feedbackExistente);
                    return ResponseEntity.ok(toFeedbackResponseDTO(salvo)); // Retorna DTO
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
