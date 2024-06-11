package psc11.tiendaOnline.DataDomain;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Plato")
public class Plato {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    protected int id_Plato;
    protected String nombre;
    protected String descripcion;
    protected double precio;
    protected String tamano;
    @Enumerated(EnumType.STRING)
    protected Categoria categoria;


    public Plato(Categoria categoria, String descripcion, String nombre, double precio,
            String tamano) {
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.precio = precio;
        this.tamano = tamano;
    }

    public Plato() {
        this.categoria = null;
        this.descripcion = "";
        this.nombre = "";
        this.precio = 0;
        this.tamano = "";
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId() {
        return id_Plato;
    }

    public void setId(Integer id) {
        this.id_Plato = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getTamano() {
        return tamano;
    }

    public void setTamano(String tamano) {
        this.tamano = tamano;
    }

    @Override
    public String toString() {
        return "Plato [categoria=" + categoria + ", descripcion=" + descripcion
                + ", nombre=" + nombre + ", precio=" + precio + ", tamano=" + tamano + "]";
    }  
    
}
