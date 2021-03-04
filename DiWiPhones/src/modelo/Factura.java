package modelo;

public class Factura {
	int cantidad;
	String descripcion;
	int precioUnico;
	int importe;
	int importeTotal;
	
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getPrecioUnico() {
		return precioUnico;
	}
	public void setPrecioUnico(int precioUnico) {
		this.precioUnico = precioUnico;
	}
	public int getImporte() {
		return importe;
	}
	public void setImporte(int importe) {
		this.importe = importe;
	}
	public int getImporteTotal() {
		return importeTotal;
	}
	public void setImporteTotal(int importeTotal) {
		this.importeTotal = importeTotal;
	}

}
