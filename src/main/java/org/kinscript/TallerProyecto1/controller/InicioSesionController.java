package org.kinscript.TallerProyecto1.controller;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import org.kinscript.TallerProyecto1.entity.Usuario;
import org.kinscript.TallerProyecto1.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;

@RequestScoped
@Named("inicioSesionController")
public class InicioSesionController {
    private String usuario;
    private String password;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public String iniciarSesion() {
        Usuario u = usuarioRepository.findByUsuarioAndPassword(usuario, password);
        if (u != null) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuarioLogueado", u);
            return "index.xhtml?faces-redirect=true";
        } else {
            return null;
        }
    }
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
