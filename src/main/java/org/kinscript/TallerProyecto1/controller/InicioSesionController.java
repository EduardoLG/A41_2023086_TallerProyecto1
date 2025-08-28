package org.kinscript.TallerProyecto1.controller;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import lombok.Data;
import org.kinscript.TallerProyecto1.entity.Usuario;
import org.kinscript.TallerProyecto1.repository.UsuarioRepository;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;

@Component
@ViewScoped
@Data
@Profile("web")
public class InicioSesionController implements Serializable {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private String usuario;
    private String password;

    public String iniciarSesion() {
        Usuario u = usuarioRepository.findByUsuarioAndPassword(usuario, password);

        if (u != null) {
            FacesContext.getCurrentInstance().getExternalContext()
                    .getSessionMap().put("usuarioLogueado", u);
            return "index.xhtml?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Usuario o contraseña incorrectos"));
            PrimeFaces.current().ajax().update("form");
            return null;
        }
    }
}
