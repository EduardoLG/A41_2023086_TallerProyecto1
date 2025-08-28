package org.kinscript.TallerProyecto1.controller;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import lombok.Data;
import org.kinscript.TallerProyecto1.entity.Contactos;
import org.kinscript.TallerProyecto1.entity.Usuario;
import org.kinscript.TallerProyecto1.service.ContactosService;
import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import jakarta.faces.view.ViewScoped;
import java.util.List;

@Component
@ViewScoped
@Data
@Profile("web")
public class IndexController {

    @Autowired
    private ContactosService contactosService;

    private List<Contactos> contactos;
    private Contactos contactoSeleccionado;

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @PostConstruct
    public void init() {
        cargarContactosDelUsuario();
    }

    public void cargarContactosDelUsuario() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext != null) {
            Usuario usuarioLogueado = (Usuario) facesContext.getExternalContext().getSessionMap().get("usuarioLogueado");
            if (usuarioLogueado != null) {
                this.contactos = contactosService.listarContactosPorUsuario(usuarioLogueado);
                this.contactos.forEach(contacto -> logger.info(contacto.toString()));
            } else {
                this.contactos = List.of();
            }
        } else {
            this.contactos = List.of();
        }
    }

    public void agregarContacto() {
        this.contactoSeleccionado = new Contactos();
    }

    public void guardarContacto() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Usuario usuarioLogueado = (Usuario) facesContext.getExternalContext().getSessionMap().get("usuarioLogueado");

        if (usuarioLogueado == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No hay usuario logueado"));
            return;
        }

        contactoSeleccionado.setUsuario(usuarioLogueado);
        logger.info("Guardando contacto: " + this.contactoSeleccionado);

        contactosService.guardarContacto(this.contactoSeleccionado);

        if (!contactos.contains(contactoSeleccionado)) {
            contactos.add(contactoSeleccionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Contacto agregado exitosamente"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Contacto actualizado exitosamente"));
        }

        PrimeFaces.current().executeScript("PF('ventanaModalContacto').hide()");
        PrimeFaces.current().ajax().update("formulario-contactos:mensaje-emergente","formulario-contactos:tabla-contactos");
        this.contactoSeleccionado = null;
    }

    public void eliminarContacto() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Usuario usuarioLogueado = (Usuario) facesContext.getExternalContext().getSessionMap().get("usuarioLogueado");

        if (usuarioLogueado == null || contactoSeleccionado == null || !contactoSeleccionado.getUsuario().equals(usuarioLogueado)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se puede eliminar este contacto"));
            return;
        }

        logger.info("Eliminando contacto: " + this.contactoSeleccionado);
        contactosService.eliminarContacto(this.contactoSeleccionado);
        contactos.remove(this.contactoSeleccionado);
        this.contactoSeleccionado = null;

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Contacto eliminado exitosamente"));
        PrimeFaces.current().ajax().update("formulario-contactos:mensaje-emergente","formulario-contactos:tabla-contactos");
    }
}
