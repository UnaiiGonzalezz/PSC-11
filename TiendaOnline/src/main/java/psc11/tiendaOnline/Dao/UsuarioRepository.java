package psc11.tiendaOnline.Dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import psc11.tiendaOnline.DataDomain.TipoUsuario;
import psc11.tiendaOnline.DataDomain.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByid(String id);
    Usuario findByTipoUsuario(TipoUsuario tipoUsuario);
}
