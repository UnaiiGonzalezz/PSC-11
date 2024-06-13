package psc11.tiendaOnline.Service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import psc11.tiendaOnline.Dao.PedidoRepository;
import psc11.tiendaOnline.DataDomain.Estado;
import psc11.tiendaOnline.DataDomain.Pedido;
import psc11.tiendaOnline.DataDomain.TipoUsuario;
import psc11.tiendaOnline.DataDomain.Usuario;


@RunWith(MockitoJUnitRunner.class)
@PerfTest(invocations = 500)
@Required(max = 1200, average = 250)
public class PedidoServiceTest {

    private PedidoService pedidoService;
    @Mock
    private PedidoRepository pedidoRepository;
    @Mock
    private PlatoService platoService;
    @Mock
    private Pedido pedido;
    @Mock
    private Pedido pedido1;
    @Mock
    private Usuario usuario;
    
    @Rule public ContiPerfRule rule = new ContiPerfRule();

    @Before
    public void setUp() {
        pedido = new Pedido(new Usuario(), Estado.Preparacion);
        pedido.setId(1);
        usuario = new Usuario("contr", "dni", "nombre", "correo", null, TipoUsuario.Administrador);
        pedido1 = new Pedido(usuario, Estado.Entregado);
        pedido1.setId(2);
        this.pedidoService = new PedidoService(this.pedidoRepository, this.platoService);
    }

    @Test
    public void testGetPedido() {
        pedido.setId(1);
        when(pedidoRepository.findById(1)).thenReturn(pedido);
        Pedido result = pedidoService.getPedido(1);
        assertEquals(pedido, result);
    }

    @Test
    public void testGetAllPedidos() {
        when(pedidoService.getAllPedidos()).thenReturn(Arrays.asList(pedido, pedido1));
        List<Pedido> pedidos = pedidoService.getAllPedidos();
        assertNotNull(pedidos);
    }

    @Test
    public void testAddPedido() {
        when(pedidoService.addPedido(any(Pedido.class))).thenReturn(pedido);
        Pedido ped = pedidoService.addPedido(pedido);
        assertNotNull(ped);
    }

    @Test
    public void testUpdatePedido() {
        pedido.setEstado(Estado.Reparto);
        when(pedidoService.updatePedido(pedido, 1)).thenReturn(pedido);
        Pedido result = pedidoService.updatePedido(pedido, 1);
        assertEquals(Estado.Reparto, result.getEstado());
    }
}