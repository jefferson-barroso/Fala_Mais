package com.example.fala.controller;

import com.example.fala.model.Usuario;
import com.example.fala.repository.UsuarioRepository;
import com.example.fala.dto.UsuarioRegistroDTO;
import com.example.fala.dto.UsuarioResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    @Autowired
    private  UsuarioRepository usuarioRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;

    private UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setRole(usuario.getRole());
        return dto;
    }

    @Operation(summary = "Cadastrar um novo usuário", description = "Cria um novo usuário no sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping("/cadastrarUsuario")
    public ResponseEntity<UsuarioResponseDTO> cadastrarUsuario(@RequestBody UsuarioRegistroDTO registroDTO) {

        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(registroDTO.getNome());
        novoUsuario.setEmail(registroDTO.getEmail());
        novoUsuario.setRole(registroDTO.getRole());
        novoUsuario.setSenhaHash(passwordEncoder.encode(registroDTO.getSenha()));

        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(toResponseDTO(usuarioSalvo));
    }

    @Operation(summary = "Listar todos os usuários", description = "Retorna todos os usuários cadastrados.")
    @GetMapping("/listarTodosUsuarios")
    public List<UsuarioResponseDTO> listarTodosUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Buscar usuário por ID", description = "Retorna os dados de um usuário específico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/buscarUsuarioPorId/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarUsuarioPorId(@PathVariable Long id) {
        return usuarioRepository.findById(id)
                .map(this::toResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Atualizar um usuário existente", description = "Atualiza os dados de um usuário pelo ID.")
    @PutMapping("/atualizarUsuario/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(@PathVariable Long id, @RequestBody UsuarioRegistroDTO registroDTO) {
        return usuarioRepository.findById(id).map(usuarioExistente -> {
            usuarioExistente.setNome(registroDTO.getNome());
            usuarioExistente.setEmail(registroDTO.getEmail());
            usuarioExistente.setRole(registroDTO.getRole());
            if (registroDTO.getSenha() != null && !registroDTO.getSenha().isEmpty()) {
                usuarioExistente.setSenhaHash(passwordEncoder.encode(registroDTO.getSenha()));
            }

            Usuario salvo = usuarioRepository.save(usuarioExistente);
            return ResponseEntity.ok(toResponseDTO(salvo));
        }).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Excluir um usuário", description = "Remove um usuário do sistema pelo ID.")
    @DeleteMapping("/deletarUsuario/{id}")
    public ResponseEntity<Void> excluirUsuario(@PathVariable Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}