package psc11.tiendaOnline.Dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import psc11.tiendaOnline.DataDomain.Venta;
import psc11.tiendaOnline.DataDomain.Pedido;
import psc11.tiendaOnline.DataDomain.Usuario;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer>{
    Pedido findById(int id);
    Pedido findByUsuario(Usuario usuario);
    
}
