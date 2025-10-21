package com.example.fala.model;

import com.example.fala.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length=100)
    private String email;

    @Column(nullable = false, length= 255)
    private String senhaHash;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

}
