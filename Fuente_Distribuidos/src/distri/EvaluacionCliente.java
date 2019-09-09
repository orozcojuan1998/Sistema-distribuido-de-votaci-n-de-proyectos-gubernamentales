package distri;

import java.io.Serializable;

public class EvaluacionCliente implements Serializable {

	
	private static final long serialVersionUID = 4896607950415197083L;
	private String idCliente;
	private Proyecto proyecto;
	private String calificación;
	
	
	public EvaluacionCliente(String cliente, Proyecto p, String calificacion) {
		this.idCliente=cliente;
		this.proyecto=p;
		this.calificación=calificacion;
	}


	public Proyecto getProyecto() {
		return proyecto;
	}


	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}


	public String getCalificación() {
		return calificación;
	}


	public void setCalificación(String calificación) {
		this.calificación = calificación;
	}


	public String getIdCliente() {
		return idCliente;
	}


	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}
	

}