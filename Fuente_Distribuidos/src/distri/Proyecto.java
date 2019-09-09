package distri;

import java.io.Serializable;
import java.util.ArrayList;

public class Proyecto implements Serializable {

	private static final long serialVersionUID = -2666396709648363570L;
	private String nombre;
	private String entidad;
	private String descripcion;
	private ArrayList<EvaluacionCliente> clientesEvaluacion;
	private Resultado resultado;
	private String departamento;
	private String id;
	
	public Proyecto(String id, String nombre2, String entidad2, String departamento2, String descripcion2) {
		this.id = id;
		this.nombre = nombre2;
		this.departamento = departamento2;
		this.descripcion = descripcion2;
		this.entidad = entidad2;
		this.clientesEvaluacion = new ArrayList<EvaluacionCliente>();
		resultado = new Resultado(this,0,0,0);
	
	}
	public Proyecto() {
		this.clientesEvaluacion = new ArrayList<EvaluacionCliente>();
		resultado = new Resultado(this,0,0,0);
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


	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public ArrayList<EvaluacionCliente> getClientesEvaluacion() {
		return clientesEvaluacion;
	}


	public void setClientesEvaluacion(ArrayList<EvaluacionCliente> clientesEvaluacion) {
		this.clientesEvaluacion = clientesEvaluacion;
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
	
	public synchronized void conteoCalificaciones() {
		for (EvaluacionCliente evaluacionCliente : this.getClientesEvaluacion()) {
			
		
				if(evaluacionCliente.getCalificación().toLowerCase().equals("alto")){
					resultado.setAlto(resultado.getAlto()+1);
				}else if(evaluacionCliente.getCalificación().toLowerCase().equals("medio")) {
					resultado.setMedio(resultado.getMedio()+1);
				}else {
					resultado.setBajo(resultado.getBajo()+1);
				}

		}
		
	}
	
	public Resultado getResultados() {
		return resultado;
	}
	public void setResultados(Resultado resultados) {
		this.resultado = resultados;
	}
	public synchronized void imprimirResultados() {
			
			System.out.println("Nombre proyecto "+resultado.getProyecto().getNombre());
			System.out.println("ID proyecto"+resultado.getProyecto().getId());
			System.out.println("Alto "+resultado.getAlto());
			System.out.println("Medio "+resultado.getMedio());
			System.out.println("Bajo "+resultado.getBajo());
			
			
			resultado.setAlto(0);
			resultado.setMedio(0);
			resultado.setBajo(0);
		
	}
	
	public synchronized int estaProyecto(ArrayList<Resultado> resultados,String id) {
		int i=0;

		for (Resultado resul : resultados) {
			if(resul.getProyecto().getId().equals(id)) {
				return i;
			}
			i++;
		}
		return -1;
	}


}