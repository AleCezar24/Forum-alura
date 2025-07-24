package com.alura.forum.service;

import com.alura.forum.model.Curso;
import com.alura.forum.model.Topico;
import com.alura.forum.model.Usuario;
import com.alura.forum.repository.CursoRepository;
import com.alura.forum.repository.TopicoRepository;
import com.alura.forum.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TopicoService {

    private final TopicoRepository topicoRepository;
    private final CursoRepository cursoRepository;
    private final UsuarioRepository usuarioRepository;

    public TopicoService(TopicoRepository topicoRepository,
                         CursoRepository cursoRepository,
                         UsuarioRepository usuarioRepository) {
        this.topicoRepository = topicoRepository;
        this.cursoRepository = cursoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Topico> listar() {
        return topicoRepository.findAll();
    }

    public Optional<Topico> buscarPorId(Long id) {
        return topicoRepository.findById(id);
    }

    public Topico criar(String titulo, String mensagem, String emailUsuario, String nomeCurso) {
        Usuario autor = usuarioRepository.findByEmail(emailUsuario)
                           .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Curso curso = cursoRepository.findByNome(nomeCurso)
                         .orElseThrow(() -> new RuntimeException("Curso não encontrado"));

        Topico topico = new Topico();
        topico.setTitulo(titulo);
        topico.setMensagem(mensagem);
        topico.setAutor(autor);
        topico.setCurso(curso);

        return topicoRepository.save(topico);
    }

    public Topico atualizar(Long id, String novoTitulo, String novaMensagem, String emailUsuario) {
        Topico topico = buscarPorId(id)
                          .orElseThrow(() -> new RuntimeException("Tópico não encontrado"));

        if (!topico.getAutor().getEmail().equals(emailUsuario)) {
            throw new RuntimeException("Somente o autor pode editar o tópico");
        }

        topico.setTitulo(novoTitulo);
        topico.setMensagem(novaMensagem);
        return topicoRepository.save(topico);
    }

    public void deletar(Long id, String emailUsuario) {
        Topico topico = buscarPorId(id)
                          .orElseThrow(() -> new RuntimeException("Tópico não encontrado"));

        if (!topico.getAutor().getEmail().equals(emailUsuario)) {
            throw new RuntimeException("Somente o autor pode deletar o tópico");
        }

        topicoRepository.delete(topico);
    }
}
