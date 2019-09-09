package distri;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServidorClientes extends Thread{

	private final int  serverPort = 8001;
	private ServerSocket listenSocket;	
	private Directorio d;
	
	public ServidorClientes(Directorio d) {
		
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
				ConectionClientes  c = new ConectionClientes(clientSocket, this);
				
				
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
	


	

}
