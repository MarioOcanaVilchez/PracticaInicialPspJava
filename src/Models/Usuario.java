package Models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Usuario implements Serializable {
    private long id;
    private String nombre;
    private String email;
    private String clave;
    private boolean estado;
    private Carrito carrito;
    private boolean admin;
    private ArrayList<Pedido> pedidos;

    public Usuario(long id,String clave, String email, String nombre) {
        this.clave = clave;
        this.email = email;
        this.nombre = nombre;
        this.id = id;
        estado = true;
        carrito = new Carrito();
        admin = false;
        pedidos = new ArrayList<>();

    }

    public Usuario(long id, String nombre, String email, String clave, boolean estado, Carrito carrito, boolean admin, ArrayList<Pedido> pedidos) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.clave = clave;
        this.estado = estado;
        this.carrito = carrito;
        this.admin = admin;
        this.pedidos = pedidos;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public ArrayList<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(ArrayList<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
    public boolean addProductoCarrito(Producto producto, int stock){
        return carrito.addProducto(producto,stock);
    }
    public boolean eliminaProducto(Producto producto){
        return carrito.eliminaProducto(producto);
    }
    public boolean eliminaProducto(Producto producto,int stock){
        return carrito.eliminaProducto(producto,stock);
    }
    public boolean addPedido(long id){
        pedidos.add(new Pedido(id, LocalDateTime.now(),carrito));
        carrito = new Carrito();
        return true;
    }


    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", clave='" + clave + '\'' +
                ", estado=" + estado +
                ", carrito=" + carrito +
                ", admin=" + admin +
                ", pedidos=" + pedidos +
                '}';
    }
    public String pintaUsuarioActivos(){
        return "User: " + id + " con email: " + email;
    }
}
