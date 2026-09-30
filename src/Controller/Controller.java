package Controller;

import Models.*;
import Persistence.Persistence;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class Controller {
    private Usuario uTemp;
    private ArrayList<Usuario> usuarios;
    private ArrayList<Producto> productos;

    public Controller() {
        uTemp = null;
        usuarios = Persistence.leeUsuarios();
        productos = Persistence.leeProductos();
    }

    public Usuario getuTemp() {
        return uTemp;
    }

    public void setuTemp(Usuario uTemp) {
        this.uTemp = uTemp;
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public Usuario buscaUsuario(String email){
        for (Usuario u : usuarios){
            if (u.getEmail().equals(email)){
                if (u.isEstado()) return u;
                else return null;
            }
        }
        return null;
    }
    public Usuario buscaUsuarioBorrado(String email, String clave){
        for (Usuario u : usuarios){
            if (u.getEmail().equals(email) && u.getClave().equals(clave)){
                if (!u.isEstado()) return u;
                else return null;
            }
        }
        return null;
    }
    public boolean recuperaUser(String email,String clave){
        Usuario usuario = buscaUsuarioBorrado(email,clave);
        usuario.setEstado(true);
        if(Persistence.guardaUsuario(usuario)){
            uTemp = usuario;
            return true;
        }
        return false;
    }
    public Usuario buscaUsuario(long id){
        for (Usuario u : usuarios){
            if (u.getId() == id) return u;
        }
        return null;
    }
    public boolean login(String email,String clave){
        Usuario userEmail = buscaUsuario(email);
        if (userEmail != null && userEmail.getClave().equals(clave)){
            uTemp = userEmail;
            return true;
        }
        return false;
    }
    public boolean registraUsuario(String email,String clave,String nombre){
        long id = generaIdUsuario();
        if (buscaUsuario(email) != null) return false;
        Usuario usuario = new Usuario(id,clave,email,nombre);
        if (Persistence.guardaUsuario(usuario)) {
            uTemp = usuario;
            usuarios.add(usuario);
            return true;
        }
        return false;
    }
    public Producto buscaProducto(String nombre){
        for (Producto p : productos){
            if (p.getNombre().equalsIgnoreCase(nombre)) return p;
        }
        return null;
    }
    public Producto buscaProducto(long id){
        for (Producto p : productos){
            if (p.getId() == id) return p;
        }
        return null;
    }
    public long generaIdUsuario(){
        long id;
        do{
            id = (long) (Math.random() * (Long.MAX_VALUE - 1) + 1);
        }while(buscaUsuario(id) != null);
        return id;
    }
    public long generaIdProducto(){
        long id;
        do{
            id = (long) (Math.random() * (Long.MAX_VALUE - 1) + 1);
        }while(buscaProducto(id) != null);
        return id;
    }
    public boolean addProducto(String nombre, double precio, int stock,String categoria, double peso, double precioEnvio){
        long id = generaIdProducto();
        ProductoFisico productoFisico = new ProductoFisico(id,nombre,precio,stock,categoria,peso,precioEnvio);
        if (Persistence.guardaProducto(productoFisico)){
            productos.add(productoFisico);
            return true;
        }
        return false;
    }
    public boolean addProducto(String nombre, double precio, int stock,String categoria, String tamanio, String licencia){
        long id = generaIdProducto();
        ProductoDigital productoDigital = new ProductoDigital(id,nombre,precio,stock,categoria,tamanio,licencia);
        if (Persistence.guardaProducto(productoDigital)){
            productos.add(productoDigital);
            return true;
        }
        return false;
    }
    public boolean eliminarProducto(Producto producto){
        if (Persistence.eliminarProducto(producto)){
            productos.remove(producto);
            return true;
        }
        return false;
    }
    public boolean addStock(Producto producto,int nuevoStock){
        int stock = producto.getStock();
        producto.addStock(nuevoStock);
        if (Persistence.guardaProducto(producto)) return true;
        producto.setStock(stock);
        return false;
    }
    public boolean restaStock(Producto producto,int stockRestar){
        int stock = producto.getStock();
        producto.restaStock(stockRestar);
        if (Persistence.guardaProducto(producto)) return true;
        producto.setStock(stock);
        return false;
    }
    public String pintaUsuariosActivos(){
        String respuesta = "";
        int cont = 0;
        for(Usuario u: usuarios){
            if (u.isEstado()){
                respuesta += u.pintaUsuarioActivos() + "\n";
                cont++;
            }
        }
        return "  Hay un total de " + cont + " Usuarios activos\n" + respuesta;
    }
    public ArrayList<Producto> getProductosMenorMayor(){
        if (productos == null || productos.isEmpty()) return null;
        ArrayList<Producto> productosSinOrdenar = new ArrayList<>();
        productosSinOrdenar.addAll(this.productos);
        Producto productoActual;
        ArrayList<Producto> productosOrdenados = new ArrayList<>();
        int numVeces = productosSinOrdenar.size();
        for (int i = 0; i < numVeces; i++) {
            productoActual = null;
            for(Producto p : productosSinOrdenar){
                if (productoActual == null) productoActual = p;
                else if (p.getPrecio() < productoActual.getPrecio()) productoActual = p;
            }
            productosOrdenados.add(productoActual);
            productosSinOrdenar.remove(productoActual);
        }
        return productosOrdenados;
    }
    public ArrayList<Producto> getProductosMayorMenor(){
        if (productos == null || productos.isEmpty()) return null;
        ArrayList<Producto> productosSinOrdenar = new ArrayList<>();
        productosSinOrdenar.addAll(this.productos);
        Producto productoActual;
        ArrayList<Producto> productosOrdenados = new ArrayList<>();
        int numVeces = productosSinOrdenar.size();
        for (int i = 0; i < numVeces; i++) {
            productoActual = null;
            for(Producto p : productosSinOrdenar){
                if (productoActual == null) productoActual = p;
                else if (p.getPrecio() > productoActual.getPrecio()) productoActual = p;
            }
            productosOrdenados.add(productoActual);
            productosSinOrdenar.remove(productoActual);
        }
        return productosOrdenados;
    }
    public boolean addCompra(Producto p,int unidades){
        p.restaStock(unidades);
        if (uTemp.addProductoCarrito(p,unidades) && Persistence.guardaProducto(p) && Persistence.guardaUsuario(uTemp)){
            productos = Persistence.leeProductos();
            return true;
        }
        uTemp.eliminaProducto(p,unidades);
        p.addStock(unidades);
        return false;
    }
    public boolean eliminarCuenta(){
        uTemp.setEstado(false);
        if (Persistence.guardaUsuario(uTemp)){
            uTemp.setId(-1);
            usuarios = Persistence.leeUsuarios();
            return true;
        }
        else {
            uTemp.setEstado(true);
            return false;
        }
    }
    public boolean vaciarCarrito(){
        Map<Producto, Integer> carrito = uTemp.getCarrito().getProductos();
        Set<Producto> productosCarrito = carrito.keySet();
        for(Producto p : productosCarrito){
            if (!eliminaProductoCarrito(p,carrito.get(p))) return false;
        }
        return true;
    }
    public String pintaCarrito(){
        return uTemp.getCarrito().pintaCarrito();
    }
    public String pintaCarritoEliminar(){
        return uTemp.getCarrito().pintaCarritoEliminar();
    }
    public ArrayList<Producto> buscaProductoPorNombre(String nombre){
        ArrayList<Producto> productosConNombre = new ArrayList<>();
        for (Producto p : productos){
            if (p.getNombre().toLowerCase().contains(nombre.toLowerCase())) productosConNombre.add(p);
        }
        return productosConNombre;
    }

    public ArrayList<Producto> buscaProductoPorCategoria(String categoria){
        ArrayList<Producto> productosConCategoria = new ArrayList<>();
        for (Producto p : productos){
            if (p.getCategoria().equalsIgnoreCase(categoria)) productosConCategoria.add(p);
        }
        return productosConCategoria;
    }
    public boolean eliminaProductoCarrito(Producto producto, int unidades){
        uTemp.getCarrito().eliminaProducto(producto,unidades);
        producto.addStock(unidades);
        if (Persistence.guardaUsuario(uTemp) && Persistence.guardaProducto(producto)){
            productos = Persistence.leeProductos();
            return true;
        }
        uTemp.getCarrito().addProducto(producto,unidades);
        producto.restaStock(unidades);
        return false;
    }
    public boolean carritoVacio(){
        return uTemp.getCarrito().carritoVacio();
    }
    public boolean addPedido(){
        Carrito carritoAnt = uTemp.getCarrito();
        uTemp.addPedido(generaIdPedido());
        if (Persistence.guardaUsuario(uTemp)) return true;
        uTemp.setCarrito(carritoAnt);
        uTemp.getPedidos().removeLast();
        return false;
    }
    public long generaIdPedido(){
        long id;
        do{
            id = (long) (Math.random() * Long.MAX_VALUE - 1) + 1;
        }while(buscaPedidoId(id) != null);
        return id;
    }
    public Pedido buscaPedidoId(long id){
        for (Usuario u : usuarios){
            for (Pedido p : u.getPedidos()){
                if (p.getId() == id) return p;
            }
        }
        return null;
    }
    public String pintaPedidos(){
        String salida = "";
        if (uTemp.getPedidos() == null || uTemp.getPedidos().isEmpty()){
            salida += "No has realizado ninguna compra";
            return salida;
        }
        for(Pedido p : uTemp.getPedidos()){
            salida += p.pintaSalida() + "\n\n";
        }
        return salida;
    }
    public boolean exportarProductosCSV(String ruta){
        return Persistence.exportarProductosCSV(ruta,productos);
    }

}
