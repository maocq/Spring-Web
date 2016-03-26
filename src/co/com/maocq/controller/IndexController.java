package co.com.maocq.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import co.com.maocq.beans.Usuario;
import co.com.maocq.util.error.ErrorResponse;
import co.com.maocq.util.excel.ExcelIterador;
import co.com.maocq.util.excel.ExcelUtil;
import co.com.maocq.util.hibernate.HibernateUtil;
import co.com.maocq.util.mail.SendMail;

@Controller
public class IndexController {

	final static Gson gson = new Gson();

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {

		return "index";
	}

	@RequestMapping(value = "/usuario", method = RequestMethod.POST, produces = (MediaType.APPLICATION_JSON_VALUE
			+ ";charset=utf-8"), consumes = (MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8"))
	public @ResponseBody String nuevoUsuario(@RequestBody String request) {

		Usuario usuario = gson.fromJson(request, Usuario.class);

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Usuario>> constraintViolations = validator.validate(usuario);
		if (!constraintViolations.isEmpty()) {
			Map<String, String> errores = new HashMap<>();
			for (ConstraintViolation<Usuario> constraintViolation : constraintViolations)
				errores.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());

			return gson.toJson(new ErrorResponse(true, errores));
		}

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(usuario);
		session.getTransaction().commit();

		return gson.toJson(usuario);

	}

	@RequestMapping(value = "/usuario/{idUsuario}", method = RequestMethod.GET, produces = (MediaType.APPLICATION_JSON_VALUE
			+ ";charset=utf-8"))
	public @ResponseBody String usuario(@PathVariable("idUsuario") int idUsuario) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Usuario usuario = (Usuario) session.get(Usuario.class, idUsuario);
		session.close();
		return gson.toJson(usuario);

		/*
		 * Otra forma, con getCurrentSession()
		 */
		// Session session =
		// HibernateUtil.getSessionFactory().getCurrentSession();
		// session.beginTransaction();
		// Usuario usuario = (Usuario) session.get(Usuario.class, idUsuario);
		// session.getTransaction().commit();
		// return gson.toJson(usuario);

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/hql", method = RequestMethod.GET, produces = (MediaType.APPLICATION_JSON_VALUE
			+ ";charset=utf-8"))
	public @ResponseBody String hql() {

		try {

			Session session = HibernateUtil.getSessionFactory().openSession();
			// Query query = session.createSQLQuery("SELECT * FROM usuario");

			// Query query = session.createQuery("SELECT u FROM Usuario u");
			Query query = session.getNamedQuery("findAllUsuarios")
					.setMaxResults(100);
			List<Usuario> usuarios = query.list();

			session.close();

			return gson.toJson(usuarios);

		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
			return gson.toJson(null);
		}

	}

	@RequestMapping(value = "/leerExcel", method = RequestMethod.GET, produces = (MediaType.APPLICATION_JSON_VALUE
			+ ";charset=utf-8"))
	public @ResponseBody String leerExcel() {
		try {
			File file = new File("C:\\Users/John M. Carmona/Desktop/prueba.xlsx");
			// (new ExcelUtil(file)).getLibro();
			ExcelUtil excelUtil = new ExcelUtil(file).getLibro().getHoja(0);

			excelUtil.recorrerHoja(new ExcelIteradorImp(), 4);
			// String[][] matriz = excelU.getMatriz();

			return gson.toJson(null);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return gson.toJson(null);
		}
	}

	/*
	 * Iterador Excel
	 */
	public class ExcelIteradorImp implements ExcelIterador {
		@Override
		public void iteracion(String[] iteracion, int numIteracion) {
			System.out.println(iteracion.length);
			System.out.println(iteracion[0]);
			System.out.println(iteracion[1]);
		}

	}

	@RequestMapping(value = "/lote", method = RequestMethod.GET, produces = (MediaType.APPLICATION_JSON_VALUE
			+ ";charset=utf-8"))
	public @ResponseBody String lote() {

		long inicio = System.currentTimeMillis();

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();

		for (int i = 0; i < 100000; i++) {
			Usuario us = new Usuario();
			us.setName("Mauricio " + i);
			us.setEmail("carmonaesc" + i + "@gmail.com");
			session.save(us);
			if (i % 20 == 0) {
				session.flush();
				session.clear();
			}
		}
		tx.commit();
		session.close();

		long fin = System.currentTimeMillis();

		System.out.println( (fin - inicio)/1000 );
		return gson.toJson(null);
	}
	
	@RequestMapping(value = "/mail", method = RequestMethod.GET, produces = (MediaType.APPLICATION_JSON_VALUE
			+ ";charset=utf-8"))
	public @ResponseBody String mail() {
		
		SendMail.send("xyz0@gmail.com", "Saludo", "Hola =)");
		
		return gson.toJson(null);
	}

}
