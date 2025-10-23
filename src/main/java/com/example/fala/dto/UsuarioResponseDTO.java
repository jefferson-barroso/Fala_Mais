package com.example.fala.dto;

import com.example.fala.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioResponseDTO {
    private Long idUsuario;
    private String nome;
    private String email;
    private Role role;
}
