package org.kinscript.TallerProyecto1.service;
import java.util.List;
import org.kinscript.TallerProyecto1.entity.Contactos;
import org.kinscript.TallerProyecto1.entity.Usuario;

public interface iContactosService {
    List<Contactos> listarContactos();
    List<Contactos> listarContactosPorUsuario(Usuario usuario);
    Contactos buscarContactoPorId(Integer idContacto);
    void guardarContacto(Contactos contactos);
    void eliminarContacto(Contactos contactos);
}
