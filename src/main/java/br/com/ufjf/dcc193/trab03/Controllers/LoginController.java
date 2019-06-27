package br.com.ufjf.dcc193.trab03.Controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.ufjf.dcc193.trab03.Models.Usuario;
import br.com.ufjf.dcc193.trab03.Persistence.UsuarioRepository;

@Controller
public class LoginController {

    @Autowired
    private UsuarioRepository UsuarioRepository;

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public ModelAndView carregaLogin (HttpSession session)
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

    @RequestMapping(value = {"/login"}, method = RequestMethod.POST)
    public ModelAndView realizaLogin (Usuario usuario, HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        Usuario usr = UsuarioRepository.findByEmail(usuario.getEmail());
        if (usr != null && usr.getChave().equals(usuario.getChave()))
        {
            session.setAttribute("usuarioLogado", usr);
            mv.addObject("usuario", usr);
            if (usr.getEmail().equals("admin"))
            {
                mv.setViewName("redirect:principal-adm");
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

    @RequestMapping(value = {"/logout"}, method = RequestMethod.GET)
    public ModelAndView realizaLogout (HttpSession session)
    {
        ModelAndView mv = new ModelAndView();
        session.setAttribute("usuarioLogado", null);
        mv.setViewName("redirect:index");
        return mv;
    }
    
}