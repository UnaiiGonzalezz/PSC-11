package psc11.tiendaOnline.DataDomain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Usuario") //prueba
public class Usuario {
    @Id
    protected String dni;
    protected String contrasena;
    protected String nombre;
    protected String correo;

    @OneToMany
    protected List<Pedido> pedidos;
    @Enumerated(EnumType.STRING)
    protected TipoUsuario tipoUsuario;
   

    public Usuario(String contrasena, String dni, String nombre, String correo, List<Pedido> pedidos, TipoUsuario tipoUsuario) {
        this.contrasena = contrasena;
        this.dni = dni;
        this.nombre = nombre;
        this.correo = correo;
        this.pedidos = new ArrayList<Pedido>();
        this.tipoUsuario = tipoUsuario;
    }

    public Usuario() {
        this.contrasena = "";
        this.dni = "";
        this.nombre = "";
        this.correo = "";
        this.pedidos = new ArrayList<Pedido>();
        this.tipoUsuario = null;
    }

    public String getContrasena() {
        return contrasena;
    }
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    public String getDni() {
        return dni;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public List<Pedido> getPedidos() {
        return pedidos;
    }
    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    @Override
    public String toString() {
        return "Usuario [contrasena=" + contrasena + ", dni=" + dni + ", nombre=" + nombre + ", correo=" + correo
                + ", pedidos=" + pedidos + ", tipoUsuario=" + tipoUsuario + "]";
    }
    
}
