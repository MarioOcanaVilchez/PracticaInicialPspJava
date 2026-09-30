package View;

import Controller.Controller;
import Models.Producto;
import Models.Usuario;
import Utils.Utilidades;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

public class main {
    public static void main() {
        runApp();
    }
    public static void runApp(){
        String op;
        Controller controller = new Controller();
        Utilidades.limpiarPantalla();
        do{
            iniciaSesion(controller);
            if (controller.getuTemp() != null) {
                do{
                    op = pintaMenuPrincipal(controller.getuTemp());
                    Utilidades.limpiarPantalla();
                    //El usuario es admin
                    if (controller.getuTemp().isAdmin()){
                        switch (op){
                            case "1":
                                gestionMenuProductos(controller);
                                break;
                            case "2":
                                gestionaMenuCarrito(controller);
                                break;
                            case "3":
                                pintaPedidos(controller);
                                break;
                            case "4":
                                gestionMenuAdmin(controller);
                                break;
                            case "5":
                                eliminarCuenta(controller);
                                break;
                            case "6":
                                break;
                            default:
                                System.out.println("Opción no existente");
                        }
                        //El usuario no es admin
                    } else {
                        switch (op){
                            case "1":
                                gestionMenuProductos(controller);
                                break;
                            case "2":
                                gestionaMenuCarrito(controller);
                                break;
                            case "3":
                                pintaPedidos(controller);
                                break;
                            case "4":
                                eliminarCuenta(controller);
                                break;
                            case "5":
                                break;
                            default:
                                System.out.println("Opción no existente");
                        }
                    }
                    if (op.equals("3")) Utilidades.pulsaParaContinuar();
                    Utilidades.limpiarPantalla();
                }while((!op.equals("6") && controller.getuTemp().getId() != -1) && controller.getuTemp().isAdmin() || (!op.equals("5") && controller.getuTemp().getId() != -1) && !controller.getuTemp().isAdmin());
            }
        }while(controller.getuTemp() != null);
    }
    public static String preguntaPers(String mensaje){
        System.out.print(mensaje + ": ");
        return new Scanner(System.in).nextLine();
    }

