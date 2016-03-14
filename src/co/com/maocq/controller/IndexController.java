package co.com.maocq.controller;

import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import co.com.maocq.beans.Usuario;

@SuppressWarnings("deprecation")
@Controller
@RequestMapping(value = "/api")
public class IndexController {

	final static Gson gson = new Gson();

	@RequestMapping(value = "/", method = RequestMethod.GET, produces = (MediaType.APPLICATION_JSON_VALUE
			+ ";charset=utf-8"))
	public @ResponseBody String index() {

		Usuario usuario = new Usuario();
		usuario.setName("Mauricio");
		usuario.setEmail("carmonaesc@gmail.com");

		return gson.toJson(usuario);
	}

	@RequestMapping(value = "/usuario", method = RequestMethod.POST, produces = (MediaType.APPLICATION_JSON_VALUE
			+ ";charset=utf-8"), consumes = (MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8"))
	public @ResponseBody String nuevoUsuario(@RequestBody String request) {

		Usuario usuario = gson.fromJson(request, Usuario.class);

		SessionFactory sessionFactory;

		Configuration configuration = new Configuration();
		configuration.configure();
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
				.buildServiceRegistry();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);

		Session session = sessionFactory.openSession();

		try {

			session.beginTransaction();
			session.save(usuario);
			session.getTransaction().commit();

		} catch (ConstraintViolationException cve) {
			session.getTransaction().rollback();

			System.out.println("No se ha podido insertar el usuario debido a los siguientes errores:");
			for (ConstraintViolation constraintViolation : cve.getConstraintViolations()) {
				System.out.println("En el campo '" + constraintViolation.getPropertyPath() + "': "
						+ constraintViolation.getMessage());
			}
			return gson.toJson(null);
		} finally {
			session.close();
			sessionFactory.close();
		}

		return gson.toJson(usuario);

	}

	@RequestMapping(value = "/usuario/{idUsuario}", method = RequestMethod.GET, produces = (MediaType.APPLICATION_JSON_VALUE
			+ ";charset=utf-8"))
	public @ResponseBody String usuario(@PathVariable("idUsuario") int idUsuario) {

		try {

			SessionFactory sessionFactory;

			Configuration configuration = new Configuration();
			configuration.configure();
			ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
					.buildServiceRegistry();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);

			Session session = sessionFactory.openSession();

			Usuario usuario = (Usuario) session.get(Usuario.class, idUsuario);

			session.close();
			sessionFactory.close();

			return gson.toJson(usuario);

		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
			return gson.toJson(null);
		}

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/hql", method = RequestMethod.GET, produces = (MediaType.APPLICATION_JSON_VALUE
			+ ";charset=utf-8"))
	public @ResponseBody String hql() {

		try {

			SessionFactory sessionFactory;

			Configuration configuration = new Configuration();
			configuration.configure();
			ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
					.buildServiceRegistry();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);

			Session session = sessionFactory.openSession();

			// Query query = session.createSQLQuery("SELECT * FROM usuario");

			// Query query = session.createQuery("SELECT u FROM Usuario u");
			Query query = session.getNamedQuery("findAllUsuarios");
			List<Usuario> usuarios = query.list();

			session.close();
			sessionFactory.close();

			return gson.toJson(usuarios);

		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
			return gson.toJson(null);
		}

	}

}
