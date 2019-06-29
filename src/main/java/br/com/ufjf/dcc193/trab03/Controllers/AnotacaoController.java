package br.com.ufjf.dcc193.trab03.Controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.ufjf.dcc193.trab03.Models.Anotacao;
import br.com.ufjf.dcc193.trab03.Models.Item;
import br.com.ufjf.dcc193.trab03.Models.Usuario;
import br.com.ufjf.dcc193.trab03.Persistence.AnotacaoRepository;
import br.com.ufjf.dcc193.trab03.Persistence.ItemRepository;

@Controller
public class AnotacaoController {

    @Autowired
    private AnotacaoRepository repositoryAnotacao;
    @Autowired
    private ItemRepository repositoryItem;

    @RequestMapping(value = {"/item-anotacoes/{id}"}, method = RequestMethod.GET)
    public ModelAndView carregaItensAnotacao (@PathVariable(value = "id", required = true) Long id, HttpSession session)
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
                mv.addObject("anotacoes", item.getAnotacoes());
                mv.addObject("id2", id);  
                mv.addObject("item", item);              
                mv.setViewName("item-anotacoes");
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

    @RequestMapping(value = {"/detalhes-anotacao/{id}"}, method = RequestMethod.GET)
    public ModelAndView carregaDetalhes (@PathVariable(value = "id", required = true) Long id, HttpSession session)
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
                Anotacao anotacao = repositoryAnotacao.getOne(id);
                SimpleDateFormat formatBra = new SimpleDateFormat("yyyy-MM-dd");
                String dataDeInicio = formatBra.format(anotacao.getDataDeInicio());
                mv.addObject("dataDeInicio", dataDeInicio);
                if (anotacao.getDataDeAtualizacao() != null)
                {
                     String dataDeFim = formatBra.format(anotacao.getDataDeAtualizacao());
                      mv.addObject("dataDeAtualizacao", dataDeFim);        
                }
                mv.addObject("anotacao", anotacao);
                mv.setViewName("detalhes-anotacao");
            }
        }
        else
        {
            mv.setViewName("redirect:login");
        }            
        return mv;
    }     


    @RequestMapping(value = {"/adicionar-anotacao-item/{id}"}, method = RequestMethod.GET)
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
                Anotacao anotacao = new Anotacao();
                mv.addObject("anotacao", anotacao);
                mv.addObject("id2", id);
                mv.setViewName("cadastro-item-anotacao");
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

    @RequestMapping(value = {"/cadastro-item-anotacao"}, method = RequestMethod.POST)
    public ModelAndView realizaCadastrar (@RequestParam(value = "id", required = true) Long id, Anotacao anotacao, HttpSession session)
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
                Item item = repositoryItem.getOne(id);
                java.util.Date dataUtil = new java.util.Date();
                java.sql.Date dataSql = new java.sql.Date(dataUtil.getTime());
                anotacao.setDataDeInicio(dataSql);
                anotacao.setUsuario(usuario2);
                anotacao.setItem(item);
                repositoryAnotacao.save(anotacao);
                mv.setViewName("redirect:lista-itens");
            }
        }
        else
        {
            mv.setViewName("redirect:login");
        }            
        return mv;
    }     

    @RequestMapping(value = { "/excluir-anotacao/{id}" }, method = RequestMethod.GET)
    public ModelAndView carregaExcluirItemEtiqueta(@PathVariable(value = "id", required = true) Long id, HttpSession session) {
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
                repositoryAnotacao.deleteById(id);
                mv.setViewName("redirect:/lista-itens");
            }
        }
        else
        {
            mv.setViewName("redirect:login");
        }
        return mv;    
    }

    @RequestMapping(value = {"/editar-anotacao/{id}"}, method = RequestMethod.GET)
    public ModelAndView editarAnotacao (@PathVariable(value = "id", required = true) Long id, HttpSession session)
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
                Anotacao anotacao = repositoryAnotacao.getOne(id);
                mv.addObject("anotacao", anotacao);
                mv.addObject("id2", id);
                mv.setViewName("editar-anotacao");
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

    @RequestMapping(value = {"/editar-anotacao"}, method = RequestMethod.POST)
    public ModelAndView realizaEditarAnotacao (@RequestParam(value = "id", required = true) Long id, Anotacao anotacao, HttpSession session)
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
                java.util.Date dataUtil = new java.util.Date();
                java.sql.Date dataSql = new java.sql.Date(dataUtil.getTime());
                anotacao.setDataDeAtualizacao(dataSql);
                anotacao.setId(id);
                repositoryAnotacao.save(anotacao);
                mv.setViewName("redirect:lista-itens");
            }
        }
        else
        {
            mv.setViewName("redirect:login");
        }            
        return mv;
    }     


}