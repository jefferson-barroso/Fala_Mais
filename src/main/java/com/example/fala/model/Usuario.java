package com.example.fala.model;

import com.example.fala.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Schema(
        description = "Entidade que representa um usuario",
        example = "{ \"nome\": \"Fulano de Tal\", \"email\": \"fulano@email.com\", \"senhaHash\": \"exemplo123\", \"role\": \"USER\" }"
)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long idUsuario;

    @Schema(description = "Nome completo", example = "Fulano de Tal")
    @Column(nullable = false, length = 100)
    private String nome;

    @Schema(description = "Email para login", example = "fulanodetal@email.com")
    @Column(nullable = false, unique = true, length=100)
    private String email;

    @Schema(description = "Senha", example = "12345")
    @Column(nullable = false, length= 255)
    private String senhaHash;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

}
