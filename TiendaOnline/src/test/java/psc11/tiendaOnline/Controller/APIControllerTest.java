package psc11.tiendaOnline.Controller;


import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import psc11.tiendaOnline.DataDomain.Plato;
import psc11.tiendaOnline.DataDomain.Estado;
import psc11.tiendaOnline.DataDomain.Pedido;
import psc11.tiendaOnline.DataDomain.TipoUsuario;
import psc11.tiendaOnline.DataDomain.Usuario;
import psc11.tiendaOnline.Service.PedidoService;
import psc11.tiendaOnline.Service.PlatoService;
import psc11.tiendaOnline.Service.UsuarioService;


@RunWith(MockitoJUnitRunner.class)
public class APIControllerTest {

    @Mock
    private PlatoService platoService;
    @Mock
    private UsuarioService usuarioService;
    @Mock
    private PedidoService pedidoService;
    @Mock
    private Usuario usuario;

    @InjectMocks
    private APIController apiController;

    @BeforeEach
    public void setUp() {
        apiController = new APIController(platoService, usuarioService, pedidoService);
        usuario = new Usuario("contrasena", "dni", "nombre", "correo", new ArrayList<>(), TipoUsuario.Cliente);
    }

    @Test
    public void testGetPlatos() {
        apiController.getPlatos();
        verify(platoService).getAllPlatos();
    }

    @Test
    public void testGetUsuarios() {
        apiController.getUsuarios();
        verify(usuarioService).getAllUsuarios();
    }

     
    @Test
    public void testValidarContrasena() {
        Usuario usuario = new Usuario();
        usuario.setContrasena("contrasena");

        when(usuarioService.getUsuario("dni")).thenReturn(usuario);
        

        String resultado = apiController.validarContrasena(null, null);
        assertEquals("false", resultado);
        resultado = apiController.validarContrasena("dni", "a");
        assertEquals("false", resultado);
        resultado = apiController.validarContrasena("dni", "contrasena");
        assertEquals("Administrador", resultado);
        when(usuarioService.getAllClientes()).thenReturn(Arrays.asList(usuario));
        resultado = apiController.validarContrasena("dni", "contrasena");
        assertEquals("Cliente", resultado);

    }

    @Test
    public void testGetClientes() {
        apiController.getClientes();
        verify(usuarioService).getAllClientes();
    }

    @Test
    public void testGetPedidos() {
        apiController.getPedidos();
        verify(pedidoService).getAllPedidos();
    }

    @Test
    public void testValidarCliente() {
        Usuario usuario = new Usuario();
        usuario.setDni("dni"); 
    
        when(usuarioService.getAllClientes()).thenReturn(Arrays.asList(usuario));
        when(usuarioService.getUsuario(anyString())).thenReturn(usuario);

        boolean resultado = apiController.validarCliente("dni");

        verify(usuarioService).getAllClientes();
        verify(usuarioService).getUsuario("dni");

        assertTrue(resultado);
    }

    @Test
    public void validarUsuarioTest() {
        when(usuarioService.getUsuario("123")).thenReturn(new Usuario());
        assertTrue(apiController.validarUsuario("123"));
        assertFalse(apiController.validarUsuario("456"));
    }

    @Test
    public void validarPlatoTest() {
        when(platoService.getPlato(1)).thenReturn(new Plato());
        assertTrue(apiController.validarPlato(1));
        assertFalse(apiController.validarPlato(2));
    }

    @Test
    public void updatePlatoTest() {
        when(platoService.getPlato(1)).thenReturn(new Plato());
        assertTrue(apiController.updatePlato(1, "nombre", "desc", "Entrante", 100.0, "Normal"));
        assertFalse(apiController.updatePlato(2, "nombre", "desc", "Entrante", 100.0, "Normal"));
    }

    @Test
    public void updateClienteTest() {
        when(usuarioService.getAllClientes()).thenReturn(Arrays.asList(usuario));
        when(usuarioService.getUsuario("dni")).thenReturn(usuario);
        assertTrue(apiController.updateCliente("dni", "contrasena", "nombre", "correo", new ArrayList<>()));
        assertFalse(apiController.updateCliente("notDNI", "contrasena", "nombre", "correo", new ArrayList<>())); 
    }

    @Test
    public void testDeletePlato() {
        Integer id = 1;
        Plato plato = new Plato();
        when(platoService.getPlato(id)).thenReturn(plato);
        assertTrue(apiController.deletePlato(id));
        apiController.deletePlato(2);
    }

    @Test
    public void testDeleteCliente() {
        when(usuarioService.getAllClientes()).thenReturn(Arrays.asList(usuario));
        when(usuarioService.getUsuario("dni")).thenReturn(usuario);
        assertTrue(apiController.deleteCliente("dni"));
        apiController.deleteCliente("notDNI");
    }

    @Test
    public void testCrearUsuario() {
        when(usuarioService.getUsuario("dni")).thenReturn(null);
        when(usuarioService.getUsuario("notDNI")).thenReturn(usuario);
        assertTrue(apiController.crearUsuario("dni", "cont", "nom", "cor", new ArrayList<>(), "Administrador"));
        assertFalse(apiController.crearUsuario("notDNI", "cont", "nom", "cor", new ArrayList<>(), "Administrador"));
    }

    @Test
    public void testCrearPlato() {
        assertTrue(apiController.crearPlato("nom", "desc", "Entrante", 5.0, "Normal"));
    }

    @Test
    public void testCrearPedido() {
        when(usuarioService.getUsuario("dni")).thenReturn(usuario);
    
        assertTrue(apiController.crearPedido("dni"));

        when(usuarioService.getUsuario("1")).thenReturn(null);

        assertFalse(apiController.crearPedido("1"));
    }   

    @Test
    public void testUpdatePedidoEstado() {
        Pedido pedido = new Pedido();
        pedido.setId(1);
        usuario = new Usuario();
        usuario.setDni("12345678");
        pedido.setUsuario(usuario);
        when(pedidoService.getPedido(1)).thenReturn(pedido);
        assertNotNull(usuario.getDni());
        boolean result = apiController.updatePedidoEstado("12345678", "Entregado", 1);
        assertTrue(result);
        verify(pedidoService, times(1)).updatePedido(pedido, 1);
        assertEquals(Estado.Entregado, pedido.getEstado());

        usuario.setDni("1");
        pedido.setUsuario(usuario);
        assertFalse(apiController.updatePedidoEstado("12345678", "Entregado", 1));

        when(pedidoService.getPedido(2)).thenReturn(null);
        boolean result1 = apiController.updatePedidoEstado("12345678", "Entregado", 2);
        assertEquals(false, result1);
    }
}
