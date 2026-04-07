package br.com.gerenciamento.service;

import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ServiceAluno {

    @Autowired
    private AlunoRepository alunoRepository;

    public void salvar(Aluno aluno) throws EmailExistsException { 
        Objects.requireNonNull(aluno, "O objeto aluno não pode ser nulo");
        
        validarEmailUnico(aluno);
        
        alunoRepository.save(aluno);
    }

    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    public Aluno buscarPorId(Long id) {
        Objects.requireNonNull(id, "O ID do aluno é obrigatório para a busca");
        
        return alunoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado com o ID: " + id));
    }

    public void excluir(Long id) {
        Objects.requireNonNull(id, "O ID é obrigatório para exclusão");
        alunoRepository.deleteById(id);
    }

    public List<Aluno> buscarAtivos() {
        return alunoRepository.findByStatusAtivo();
    }

    public List<Aluno> buscarInativos() {
        return alunoRepository.findByStatusInativo();
    }

    public List<Aluno> pesquisarPorNome(String nome) {
        return alunoRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Double calcularMediaAtivos() {
        List<Aluno> ativos = buscarAtivos();
        
        if (ativos.isEmpty()) return 0.0;

        return ativos.stream()
                .mapToDouble(a -> a.getNotaEnade() != null ? a.getNotaEnade() : 0.0)
                .average()
                .orElse(0.0);
    }

    private void validarEmailUnico(Aluno aluno) throws EmailExistsException {
        String email = Objects.requireNonNull(aluno.getEmail(), "O e-mail é obrigatório");
        Aluno alunoExistente = alunoRepository.findByEmail(email);

        if (alunoExistente != null && !alunoExistente.getId().equals(aluno.getId())) {
            throw new EmailExistsException("Já existe um registro com o e-mail: " + email);
        }
    }
}