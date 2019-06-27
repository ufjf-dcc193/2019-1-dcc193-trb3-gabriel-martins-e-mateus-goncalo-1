package br.com.ufjf.dcc193.trab03.Controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.ufjf.dcc193.trab03.Models.Usuario;

@Controller
public class HomeController {

    @RequestMapping({"", "/", "/index", "/principal-adm", "/principal-usuario"})
    public String index (HttpSession session)
    {
        if (session.getAttribute("usuarioLogado") != null)
        {   
            Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
            if (usuario.getEmail().equals("admin"))
            {
                return "principal-adm";
            }
            else
            {
                return "principal-usuario";
            }
        }
        else
        {
            return "index";
        }
    }

    @RequestMapping({"/sobre"})
    public String sobre (HttpSession session)
    {
        if (session.getAttribute("usuarioLogado") != null)
        {   
//            Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
//            if (usuario.getEmail().equals("admin"))
//            {
//                return "principal-adm";
//            }
//            else
//            {
                return "principal-usuario";
 //           }
        }
        else
        {
            return "sobre";
        }
    }

}