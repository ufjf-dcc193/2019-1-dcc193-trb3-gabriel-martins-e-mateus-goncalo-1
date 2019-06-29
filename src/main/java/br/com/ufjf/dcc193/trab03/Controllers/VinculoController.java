package br.com.ufjf.dcc193.trab03.Controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.ufjf.dcc193.trab03.Models.Item;
import br.com.ufjf.dcc193.trab03.Models.Usuario;
import br.com.ufjf.dcc193.trab03.Persistence.ItemRepository;
import br.com.ufjf.dcc193.trab03.Persistence.VinculoRepository;

@Controller
public class VinculoController {

    @Autowired
    private ItemRepository repositoryItem;

    @Autowired
    private VinculoRepository repositoryVinculo;

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
                Item item = repositoryItem.getOne(id);
                item.getVinculosEntrada();
                item.getVinculosSaida();                
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


}