package distri;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServidorProxies extends Thread{

	private final int  serverPort = 8000;
	private ServerSocket listenSocket;
	private boolean aceptarConexiones = true;
	private Directorio d;
	
	
	
	public ServidorProxies(Directorio d) {
		this.d = d;
		try {
			listenSocket = new ServerSocket(serverPort);
		} catch (IOException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}


		
	}


	public void run() {

		while(aceptarConexiones) {
			Socket clientSocket;
			try {
				clientSocket = listenSocket.accept();
				ConectionProxies c2 = new ConectionProxies(clientSocket, this);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Listen socket:"+e.getMessage());
				e.printStackTrace();
			}

		}



	}


	public Proxy buscarProxy(String ip, int portCliente, int portProxy) {
		
		return d.buscarProxy(ip, portCliente, portProxy);
	}


	public ServerSocket getListenSocket() {
		return listenSocket;
	}


	public void setListenSocket(ServerSocket listenSocket) {
		this.listenSocket = listenSocket;
	}


	public boolean isAceptarConexiones() {
		return aceptarConexiones;
	}


	public void setAceptarConexiones(boolean aceptarConexiones) {
		this.aceptarConexiones = aceptarConexiones;
	}


	public Directorio getD() {
		return d;
	}


	public void setD(Directorio d) {
		this.d = d;
	}


	public int getServerPort() {
		return serverPort;
	}

	public synchronized void quitarProxy(Proxy p) {
		d.quitarProxy(p);
	}
}
