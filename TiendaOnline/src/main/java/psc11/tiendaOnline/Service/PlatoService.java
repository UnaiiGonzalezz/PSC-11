package psc11.tiendaOnline.Service;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import psc11.tiendaOnline.DataDomain.Plato;
import psc11.tiendaOnline.DataDomain.Categoria;
import psc11.tiendaOnline.PlatoRepository;

@Service
public class PlatoService {

private PlatoRepository platoRepository;
private String connectionString;

    public PlatoService(PlatoRepository platoRepository1){
        this.platoRepository = paltoRepository1;
        connectionString = "jdbc:sqlite:TiendaOnline.db";
        loadDatos();
    }
    
    /** @brief Carga todos los artículos de la base de datos y los guarda en el repositorio de artículos
     */

    public void loadDatos(){
        String sql = "SELECT * FROM plato";
		
		try (Connection con = DriverManager.getConnection(connectionString);
		    PreparedStatement pStmt = con.prepareStatement(sql)) {	
			
			ResultSet rs = pStmt.executeQuery();
           
			while(rs.next()) {
				Plato plato = new Plato(Categoria.valueOf(rs.getString("categoria")), rs.getString("descripcion"), rs.getString("nombre"), rs.getDouble("precio"), rs.getString("tamano"));
                plato.setId(rs.getInt("id_Plato"));
				platoRepository.save(plato);
			}
		} catch (SQLException e) {
		}
    }

    /** @brief Coge el artículo por el id
     *  @return Devuelve el artículo si coincide con el id
     */

    public Plato getPlato(int id){
        Plato result = platoRepository.findById(id);
        
        return result;
    }

    /** @brief Coge todos los artículos
     *  @return Lista de los artículos del repositorio
     */

    public List<Plato> getAllPlatos(){
        return platoRepository.findAll();
    }
    
    /** @brief Coge todos los artículos según la categoría enviada
     *  @return Lista de los artículos que coinciden con la categoría enviada
     */

    public List<Plato> getByCategoria(Categoria categoria){
        
        List<Plato> platos = getAllPlatos();
        List<Plato> result = new ArrayList<Plato>();

        for (Plato plato : platos) {
            if (plato.getCategoria().equals(categoria)) {
                result.add(plato);
            }
        }

        return result.isEmpty() ? null : result;
    }


    

    public Plato addPlato(Plato plato){
        return platoRepository.save(plato);
    }

    

    public Plato updatePlato(Plato plato, int id){
    Plato result = platoRepository.findById(id);
        if (!(result == null)) {
            
            Plato updatedPlato = result;

            updatedPlato.setDescripcion(plato.getDescripcion());
            updatedPlato.setCategoria(plato.getCategoria());
            updatedPlato.setNombre(plato.getNombre());
            updatedPlato.setPrecio(plato.getPrecio());
            updatedPlato.setTamano(plato.getTamano());
            
            platoRepository.save(updatedPlato);

            return result;
        }
        return result;
    }

    

    public void deletePlato(int id){
        Plato result = platoRepository.findById(id);

        if (!(result == null)) {
            platoRepository.delete(result);
        }
    }
}
