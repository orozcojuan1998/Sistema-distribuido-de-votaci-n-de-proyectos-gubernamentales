package distri;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServidorClientes extends Thread{

	private final int  serverPort;
	private ServerSocket listenSocket;
	private ArrayList<ConectionClientes> hilosServidorCliente;
	private Proxy p;
	private ArrayList<Cliente> clientesID;
	
	public ServidorClientes(ArrayList<ConectionClientes> hilosServidorCliente, Proxy p, ArrayList<Cliente> clientesID2, int puertoClientes) {
		this.serverPort=puertoClientes;
		this.p = p;
		this.clientesID = clientesID2;
		this.hilosServidorCliente = hilosServidorCliente;
		try {
			listenSocket = new ServerSocket(serverPort);
		} catch (IOException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}



	}


	public void run() {

		while(true) {
			Socket clientSocket;
			try {
				clientSocket = listenSocket.accept();
				ConectionClientes  c = new ConectionClientes(clientSocket, this);
				agregarHilos(c);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Listen socket:"+e.getMessage());
				e.printStackTrace();
			}

		}



	}


	private synchronized void agregarHilos(ConectionClientes c) {
		hilosServidorCliente.add(c);
		
	}


	public synchronized ArrayList<Proyecto> leerProyectosP(Cliente c) {
		

		return p.leerProyectosP(c);
	}


	public synchronized ArrayList<Cliente> getClientesID() {
		return clientesID;
	}


	public void setClientesID(ArrayList<Cliente> clientesID) {
		this.clientesID = clientesID;
	}


	public synchronized Cliente buscarCliente(String usuario) {
		for (Cliente cliente : clientesID) {
			if(cliente.getID().equals(usuario)){
				return cliente;
			}
		}
		return null;
	}


	public synchronized Proyecto buscarProyecto(String idProyecto, String departamento) {
		// TODO Auto-generated method stub
		return p.buscarProyecto(idProyecto, departamento);
	}


	

}
