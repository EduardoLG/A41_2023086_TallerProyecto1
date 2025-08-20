package org.kinscript.TallerProyecto1.service;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.kinscript.TallerProyecto1.entity.Contactos;
import org.kinscript.TallerProyecto1.repository.ContactoRepository;
import java.util.List;
@Service
public class ContactosService implements iContactosService{

    @Autowired
    private ContactoRepository contactoRepository;

    @Override
    public List<Contactos> listarContactos() {
        List<Contactos> contactos = contactoRepository.findAll();
        return contactos;
    }

    @Override
    public Contactos buscarContactoPorId(Integer idContacto) {
        Contactos contactos = contactoRepository.findById(idContacto).orElse(null);
        return contactos;
    }

    @Override
    public void guardarContacto(Contactos contactos) {
        contactoRepository.save(contactos);
    }

    @Override
    public void eliminarContacto(Contactos contactos) {
        contactoRepository.delete(contactos);
    }


}
