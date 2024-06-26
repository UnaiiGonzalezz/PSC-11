package psc11.tiendaOnline.DataDomain;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PlatosElegidosTest {

    private PlatosElegidos platosElegidos;

    @Before
    public void setUp() {
        platosElegidos = new PlatosElegidos();
    }

 

    // Repite el mismo proceso para probar los otros getters y setters...

    @Test
    public void testConstructor() {
        Plato plato = new Plato();
        Pedido pedido = new Pedido();
        Integer cantidad = 2;
        
        
        PlatosElegidos nuevoPlatosElegidos = new PlatosElegidos(plato, pedido, cantidad);
        assertNotNull(nuevoPlatosElegidos);
    }


    @Test
    public void testGetArticulo(){
        platosElegidos.setPlato(new Plato());
        assertNotNull(platosElegidos.getPlato());
    }

    @Test
    public void testGetPedido(){
        platosElegidos.setPedido(new Pedido());
        assertNotNull(platosElegidos.getPedido());
    }

    @Test
    public void testGetCantidad(){
        platosElegidos.setCantidad(2);
        assertEquals(2, platosElegidos.getCantidad());
    }
    
    @Test
    public void testGetId(){
        platosElegidos.setId(1);
        assertEquals(1, platosElegidos.getId());
    }

    @Test
    public void testToString(){
        assertNotNull(platosElegidos.toString());
    }
}