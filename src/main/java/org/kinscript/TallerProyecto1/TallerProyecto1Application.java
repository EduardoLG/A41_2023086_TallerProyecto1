package org.kinscript.TallerProyecto1;

import org.kinscript.TallerProyecto1.service.ContactosService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.kinscript.TallerProyecto1.entity.Contactos;
import org.kinscript.TallerProyecto1.service.iContactosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class TallerProyecto1Application implements CommandLineRunner{
	@Autowired
	private ContactosService contactosService;

	private static final Logger logger = LoggerFactory.getLogger(TallerProyecto1Application.class);
	String salto = System.lineSeparator();
	public static void main(String[] args) {
		logger.info("Iniciando la aplicacion");
		SpringApplication.run(TallerProyecto1Application.class, args);
		logger.info("Aplicacion Finalizada");
	}

	@Override
	public void run(String... args) throws Exception {
		registroContactosApp();
	}
	private void registroContactosApp(){
		logger.info("***** Bievenido a tu Agenda de Contactos *****");
		var salir = false;
		var consola = new Scanner(System.in);
		while (!salir){
			var opcion = mostrarMenu(consola);
			salir = ejecutarOpciones(consola, opcion);
			logger.info(salto);

		}

	}
	private int mostrarMenu(Scanner consola){
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
	private boolean ejecutarOpciones(Scanner consola, int opcion){
		var salir = false;
		switch (opcion){
			case 1 ->{
				logger.info(salto+"*** Tus contactos" + salto);
				List<Contactos> contactos = contactosService.listarContactos();
				contactos.forEach(contacto -> logger.info(contacto.toString()+salto));
			}
			case 2 ->{
				logger.info(salto + "Buscar Contacto por su ID"+ salto);
				var codigo = Integer.parseInt(consola.nextLine());
				Contactos contactos = contactosService.buscarContactoPorId(codigo);
				if (contactos != null){
					logger.info("Contacto encontrado: "+ contactos + salto);
				} else {
					logger.info("Contacto no encontrado"+ contactos + salto);
				}
			}
			case 3 ->{
				logger.info(salto+"*** Agregar Contactos ***"+salto);
				logger.info("Ingrese el nombre");
				var nombre = consola.nextLine();
				logger.info("Ingrese el telefono");
				var numeroTelefono = consola.nextLine();
				logger.info("Ingrese el email");
				var email = consola.nextLine();

				var contactos = new Contactos();
				contactos.setNombre(nombre);
				contactos.setNumeroTelefono(numeroTelefono);
				contactos.setEmail(email);
				contactosService.guardarContacto(contactos);
				logger.info("Contacto agregado: " + contactos + salto);
			}
			case 4 ->{
				logger.info(salto+"*** Modificar Contacto ***"+salto);
				logger.info("Agregue el codigo del contacto que desea modificar");
				var codigo = Integer.parseInt(consola.nextLine());
				Contactos contactos = contactosService.buscarContactoPorId(codigo);
				if (contactos != null){
					logger.info("Ingrese el nombre");
					var nombre = consola.nextLine();
					logger.info("Ingrese el telefono");
					var numeroTelefono = consola.nextLine();
					logger.info("Ingrese el email");
					var email = consola.nextLine();
					contactos.setNombre(nombre);
					contactos.setNumeroTelefono(numeroTelefono);
					contactos.setEmail(email);
					contactosService.guardarContacto(contactos);

				}else {
					logger.info("Contacto no Encontrado" + contactos+salto);
				}
			}
			case 5 ->{
				logger.info(salto+"*** Eliminar COntacto ***"+salto);
				logger.info("Ingrese el ID del contacto que desea eliminar");
				var codigo = Integer.parseInt(consola.nextLine());
				var contacto = contactosService.buscarContactoPorId(codigo);
				if (contacto != null){
					contactosService.eliminarContacto(contacto);
					logger.info("Contacto eliminado, adios" + contacto + salto);
				}else{
					logger.info("Cliente no encontrado" + contacto + salto);
				}

			}
			case 6 ->{
				logger.info("adios"+salto);
				salir = true;
			}
			default -> logger.info("Opcion invalida");

		}
		return false;
	}
}
