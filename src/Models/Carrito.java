package Models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Carrito implements Serializable {
    private Map<Producto,Integer > productos;

    public Carrito(Map<Producto, Integer> productos) {
        this.productos = productos;
    }

    public Carrito() {
        productos = new HashMap<Producto, Integer>();
    }

    public Map<Producto, Integer> getProductos() {
        return productos;
    }

    public void setProductos(Map<Producto, Integer> productos) {
        this.productos = productos;
    }
    public boolean addProducto(Producto producto, Integer cantidad){
        if (buscaProducto(producto) != null){
            cantidad = cantidad + productos.get(buscaProducto(producto));
            productos.replace(buscaProducto(producto),productos.get(buscaProducto(producto)),cantidad);
        } else {
            productos.put(producto,cantidad);
        }
        return true;
    }
    public Producto buscaProducto(Producto producto){
        Set<Producto> productos = this.productos.keySet();
        for(Producto p : productos){
            if (p.getId() == producto.getId()) return p;
        }
        return null;
    }
    public boolean eliminaProducto(Producto producto){
        if (buscaProducto(producto) != null) productos.remove(buscaProducto(producto));
        return true;
    }
    public boolean eliminaProducto(Producto producto,int unidades){
        if (buscaProducto(producto) != null){
            if (productos.get(buscaProducto(producto)) == unidades) return eliminaProducto(producto);
            productos.replace(producto,productos.get(buscaProducto(producto)),productos.get(buscaProducto(producto)) - unidades);
            return true;
        }
        return false;
    }
    public String pintaCarrito(){
        String salida = "";
        int cont = 1;
        double total = 0;
        Set<Producto> productos = this.productos.keySet();
        if (productos.isEmpty()) salida += "No hay productos";
        else {
            for (Producto p : productos) {
                salida += cont + ". " + p.getNombre() + "  x" + this.productos.get(p) + "   = " + (p.getPrecio() * this.productos.get(p) + " €\n");
                cont++;
                total += p.getPrecio() * this.productos.get(p);
            }
            salida += "Total = " + total + " €";
        }
        return salida;
    }
    public String pintaCarritoEliminar(){
        String salida = "";
        int cont = 1;
        double total = 0;
        Set<Producto> productos = this.productos.keySet();
        if (productos.isEmpty()) salida += "No hay productos";
        else {
            for (Producto p : productos) {
                salida += cont + ". " + p.getNombre() + "  x" + this.productos.get(p) + "   = " + (p.getPrecio() * this.productos.get(p) + " €\n");
                cont++;
                total += p.getPrecio() * this.productos.get(p);
            }
        }
        return salida;
    }
    public boolean carritoVacio(){
        return productos.isEmpty();
    }
}
