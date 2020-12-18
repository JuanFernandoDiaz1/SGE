package modelo;

public class Venta {
	private int factura;
	private Fecha fecha;
	private String fechaTotal;
	private String cliente;
	private String personal;
	private String dniCliente;
	private String dniPersonal;
	private String producto;
	private int unidades;
	private int precio;
	private int precioTotal;
	
	
	public int getPrecio() {
		return precio;
	}
	public void setPrecio(int precio) {
		this.precio = precio;
	}
	public int getPrecioTotal() {
		return precioTotal;
	}
	public void setPrecioTotal(int precioTotal) {
		this.precioTotal = precioTotal;
	}
	public String getProducto() {
		return producto;
	}
	public void setProducto(String producto) {
		this.producto = producto;
	}
	public int getUnidades() {
		return unidades;
	}
	public void setUnidades(int unidades) {
		this.unidades = unidades;
	}
	public String getFechaTotal() {
		return fechaTotal;
	}
	public void setFechaTotal(String fechaTotal) {
		this.fechaTotal = fechaTotal;
	}
	public int getFactura() {
		return factura;
	}
	public void setFactura(int factura) {
		this.factura = factura;
	}
	public Fecha getFecha() {
		return fecha;
	}
	public void setFecha(Fecha fecha) {
		this.fecha = fecha;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getPersonal() {
		return personal;
	}
	public void setPersonal(String personal) {
		this.personal = personal;
	}
	public String getDniCliente() {
		return dniCliente;
	}
	public void setDniCliente(String dniCliente) {
		this.dniCliente = dniCliente;
	}
	public String getDniPersonal() {
		return dniPersonal;
	}
	public void setDniPersonal(String dniPersonal) {
		this.dniPersonal = dniPersonal;
	}
	
	
}
