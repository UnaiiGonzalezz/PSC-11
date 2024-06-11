package psc11.tiendaOnline.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import psc11.tiendaOnline.DataDomain.Plato;
import psc11.tiendaOnline.DataDomain.Usuario;
import psc11.tiendaOnline.DataDomain.Venta;

import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Integer> {
    List<Venta> findByUsuario(Usuario usuario);
    List<Venta> findByPlato(Plato plato);
}
