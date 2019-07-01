package br.com.ufjf.dcc193.trab03.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.ufjf.dcc193.trab03.Models.Etiqueta;
import br.com.ufjf.dcc193.trab03.Models.Item;
import br.com.ufjf.dcc193.trab03.Models.Usuario;
import br.com.ufjf.dcc193.trab03.Models.Vinculo;
import br.com.ufjf.dcc193.trab03.Models.VinculoEtiqueta;
import br.com.ufjf.dcc193.trab03.Persistence.EtiquetaRepository;
import br.com.ufjf.dcc193.trab03.Persistence.ItemRepository;
import br.com.ufjf.dcc193.trab03.Persistence.VinculoEtiquetaRepository;
import br.com.ufjf.dcc193.trab03.Persistence.VinculoRepository;

@Controller
public class VinculoController {

    @Autowired
    private ItemRepository repositoryItem;

    @Autowired
    private VinculoRepository repositoryVinculo;

    @Autowired
    private VinculoEtiquetaRepository repositoryVinculoEtiqueta; 

    @Autowired
    private EtiquetaRepository repositoryEtiqueta;

    @RequestMapping(value = {"/item-vinculos/{id}"}, method = RequestMethod.GET)
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
                List<Vinculo> vinculos = new ArrayList<>();
                Item item = repositoryItem.getOne(id);
                vinculos.addAll(item.getVinculosEntrada());
                vinculos.addAll(item.getVinculosSaida());    
                mv.addObject("vinculos", vinculos);
                mv.addObject("id2", id);  
                mv.addObject("item", item);              
                mv.setViewName("item-vinculos");
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

    @RequestMapping(value = {"/vinculo-etiquetas/{id}"}, method = RequestMethod.GET)
    public ModelAndView carregaVinculoEtiquetas (@PathVariable(value = "id", required = true) Long id, HttpSession session)
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
                Vinculo vinculo = repositoryVinculo.getOne(id);
                Set<VinculoEtiqueta> etiquetas = vinculo.getVinculoEtiqueta();
                for (VinculoEtiqueta var : etiquetas) {
                    equitasEnvio.add(var.getEtiquetaVinculo());
                }
                mv.addObject("id2", id);
                mv.addObject("etiquetas", equitasEnvio);
                mv.setViewName("vinculo-etiquetas");
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

    @RequestMapping(value = {"/adicionar-item-vinculo/{id}"}, method = RequestMethod.GET)
    public ModelAndView adicionarItemVinculo (@PathVariable(value = "id", required = true) Long id, HttpSession session)
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
                List<Item> itens = new ArrayList<>();
                for (Item var : repositoryItem.findAll()) {
                    if (!var.getId().equals(id))
                    {
                        itens.add(var);
                    }                    
                }
                mv.addObject("id2", id);
                mv.addObject("itens", itens);
                mv.setViewName("adicionar-item-vinculos");
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

    @RequestMapping(value = {"/adicionar-item-vinculo/{id}/{id2}"}, method = RequestMethod.GET)
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
                List<Vinculo> vinculos = repositoryVinculo.findAll();
                for (Vinculo var : vinculos) {
                    if (var.getVinculoEntrada().getId().equals(id2) && var.getVinculoSaida().getId().equals(id))
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
                    Vinculo vinculo = new Vinculo();
                    Item itemEntrada = repositoryItem.getOne(id2);
                    Item itemSaida = repositoryItem.getOne(id);
                    vinculo.setVinculoEntrada(itemEntrada);
                    vinculo.setVinculoSaida(itemSaida);
                    itemEntrada.getVinculosEntrada().add(vinculo);
                    itemSaida.getVinculosSaida().add(vinculo);
                    repositoryItem.save(itemEntrada);
                    repositoryItem.save(itemSaida);
                    repositoryVinculo.save(vinculo);
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

    @RequestMapping(value = {"/excluir-vinculo/{id}"}, method = RequestMethod.GET)
    public ModelAndView excluirVinculo (@PathVariable(value = "id", required = true) Long id, HttpSession session)
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
                repositoryVinculo.deleteById(id);
                mv.setViewName("redirect:/lista-itens");
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

    @RequestMapping(value = {"/adicionar-vinculo-etiquetas/{id}"}, method = RequestMethod.GET)
    public ModelAndView adicionarVinculoEtiqueta (@PathVariable(value = "id", required = true) Long id, HttpSession session)
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
                List<Etiqueta> etiquetas = repositoryEtiqueta.findAll();
                mv.addObject("id2", id);
                mv.addObject("etiquetas", etiquetas);
                mv.setViewName("adicionar-vinculo-etiquetas");
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

    @RequestMapping(value = {"/adicionar-vinculo-etiquetas/{id}/{id2}"}, method = RequestMethod.GET)
    public ModelAndView adicionarVinculoEtiqueta2 (@PathVariable(value = "id", required = true) Long id, 
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
                List<VinculoEtiqueta> etiquetas = repositoryVinculoEtiqueta.findAll();
                for (VinculoEtiqueta var : etiquetas) {
                    if (var.getVinculoEtiqueta().getId().equals(id2) && var.getEtiquetaVinculo().getId().equals(id))
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
                    Vinculo vinculo = repositoryVinculo.getOne(id2);
                    VinculoEtiqueta vE = new VinculoEtiqueta();
                    vE.setEtiquetaVinculo(etiqueta);
                    vE.setVinculoEtiqueta(vinculo);
                    repositoryVinculoEtiqueta.save(vE);
                    vinculo.getVinculoEtiqueta().add(vE);
                    repositoryVinculo.save(vinculo);
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