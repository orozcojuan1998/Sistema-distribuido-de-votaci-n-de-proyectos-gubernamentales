package distri;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class TCPFuente extends Thread {
	
	private Fuente fuente;
	private Proxy proxy;
	private DataInputStream in;
	private DataOutputStream out;
	private Socket s;
	
	public TCPFuente(Fuente fuente, Proxy proxy) {
		this.proxy = proxy;
		this.fuente = fuente;
		s = null;
		try{
			
			s = new Socket(proxy.getIp(), proxy.getPort());    
			in = new DataInputStream(s.getInputStream());
			out =new DataOutputStream(s.getOutputStream());

			
		}catch (UnknownHostException e){
			System.out.println("Socket:"+e.getMessage());
		}catch (EOFException e){
			System.out.println("EOF:"+e.getMessage());
		}catch (IOException e){
			System.out.println("readline:"+e.getMessage());
			e.printStackTrace();
		}
	}
	public void run() {
		try {
			out.writeUTF("\\LOGIN--"+fuente.getIdFuente());
			 // UTF is a string encoding
		String data = in.readUTF(); // read a line of data from the stream
		if(data.equals("\\REGISTRADO")) {
			ArrayList<Proyecto> proyectos = fuente.getProyectos();
			for (Proyecto proyecto : proyectos) {
				out.writeUTF("\\ADDP--"+proyecto.getId()+"--"+proyecto.getNombre()+"--"+proyecto.getEntidad()+"--"+proyecto.getDepartamento()+"--"+proyecto.getDescripcion());
			}
		}

		while(true) {
			
			if(data.contains("\\EVALUAR_P")) {
				
				String[] evC = data.split("--");
				
				Proyecto p = buscarProyecto(evC[1]);
				
				
				
				
				EvaluacionCliente ev =new EvaluacionCliente(evC[3], p, evC[2]);
				
				boolean existe = false;
				for (EvaluacionCliente ev2 : p.getClientesEvaluacion()) {
					if(ev2.getIdCliente().equals(evC[3])){
						existe = true;
					}
				}
				if(!existe){
					p.getClientesEvaluacion().add(ev);
				}
				
			
				
				
				out.writeUTF("\\RC_EVALUAR_P--"+evC[1]);
				
				
				
			}
			else {
					out.writeUTF("\\PING--"+fuente.getIdFuente());
					Thread.sleep(1000);
			}
			
			data = in.readUTF();
			
		}
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  finally {if(s!=null) try { s.close(); }catch (IOException e){
			System.out.println("close:"+e.getMessage());}}     
	}
	private Proyecto buscarProyecto(String idProyecto) {
		return fuente.buscarProyecto(idProyecto);
	}
}
