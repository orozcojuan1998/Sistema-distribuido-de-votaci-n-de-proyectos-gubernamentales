package distri;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServidorFuentes extends Thread{

	private final int  serverPort;
	private ServerSocket listenSocket;
	private boolean aceptarConexiones = true;
	private Proxy p;
	private ArrayList<Fuente> fuentes;
	
	
	public ServidorFuentes(Proxy p, ArrayList<Fuente> fuentes2, int puertoFuentes) {
		this.fuentes = fuentes2;
		this.p = p;
		serverPort=puertoFuentes;
		
		try {
			listenSocket = new ServerSocket(serverPort);
		} catch (IOException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}


		this.p = p;
	}


	public void run() {

		while(true) {
			Socket clientSocket;
			try {


				clientSocket = listenSocket.accept();
				ConectionFuentes c2 = new ConectionFuentes(clientSocket, this);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Listen socket:"+e.getMessage());
				e.printStackTrace();
			}

		}



	}


	



	public boolean buscarFuente(String id) {
		for (Fuente fuente : fuentes) {
			if(fuente.getID().equals(id)){
				return true;
			}
		}
		return false;
	}


	public ArrayList<Fuente> getFuentes() {
		return fuentes;
	}


	public void setFuentes(ArrayList<Fuente> fuentes) {
		this.fuentes = fuentes;
	}


	public Proyecto buscarProyecto(String id, String departamento) {
		return p.buscarProyecto(id, departamento);
	}


	public synchronized ArrayList<EvaluacionCliente> revisarConfirmacionesCalificaciones(Fuente fuente) {
		return p.revisarConfirmacionesCalificaciones(fuente);
	}


	





}
