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

import br.com.ufjf.dcc193.trab03.Models.Item;
import br.com.ufjf.dcc193.trab03.Models.Usuario;
import br.com.ufjf.dcc193.trab03.Persistence.ItemRepository;

@Controller
public class ItemController {

    @Autowired
    private ItemRepository repositoryItem;

    @RequestMapping(value = {"/lista-itens"}, method = RequestMethod.GET)
    public ModelAndView carregaItens (HttpSession session)
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
                List<Item> itens = repositoryItem.findAll();
                mv.addObject("itens", itens);
                mv.setViewName("lista-itens");
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

    @RequestMapping(value = {"/cadastro-item"}, method = RequestMethod.GET)
    public ModelAndView cadastroItem (HttpSession session)
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
                Item item = new Item();
                mv.addObject("item", item);
                mv.setViewName("cadastro-item");
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

    @RequestMapping(value = {"/cadastro-item"}, method = RequestMethod.POST)
    public ModelAndView realizaCadastro (@Valid Item item, HttpSession session)
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
                repositoryItem.save(item);
                mv.setViewName("redirect:lista-itens");
            }
        }
        else
        {
            mv.setViewName("redirect:login");
        }            
        return mv;
    }    
    
    @RequestMapping(value = {"/editar-item/{id}"}, method = RequestMethod.GET)
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
                Item itemASerEditado = new Item();
                itemASerEditado = repositoryItem.getOne(id);
                mv.addObject("item", itemASerEditado);
                mv.addObject("id", id);
                mv.setViewName("editar-item");
            }
        }
        else
        {
            mv.setViewName("redirect:login");
        }
        return mv;
    }

    @RequestMapping(value = {"/editar-item"}, method = RequestMethod.POST)
    public ModelAndView realizaEditar (@RequestParam(value = "id", required = true) Long id, Item item, HttpSession session)
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
                if (item != null)
                {
                    item.setId(id);
                    repositoryItem.save(item);
                    mv.setViewName("redirect:lista-itens");
                }
                else
                {
                    mv.addObject("item", item);
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

    @RequestMapping(value = { "/excluir-item/{id}" }, method = RequestMethod.GET)
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
                repositoryItem.deleteById(id);
                mv.setViewName("redirect:/lista-itens");
            }
        }
        else
        {
            mv.setViewName("redirect:login");
        }
        return mv;    
    }
    
}