package distri;

import java.util.ArrayList;

public class Cliente {

	private String ID;
	private ArrayList<EvaluacionCliente> evaluaciones;
	private String departamento;
	

	public Cliente(String usuario) {
		this.ID = usuario;
		
		evaluaciones = new ArrayList<EvaluacionCliente>();
	}
	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public ArrayList<EvaluacionCliente> getEvaluaciones() {
		return evaluaciones;
	}

	public void setEvaluaciones(ArrayList<EvaluacionCliente> evaluaciones) {
		this.evaluaciones = evaluaciones;
	}
	public String getDepartamento() {
		return departamento;
	}
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
}
