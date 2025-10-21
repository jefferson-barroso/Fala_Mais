package com.example.fala.model;

import com.example.fala.enums.Categoria;
import com.example.fala.enums.Status;
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
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFeedback;

    @Column(nullable = false, length=100)
    private String titulo;

    @Column(nullable = false, columnDefinition ="TEXT")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Categoria categoria;

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
