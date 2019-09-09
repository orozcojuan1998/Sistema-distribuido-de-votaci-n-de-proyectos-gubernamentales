package distri;

import java.util.ArrayList;

public class Fuente {

	private ArrayList<Proyecto> proyectos;
	private String ID;

	
	public Fuente(String id2) {
		this.ID = id2;
		proyectos = new ArrayList<Proyecto>();
	}

	public ArrayList<Proyecto> getProyectos() {
		return proyectos;
	}

	public void setProyectos(ArrayList<Proyecto> proyectos) {
		this.proyectos = proyectos;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

}
