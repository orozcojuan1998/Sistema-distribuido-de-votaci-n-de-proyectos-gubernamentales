package distri;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServidorFuentes extends Thread{

	private final int  serverPort = 8002;
	private ServerSocket listenSocket;	
	private Directorio d;
	
	public ServidorFuentes(Directorio d) {
		
		this.d =d ;
		
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
				ConectionFuentes  c = new ConectionFuentes(clientSocket, this);
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Listen socket:"+e.getMessage());
				e.printStackTrace();
			}

		}



	}


	public Cliente buscarCliente(String usuario) {
		
		return d.buscarCliente(usuario);
	}


	public void adicionarCliente(Cliente cliente) {
		d.adicionarCliente(cliente);
		
	}


	public Proxy asignarProxy(Cliente cliente) {
		return d.asignarProxy(cliente);
	}


	public ArrayList<Proxy> obtenerProxies() {
		return d.getProxies();
	}
	


	

}