    public static void iniciaSesion(Controller controller){
        String op,email,clave;
        do{
            System.out.println("""
                      Inicio de sesión
                    1. Iniciar sesión
                    2. Registrarse
                    3. Cerrar app""");
            op = preguntaPers("Seleccione una opción");
            switch (op){
                case "1":
                    email = preguntaPers("Introduce el email");
                    clave = preguntaPers("Introduce la contraseña");
                    if (controller.login(email,clave)) return;
                    else System.out.println("Error usuario o contraseña incorrectos");
                    break;
                case "2":
                    email = preguntaPers("Introduce el email");
                    if (controller.buscaUsuario(email) != null) System.out.println("Usuario ya registrado");
                    else {
                        clave = preguntaPers("Introduce la contraseña");
                        if (controller.buscaUsuarioBorrado(email,clave) != null) {
                            if (controller.recuperaUser(email,clave)){
                                System.out.println("Usuario recuperado");
                                Utilidades.pulsaParaContinuar();
                                Utilidades.limpiarPantalla();
                                return;
                            } else System.out.println("Usuario borrado existente pero error al recuperar");
                        } else {
                            String nombre = preguntaPers("Introduce tu nombre");
                            if (controller.registraUsuario(email, clave, nombre)) return;
                            else System.out.println("Error al registrar usuario");
                        }
                    }
                    break;
                case "3":
                    controller.setuTemp(null);
                    break;
                default:
                    System.out.println("Opción no existente");
                    break;
            }
            if (!op.equals("3")){
                Utilidades.pulsaParaContinuar();
                Utilidades.limpiarPantalla();
            }
        }while(!op.equals("3"));
    }
    public static String pintaMenuPrincipal(Usuario usuario){
        if (usuario.isAdmin()) System.out.println("""
                  Menú principal
                1. Buscar producto
                2. Gestionar carrito
                3. Ver historial de pedidos
                4. Menú admin
                5. Eliminar cuenta
                6. Cerrar sesión""");
        else System.out.println("""
                  Menú principal
                1. Buscar producto
                2. Gestionar carrito
                3. Ver historial de pedidos
                4. Eliminar cuenta
                5. Cerrar sesión""");
        return preguntaPers("Introduce una Opción");
    }
    public static String pintaMenuGestionaProductos(){
        System.out.println("""
                1. Filtrar por nombre
                2. Ordenar de mayor a menor precio
                3. Ordenar de menor a mayor precio
                4. Busqueda por categoria
                5. Volver al menú principal
                """);
        return preguntaPers("Introduce una opción");
    }
    public static void gestionMenuProductos(Controller controller){
        Utilidades.limpiarPantalla();
        String op;
        do{
            op = pintaMenuGestionaProductos();
            Utilidades.limpiarPantalla();
            switch (op){
                case "1":
                    usaMenuProductos(controller,controller.buscaProductoPorNombre(preguntaPers("Introduce el nombre a buscar")));
                    break;
                case "2":
                    usaMenuProductos(controller,controller.getProductosMayorMenor());
                    break;
                case "3":
                    usaMenuProductos(controller,controller.getProductosMenorMayor());
                    break;
                case "4":
                    usaMenuProductos(controller,controller.buscaProductoPorCategoria(seleccionaCategoria()));
                    break;
                case "5":
                    break;
                default:
                    System.out.println("Opción no existente");
                    Utilidades.pulsaParaContinuar();
                    Utilidades.limpiarPantalla();
                    break;
            }
        } while(!op.equals("5"));
    }
    public static Producto seleccionaProducto(ArrayList<Producto> productos){
        while(true) {
            int num;
            if (productos.isEmpty()) System.out.println("No hay productos");
            for (int i = 0; i < productos.size(); i++) {
                System.out.println((i + 1) + ". " + productos.get(i).getNombre() + " por " + productos.get(i).getPrecio() + " €");
            }
            System.out.println((productos.size() + 1) + " . Salir");
            num = pedirInt("Selecciona unn producto");
            if (num == productos.size() + 1) return null;
            else if (num > 0 && num <= productos.size()) return productos.get(num - 1);
            else {
                Utilidades.limpiarPantalla();
                System.out.println("Opción no existente");
                Utilidades.pulsaParaContinuar();
                Utilidades.limpiarPantalla();
            }
        }
    }
    public static void usaMenuProductos(Controller controller,ArrayList<Producto> productos){
        Producto producto;
        do{
            producto = seleccionaProducto(productos);
            Utilidades.limpiarPantalla();
            if (producto != null) gestionaCompra(producto,controller);
        }while(producto != null);
    }
    public static void gestionaCompra(Producto p,Controller controller){
        int numProductos,stockInicial = p.getStock();
        do {
            System.out.println(p.pintaProductoComprar());
            numProductos = pedirInt("introduce las unidades que desea comprar (0 para salir)");
        if (numProductos == 0) System.out.println("Compra cancelada");
        else if(numProductos > p.getStock()) System.out.println("Stock insuficiente para su pedido");
        else if(controller.addCompra(p,numProductos)) System.out.println("Producto añadido al carrito");
        else System.out.println("Error en la compra");
        Utilidades.pulsaParaContinuar();
        Utilidades.limpiarPantalla();
        }while(numProductos > stockInicial || numProductos < 0);
    }
    public static String pintaMenuAdmin(){
        System.out.println("""
                1. Añadir producto
                2. Añadir stock
                3. Eliminar producto
                4. Quitar stock
                5. Listado de usuarios activos
                6. Exportar productos CSV
                7. Volver al menú principal""");
        return preguntaPers("Introduce una opción");
    }
    public static void gestionMenuAdmin(Controller controller){
        String op;
        do{
            op = pintaMenuAdmin();
            Utilidades.limpiarPantalla();
            switch (op){
                case "1":
                    addProducto(controller);
                    break;
                case "2":
                    addStock(controller);
                    break;
                case "3":
                    eliminarProducto(controller);
                    break;
                case "4":
                    restaStock(controller);
                    break;
                case "5":
                    pintaUsuariosActivos(controller);
                    break;
                case "6":
                    exportarProductosCSV(controller);
                    break;
                case "7":
                    break;
                default:
                    System.out.println("Opción no existente");
                    break;
            }
            if (!op.equals("7")){
                Utilidades.pulsaParaContinuar();
                Utilidades.limpiarPantalla();
            }
        }while(!op.equals("7"));
    }
    public static void exportarProductosCSV(Controller controller){
        String ruta = preguntaPers("Introduce la ruta a la que deseas exportar EJ = \"C:\\Users\\tuUser\\Desktop\\Carpeta\"");
        if (controller.exportarProductosCSV(ruta)) System.out.println("Fichero creado");
        else System.out.println("Error al crear el fichero comprueba la ruta");

    }
    public static void addProducto(Controller controller){
        String nombre,tamanio,licencia,categoria;
        double precio,peso,precioEnvio;
        int stock;
        nombre = preguntaPers("Introduce el nombre del producto");
        if (controller.buscaProducto(nombre) != null){
            System.out.println("Producto ya existente");
            return;
        }
        if (nombre.equalsIgnoreCase("salir") || nombre.equalsIgnoreCase("cancelar")){
            System.out.println("Operación cancelada");
            return;
        }
        precio = pedirDouble("Introduce el precio de " + nombre);
        stock = pedirInt("Introduce el stock inicial de " + nombre);
        categoria = seleccionaCategoria();
        if (pedirBoolean("¿" + nombre + " es fisico(Si/No)?")){
            peso = pedirDouble("Introduce el peso en kg");
            precioEnvio = pedirDouble("Introduce el precio de envio");
            if (controller.addProducto(nombre,precio,stock,categoria,peso,precioEnvio)) System.out.println("Producto añadido con exito");
        } else {
            tamanio = preguntaPers("Introduce el tamaño y la medida utilizada");
            licencia = preguntaPers("Introduce la licencia");
            if (controller.addProducto(nombre,precio,stock,categoria,tamanio,licencia)) System.out.println("Producto añadido con exito");
        }
    }
    public static String seleccionaCategoria(){
        int numCategoria;
        do{
            System.out.println("""
                        Categorias
                      1. Moviliario
                      2. Electronica
                      3. Libros
                      4. Alimentos
                      5. Películas
                      6. Vestimenta
                      7. Electrodomesticos
                      8. Videojuegos""");
            numCategoria = pedirInt("Introduce una opción");
            Utilidades.limpiarPantalla();
            switch (numCategoria){
                case 1:
                    return "Moviliario";
                case 2:
                    return "Electronica";
                case 3:
                    return "Libros";
                case 4:
                    return "Alimentos";
                case 5:
                    return "Películas";
                case 6:
                    return "Vestimenta";
                case 7:
                    return "Electrodomesticos";
                case 8:
                    return "Videojuegos";
                default:
                    System.out.println("Opción no existente");
            }
        }while(true);
    }
    public static int pedirInt(String mensaje){
        int num = Integer.MIN_VALUE;
        do{
            try {
                num = Integer.parseInt(preguntaPers(mensaje).trim());
                if (num < 0) {
                    System.out.println("Número no válido solo se admiten números enteros positivos");
                    num = Integer.MIN_VALUE;
                }
            } catch (NumberFormatException e) {
                System.out.println("Número no válido solo se admiten números enteros positivos");
            }
        }while (num == Integer.MIN_VALUE);
        return num;
    }
    public static double pedirDouble(String mensaje){
        double num = Double.MIN_VALUE;
        do{
            try {
                num = Double.parseDouble(preguntaPers(mensaje).replace(',','.').replace("€","").replace("$","").trim());
                if (num < 0) {
                    System.out.println("Número no válido solo se admiten números decimales positivos");
                    num = Double.MIN_VALUE;
                }
            } catch (NumberFormatException e) {
                System.out.println("Número no válido solo se admiten números decimales positivos");
            }
        }while (num == Double.MIN_VALUE);
        return num;
    }
    public static boolean pedirBoolean(String mensaje){
        String respuesta;
        do{
            respuesta = preguntaPers(mensaje);
            if (!respuesta.equalsIgnoreCase("si") && !respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("no") && !respuesta.equalsIgnoreCase("n")) System.out.println("Respuesta no válida responde con si o no");
        } while(!respuesta.equalsIgnoreCase("si") && !respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("no") && !respuesta.equalsIgnoreCase("n"));
        return respuesta.equalsIgnoreCase("si") || respuesta.equalsIgnoreCase("s");
    }
    public static void addStock(Controller controller){
        String nombre = preguntaPers("Introduce el nombre del producto");
        if (nombre.equalsIgnoreCase("salir") || nombre.equalsIgnoreCase("cancelar")){
            System.out.println("Operación cancelada");
            return;
        }
        Producto producto = controller.buscaProducto(nombre);
        if (producto == null) System.out.println("Producto no encontrado");
        else {
            int nuevoStock = pedirInt("Introduce el stock entrante");
            if (controller.addStock(producto,nuevoStock)) System.out.println("Stock actualizado");
            else System.out.println("Error al actualizar el stock");
        }
    }
    public static void restaStock(Controller controller){
        String nombre = preguntaPers("Introduce el nombre del producto");
        if (nombre.equalsIgnoreCase("salir") || nombre.equalsIgnoreCase("cancelar")){
            System.out.println("Operación cancelada");
            return;
        }
        Producto producto = controller.buscaProducto(nombre);
        if (producto == null) System.out.println("Producto no encontrado");
        else {
            int stockRestar = pedirInt("Introduce el stock saliente");
            if (controller.restaStock(producto,stockRestar)) System.out.println("Stock actualizado");
            else System.out.println("Error al actualizar el stock");
        }
    }
    public static void eliminarProducto(Controller controller){
        String nombre = preguntaPers("Introduce el nombre del producto");
        if (nombre.equalsIgnoreCase("salir") || nombre.equalsIgnoreCase("cancelar")){
            System.out.println("Operación cancelada");
            return;
        }
        Producto producto = controller.buscaProducto(nombre);
        if (producto == null) System.out.println("Producto no encontrado");
        else {
            if (controller.eliminarProducto(producto)) System.out.println("Producto eliminado");
            else System.out.println("Error al borrar el producto");
        }
    }
    public static void pintaUsuariosActivos(Controller controller){
        System.out.println(controller.pintaUsuariosActivos());
    }
    public static void eliminarCuenta(Controller controller){
        if (pedirBoolean("Estas seguro no podras recuperar la cuenta")) {
            if (controller.eliminarCuenta())System.out.println("Cuenta eliminada");
            else System.out.println("Error al eliminar cuenta");
            Utilidades.pulsaParaContinuar();
            Utilidades.limpiarPantalla();
        } else System.out.println("Operación cancelada");
    }
    public static String pintaMenuCarrito(){
        System.out.println("""
                1. Ver carrito
                2. Eliminar producto
                3. Vaciar carrito
                4. Hacer compra
                5. Volver al menú""");
        return preguntaPers("Introduce una opción");
    }
    public static void gestionaMenuCarrito(Controller controller){
        String op;
        do{
            op = pintaMenuCarrito();
            Utilidades.limpiarPantalla();
            switch (op){
                case "1":
                    System.out.println(controller.pintaCarrito());
                    break;
                case "2":
                    eliminarProductoCarrito(controller);
                    break;
                case "3":
                    vaciarCarrito(controller);
                    break;
                case "4":
                    hacerCompra(controller);
                    break;
                case "5":
                    break;
                default:
                    System.out.println("Opción no existente");
            }
            if (!op.equals("5")){
                Utilidades.pulsaParaContinuar();
                Utilidades.limpiarPantalla();
            }
        }while(!op.equals("5"));
    }
    public static void vaciarCarrito(Controller controller){
        if (controller.vaciarCarrito()) System.out.println("Carrito vaciado");
        else System.out.println("Error al vaciar el carrito");
    }
    public static Producto seleccionaProductoCarrito(Controller controller,Set<Producto> productos){
        while(true) {
            int num;
            System.out.println(controller.pintaCarritoEliminar());
            System.out.println((productos.size() + 1) + " . Salir");
            num = pedirInt("Selecciona unn producto");
            if (num == productos.size() + 1) return null;
            else if (num > 0 && num <= productos.size()) return (Producto) productos.toArray()[num - 1];
            else {
                Utilidades.limpiarPantalla();
                System.out.println("Opción no existente");
                Utilidades.pulsaParaContinuar();
                Utilidades.limpiarPantalla();
            }
        }
    }
    public static void eliminarProductoCarrito(Controller controller){
        Set<Producto> productos = controller.getuTemp().getCarrito().getProductos().keySet();
        Producto producto = seleccionaProductoCarrito(controller,productos);
        int unidades;
        do{
            unidades = pedirInt("Introduce cuantas unidades quieres quitar (hay " + controller.getuTemp().getCarrito().getProductos().get(producto) + " unidades)");
            if (unidades < 0 || unidades > controller.getuTemp().getCarrito().getProductos().get(producto))
                System.out.println("Número invalido");
        }while(unidades < 0 || unidades > controller.getuTemp().getCarrito().getProductos().get(producto));
        if (controller.eliminaProductoCarrito(producto,unidades)) System.out.println("Carrito actualizado");
        else System.out.println("Error al eliminar");
    }
    public static void hacerCompra(Controller controller){
        if (controller.carritoVacio()){
            System.out.println("Carrito vacio");
            return;
        }
        if (controller.addPedido()) System.out.println("Compra realizada con exito");
        else System.out.println("Error al hacer la compra");
    }
    public static void pintaPedidos(Controller controller){
        Utilidades.limpiarPantalla();
        System.out.println("   Pedidos");
        System.out.println(controller.pintaPedidos());
    }
}
