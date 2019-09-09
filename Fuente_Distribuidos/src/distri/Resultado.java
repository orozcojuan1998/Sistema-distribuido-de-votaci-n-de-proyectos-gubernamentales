package distri;

public class Resultado {
	Proyecto proyecto;
	int alto;
	int medio;
	int bajo;
	public Resultado(Proyecto proyecto,int a, int m, int b) {
		this.alto=a;
		this.medio=m;
		this.bajo=b;
		this.proyecto=proyecto;
	}
	public Proyecto getProyecto() {
		return proyecto;
	}
	public void setProyecto(Proyecto idProyecto) {
		this.proyecto = idProyecto;
	}
	public int getAlto() {
		return alto;
	}
	public void setAlto(int alto) {
		this.alto = alto;
	}
	public int getMedio() {
		return medio;
	}
	public void setMedio(int medio) {
		this.medio = medio;
	}
	public int getBajo() {
		return bajo;
	}
	public void setBajo(int bajo) {
		this.bajo = bajo;
	}
	
	
}
