package psc11.tiendaOnline.DataDomain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.util.ArrayList;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
@PerfTest(invocations = 5)
@Required(max = 1200, average = 250)
public class VentaTest {

    private Venta venta;
    private Usuario cliente;
    private Pedido pedido;

    @Rule
    public ContiPerfRule rule = new ContiPerfRule();

    @Before
    public void setUp() {
        cliente = new Usuario();
        pedido = new Pedido();
        venta = new Venta(cliente, pedido, Estado.Preparacion);
    }

    @Test
    @PerfTest(invocations = 1000, threads = 20)
    @Required(max = 120, average = 30)
    public void testConstructor() {
        Usuario cliente = new Usuario();
        Pedido pedido = new Pedido();
        Estado estado = Estado.Preparacion;
        
        Venta nuevaVenta = new Venta(cliente, pedido, estado);
        assertNotNull(nuevaVenta);
        assertSame(cliente, nuevaVenta.getCliente());
        assertSame(pedido, nuevaVenta.getPedido());
        assertSame(estado, nuevaVenta.getEstado());
    }

    @Test
    public void testDefaultConstructor() {
        Venta nuevaVenta = new Venta();
        assertNotNull(nuevaVenta);
        assertNotNull(nuevaVenta.getCliente());
        assertNotNull(nuevaVenta.getPedido());
        assertEquals(Estado.Preparacion, nuevaVenta.getEstado());
    }

    @Test
    public void testGetId() {
        venta.setId(1);
        assertEquals(1, venta.getId());
    }

    @Test
    public void testGetCliente() {
        assertSame(cliente, venta.getCliente());
    }

    @Test
    public void testSetCliente() {
        Usuario nuevoCliente = new Usuario();
        venta.setCliente(nuevoCliente);
        assertSame(nuevoCliente, venta.getCliente());
    }

    @Test
    public void testGetPedido() {
        assertSame(pedido, venta.getPedido());
    }

    @Test
    public void testSetPedido() {
        Pedido nuevoPedido = new Pedido();
        venta.setPedido(nuevoPedido);
        assertSame(nuevoPedido, venta.getPedido());
    }

    @Test
    public void testGetEstado() {
        assertEquals(Estado.Preparacion, venta.getEstado());
    }

    @Test
    public void testSetEstado() {
        venta.setEstado(Estado.Entregado);
        assertEquals(Estado.Entregado, venta.getEstado());
    }

    @Test
    public void testToString() {
        assertNotNull(venta.toString());
    }
}
