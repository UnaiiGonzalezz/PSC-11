package psc11.tiendaOnline.DataDomain;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
public class PlatoTest {

    private Plato plato;

    @Rule public ContiPerfRule rule = new ContiPerfRule();

    @Before
    public void setUp() {
       plato = new Plato();
    }

    @Test
    @PerfTest(invocations = 1000, threads = 20)
    @Required(max = 120, average = 30)
    public void testConstructor() {
        Categoria categoria = Categoria.Entrante;
        String descripcion = "Descripción";
        String nombre = "Nombre";
        double precio = 5;
        String tamano = "Normal";
        
        Plato nuevoPlato = new Plato(categoria, descripcion, nombre, precio, tamano);
        assertNotNull(nuevoPlato);
    }


    @Test
    public void testGetCategoria(){
        plato.setCategoria(Categoria.Entrante);
        assertEquals(Categoria.Entrante, plato.getCategoria());
    }

    @Test
    public void testGetNombre(){
        plato.setNombre("Nombre");
        assertEquals("Nombre", plato.getNombre());
    }

    @Test
    public void testGetDescripcion(){
        plato.setDescripcion("Descripcion");
        assertEquals("Descripcion", plato.getDescripcion());
    }

    @Test
    public void testGetPrecio(){
        plato.setPrecio(100.0);
        assertEquals(100.0, plato.getPrecio(), 0);
    }

    @Test
    public void testGetTamano(){
        plato.setTamano("Normal");
        assertEquals("Normal", plato.getTamano());
    }

    @Test
    public void testGetId(){
        plato.setId(1);
        assertEquals(1, plato.getId());
    }
    
    @Test
    public void testToString(){
        assertNotNull(plato.toString());
    }
}