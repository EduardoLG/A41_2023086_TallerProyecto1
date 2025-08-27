package org.kinscript.TallerProyecto1.repository;

import org.kinscript.TallerProyecto1.entity.Contactos;
import org.kinscript.TallerProyecto1.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactoRepository extends JpaRepository<Contactos, Integer> {

    List<Contactos> findByUsuario(Usuario usuario);

}
