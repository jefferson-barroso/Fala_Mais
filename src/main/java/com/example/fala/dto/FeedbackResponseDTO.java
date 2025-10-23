package com.example.fala.dto;

import com.example.fala.enums.Categoria;
import com.example.fala.enums.Status;
import com.example.fala.model.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FeedbackResponseDTO {
    private Long idFeedback;
    private String titulo;
    private String descricao;
    private Categoria categoria;
    private Boolean anonimo;
    private LocalDateTime dataEnvio;
    private Status status;
    private String resposta;
    private UsuarioResponseDTO autor ;

}
