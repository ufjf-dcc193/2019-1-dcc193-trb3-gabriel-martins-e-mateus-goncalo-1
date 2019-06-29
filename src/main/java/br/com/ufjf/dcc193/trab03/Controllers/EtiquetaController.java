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
import br.com.ufjf.dcc193.trab03.Models.VinculoEtiqueta;
import br.com.ufjf.dcc193.trab03.Persistence.EtiquetaRepository;
import br.com.ufjf.dcc193.trab03.Persistence.ItemEtiquetaRepository;
import br.com.ufjf.dcc193.trab03.Persistence.ItemRepository;
import br.com.ufjf.dcc193.trab03.Persistence.VinculoEtiquetaRepository;

@Controller
public class EtiquetaController {

    @Autowired
    private EtiquetaRepository repositoryEtiquetas;

    @Autowired
    private ItemEtiquetaRepository itemEtiquetaRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private VinculoEtiquetaRepository repositoryVinculoEtiqueta;

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
                Item item = itemRepository.getOne(id);
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
                Item item = itemRepository.getOne(id);
                List<Etiqueta> etiquetas = repositoryEtiquetas.findAll();
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
                List<ItemEtiqueta> etiquetas = itemEtiquetaRepository.findAll();
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
                    Etiqueta etiqueta = repositoryEtiquetas.getOne(id);
                    Item item = itemRepository.getOne(id2);
                    ItemEtiqueta iE = new ItemEtiqueta();
                    iE.setEtiqueta(etiqueta);
                    iE.setItem(item);
                    itemEtiquetaRepository.save(iE);
                    item.getItemEtiquetas().add(iE);
                    itemRepository.save(item);
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
                List<ItemEtiqueta> etiquetas = itemEtiquetaRepository.findAll();
                for (ItemEtiqueta var : etiquetas) {
                    if (var.getItem().getId().equals(id2) && var.getEtiqueta().getId().equals(id))
                    {
                        itemEtiquetaRepository.deleteById(var.getId());
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

    @RequestMapping(value = { "/excluir-etiqueta-vinculo/{id}/{id2}" }, method = RequestMethod.GET)
    public ModelAndView carregaExcluirVinculoEtiqueta(@PathVariable(value = "id", required = true) Long id, 
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
                List<VinculoEtiqueta> etiquetas = repositoryVinculoEtiqueta.findAll();
                for (VinculoEtiqueta var : etiquetas) {
                    if (var.getVinculoEtiqueta().getId().equals(id2) && var.getEtiquetaVinculo().getId().equals(id))
                    {
                        repositoryVinculoEtiqueta.deleteById(var.getId());
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