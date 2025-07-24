package com.alura.forum.controller;

import com.alura.forum.model.Topico;
import com.alura.forum.service.TopicoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/topicos")
public class TopicoController {

    private final TopicoService service;

    public TopicoController(TopicoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Topico> listar() {
        return service.listar();
    }

    @PostMapping
    public ResponseEntity<Topico> criar(@RequestParam String titulo,
                                        @RequestParam String mensagem,
                                        @RequestParam String curso,
                                        Principal principal) {
        var topico = service.criar(titulo, mensagem, principal.getName(), curso);
        return ResponseEntity.ok(topico);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Topico> atualizar(@PathVariable Long id,
                                            @RequestParam String titulo,
                                            @RequestParam String mensagem,
                                            Principal principal) {
        var topicoAtualizado = service.atualizar(id, titulo, mensagem, principal.getName());
        return ResponseEntity.ok(topicoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id, Principal principal) {
        service.deletar(id, principal.getName());
        return ResponseEntity.noContent().build();
    }
}
