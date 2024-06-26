package psc11.tiendaOnline.Controller;



import org.springframework.web.bind.annotation.RestController;


import psc11.tiendaOnline.DataDomain.Plato;
import psc11.tiendaOnline.DataDomain.Categoria;
import psc11.tiendaOnline.DataDomain.Estado;
import psc11.tiendaOnline.DataDomain.Pedido;
import psc11.tiendaOnline.DataDomain.TipoUsuario;
import psc11.tiendaOnline.DataDomain.Usuario;
import psc11.tiendaOnline.Service.PedidoService;
import psc11.tiendaOnline.Service.PlatoService;
import psc11.tiendaOnline.Service.UsuarioService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
public class APIController {
    
    private PlatoService platoService;
    private UsuarioService usuarioService;
    private PedidoService pedidoService;

    public APIController(PlatoService platoService, UsuarioService usuarioService, PedidoService pedidoService) {
        this.platoService = platoService;
        this.usuarioService = usuarioService;
        this.pedidoService = pedidoService;   
    }


    @RequestMapping("plato/all")
    public List<Plato> getPlatos() {
        return platoService.getAllPlatos();
    }
    
    
    @RequestMapping("usuario/all")
    public List<Usuario> getUsuarios() {
        return usuarioService.getAllUsuarios();
    }



    @RequestMapping("usuario/iniciarSesion")
    public String validarContrasena(@RequestParam (name = "dni") String dni,
                                    @RequestParam (name = "contr") String contrasena) {
        if (validarUsuario(dni)){
            Usuario usuario = usuarioService.getUsuario(dni);
            if (usuario.getContrasena().equals(contrasena)){
                if(validarCliente(dni)){
                    return "Cliente";
                }
                else {
                    return "Administrador";
                }
            }
            return "false";
        }
        return "false";
    }

    /** @brief Devuelve todos los usuarios que son cliente de la base de datos
     *  @return Lista de los usuarios que son cliente de la bd
     */

    @RequestMapping("cliente/all")
    public List<Usuario> getClientes() {
        return usuarioService.getAllClientes();
    }

    /** @brief Valida que el dni de la función es un dni de algún usuario tipo cliente de la base de datos
     *  @retval True El dni pertenece a un usuario tipo cliente
     *  @retval False El dni no pertenece a ningún usuario tipo cliente
     */

    @RequestMapping("cliente/validar")
    public boolean validarCliente(@RequestParam (name = "dni") String dni) {
        return usuarioService.getAllClientes().contains(usuarioService.getUsuario(dni));
    }

    /** @brief Valida que el dni de la función es un dni de algún usuario de la base de datos
     *  @retval True El dni pertenece a un usuario
     *  @retval False El dni no es de ningún usuario de la base de datos
     */

    @RequestMapping("usuario/validar")
    public boolean validarUsuario(@RequestParam (name = "dni") String dni) {
        return usuarioService.getUsuario(dni) != null;
    }

    
    
    @RequestMapping("plato/validar")
    public boolean validarPlato(@RequestParam (name = "id") Integer id) {
        return platoService.getPlato(id) != null;
    }

    

    @RequestMapping("plato/update")
    public boolean updatePlato(@RequestParam (name = "id") Integer id, 
                                  @RequestParam (name = "nombre") String nombre, 
                                  @RequestParam (name = "desc")String descripcion, 
                                  @RequestParam (name = "categoria")String categoria , 
                                  @RequestParam (name = "precio") Double precio, 
                                  @RequestParam (name = "tam") String tamano) {
        if (validarPlato(id)) {
            platoService.updatePlato(new Plato(Categoria.valueOf(categoria), descripcion, nombre, precio, tamano), id);
            return true;
        } else {
            return false;
        }
    }

    /** @brief Actualiza un usuario tipo cliente recibiendo todas las variables de un usuario
     *  @retval True El usuario tipo cliente se ha actualizado
     *  @retval False El usuario tipo cliente no se ha actualizado, ya que no existe ese dni de ningún cliente
     */

