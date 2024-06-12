package psc11.tiendaOnline.Service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;


import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import psc11.tiendaOnline.DataDomain.Estado;
import psc11.tiendaOnline.DataDomain.Pedido;
import psc11.tiendaOnline.Dao.PedidoRepository;

@RunWith(MockitoJUnitRunner.class)
public class PedidoServiceTest {

    @Rule
    public ContiPerfRule contiPerfRule = new ContiPerfRule();

    @InjectMocks
    private PedidoService pedidoService;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private PlatoService platoService;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetPedido() {
        Pedido expectedPedido = new Pedido();
        expectedPedido.setId(1);
        when(pedidoRepository.findById(1)).thenReturn(expectedPedido);

        Pedido actualPedido = pedidoService.getPedido(1);

        assertNotNull(actualPedido);
        assertEquals(expectedPedido, actualPedido);
    }

    @Test
    public void testGetAllPedidos() {
        Pedido pedido1 = new Pedido();
        Pedido pedido2 = new Pedido();
        List<Pedido> expectedPedidos = Arrays.asList(pedido1, pedido2);
        when(pedidoRepository.findAll()).thenReturn(expectedPedidos);

        List<Pedido> actualPedidos = pedidoService.getAllPedidos();

        assertNotNull(actualPedidos);
        assertEquals(expectedPedidos.size(), actualPedidos.size());
        assertSame(expectedPedidos.get(0), actualPedidos.get(0));
    }

    @Test
    public void testAddPedido() {
        Pedido newPedido = new Pedido();
        when(pedidoRepository.save(newPedido)).thenReturn(newPedido);

        Pedido actualPedido = pedidoService.addPedido(newPedido);

        assertNotNull(actualPedido);
        assertEquals(newPedido, actualPedido);
    }

    @Test
    public void testUpdatePedido() {
        Pedido existingPedido = new Pedido();
        existingPedido.setId(1);
        existingPedido.setEstado(Estado.Reparto);
        Pedido updatedPedido = new Pedido();
        updatedPedido.setEstado(Estado.Entregado);

        when(pedidoRepository.findById(1)).thenReturn(existingPedido);
        when(pedidoRepository.save(existingPedido)).thenReturn(existingPedido);

        Pedido resultPedido = pedidoService.updatePedido(updatedPedido, 1);

        assertNotNull(resultPedido);
        assertEquals(Estado.Entregado, resultPedido.getEstado());
    }

    /*@Test
    @PerfTest(invocations = 100, threads = 20)
    @Required(max = 1200, average = 250)
    public void testLoadPlatosbyPedido() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);

        Plato expectedPlato = new Plato();
        expectedPlato.setId(1);
        when(platoService.getPlato(anyInt())).thenReturn(expectedPlato);

        List<Plato> platos = pedidoService.loadPlatosbyPedido(1);

        assertNotNull(platos);
        assertEquals(1, platos.size());
        assertEquals(expectedPlato, platos.get(0));
    }*/
}

