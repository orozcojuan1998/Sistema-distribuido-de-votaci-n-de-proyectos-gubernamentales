package distri;

import java.util.ArrayList;

public class Proyecto {

	private String nombre;
	private String entidad;
	private String descripcion;
	private String clientesEvaluacion;
	private String departamento;
	private String id;
	
	
	public Proyecto(String id, String nombre2, String entidad2, String departamento2, String descripcion2, String clientesEvaluacion) {
		this.id = id;
		this.nombre = nombre2;
		this.departamento = departamento2;
		this.descripcion = descripcion2;
		this.entidad = entidad2;
		this.clientesEvaluacion=clientesEvaluacion;
	}

	public Proyecto() {
		
	}

	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getEntidad() {
		return entidad;
	}


	public void setClientesEvaluacion(String clientesEvaluacion) {
		this.clientesEvaluacion = clientesEvaluacion;
	}

	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}


	public String getClientesEvaluacion() {
		return clientesEvaluacion;
	}

	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getDepartamento() {
		return departamento;
	}


	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


}