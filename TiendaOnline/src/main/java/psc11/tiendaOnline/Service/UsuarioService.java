package psc11.tiendaOnline.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import org.springframework.stereotype.Service;

import psc11.tiendaOnline.Dao.UsuarioRepository;
import psc11.tiendaOnline.DataDomain.TipoUsuario;
import psc11.tiendaOnline.DataDomain.Usuario;


@Service
public class UsuarioService {
    private UsuarioRepository usuarioRepository;
    private String connectionString;

    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;

        connectionString = "jdbc:sqlite:DeustoImperioModa.db";
        loadDatos();
    }

    /** @brief Carga todos los usuarios de la base de datos y los guarda en el repositorio de usuarios
     */

    public void loadDatos(){
        String sql = "SELECT * FROM usuario";
		
		try (Connection con = DriverManager.getConnection(connectionString);
		    PreparedStatement pStmt = con.prepareStatement(sql)) {	
			
			ResultSet rs = pStmt.executeQuery();
           
			while(rs.next()) {
                
				Usuario usuario = new Usuario(rs.getString("contrasena"), rs.getString("dni"), rs.getString("nombre"), rs.getString("correo"), null, TipoUsuario.valueOf(rs.getString("tipo_usuario")));
				usuarioRepository.save(usuario);
			}
		} catch (SQLException e) {
		}
    }

    /** @brief Coge el usuario por el dni
     *  @return Devuelve el usuario si coincide con el dni
     */

    public Usuario getUsuario(String id){
        Usuario result = usuarioRepository.findByid(id);
        
        return result;
    }

    /** @brief Coge todos los usuarios
     *  @return Lista de los usarios del repositorio
     */

    public List<Usuario> getAllUsuarios(){
        return usuarioRepository.findAll();
    }

    /** @brief Coge todos los usuarios tipo Cliente
     *  @return Lista de los usuarios tipo Cliente del repositorio
     */

    public List<Usuario> getAllClientes(){
        List<Usuario> usuarios = getAllUsuarios();
        List<Usuario> result = new ArrayList<Usuario>();

        for (Usuario usuario : usuarios) {
            if (usuario.getTipoUsuario().equals(TipoUsuario.Cliente)) {
                result.add(usuario);
            }
        }

        return result.isEmpty() ? null : result;
    }

    /** @brief Actualiza un usuario del dni enviado
     *  @return El usuario actualizado
     */

    public Usuario updateUsuario(Usuario usuario, String id){
        Usuario updatedUsuario = usuarioRepository.findByid(id);

        if (!(updatedUsuario == null)) {

            updatedUsuario.setContrasena(usuario.getContrasena());
            updatedUsuario.setNombre(usuario.getNombre());
            updatedUsuario.setCorreo(usuario.getCorreo());
            updatedUsuario.setPedidos(usuario.getPedidos());
            updatedUsuario.setTipoUsuario(usuario.getTipoUsuario());

            usuarioRepository.save(updatedUsuario);

            return usuario;
        }

        return usuario;
    }

    /** @brief Añade un usuario al repositorio
     *  @return Usuario añadido o si ya existía ese usuario el usuario ya existente
     */

    public Usuario addUsuario(Usuario usuario, String dni){
        Usuario result = usuarioRepository.findByid(usuario.getDni());

        if ((result == null)) {

            usuarioRepository.save(usuario);

            if (!(usuarioRepository.findByid(dni)== null)) {
                return result;
            }
        }

        return result;
    }

    /** @brief Borra un usuario por el dni
     */

    public void deleteUsuario(String dni){
        Usuario result = usuarioRepository.findByid(dni);

        if (!(result == null)) {
            usuarioRepository.delete(result);
        }
    }
}
