package distri;

import java.io.Serializable;

public class EvaluacionCliente implements Serializable {

	
	private static final long serialVersionUID = 4896607950415197083L;
	private String idCliente;
	private Proyecto proyecto;
	private String calificaci�n;
	
	
	public EvaluacionCliente(String cliente, Proyecto p, String calificacion) {
		this.idCliente=cliente;
		this.proyecto=p;
		this.calificaci�n=calificacion;
	}


	public Proyecto getProyecto() {
		return proyecto;
	}


	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}


	public String getCalificaci�n() {
		return calificaci�n;
	}


	public void setCalificaci�n(String calificaci�n) {
		this.calificaci�n = calificaci�n;
	}


	public String getIdCliente() {
		return idCliente;
	}


	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}
	

}