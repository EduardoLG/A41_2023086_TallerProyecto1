package org.kinscript.TallerProyecto1.service;
import java.util.List;
import org.kinscript.TallerProyecto1.entity.Contactos;
public interface iContactosService {
    public List<Contactos> listarContactos();
    public Contactos buscarContactoPorId(Integer idContacto);
    public void guardarContacto(Contactos contactos);
    public void eliminarContacto(Contactos contactos);
}
