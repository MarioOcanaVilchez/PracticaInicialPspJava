package Models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Pedido implements Serializable {
    private long id;
    private LocalDateTime fecha;
    private Carrito carrito;

    public Pedido(long id, LocalDateTime fecha, Carrito carrito) {
        this.id = id;
        this.fecha = fecha;
        this.carrito = carrito;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    public String pintaSalida(){
       return  "-----------------------------------------------\n" +
               "  Pedido: " + id + "\n" +
               "  Fecha: " + fecha + "\n" +
               carrito.pintaCarrito() +
               "\n-----------------------------------------------";
    }
}
