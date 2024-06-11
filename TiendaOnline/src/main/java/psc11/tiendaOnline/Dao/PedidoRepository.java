package psc11.tiendaOnline.Dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import psc11.tiendaOnline.DataDomain.Venta;
import psc11.tiendaOnline.DataDomain.Pedido;
import psc11.tiendaOnline.DataDomain.Usuario;

@Repository
public interface PedidoRepository extends JpaRepository<Venta, Integer>{
    Venta findById(int id);
    Venta findByUsuario(Usuario usuario);
    Pedido save(Pedido pedido);
}
