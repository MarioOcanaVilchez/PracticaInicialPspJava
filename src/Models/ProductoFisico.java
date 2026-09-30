package Models;

public class ProductoFisico extends Producto{
    private double peso;
    private double precioEnvio;

    public ProductoFisico(long id, String nombre, double precio, int stock, String categoria, double peso, double precioEnvio) {
        super(id, nombre, precio, stock, categoria);
        this.peso = peso;
        this.precioEnvio = precioEnvio;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getPrecioEnvio() {
        return precioEnvio;
    }

    public void setPrecioEnvio(double precioEnvio) {
        this.precioEnvio = precioEnvio;
    }

    @Override
    public String pintaProductoComprar() {
        return
                "Producto digital: " + nombre +
                        "\nPrecio: " + precio + " €" +
                        "\nStock disponible: " + stock +
                        "\nCategoría: " + categoria +
                        "\nPeso: " + peso + " kg" +
                        "\nCoste de envío: " + precioEnvio + " €";
    }
}
