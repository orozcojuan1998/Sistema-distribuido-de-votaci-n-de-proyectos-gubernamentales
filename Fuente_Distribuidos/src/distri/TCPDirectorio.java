package distri;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.annotation.processing.SupportedSourceVersion;

public class TCPDirectorio extends Thread {
	
	
	
	private Fuente fuente;

	private DataInputStream in;
	private DataOutputStream out;
	private Socket s;
	
	private String ipDirectorio= "10.192.101.21";
	private int port = 8002;

	public TCPDirectorio(Fuente fuente) {
		this.fuente = fuente;
	
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
	public void obtenerProxies() {
		String data="";
		try{
			
			out.writeUTF("\\REQUEST_PROXIES--");
			
			data = in.readUTF();
			while(!data.contains("\\FIN")) {
				
				String[] resp = data.split("--");
				
				String ip = resp[1];
				String port = resp[2];
				Proxy p = new Proxy(ip,Integer.parseInt(port) );
				
				fuente.getProxies().add(p);
	
				data = in.readUTF();
				
			}
			
			}catch (UnknownHostException e){
				System.out.println("Socket:"+e.getMessage());
			}catch (EOFException e){
				System.out.println("EOF:"+e.getMessage());
			}catch (IOException e){
				System.out.println("readline:"+e.getMessage());
			} finally {if(s!=null) try { s.close(); }catch (IOException e){
				System.out.println("close:"+e.getMessage());}}
	
	
	}
	
	
	
	
}