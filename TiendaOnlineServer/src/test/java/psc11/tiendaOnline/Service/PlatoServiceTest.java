package psc11.tiendaOnline.Service;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
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

import psc11.tiendaOnline.DataDomain.Plato;
import psc11.tiendaOnline.DataDomain.Categoria;
import psc11.tiendaOnline.Dao.PlatoRepository;

@RunWith(MockitoJUnitRunner.class)
@PerfTest(invocations = 500)
@Required(max = 1200, average = 250)
public class PlatoServiceTest {

    private PlatoService platoService;
    @Mock
    private PlatoRepository platoRepository;
    @Mock
    private Plato plato;
    @Mock
    private Plato plato1;
    
    @Rule public ContiPerfRule rule = new ContiPerfRule();

    @Before
    public void setUp() {
        plato = new Plato(Categoria.Entrante, "Descripcion", "Nombre", 5, "Normal");
        plato.setId(1);
        plato1 = new Plato(Categoria.Batido, "Descripcion", "Nombre", 3, "Grande");
        plato.setId(2);
        this.platoService = new PlatoService(this.platoRepository);
    }

    @Test
    public void testGetPlato() {
        when(platoRepository.findById(1)).thenReturn(plato);
        Plato plato1 = platoService.getPlato(1);
        assertNotNull(plato1);
    }

    @Test
    public void testGetByCategoria() {
        when(platoService.getAllPlatos()).thenReturn(Arrays.asList(plato, plato1));
        List<Plato> platos = platoService.getByCategoria(Categoria.Entrante);
        when(platoService.getAllPlatos()).thenReturn(Arrays.asList(plato, plato1));
        platoService.getByCategoria(Categoria.Batido);
        assertEquals(Categoria.Entrante, platos.get(0).getCategoria());
    }

    @Test
    public void testGetAllPlatos() {
        when(platoService.getAllPlatos()).thenReturn(Arrays.asList(plato));
        List<Plato> platos = platoService.getAllPlatos();
        assertNotNull(platos);
    }

    @Test
    public void testAddPlato() {
        Plato plato = new Plato();
        when(platoService.addPlato(any(Plato.class))).thenReturn(plato);
        Plato plato2 = platoService.addPlato(plato);
        assertNotNull(plato2);
    }

    @Test
    public void testUpdatePlato() {
        when(platoRepository.findById(1)).thenReturn(plato);
        Plato platoViejo = new Plato();
        platoViejo.setId(1);
        platoViejo.setNombre("Patatas");
        platoService.updatePlato(platoViejo, 1);
        platoService.updatePlato(platoViejo, 2);
        assertEquals("Patatas", platoViejo.getNombre());
    }

    @Test
    public void testDeletePlato() {
        when(platoRepository.findById(1)).thenReturn(plato);
        platoService.deletePlato(1);
        when(platoRepository.findById(1)).thenReturn(null);
        platoService.deletePlato(1);
        assertNull(platoService.getPlato(1));
    }
}
