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
import psc11.tiendaOnline.DataDomain.Estado;
import psc11.tiendaOnline.DataDomain.Pedido;
import psc11.tiendaOnline.DataDomain.Usuario;
import psc11.tiendaOnline.Dao.PedidoRepository;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final PlatoService platoService;
    private final String connectionString;

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository, PlatoService platoService) {
        this.pedidoRepository = pedidoRepository;
        this.platoService = platoService;
        this.connectionString = "jdbc:sqlite:TiendaOnline.db";
        loadDatos();
    }

    /** @brief Carga todos los pedidos de la base de datos y los guarda en el repositorio de pedidos
     */

    public void loadDatos(){
        String sql = "SELECT * FROM pedido";
        try (Connection con = DriverManager.getConnection(connectionString);
             PreparedStatement pStmt = con.prepareStatement(sql)) {
    
            ResultSet rs = pStmt.executeQuery();
            while(rs.next()) {
                Usuario usuario = new Usuario(); // Necesitas obtener el usuario de alguna manera
                Estado estado = Estado.valueOf(rs.getString("estado")); // Asumiendo que "estado" es un enum
                // Obtén el ID del pedido
                int idPedido = rs.getInt("ID_ped");
                List<Plato> platos = loadPlatosbyPedido(idPedido);
                Pedido pedido = new Pedido(usuario, platos, estado);
            }
        } catch (SQLException e) {
            // Deberías manejar esta excepción de alguna manera
        }
    }

    /** @brief Carga todos los artículos de la base de datos por pedido
     */

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

    /** @brief Coge todos los pedidos
     *  @return Lista de los todos los pedidos del repositorio
     */

    public List<Pedido> getAllPedidos() {
        return pedidoRepository.findAll();
    }

    /** @brief Añade un pedido al repositorio
     *  @retval True Pedido correctamente añadido
     *  @retval False Pedido no añadido
     */

    public Pedido addPedido(Pedido pedido){
        return pedidoRepository.save(pedido);
    }

    /** @brief Actualiza un pedido del id enviado
     *  @return El pedido actualizado
     */


    public Pedido updatePedido(Pedido pedido, int idPedido){
        Pedido updatedPedido = pedidoRepository.findById(idPedido);
    
        if (updatedPedido != null) {
            updatedPedido.setEstado(pedido.getEstado());
            pedidoRepository.save(updatedPedido);
    
            return updatedPedido;
        }
    
        return null;
    }
//Método para listar compras de un usuario
    public List<Venta> listarComprasPorUsuario(String usuarioId) {
        return ventas.stream()
                .filter(venta -> venta.getUsuario().getId().equals(usuarioId))
                .collect(Collectors.toList());
    }

}