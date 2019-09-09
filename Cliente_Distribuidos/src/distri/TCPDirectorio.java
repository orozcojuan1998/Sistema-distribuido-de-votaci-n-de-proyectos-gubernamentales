package distri;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.annotation.processing.SupportedSourceVersion;

public class TCPDirectorio extends Thread {
	private Cliente cliente;

	private DataInputStream in;
	private DataOutputStream out;
	private Socket s;
	
	private String ipDirectorio= "10.192.101.21";
	private int port = 8001;

	public TCPDirectorio(Cliente cliente) {
		this.cliente = cliente;
	
		s = null;
		try{
			
			s = new Socket(ipDirectorio, port);    
			in = new DataInputStream(s.getInputStream());
			out =new DataOutputStream(s.getOutputStream());

		}catch (UnknownHostException e){
			System.out.println("Socket:"+e.getMessage());
		}catch (EOFException e){
			System.out.println("EOF:"+e.getMessage());
		}catch (IOException e){
			System.out.println("readline:"+e.getMessage());
		} 
	}
	public Proxy obtenerProxy() {
		String data="";
		try{
			
			out.writeUTF("\\REQUEST_PROXY--"+cliente.getID());
			
			
			data = in.readUTF();
			
			
			String[] resp = data.split("--");
			
			String ip = resp[1];
			String port = resp[2];
			
			
			
			
			Proxy p = new Proxy(ip,Integer.parseInt(port) );
			
			
			
		
			
			return p;
			
			
			
			
			}catch (UnknownHostException e){
				System.out.println("Socket:"+e.getMessage());
			}catch (EOFException e){
				System.out.println("EOF:"+e.getMessage());
			}catch (IOException e){
				System.out.println("readline:"+e.getMessage());
			} finally {if(s!=null) try { s.close(); }catch (IOException e){
				System.out.println("close:"+e.getMessage());}}
	
		
		return null;
	
	}
	
	
	
	
}
