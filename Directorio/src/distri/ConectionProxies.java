package distri;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

class ConectionProxies extends Thread {

	private DataInputStream in;
	private DataOutputStream out;
	private Socket clientSocket;
	private ServidorProxies sp;
	private Proxy proxy;

	
	
	public ConectionProxies (Socket aClientSocket, ServidorProxies sp) {
		try {

			this.sp = sp;
			clientSocket = aClientSocket;
			in = new DataInputStream(clientSocket.getInputStream());
			out =new DataOutputStream(clientSocket.getOutputStream());
			this.start();
		} catch(IOException e){
			System.out.println("Connection:"+e.getMessage());
		}
	} // end Connection

	public void run() {
		try {	                                      // an echo server

			String data = null;
			while(true) {
				data = in.readUTF();
				
				
				System.out.println(data);
				
				//System.out.println("Receveid "+data);
				if(data.contains("\\LOGIN")) {

					String[] dataSeparado = data.split("--");
					String ip = dataSeparado[1];
					int portCliente = Integer.parseInt(dataSeparado[2]);
					int portFuente = Integer.parseInt(dataSeparado[3]);
					Proxy esta = sp.buscarProxy(ip,portCliente, portFuente);
					if(esta==null) {
						Proxy proxy = new Proxy(ip,portCliente, portFuente);
						this.proxy = proxy;
						sp.getD().getProxies().add(proxy);
						out.writeUTF("\\REGISTRADO");
					}else {
						out.writeUTF("\\YA_REGISTRADO");
					}
				}
				else if(data.contains("\\PING")&&proxy!=null) {
					
					out.writeUTF("\\PONG-DIRECTORIO");
				}
				
			}

		} catch (EOFException e){
			System.out.println("EOF:"+e.getMessage());
		} catch(IOException e){
			System.out.println("readline:"+e.getMessage());
		} finally{
			try {
				sp.quitarProxy(this.proxy);
				clientSocket.close(); 
			}catch (IOException e){/*close failed*/}}
	} // end run

	
	

} 