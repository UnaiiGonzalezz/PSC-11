package psc11.tiendaOnline.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import psc11.tiendaOnline.DataDomain.Estado;
import psc11.tiendaOnline.DataDomain.Pedido;
import psc11.tiendaOnline.DataDomain.Usuario;
import psc11.tiendaOnline.DataDomain.Venta;



@Repository
public interface VentaRepository extends JpaRepository<Venta, Integer> {
    Venta findById(int id);
    Venta findByCliente(Usuario cliente);
    Venta findByPedido(Pedido pedido);
    Venta findByEstado(Estado estado);
}
