package modelo;

public class Compras {
	private int factura;
	private Fecha fecha;
	private String fechaTotal;
	private String proveedor;
	private String personal;
	private String nifProveedor;
	private String dniPersonal;
	private String producto;
	private int unidades;
	
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
	public String getFechaTotal() {
		return fechaTotal;
	}
	public void setFechaTotal(String fechaTotal) {
		this.fechaTotal = fechaTotal;
	}
	public String getProveedor() {
		return proveedor;
	}
	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}
	public String getPersonal() {
		return personal;
	}
	public void setPersonal(String personal) {
		this.personal = personal;
	}
	public String getNifProveedor() {
		return nifProveedor;
	}
	public void setNifProveedor(String dniProveedor) {
		this.nifProveedor = dniProveedor;
	}
	public String getDniPersonal() {
		return dniPersonal;
	}
	public void setDniPersonal(String dniPersonal) {
		this.dniPersonal = dniPersonal;
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
	
}
