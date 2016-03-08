package co.com.maocq.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import co.com.maocq.beans.Usuario;

@Controller
public class IndexController {

	final static Gson gson = new Gson();

	@RequestMapping(value = "/", method = RequestMethod.POST, produces = (MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8"))
	public @ResponseBody String showAbout() {
		
		Usuario usuario = new Usuario();
		usuario.setName("Mauricio");
		usuario.setEmail("carmonaesc@gmail.com");

		return gson.toJson(usuario);
	}
}
