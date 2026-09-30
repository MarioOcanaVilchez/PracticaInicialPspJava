package Persistence;

import Models.Producto;
import Models.ProductoDigital;
import Models.ProductoFisico;
import Models.Usuario;

import java.io.*;
import java.util.ArrayList;

public class Persistence {
    public static ArrayList<Usuario> leeUsuarios(){
        ArrayList<Usuario> usuarios = new ArrayList<>();
        File directorio = new File("Usuarios/");
        String[] urls = directorio.list();
        if (urls == null) return null;
        try {
            for (String url : urls){
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Usuarios/" + url));
                usuarios.add((Usuario) ois.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return usuarios;
    }
    public static boolean guardaUsuario(Usuario usuario){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Usuarios/" + usuario.getId() + ".bin"));
            oos.writeObject(usuario);
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static ArrayList<Producto> leeProductos(){
        ArrayList<Producto> productos = new ArrayList<>();
        File directorio = new File("Productos/");
        String[] urls = directorio.list();
        if (urls == null) return null;
        try {
            for (String url : urls){
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Productos/" + url));
                productos.add((Producto) ois.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return productos;
    }
    public static boolean guardaProducto(Producto producto){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Productos/" + producto.getId() + ".bin"));
            oos.writeObject(producto);
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean eliminarProducto(Producto producto){
        String ruta = "Productos/" + producto.getId() + ".bin";
        File fichero = new File(ruta);
        if (!fichero.exists()) return false;
        return fichero.delete();
    }
    public static boolean exportarProductosCSV(String ruta,ArrayList<Producto> productos){
        File file = new File(ruta);
        if (file.exists() && file.isFile() && ruta.endsWith(".csv")){
            try {
                FileWriter fw = new FileWriter(file);
                fw.write("Id;Nombre;Precio;Stock;Categoria;\n");
                for(Producto p : productos){
                    fw.write(p.getId() + ";" + p.getNombre() + ";" + p.getPrecio() + " €;" + p.getStock() + ";" + p.getCategoria() + ";\n");
                }
                fw.close();
                return true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (file.exists() && file.isDirectory()){
            File fichero = new File(ruta + "/productos.csv");
            try {
                FileWriter fw = new FileWriter(fichero);
                fw.write("Id;Nombre;Precio;Stock;Categoria;\n");
                for(Producto p : productos){
                    fw.write(p.getId() + ";" + p.getNombre() + ";" + p.getPrecio() + " €;" + p.getStock() + ";" + p.getCategoria() + ";\n");
                }
                fw.close();
                return true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }
}
