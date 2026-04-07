package br.com.gerenciamento.controller;

import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.service.ServiceUsuario;
import br.com.gerenciamento.service.ServiceAluno; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class UsuarioController {

    @Autowired
    private ServiceUsuario serviceUsuario;

    @Autowired
    private ServiceAluno serviceAluno;

    private static final String VIEW_LOGIN    = "login/login";
    private static final String VIEW_CADASTRO = "login/cadastro";
    private static final String VIEW_INDEX    = "home/index";
    
    private static final String REDIRECT_INDEX = "redirect:/index";
    private static final String REDIRECT_HOME  = "redirect:/";

    @GetMapping("/")
    public ModelAndView login() {
        return new ModelAndView(VIEW_LOGIN).addObject("usuario", new Usuario());
    }

    @GetMapping("/cadastro")
    public ModelAndView cadastrar() {
        return new ModelAndView(VIEW_CADASTRO).addObject("usuario", new Usuario());
    }

    @GetMapping("/index") 
    public ModelAndView index(HttpSession session) {
        if (session.getAttribute("usuarioLogado") == null) {
            return new ModelAndView(REDIRECT_HOME);
        }

        ModelAndView mv = new ModelAndView(VIEW_INDEX);

        mv.addObject("aluno", new Aluno());
        mv.addObject("mediaAtivos", serviceAluno.calcularMediaAtivos());
        mv.addObject("quantidadeAtivos", serviceAluno.buscarAtivos().size());
        
        return mv;
    }

    @PostMapping("/salvarUsuario")
    public ModelAndView salvar(@Valid Usuario usuario, BindingResult br, RedirectAttributes attr) {
        if (br.hasErrors()) return retornarParaCadastro(usuario);

        try {
            serviceUsuario.salvarUsuario(usuario);
            // Alterado para message_success
            attr.addFlashAttribute("message_success", "Cadastro realizado com sucesso!");
            return new ModelAndView(REDIRECT_HOME);
        } catch (Exception e) {
            // Alterado para message_error
            return retornarParaCadastro(usuario).addObject("message_error", e.getMessage());
        }
    }

    @PostMapping("/login")
    public ModelAndView realizarLogin(Usuario usuario, HttpSession session) {
        try {
            Usuario userLogin = serviceUsuario.loginUser(usuario.getUser(), usuario.getSenha());
            
            if (userLogin == null) {
                // Alterado para message_error
                return new ModelAndView(VIEW_LOGIN).addObject("message_error", "Usuário ou senha inválidos.");
            }

            session.setAttribute("usuarioLogado", userLogin);
            return new ModelAndView(REDIRECT_INDEX);
        } catch (Exception e) {
            // Alterado para message_error
            return new ModelAndView(VIEW_LOGIN).addObject("message_error", "Erro ao processar login.");
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return REDIRECT_HOME;
    }

    private ModelAndView retornarParaCadastro(Usuario usuario) {
        return new ModelAndView(VIEW_CADASTRO).addObject("usuario", usuario);
    }
}