    @RequestMapping("cliente/update")
    public boolean updateCliente(@RequestParam (name = "dni") String dni, 
                                 @RequestParam (name = "contr") String contrasena, 
                                 @RequestParam (name = "nombre")String nombre, 
                                 @RequestParam (name = "correo") String correo , 
                                 @RequestParam (name = "pedidos") ArrayList<String> pedidos) {
        if (validarCliente(dni)) {
            usuarioService.updateUsuario(new Usuario(contrasena, dni, nombre, correo, null, TipoUsuario.Cliente), dni);
            return true;
        } else {
            return false;
        }
    }

    
    @RequestMapping("plato/borrar")
    public boolean deletePlato(@RequestParam (name = "id") Integer id) {
        if (validarPlato(id)) {
            platoService.deletePlato(id);
            return true;
        } else {
            return false;
        }
    }
    
    /** @brief Borra un usuario tipo cliente por su dni
     *  @retval True El usuario tipo cliente se ha borrado
     *  @retval False El usuario tipo cliente no se ha borrado, ya que no existe ese dni de ningún cliente
     */

    @RequestMapping("cliente/borrar")
    public boolean deleteCliente(@RequestParam (name = "dni") String dni) {
        if (validarCliente(dni)) {
            usuarioService.deleteUsuario(dni);
            return true;
        } else {
            return false;
        }
    }

  

    @RequestMapping("plato/crear")
    public boolean crearPlato(@RequestParam (name = "nombre") String nombre, 
                                 @RequestParam (name = "desc")String descripcion, 
                                 @RequestParam (name = "categoria")String categoria , 
                                 @RequestParam (name = "precio") Double precio, 
                                 @RequestParam (name = "tam") String tamano) {
        platoService.addPlato(new Plato(Categoria.valueOf(categoria), descripcion, nombre, precio, tamano));
        return true;
    }

    /** @brief Crea un usuario recibiendo todas las variables de un usuario
     *  @return El usuario ha sido añadido correctamente a la base de datos
     */

    @RequestMapping("usuario/crear")
    public boolean crearUsuario(@RequestParam (name = "dni") String dni, 
                                @RequestParam (name = "contr") String contrasena, 
                                @RequestParam (name = "nombre")String nombre, 
                                @RequestParam (name = "correo") String correo , 
                                @RequestParam (name = "pedidos") ArrayList<String> pedidos,
                                @RequestParam (name = "tipoU") String tipoUsuario) {
        if (!validarUsuario(dni)) {
            usuarioService.addUsuario(new Usuario(contrasena, dni, nombre, correo, null, TipoUsuario.valueOf(tipoUsuario)), dni);
            return true;
        } else {
            return false;
        }
    }

    /** @brief Devuelve todos los pedidos de la base de datos
     *  @return Lista de los pedidos de la bd
     */

    @RequestMapping("pedido/all")
    public List<Pedido> getPedidos() {
        return pedidoService.getAllPedidos();
    }

    /** @brief Crea un pedido recibiendo todas las variables de un pedido
     *  @return El pedido ha sido añadido correctamente a la base de datos
     */

    @RequestMapping("pedido/crear")
    public boolean crearPedido(@RequestParam (name = "dni") String dni) {

    Usuario u = usuarioService.getUsuario(dni);
    if (u != null) {
        Pedido pedido = new Pedido();
        pedido.setUsuario(u);
        pedido.setEstado(Estado.Preparacion);
        pedidoService.addPedido(pedido);
        return true;
    } else {
        return false;
    }
    }

    /** @brief Actualiza el estado de un pedido recibiendo todas las variables de un pedido
     *  @retval True El estado del pedido se ha actualizado
     *  @retval False El estado del pedido no se ha actualizado, ya que no existe ese pedido o el pedido no pertenece a ese usuario
     */
    
    @RequestMapping("pedido/update")
    public boolean updatePedidoEstado(@RequestParam(name = "dni") String dni, 
                                      @RequestParam(name = "estado") String nuevoEstado,
                                      @RequestParam(name = "id") Integer id) {
        Pedido pedido = pedidoService.getPedido(id);
        if (pedido != null && pedido.getUsuario().getDni().equals(dni)) {
            pedido.setEstado(Estado.valueOf(nuevoEstado));
            pedidoService.updatePedido(pedido, id);
            return true;
        } else {
            return false;
        }
    }         

    
}
