package com.example.fala.model;

import com.example.fala.enums.Categoria;
import com.example.fala.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "feedback")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Schema(
        description = "Entidade que representa um feedback enviado por um usuário",
        example = "{ \"titulo\": \"Melhorar a iluminação\", \"descricao\": \"Lâmpadas fracas\", \"categoria\": \"ELOGIO\", \"anonimo\": false, \"usuario\": { \"idUsuario\": 1 } }"
)
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFeedback;

    @Schema(description = "Título do feedback", example = "Melhorar a iluminação da sala de reunião")
    @Column(nullable = false, length=100)
    private String titulo;

    @Schema(description = "Descrição detalhada do feedback", example = "A iluminação é muito fraca, dificultando o trabalho.")
    @Column(nullable = false, columnDefinition ="TEXT")
    private String descricao;

    @Schema(description = "Categoria do feedback", example = "ELOGIO")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Categoria categoria;

    @Schema(description = "Indica se o feedback é anônimo", example = "false")
    @Column(nullable = false)
    private Boolean anonimo = false;

    @Column(nullable = false)
    private LocalDateTime dataEnvio = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.NOVO;

    @Column(columnDefinition = "TEXT")
    private String resposta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = true)
    private Usuario usuario;
}
