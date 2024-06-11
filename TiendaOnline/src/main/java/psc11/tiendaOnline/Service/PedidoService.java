package psc11.tiendaOnline.Service;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import psc11.tiendaOnline.DataDomain.Plato;
import psc11.tiendaOnline.Dao.PedidoRepository;
import psc11.tiendaOnline.DataDomain.Estado;
import psc11.tiendaOnline.DataDomain.Pedido;
import psc11.tiendaOnline.DataDomain.Usuario;


@Service
public class PedidoService {
    private final psc11.tiendaOnline.Dao.PedidoRepository pedidoRepository;
    private final PlatoService platoService;
    private final String connectionString;

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository, PlatoService platoService) {
        this.pedidoRepository = pedidoRepository;
        this.platoService = platoService;
        this.connectionString = "jdbc:sqlite:TiendaOnline.db";
        loadDatos();
    }


    public void loadDatos(){
        String sql = "SELECT * FROM pedido";
        try (Connection con = DriverManager.getConnection(connectionString);
             PreparedStatement pStmt = con.prepareStatement(sql)) {
    
            ResultSet rs = pStmt.executeQuery();
            while(rs.next()) {
                Usuario usuario = new Usuario(); 
                Estado estado = Estado.valueOf(rs.getString("estado")); 
                
                int idPedido = rs.getInt("ID_ped");
             
                List<Plato> platos = loadPlatosbyPedido(idPedido);
                Pedido pedido = new Pedido(usuario, platos, estado);
            }
        } catch (SQLException e) {
            // Deberías manejar esta excepción de alguna manera
        }
    }


    private List<Plato> loadPlatosbyPedido(int idPedido) throws SQLException {
        List<Plato> platos = new ArrayList<>();
    
        String sql = "SELECT plato.* FROM plato " +
                     "JOIN platos_comprados ON plato.id_Plato = platos_comprados.plato " +
                     "WHERE platos_comprados.pedido = ?";
    
        try (Connection con = DriverManager.getConnection(connectionString);
             PreparedStatement pStmt = con.prepareStatement(sql)) {
    
            pStmt.setInt(1, idPedido);
    
            ResultSet rs = pStmt.executeQuery();
    
            while (rs.next()) {
                Plato plato = platoService.getPlato(rs.getInt("id_Plato"));
                platos.add(plato);
            }
        }
    
        return platos;
    }

    /** @brief Coge el pedido por el id
     *  @return Devuelve el pedido si coincide con el id
     */

    public Pedido getPedido(int id){
        Pedido result = pedidoRepository.findById(id);
        
        return result;
    }

 
    public List<Pedido> getAllPedidos() {
        return pedidoRepository.findAll();
    }

 

    public Pedido addPedido(Pedido pedido){
        return pedidoRepository.save(pedido);
    }


    public Pedido updatePedido(Pedido pedido, int idPedido){
        Pedido updatedPedido = pedidoRepository.findById(idPedido);
    
        if (updatedPedido != null) {
            updatedPedido.setEstado(pedido.getEstado());
            pedidoRepository.save(updatedPedido);
    
            return updatedPedido;
        }
    
        return null;
    }
}