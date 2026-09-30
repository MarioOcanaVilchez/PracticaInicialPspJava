package Models;

import java.io.Serializable;

public abstract class Producto implements Serializable {
    protected long id;
    protected String nombre;
    protected double precio;
    protected int stock;
    protected String categoria;

    public Producto(long id, String nombre, double precio, int stock,String categoria) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void addStock(int nuevoStock){
        stock += nuevoStock;
    }
    public void restaStock(int stockRestar){
        stock -= stockRestar;
    }

    public abstract String pintaProductoComprar();
}
