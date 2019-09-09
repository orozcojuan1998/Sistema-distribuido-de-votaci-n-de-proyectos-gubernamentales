package distri;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPDirectorio extends Thread{

	private String ip = "10.192.101.21";
	
	
	private int port = 8000;
	
	private int portFuentes;
	private int portClientes;
	
	private DataInputStream in;
	private DataOutputStream out;
	private Socket s;
	private boolean logeado = false;
	private String ipProxy;
	
	
	
	public TCPDirectorio(int portClientes, int portFuentes, String ip2) {
		this.portClientes = portClientes;
		this.portFuentes = portFuentes;
		this.ipProxy=ip2;
		s=null;
		
		
		try {
			s = new Socket(ip,port);
			in = new DataInputStream(s.getInputStream());
			out = new DataOutputStream(s.getOutputStream());
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void run() {
		
		String data = "";
		try {
		while(true) {
			
			Thread.sleep(1000);
			
			
			if(!logeado) {
				
				out.writeUTF("\\LOGIN--"+this.ipProxy+"--"+Integer.toString(portClientes)+"--"+Integer.toString(portFuentes));
				logeado = true;
				
			}
			else {
				out.writeUTF("\\PING--"+this.ipProxy+"--"+Integer.toString(portClientes)+"--"+Integer.toString(portFuentes));
			}
			data = in.readUTF();
			
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
}
