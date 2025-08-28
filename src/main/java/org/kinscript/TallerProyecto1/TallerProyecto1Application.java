package org.kinscript.TallerProyecto1;

import org.kinscript.TallerProyecto1.entity.Contactos;
import org.kinscript.TallerProyecto1.entity.Usuario;
import org.kinscript.TallerProyecto1.repository.UsuarioRepository;
import org.kinscript.TallerProyecto1.service.ContactosService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class TallerProyecto1Application implements CommandLineRunner {

	@Autowired
	private ContactosService contactosService;

	@Autowired
	private UsuarioRepository usuarioRepository;

	private static final Logger logger = LoggerFactory.getLogger(TallerProyecto1Application.class);
	private final String salto = System.lineSeparator();

	public static void main(String[] args) {
		logger.info("Iniciando la aplicacion");
		SpringApplication.run(TallerProyecto1Application.class, args);
		logger.info("Aplicacion Finalizada");
	}

	@Override
	public void run(String... args) throws Exception {
		Usuario usuarioLogueado = iniciarSesion();
		if (usuarioLogueado != null) {
			registroContactosApp(usuarioLogueado);
		} else {
			logger.info("No se pudo iniciar sesión. Cerrando aplicación.");
		}
	}

	private Usuario iniciarSesion() {
		Scanner consola = new Scanner(System.in);
		int intentos = 3;

		while (intentos > 0) {
			logger.info("***** Iniciar Sesión *****");
            logger.info("Usuarios:");
            logger.info("1) Usuario: Eduardo, Contraseña: hola123");
            logger.info("2) Usuario: Pedro, Contraseña: pass2");
            logger.info("3) Usuario: Luis, Contraseña: pass3");
			logger.info("Usuario:");
			String usuario = consola.nextLine();
			logger.info("Contraseña:");
			String password = consola.nextLine();

			Usuario u = usuarioRepository.findByUsuarioAndPassword(usuario, password);

			if (u != null) {
				logger.info("Bienvenido, " + usuario + salto);
				return u;
			} else {
				intentos--;
				logger.info("Usuario o contraseña incorrectos. Intentos restantes: " + intentos);
			}
		}
		return null;
	}

	private void registroContactosApp(Usuario usuarioLogueado) {
		logger.info("***** Bienvenido a tu Agenda de Contactos *****");
		var salir = false;
		var consola = new Scanner(System.in);
		while (!salir) {
			var opcion = mostrarMenu(consola);
			salir = ejecutarOpciones(consola, opcion, usuarioLogueado);
			logger.info(salto);
		}
	}

	private int mostrarMenu(Scanner consola) {
		logger.info("""
                1. Listar Contactos
                2. Buscar Contactos
                3. Agregar Contactos
                4. Modificar Contactos
                5. Eliminar Contactos
                6. Salir
                """);
		var opcion = Integer.parseInt(consola.nextLine());
		return opcion;
	}

	private boolean ejecutarOpciones(Scanner consola, int opcion, Usuario usuarioLogueado) {
    var salir = false;
    switch (opcion) {
        case 1 -> { 
            logger.info(salto + "*** Tus contactos ***" + salto);
            List<Contactos> contactos = contactosService.listarContactosPorUsuario(usuarioLogueado);
            if (contactos.isEmpty()) {
                logger.info("No tienes contactos registrados" + salto);
            } else {
                contactos.forEach(contacto -> logger.info(contacto.toString() + salto));
            }
        }
        case 2 -> { 
            logger.info(salto + "Buscar Contacto por su ID" + salto);
            var codigo = Integer.parseInt(consola.nextLine());
            var contacto = contactosService.buscarContactoPorId(codigo);
            if (contacto != null && contacto.getUsuario() != null &&
                contacto.getUsuario().getIdUsuario().equals(usuarioLogueado.getIdUsuario())) {
                logger.info("Contacto encontrado: " + contacto + salto);
            } else {
                logger.info("Contacto no encontrado o no pertenece a tu usuario" + salto);
            }
        }
        case 3 -> {
            logger.info(salto + "*** Agregar Contacto ***" + salto);
            logger.info("Ingrese el nombre");
            var nombre = consola.nextLine();
            logger.info("Ingrese el telefono");
            var numeroTelefono = consola.nextLine();
            logger.info("Ingrese el email");
            var email = consola.nextLine();

            var contacto = new Contactos();
            contacto.setNombre(nombre);
            contacto.setNumeroTelefono(numeroTelefono);
            contacto.setEmail(email);
            contacto.setUsuario(usuarioLogueado); 
            contactosService.guardarContacto(contacto);
            logger.info("Contacto agregado: " + contacto + salto);
        }
        case 4 -> { 
            logger.info(salto + "*** Modificar Contacto ***" + salto);
            logger.info("Ingrese el ID del contacto que desea modificar");
            var codigo = Integer.parseInt(consola.nextLine());
            var contacto = contactosService.buscarContactoPorId(codigo);
            if (contacto != null && contacto.getUsuario() != null &&
                contacto.getUsuario().getIdUsuario().equals(usuarioLogueado.getIdUsuario())) {
                logger.info("Ingrese el nombre");
                var nombre = consola.nextLine();
                logger.info("Ingrese el telefono");
                var numeroTelefono = consola.nextLine();
                logger.info("Ingrese el email");
                var email = consola.nextLine();
                contacto.setNombre(nombre);
                contacto.setNumeroTelefono(numeroTelefono);
                contacto.setEmail(email);
                contactosService.guardarContacto(contacto);
                logger.info("Contacto modificado: " + contacto + salto);
            } else {
                logger.info("Contacto no encontrado o no pertenece a tu usuario" + salto);
            }
        }
        case 5 -> { 
            logger.info(salto + "*** Eliminar Contacto ***" + salto);
            logger.info("Ingrese el ID del contacto que desea eliminar");
            var codigo = Integer.parseInt(consola.nextLine());
            var contacto = contactosService.buscarContactoPorId(codigo);
            if (contacto != null && contacto.getUsuario() != null &&
                contacto.getUsuario().getIdUsuario().equals(usuarioLogueado.getIdUsuario())) {
                contactosService.eliminarContacto(contacto);
                logger.info("Contacto eliminado: " + contacto + salto);
            } else {
                logger.info("Contacto no encontrado o no pertenece a tu usuario" + salto);
            }
        }
        case 6 -> { // Salir
            logger.info("Adios" + salto);
            salir = true;
        }
        default -> logger.info("Opción inválida");
    }
    return salir;
	}
}
