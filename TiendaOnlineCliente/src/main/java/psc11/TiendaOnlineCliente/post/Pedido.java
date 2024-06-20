package psc11.TiendaOnlineCliente.post;



public class Pedido {

    protected int id;
    protected Usuario usuario;
    protected Estado estado;

    public enum Estado {
        Preparacion,
        Reparto,
        Cancelado,
        Entregado
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    
}
