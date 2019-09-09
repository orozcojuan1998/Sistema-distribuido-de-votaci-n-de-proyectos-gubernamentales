package distri;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

class ConectionFuentes extends Thread {

	private DataInputStream in;
	private DataOutputStream out;
	private Socket clientSocket;
	private ServidorFuentes sc;
	


	public ConectionFuentes(Socket aClientSocket, ServidorFuentes sc) {
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
				
				if(data.contains("\\REQUEST_PROXIES")) {
					Thread.sleep(100);
					
					ArrayList<Proxy> proxies = new ArrayList<>();
					proxies= obtenerProxies();
					for (Proxy proxy : proxies) {
						out.writeUTF("\\REPLY_PROXIES--"+proxy.getIp()+"--"+proxy.getPortFuente());
						System.out.println("REPLYYYYYYYYYYY-----");
					}

					out.writeUTF("\\FIN");
					
					
				}
			
				else if(data.contains("\\PING--DIRECTORIO")) {
					out.writeUTF("\\PONG-DIRECTORIO");
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
	}

	private ArrayList<Proxy> obtenerProxies() {
		return sc.obtenerProxies();
	} // end run





} 