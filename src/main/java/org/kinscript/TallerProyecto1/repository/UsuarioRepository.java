package org.kinscript.TallerProyecto1.repository;
import org.kinscript.TallerProyecto1.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByUsuarioAndPassword(String usuario, String password);
}
