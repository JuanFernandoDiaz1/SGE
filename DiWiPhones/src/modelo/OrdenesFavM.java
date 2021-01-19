package modelo;

import java.sql.Date;

public class OrdenesFavM {
	private int unidades;
	private int escandallo;
	private String personal;
	private Date FechaInicio;
	private Date FechaFin;
	private String Estado;
	
	public int getUnidades() {
		return unidades;
	}
	public void setUnidades(int unidades) {
		this.unidades = unidades;
	}
	public int getEscandallo() {
		return escandallo;
	}
	public void setEscandallo(int escandallo) {
		this.escandallo = escandallo;
	}
	public String getPersonal() {
		return personal;
	}
	public void setPersonal(String personal) {
		this.personal = personal;
	}
	public Date getFechaInicio() {
		return FechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		FechaInicio = fechaInicio;
	}
	public Date getFechaFin() {
		return FechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		FechaFin = fechaFin;
	}
	public String getEstado() {
		return Estado;
	}
	public void setEstado(String estado) {
		Estado = estado;
	}
	
	
	
}
