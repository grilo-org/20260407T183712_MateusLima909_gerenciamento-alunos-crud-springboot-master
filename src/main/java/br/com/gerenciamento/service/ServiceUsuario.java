package br.com.gerenciamento.service;

import br.com.gerenciamento.exception.CriptoExistsException;
import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.repository.UsuarioRepository;
import br.com.gerenciamento.exception.ServiceExc;
import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.NoSuchAlgorithmException;

@Service
public class ServiceUsuario {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void salvarUsuario(Usuario user) throws Exception {
        validarEmailExistente(user.getEmail());
        user.setSenha(gerarHash(user.getSenha()));
        usuarioRepository.save(user);
    }

    public Usuario loginUser(String login, String senhaPura) throws ServiceExc {
        try {
            String senhaCriptografada = gerarHash(senhaPura);
            return usuarioRepository.buscarLogin(login, senhaCriptografada);
        } catch (Exception e) {
            throw new ServiceExc("Erro ao processar a autenticação.");
        }
    }

    private void validarEmailExistente(String email) throws EmailExistsException {
        if (usuarioRepository.findByEmail(email) != null) {
            throw new EmailExistsException("Este email já está cadastrado: " + email);
        }
    }

    private String gerarHash(String senha) throws CriptoExistsException {
        try {
            return Util.md5(senha);
        } catch (NoSuchAlgorithmException e) {
            throw new CriptoExistsException("Falha crítica no motor de criptografia.");
        }
    }
}
