package br.com.gerenciamento.controller;

import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.service.ServiceAluno;
import br.com.gerenciamento.exception.EmailExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class AlunoController {

    @Autowired
    private ServiceAluno serviceAluno;

    private static final String PATH_ALUNO = "Aluno/";
    private static final String VIEW_FORM  = PATH_ALUNO + "formAluno";
    private static final String VIEW_LISTA = PATH_ALUNO + "listAlunos";
    private static final String VIEW_EDIT  = PATH_ALUNO + "editar";
    private static final String VIEW_PESQ = PATH_ALUNO + "pesquisa-resultado";
    private static final String VIEW_INDEX = "home/index"; 
    private static final String REDIRECT_LISTA = "redirect:/todos-alunos";

    @GetMapping("/dashboard")
    public ModelAndView dashboard(HttpSession session) {
        if (session.getAttribute("usuarioLogado") == null) {
            return new ModelAndView("redirect:/");
        }

        ModelAndView mv = new ModelAndView(VIEW_INDEX);
        
        mv.addObject("aluno", new Aluno()); 
        mv.addObject("mediaAtivos", serviceAluno.calcularMediaAtivos());
        mv.addObject("quantidadeAtivos", serviceAluno.buscarAtivos().size());
        return mv;
    }

    @GetMapping("/notas-enade")
    public ModelAndView notasEnade() {
        ModelAndView mv = new ModelAndView(PATH_ALUNO + "notas-enade");
        List<Aluno> ativos = serviceAluno.buscarAtivos();
        
        mv.addObject("alunosAtivos", ativos); 
        mv.addObject("mediaAtivos", serviceAluno.calcularMediaAtivos());
        mv.addObject("quantidadeAtivos", ativos.size());
        return mv;
    }

    @GetMapping("/inserir-alunos")
    public ModelAndView formCadastro() {
        return new ModelAndView(VIEW_FORM).addObject("aluno", new Aluno());
    }

    @PostMapping("/salvar-aluno")
    public ModelAndView salvar(@Valid Aluno aluno, BindingResult br, RedirectAttributes attr) {
        if(br.hasErrors()) {
            return new ModelAndView(VIEW_FORM).addObject("aluno", aluno);
        }

        try {
            serviceAluno.salvar(aluno);
            attr.addFlashAttribute("message_success", "Aluno salvo com sucesso!");
            return new ModelAndView(REDIRECT_LISTA);

        } catch (EmailExistsException e) { 
            ModelAndView mv = new ModelAndView(VIEW_FORM);
            mv.addObject("aluno", aluno);
            mv.addObject("message_error", e.getMessage()); 
            return mv;
        }
    } 

    @GetMapping("/editar-aluno/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        return new ModelAndView(VIEW_EDIT).addObject("aluno", serviceAluno.buscarPorId(id));
    }

    @PostMapping("/editar-aluno")
    public ModelAndView atualizar(@Valid Aluno aluno, BindingResult br, RedirectAttributes attr) {
        if(br.hasErrors()) {
            ModelAndView mv = new ModelAndView(VIEW_EDIT);
            mv.addObject("aluno", aluno);
            mv.addObject("message_error", "Verifique os campos preenchidos.");
            return mv;
        }

        try {
            serviceAluno.salvar(aluno);
            attr.addFlashAttribute("message_success", "Dados do aluno atualizados com sucesso!");
            return new ModelAndView(REDIRECT_LISTA);

        } catch (EmailExistsException e) {
            ModelAndView mv = new ModelAndView(VIEW_EDIT);
            mv.addObject("aluno", aluno);
            mv.addObject("message_error", "Erro ao atualizar: " + e.getMessage());
            return mv;
        }
    }

    @GetMapping("/remover-aluno/{id}")
    public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
        serviceAluno.excluir(id);
        attr.addFlashAttribute("message_success", "Aluno removido com sucesso.");
        return REDIRECT_LISTA;
    }

    @GetMapping("/todos-alunos")
    public ModelAndView listar() {
        return new ModelAndView(VIEW_LISTA).addObject("alunosList", serviceAluno.listarTodos());
    }

    @GetMapping("/filtros-alunos")
    public ModelAndView filtrosAlunos() {
        return new ModelAndView(PATH_ALUNO + "filtroAlunos");
    }

    @GetMapping("/alunos-ativos")
    public ModelAndView listarAtivos() {
        return new ModelAndView(PATH_ALUNO + "alunos-ativos")
                .addObject("alunosAtivos", serviceAluno.buscarAtivos());
    }

    @GetMapping("/alunos-inativos")
    public ModelAndView listarInativos() {
        return new ModelAndView(PATH_ALUNO + "alunos-inativos")
                .addObject("alunosInativos", serviceAluno.buscarInativos());
    }

    @PostMapping("/pesquisar-aluno")
    public ModelAndView pesquisar(@RequestParam(required = false) String nome) {
        List<Aluno> lista;
        if (nome == null || nome.trim().isEmpty()) {
            lista = serviceAluno.listarTodos();
        } else {
            lista = serviceAluno.pesquisarPorNome(nome);
        }

        return new ModelAndView(VIEW_PESQ).addObject("alunos", lista);
    }
}