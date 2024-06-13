package psc11.tiendaOnline.DataDomain;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Table(name = "Pedido")
@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int ID_ped;
    @ManyToOne
    protected Usuario usuario;
    @Enumerated(EnumType.STRING)
    protected Estado estado;

    public Pedido(Usuario usuario, List<Plato> articulosComprados, Estado estado) {
        this.usuario = usuario;
        this.estado = estado;
    }

    public Pedido(Usuario usuario, Estado estado) {
        this.usuario = usuario;
        this.estado = estado;
    }

    public Pedido() {
        this.usuario = new Usuario();
        this.estado = Estado.Preparacion;
    }

    public long getId() {
        return ID_ped;
    }

    public void setId(Integer id) {
        this.ID_ped = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Pedido [usuario=" + usuario
                + "]";
    }

}
