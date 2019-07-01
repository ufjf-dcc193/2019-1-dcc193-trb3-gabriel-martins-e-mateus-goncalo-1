package br.com.ufjf.dcc193.trab03.Controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.ufjf.dcc193.trab03.Models.Etiqueta;
import br.com.ufjf.dcc193.trab03.Models.Usuario;
import br.com.ufjf.dcc193.trab03.Persistence.EtiquetaRepository;

@Controller
public class EtiquetaController {

    @Autowired
    private EtiquetaRepository repositoryEtiquetas;

    @RequestMapping(value = {"/lista-etiquetas"}, method = RequestMethod.GET)
    public ModelAndView carregaEtiquetas (HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
            if (usuario.getEmail().equals("admin"))
            {
                mv.setViewName("redirect:principal-adm");
            }
            else
            {
                List<Etiqueta> etiquetas = repositoryEtiquetas.findAll();
                mv.addObject("etiquetas", etiquetas);
                mv.setViewName("lista-etiquetas");
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

    @RequestMapping(value = {"/cadastro-etiqueta"}, method = RequestMethod.GET)
    public ModelAndView cadastroUsuario (HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
            if (usuario.getEmail().equals("admin"))
            {
                mv.setViewName("redirect:principal-adm");
            }
            else
            {
                Etiqueta etiqueta = new Etiqueta();
                mv.addObject("etiqueta", etiqueta);
                mv.setViewName("cadastro-etiqueta");
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

    @RequestMapping(value = {"/cadastro-etiqueta"}, method = RequestMethod.POST)
    public ModelAndView realizaCadastro (@Valid Etiqueta etiqueta, HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Usuario usuario2 = (Usuario) session.getAttribute("usuarioLogado");
            if (usuario2.getEmail().equals("admin"))
            {
                mv.setViewName("redirect:principal-adm");
            }
            else
            {
                repositoryEtiquetas.save(etiqueta);
                mv.setViewName("redirect:lista-etiquetas");
            }
        }
        else
        {
            mv.setViewName("redirect:login");
        }            
        return mv;
    }    
    
    @RequestMapping(value = {"/editar-etiqueta/{id}"}, method = RequestMethod.GET)
    public ModelAndView carregaEditar (@PathVariable(value = "id", required = true) Long id, HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
            if (usuario.getEmail().equals("admin"))
            {
                mv.setViewName("redirect:principal-adm");
            }
            else
            {
                Etiqueta etiquetaASerEditada = new Etiqueta();
                etiquetaASerEditada = repositoryEtiquetas.getOne(id);
                mv.addObject("etiqueta", etiquetaASerEditada);
                mv.addObject("id", id);
                mv.setViewName("editar-etiqueta");
            }
        }
        else
        {
            mv.setViewName("redirect:login");
        }
        return mv;
    }

    @RequestMapping(value = {"/editar-etiqueta"}, method = RequestMethod.POST)
    public ModelAndView realizaEditar (@RequestParam(value = "id", required = true) Long id, Etiqueta etiqueta, HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Usuario usuario2 = (Usuario) session.getAttribute("usuarioLogado");
            if (usuario2.getEmail().equals("admin"))
            {
                mv.setViewName("redirect:principal-usuario");
            }
            else
            {
                if (etiqueta != null)
                {
                    etiqueta.setId(id);
                    repositoryEtiquetas.save(etiqueta);
                    mv.setViewName("redirect:lista-etiquetas");
                }
                else
                {
                    mv.addObject("etiqueta", etiqueta);
                    mv.setViewName("editar-item");
                }

            }
        }
        else
        {
            mv.setViewName("redirect:login");
        }            
        return mv;
    }     

    @RequestMapping(value = { "/excluir-etiqueta/{id}" }, method = RequestMethod.GET)
    public ModelAndView carregaExcluir(@PathVariable(value = "id", required = true) Long id, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
            if (usuario.getEmail().equals("admin"))
            {
                mv.setViewName("redirect:principal-adm");
            }
            else
            {
                repositoryEtiquetas.deleteById(id);
                mv.setViewName("redirect:/lista-etiquetas");
            }
        }
        else
        {
            mv.setViewName("redirect:login");
        }
        return mv;    
    }
    
}