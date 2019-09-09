package distri;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

class ConectionClientes extends Thread {

	private DataInputStream in;
	private DataOutputStream out;
	private Socket clientSocket;
	private Cliente cliente;
	private ServidorClientes sc;



	public ConectionClientes (Socket aClientSocket, ServidorClientes sc) {
		this.sc = sc;

		try {


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
				
				System.out.println("Receveid "+data);
				
				String[] usuarioS = data.split("--");
				if(data.contains("\\REQUEST_PROXY")) {
					Thread.sleep(1000);
					String usuario = usuarioS[1];
					Cliente esta = sc.buscarCliente(usuario);
					if(esta == null) {
						cliente = new Cliente(usuario);
						sc.adicionarCliente(cliente);
					}else {
						this.cliente =esta;
					}
					String respuesta = "";
					if(cliente.getP()==null) {		
						
						Proxy p = sc.asignarProxy(cliente);
						
						cliente.setP(p);		
					    p.getClientes().add(cliente);
						
					}
					
					
					respuesta = cliente.getP().getIp()+"--"+cliente.getP().getPortCliente();
					out.writeUTF("\\REPLY_PROXY--"+respuesta);
					
					
				}
			
				else if(data.contains("\\PING")&&this.cliente!=null) {
					out.writeUTF("\\PONG-"+this.cliente.getID());
				}
			}



		} catch (EOFException e){
			System.out.println("EOF:"+e.getMessage());
		} catch(IOException e){
			System.out.println("readline:"+e.getMessage());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  finally{
			try {
				
				clientSocket.close(); 
			}catch (IOException e){/*close failed*/}}
	} // end run





} 