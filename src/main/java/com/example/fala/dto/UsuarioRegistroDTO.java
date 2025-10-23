package com.example.fala.dto;

import com.example.fala.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRegistroDTO {
    private String nome;
    private String email;
    private String senha;
    private Role role = Role.USER;
}
