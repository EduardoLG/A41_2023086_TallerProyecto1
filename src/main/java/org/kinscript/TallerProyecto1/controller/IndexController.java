package org.kinscript.TallerProyecto1.controller;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import lombok.Data;
import org.kinscript.TallerProyecto1.entity.Contactos;
import org.kinscript.TallerProyecto1.service.ContactosService;
import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.faces.view.ViewScoped;
import java.util.List;

@Component
@ViewScoped
@Data
public class IndexController {
    @Autowired
    private ContactosService contactosService;
    private List<Contactos> contactos;
    private Contactos contactoSeleccionado;
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @PostConstruct
    public void init() {
        cargarContactos();
    }
    public void cargarContactos() {
        this.contactos = this.contactosService.listarContactos();
        this.contactos.forEach(contacto -> logger.info(contacto.toString()));
    }
    public void agregarContacto() {
        this.contactoSeleccionado = new Contactos();
    }
    public void guardarContacto(){
        logger.info("Guardando contacto: " + this.contactoSeleccionado);
        if (this.contactoSeleccionado.getIdContacto() == null) {
            this.contactosService.guardarContacto(this.contactoSeleccionado);
            this.contactos.add(this.contactoSeleccionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Contacto agregado exitosamente"));
        }else{
            this.contactosService.guardarContacto(this.contactoSeleccionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Contacto actualizado exitosamente"));
        }
        PrimeFaces.current().executeScript("PF('ventanaModalContacto').hide()");
        PrimeFaces.current().ajax().update("formulario-contactos:mensaje-emergente","formulario-contactos:tabla-contactos");
        this.contactoSeleccionado = null;
    }
    public void eliminarContacto(){
        logger.info("Eliminando contacto: " + this.contactoSeleccionado);
        this.contactosService.eliminarContacto(this.contactoSeleccionado);
        this.contactos.remove(this.contactoSeleccionado);
        this.contactoSeleccionado = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Contacto eliminado exitosamente"));
        PrimeFaces.current().ajax().update("formulario-contactos:mensaje-emergente","formulario-contactos:tabla-contactos");
        
    }
}
