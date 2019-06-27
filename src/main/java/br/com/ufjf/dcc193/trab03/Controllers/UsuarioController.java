package br.com.ufjf.dcc193.trab03.Controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.ufjf.dcc193.trab03.Models.Usuario;
import br.com.ufjf.dcc193.trab03.Persistence.UsuarioRepository;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository UsuarioRepository;

    @RequestMapping(value = {"/lista-usuarios"}, method = RequestMethod.GET)
    public ModelAndView carregaUsuarios (HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
            if (usuario.getEmail().equals("admin"))
            {
                List<Usuario> usuarios = UsuarioRepository.findAll();
                mv.addObject("usuarios", usuarios);
                mv.setViewName("lista-usuarios");
            }
            else
            {
                mv.setViewName("redirect:principal-usuario");
            }
        }
        else
        {
            Usuario usuario = new Usuario();
            mv.addObject("usuario", usuario);
            mv.setViewName("login");
        }
        return mv;
    }

    @RequestMapping(value = {"/cadastro-usuario"}, method = RequestMethod.GET)
    public ModelAndView cadastroUsuario (HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
            if (usuario.getEmail().equals("admin"))
            {
                Usuario usuarioCadastro = new Usuario();
                mv.addObject("usuario", usuarioCadastro);
                mv.setViewName("cadastro-usuario");
            }
            else
            {
                mv.setViewName("redirect:principal-usuario");
            }
        }
        else
        {
            Usuario usuario = new Usuario();
            mv.addObject("usuario", usuario);
            mv.setViewName("login");
        }
        return mv;
    }

    @RequestMapping(value = {"/cadastro-usuario"}, method = RequestMethod.POST)
    public ModelAndView realizaCadastro (@Valid Usuario usuario, HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Usuario usuario2 = (Usuario) session.getAttribute("usuarioLogado");
            if (usuario2.getEmail().equals("admin"))
            {
                Usuario testaEmail = UsuarioRepository.findByEmail(usuario.getEmail());
                if (usuario != null && testaEmail == null)
                {
                    UsuarioRepository.save(usuario);
                    mv.setViewName("redirect:lista-usuarios");
                }               else
                {
                    mv.addObject("usuario", usuario);
                    mv.setViewName("cadastro-usuario");
                }
            }
            else
            {
                mv.setViewName("redirect:principal-usuario");
            }
        }
        else
        {
            mv.setViewName("redirect:login");
        }            
        return mv;
    }    
    
    @RequestMapping(value = {"/editar-usuario/{id}"}, method = RequestMethod.GET)
    public ModelAndView carregaEditar (@PathVariable(value = "id", required = true) Long id, HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
            if (usuario.getEmail().equals("admin"))
            {
                Usuario usuarioASerEditado = new Usuario();
                usuarioASerEditado = UsuarioRepository.getOne(id);
                mv.addObject("usuario", usuarioASerEditado);
                mv.addObject("id", id);
                mv.setViewName("editar-usuario");
            }
            else
            {
                mv.setViewName("redirect:principal-usuario");
            }
        }
        else
        {
            mv.setViewName("redirect:login");
        }
        return mv;
    }

    @RequestMapping(value = {"/editar-usuario"}, method = RequestMethod.POST)
    public ModelAndView realizaEditar (@RequestParam(value = "id", required = true) Long id, Usuario usuario, HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Usuario usuario2 = (Usuario) session.getAttribute("usuarioLogado");
            if (usuario2.getEmail().equals("admin"))
            {
                if (usuario != null)
                {
                    usuario.setId(id);
                    UsuarioRepository.save(usuario);
                    mv.setViewName("redirect:lista-usuarios");
                }
                else
                {
                    mv.addObject("usuario", usuario);
                    mv.setViewName("editar-usuario");
                }
            }
            else
            {
                mv.setViewName("redirect:principal-usuario");
            }
        }
        else
        {
            mv.setViewName("redirect:login");
        }            
        return mv;
    }     

    @RequestMapping(value = { "/excluir-usuario/{id}" }, method = RequestMethod.GET)
    public ModelAndView carregaExcluir(@PathVariable(value = "id", required = true) Long id, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
            if (usuario.getEmail().equals("admin"))
            {
                UsuarioRepository.deleteById(id);
                mv.setViewName("redirect:/lista-usuarios");
            }
            else
            {
                mv.setViewName("redirect:principal-usuarios");
            }
        }
        else
        {
            mv.setViewName("redirect:login");
        }
        return mv;    
    }
    
}