package psc11.tiendaOnline.DataDomain;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "platosElegidos")
public class PlatosElegidos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    @ManyToOne
    @JoinColumn(name = "Plato")
    private Plato plato;

    @ManyToOne
    @JoinColumn(name = "Pedido")
    private Pedido pedido;

    private int cantidad;

    public PlatosElegidos() {
    }

    public PlatosElegidos(Plato plato, Pedido pedido, int cantidad) {
        this.plato = plato;
        this.pedido = pedido;
        this.cantidad = cantidad;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Plato getPlato() {
        return plato;
    }

    public void setPlato(Plato plato) {
        this.plato = plato;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}


