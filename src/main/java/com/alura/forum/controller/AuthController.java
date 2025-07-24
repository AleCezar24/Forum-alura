package com.alura.forum.controller;

import com.alura.forum.model.Usuario;
import com.alura.forum.repository.UsuarioRepository;
import com.alura.forum.service.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authManager,
                          JwtUtil jwtUtil,
                          UsuarioRepository usuarioRepository,
                          BCryptPasswordEncoder passwordEncoder) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email,
                                        @RequestParam String senha) {
        var authToken = new UsernamePasswordAuthenticationToken(email, senha);
        authManager.authenticate(authToken);

        var usuario = usuarioRepository.findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        var token = jwtUtil.gerarToken(usuario);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<String> cadastrar(@RequestParam String nome,
                                            @RequestParam String email,
                                            @RequestParam String senha) {
        if (usuarioRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.badRequest().body("Email já cadastrado");
        }

        var usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(passwordEncoder.encode(senha));
        usuarioRepository.save(usuario);

        var token = jwtUtil.gerarToken(usuario);
        return ResponseEntity.ok(token);
    }
}
