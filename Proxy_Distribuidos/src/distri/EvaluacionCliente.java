package distri;

public class EvaluacionCliente {

	private Cliente cliente;
	private Proyecto proyecto;
	private String calificaci�n;
	private boolean confirmado;
	
	
	
	public EvaluacionCliente(Proyecto p, String calificacion, Cliente c) {
		this.proyecto = p;
		this.calificaci�n = calificacion;
		this.cliente = c;
		this.confirmado = false;
		
		
	}


	public Cliente getCliente() {
		return cliente;
	}


	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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


	public synchronized boolean isConfirmado() {
		return confirmado;
	}


	public synchronized void  setConfirmado(boolean confirmado) {
		this.confirmado = confirmado;
	}

}
