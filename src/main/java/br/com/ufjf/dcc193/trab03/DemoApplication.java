package br.com.ufjf.dcc193.trab03;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import br.com.ufjf.dcc193.trab03.Models.Usuario;
import br.com.ufjf.dcc193.trab03.Persistence.UsuarioRepository;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(DemoApplication.class, args);
		UsuarioRepository repUsuario = ctx.getBean(UsuarioRepository.class);
		List<Usuario> usuarios = repUsuario.findAll();
		if (usuarios.size() == 0)
		{
			Usuario usuario = new Usuario();
			usuario.setEmail("admin");
			usuario.setChave("admin");
			usuario.setNome("admin");
			repUsuario.save(usuario);
		}
	}

}
