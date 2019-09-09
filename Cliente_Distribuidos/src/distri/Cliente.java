package distri;

import java.util.ArrayList;
import java.util.Scanner;

public class Cliente {
	
	private String ID;
	private String departamento;
	private TCPClient tcpCliente;
	private Scanner teclado;
	private ArrayList<Proyecto> proyectos;
	private boolean conectado = false;
	
	public Cliente() {
		
		
		
		this.proyectos= new ArrayList<>();
		teclado =  new Scanner(System.in);
		
		
		
	
		
		
		int decision = 0;
		
		do {
			
			if(!conectado) {
				System.out.println("1.Conectarse");
			}
			if(conectado) {
				System.out.println("2.Desconectarse");
				System.out.println("3.Solicitar proyectos");
				System.out.println("4.Evaluar proyectos");
				System.out.println("5.Mostrar proyectos");
				System.out.println("0.Salir");
			}
			
			
			decision = leerConsolaInt();
			switch (decision) {
			case 0:
				if(conectado) {
				desconectar();
				}
				break;
			case 1:
				if(!conectado) {
				conectarse();
				}
				break;
			case 2:
				if(conectado) {
				desconectar();
				}
				break;
			case 3:
				if(conectado) {
				solicitarProyectos();
				}
				break;
			case 4:
				if(conectado) {
				evaluarProyecto();
				}
			case 5:
				if(conectado) {
				mostrarProyectos();
				}
			default:
				break;
			}
			
			
			
		}while(!(decision==0)||!(decision==3));
		
		
		
		
		
	}
	
	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public ArrayList<Proyecto> getProyectos() {
		return proyectos;
	}

	public void setProyectos(ArrayList<Proyecto> proyectos) {
		this.proyectos = proyectos;
	}

	private void evaluarProyecto() {
		// TODO Auto-generated method stub
		this.mostrarProyectos();
		System.out.println("Ingrese el id del proyecto por el que desea votar.");
		String voto,id;
		
		id = leerConsolaString();
		System.out.println("Ingrese la valoracion (bajo/medio/alto). ");
		voto = leerConsolaString();
		tcpCliente.setIdProyectoEvaluado(id);
		tcpCliente.setValoracionProyecto(voto);
		
		
		
		
		if(tcpCliente.isAlive()) {
			tcpCliente.cambiarDecision(4);
		}
		
	}

	private void solicitarProyectos() {
		if(tcpCliente.isAlive()) {
			tcpCliente.cambiarDecision(3);
		}
		
	}

	private void conectarse() {
		
		System.out.println("Ingrese su ID: ");
		String id,departamento;
		
		id = leerConsolaString();
		
		this.ID = id;
		
		
		System.out.println("Ingrese su departamento: ");
		
		departamento = leerConsolaString();
		
		this.departamento = departamento;
		
		TCPDirectorio tcpd = new TCPDirectorio(this);
		
		
		
		
		Proxy p = tcpd.obtenerProxy();
		
		
		tcpCliente = new TCPClient(p ,this);
		tcpCliente.start();
		conectado = true;
		if(tcpCliente.isAlive()) {
			tcpCliente.cambiarDecision(1);
		}
		
	}


	private void desconectar() {		
		if(tcpCliente.isAlive()) {
			tcpCliente.cambiarDecision(0);
		}
		conectado = false;
		
	}
	
	public synchronized void agregarProyecto(Proyecto p) {
		this.proyectos.add(p);
	}

	public int leerConsolaInt() {
		int n;
		n = teclado.nextInt();
		return n;
	}
	public String leerConsolaString() {
		String n;
		n = teclado.next();
		return n;
	}
	
	public static void main(String[] args) {
		
		
		
		Cliente c = new Cliente();
	}
	
	public synchronized boolean existeProyecto(String idP) {
		for (Proyecto proyecto : this.proyectos) {
			if(proyecto.getId()==idP) {
				return true;
			}
		}
		return false;
	}
	public synchronized void mostrarProyectos() {
			for (Proyecto proyecto : this.proyectos) {
				System.out.println(proyecto.getId());
				System.out.println(proyecto.getNombre());
				System.out.println(proyecto.getEntidad());
				System.out.println(proyecto.getDepartamento());
				System.out.println(proyecto.getDescripcion());
				System.out.println(proyecto.getClientesEvaluacion());
			}
	}
	
	public void nuevoP() {
		TCPDirectorio tcpd = new TCPDirectorio(this);
		Proxy p = tcpd.obtenerProxy();
		tcpCliente = new TCPClient(p ,this);
		tcpCliente.start();
		conectado = true;
		if(tcpCliente.isAlive()) {
			tcpCliente.cambiarDecision(1);
		}
	}
}
