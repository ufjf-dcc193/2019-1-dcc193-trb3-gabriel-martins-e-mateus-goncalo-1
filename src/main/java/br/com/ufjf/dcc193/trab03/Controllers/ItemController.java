package br.com.ufjf.dcc193.trab03.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import br.com.ufjf.dcc193.trab03.Models.Item;
import br.com.ufjf.dcc193.trab03.Models.ItemEtiqueta;
import br.com.ufjf.dcc193.trab03.Models.Usuario;
import br.com.ufjf.dcc193.trab03.Persistence.EtiquetaRepository;
import br.com.ufjf.dcc193.trab03.Persistence.ItemEtiquetaRepository;
import br.com.ufjf.dcc193.trab03.Persistence.ItemRepository;

@Controller
public class ItemController {

    @Autowired
    private ItemRepository repositoryItem;
    @Autowired
    private ItemEtiquetaRepository repositoryItemEtiqueta;    
    @Autowired
    private EtiquetaRepository repositoryEtiqueta;

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

    @RequestMapping(value = {"/listar-item-etiqueta/{id}"}, method = RequestMethod.GET)
    public ModelAndView carregaItensEtiqueta (@PathVariable(value = "id", required = true) Long id, HttpSession session)
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
                Etiqueta etiqueta = repositoryEtiqueta.getOne(id);
                List<Item> itens = new ArrayList<>(); 
                List<ItemEtiqueta> itemEtiquetas = repositoryItemEtiqueta.findAll();
                for (ItemEtiqueta var : itemEtiquetas) {
                    if (var.getEtiqueta().getId().equals(id))
                    {
                        itens.add(var.getItem());
                    }
                }
                mv.addObject("etiqueta", etiqueta);
                mv.addObject("itens", itens);
                mv.setViewName("listar-item-etiqueta");
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
    
    @RequestMapping(value = {"/item-etiquetas/{id}"}, method = RequestMethod.GET)
    public ModelAndView listaEtiquetasItem (@PathVariable(value = "id", required = true) Long id, HttpSession session)
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
                List<Etiqueta> equitasEnvio = new ArrayList<>();
                Item item = repositoryItem.getOne(id);
                Set<ItemEtiqueta> etiquetas = item.getItemEtiquetas();
                for (ItemEtiqueta var : etiquetas) {
                    equitasEnvio.add(var.getEtiqueta());
                }
                mv.addObject("item", item);
                mv.addObject("id2", id);
                mv.addObject("etiquetas", equitasEnvio);
                mv.setViewName("item-etiquetas");
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

    @RequestMapping(value = {"/adicionar-item-etiquetas/{id}"}, method = RequestMethod.GET)
    public ModelAndView adicionarItemEtiqueta (@PathVariable(value = "id", required = true) Long id, HttpSession session)
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
                Item item = repositoryItem.getOne(id);
                List<Etiqueta> etiquetas = repositoryEtiqueta.findAll();
                mv.addObject("id2", id);
                mv.addObject("item", item);
                mv.addObject("etiquetas", etiquetas);
                mv.setViewName("adicionar-item-etiquetas");
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

    @RequestMapping(value = {"/adicionar-item-etiquetas/{id}/{id2}"}, method = RequestMethod.GET)
    public ModelAndView adicionarItemEtiqueta2 (@PathVariable(value = "id", required = true) Long id, 
    @PathVariable(value = "id2", required = true) Long id2, HttpSession session)
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
                Boolean encontrou = false;
                List<ItemEtiqueta> etiquetas = repositoryItemEtiqueta.findAll();
                for (ItemEtiqueta var : etiquetas) {
                    if (var.getItem().getId().equals(id2) && var.getEtiqueta().getId().equals(id))
                    {
                        encontrou = true;
                    }
                }
                if (encontrou)
                {
                    mv.setViewName("ja-existe-relacionamento");
                }
                else
                {
                    Etiqueta etiqueta = repositoryEtiqueta.getOne(id);
                    Item item = repositoryItem.getOne(id2);
                    ItemEtiqueta iE = new ItemEtiqueta();
                    iE.setEtiqueta(etiqueta);
                    iE.setItem(item);
                    repositoryItemEtiqueta.save(iE);
                    item.getItemEtiquetas().add(iE);
                    repositoryItem.save(item);
                    mv.setViewName("redirect:/lista-itens");
                }
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

    @RequestMapping(value = { "/excluir-etiqueta-item/{id}/{id2}" }, method = RequestMethod.GET)
    public ModelAndView carregaExcluirItemEtiqueta(@PathVariable(value = "id", required = true) Long id, 
    @PathVariable(value = "id2", required = true) Long id2, HttpSession session) {
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
                List<ItemEtiqueta> etiquetas = repositoryItemEtiqueta.findAll();
                for (ItemEtiqueta var : etiquetas) {
                    if (var.getItem().getId().equals(id2) && var.getEtiqueta().getId().equals(id))
                    {
                        repositoryItemEtiqueta.deleteById(var.getId());
                    }
                }
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