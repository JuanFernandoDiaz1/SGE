package modelo;

public class Venta {
	private int factura;
	private Fecha fecha;
	private String fechaTotal;
	private String cliente;
	private String personal;
	private String dniCliente;
	private String dniPersonal;
	
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
