package psc11.tiendaOnline.Service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import psc11.tiendaOnline.DataDomain.TipoUsuario;
import psc11.tiendaOnline.DataDomain.Usuario;
import psc11.tiendaOnline.Dao.UsuarioRepository;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioServiceTest {

    private UsuarioService usuarioService;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private Usuario usuario;
    @Mock
    private Usuario usuario1;

    @Before
    public void testUsuarioServiceConstructor() {
        usuarioService = new UsuarioService(usuarioRepository);
        usuario = new Usuario("Contrasena", "dni", "Nombre", "Correo", new ArrayList<>(), TipoUsuario.Administrador);
        usuario1 = new Usuario("Contrasena", "dni", "Nombre", "Correo", new ArrayList<>(), TipoUsuario.Cliente);
    }

    @Test
    public void testGetUsuario() {
        Usuario usuario1 = new Usuario();
        when(usuarioRepository.findByDni(any())).thenReturn(usuario1);
        Usuario usuario2 = usuarioService.getUsuario("12345678A");
        assertEquals(usuario1, usuario2);
    }

    @Test
    public void testGetClientes() {
        when(usuarioService.getAllClientes()).thenReturn(Arrays.asList(usuario, usuario1));
        List<Usuario> clientes = usuarioService.getAllClientes();
        when(usuarioService.getAllClientes()).thenReturn(Arrays.asList(usuario));
        usuarioService.getAllClientes();
        assertEquals(TipoUsuario.Cliente, clientes.get(0).getTipoUsuario());
    }

    @Test
    public void testUpdateUsuario() {
        when(usuarioRepository.findByDni("dni")).thenReturn(usuario);
        Usuario UsuarioViejo = new Usuario();
        UsuarioViejo.setDni("dni");
        UsuarioViejo.setNombre("Usuario1");
        usuarioService.updateUsuario(UsuarioViejo, "dni");
        usuarioService.updateUsuario(UsuarioViejo, "notDNI");
        assertEquals("dni", UsuarioViejo.getDni());
    }

    @Test
    public void testAddUsuario() {
        Usuario usuario2 = new Usuario();
        when(usuarioService.addUsuario(usuario2, "dni")).thenReturn(usuario2);
        usuarioService.addUsuario(usuario2, "dni");
        when(usuarioRepository.findByDni("")).thenReturn(usuario2);
        usuarioService.addUsuario(usuario2, "dni");
        assertNotNull(usuario2);
    }

    @Test
    public void testDeleteUsuario() {
        when(usuarioRepository.findByDni("dni")).thenReturn(usuario);
        usuarioService.deleteUsuario("dni");
        when(usuarioRepository.findByDni("notDNI")).thenReturn(null);
        usuarioService.deleteUsuario("notDNI");
        assertNull(usuarioService.getUsuario("notDNI"));
    }
}
