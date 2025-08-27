package org.kinscript.TallerProyecto1.controller;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.kinscript.TallerProyecto1.entity.Contactos;
import org.kinscript.TallerProyecto1.entity.Usuario;
import org.kinscript.TallerProyecto1.service.iContactosService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

@Named("contactosController")
@ViewScoped
public class ContactosController implements Serializable {

    @Autowired
    private iContactosService contactosService;

    private List<Contactos> contactosDelUsuario;

    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext != null) {
            Usuario usuarioLogueado = (Usuario) facesContext.getExternalContext().getSessionMap().get("usuarioLogueado");
            if (usuarioLogueado != null) {
                contactosDelUsuario = contactosService.listarContactosPorUsuario(usuarioLogueado);
            }
        } else {
            contactosDelUsuario = List.of();
        }
    }

    public List<Contactos> getContactosDelUsuario() {
        return contactosDelUsuario;
    }
}
