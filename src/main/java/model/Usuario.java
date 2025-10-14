package model;

import enums.Categoria;
import enums.Role;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "usuarios")
@Data
@EqualsAndHashCode(of="idUsuario")
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
