package Models;

public class ProductoDigital extends Producto{
    private String tamanio;
    private String licencia;

    public ProductoDigital(long id, String nombre, double precio, int stock, String categoria, String tamanio, String licencia) {
        super(id, nombre, precio, stock, categoria);
        this.tamanio = tamanio;
        this.licencia = licencia;
    }

    public String getTamanio() {
        return tamanio;
    }

    public void setTamanio(String tamanio) {
        this.tamanio = tamanio;
    }

    public String getLicencia() {
        return licencia;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }

    @Override
    public String pintaProductoComprar() {
        return
         "Producto digital: " + nombre +
                    "\nPrecio: " + precio + " €" +
                    "\nStock disponible: " + stock +
                    "\nCategoría: " + categoria +
                    "\nTamaño: " + tamanio +
                    "\nLicencia: " + licencia;
    }
}
