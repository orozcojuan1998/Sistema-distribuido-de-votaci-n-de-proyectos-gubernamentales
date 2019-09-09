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

public class TCPClient extends Thread {
	private Cliente cliente;
	private int decision = -1;
	
	private DataInputStream in;
	private DataOutputStream out;
	private Socket s;
	
	private boolean logeado = false; 
	private String idProyectoEvaluado;
	private String valoracionProyecto;

	public TCPClient(Proxy proxy,Cliente cliente) {
		this.cliente = cliente;
		
		this.idProyectoEvaluado="";
		this.valoracionProyecto="";
		s = null;
		try{
			
			s = new Socket(proxy.getIp(), proxy.getPuerto());    
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
	public void run() {
		String data="";
		try{
			while(true) {
				
				
				Thread.sleep(1000);
				if(decision == -1&&logeado) {
					
					while (decision==-1) {
						Thread.sleep(1000);
						out.writeUTF("\\PING-"+cliente.getID()); 
						
						
						data = in.readUTF();
						//System.out.println("Received: "+ data);
						
					}
					
				}
				if(decision == 0) {
					break;
				}
				if(decision == 1) {
					out.writeUTF("\\LOGIN--"+cliente.getID()); 
					data = in.readUTF();

					if(data.equals("\\REGISTRADO")) {

						out.writeUTF("\\UBICACION--"+cliente.getDepartamento());
						
						
						in.readUTF();
						
						logeado = true;

					}else if(data.equals("\\YA_REGISTRADO")){
						logeado = true;
					
						System.out.println("Ya se ha conectado");
					}
					cambiarDecision(-1);
				}
				if(decision == 3) {
					agregarProyectos();
					cambiarDecision(-1);
				}
				if(decision==4) {
					boolean b=false;
					for (Proyecto proyecto :this.cliente.getProyectos()) {
						if(proyecto.getClientesEvaluacion().equals("SinEvaluar")&&proyecto.getId().equals(idProyectoEvaluado)) {
							proyecto.setClientesEvaluacion(valoracionProyecto);
							b=true;
							break;
						}else if(proyecto.getId().equals(idProyectoEvaluado) && !proyecto.getClientesEvaluacion().equals("SinEvaluar")){
							System.out.println("Ya ha votado por este proyecto.");
						}
					}
					if(b) {
						out.writeUTF("\\EVALUAR_P--"+idProyectoEvaluado+"--"+valoracionProyecto);
						
						data = in.readUTF();
						
						if(data.contains("\\EVALUAR_AK--")) {
							System.out.println("La calificación ha sido guardada correctamente");
						}
					}
					
					
					cambiarDecision(-1);
				}
				if(decision==5) {
					this.cliente.mostrarProyectos();
				}
				
				

			}}catch (UnknownHostException e){
				System.out.println("Socket:"+e.getMessage());
			}catch (EOFException e){
				System.out.println("EOF:"+e.getMessage());
			}catch (IOException e){
				System.out.println();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally { try {
				if(s!=null) {
					s.close(); 
				}
				this.cliente.nuevoP();
				
			}catch (IOException e){
				System.out.println("close:"+e.getMessage());}}
	}
	public synchronized void cambiarDecision(int i) {
		decision = i;
	}
	
	public synchronized void agregarProyectos() throws IOException {
		
		out.writeUTF("\\REQUEST_PROYECTS");
		String data = in.readUTF();
		while(data.contains("\\PROYECT_REPLY"))
		{
			
		
			String [] linea= data.split("--");
			String idP = linea[1];
			String nombre = linea[2];
			String entidad = linea[3];
			String departamento = linea[4];
			String descripcion = linea[5];
			Proyecto p = new Proyecto(idP,nombre,entidad,departamento,descripcion,"SinEvaluar");
			if(!this.cliente.existeProyecto(idP)) {
				this.cliente.agregarProyecto(p);
			}
			out.writeUTF("\\REQUEST_PROYECTS");
			data = in.readUTF();
		}
	}
	public synchronized String getIdProyectoEvaluado() {
		return idProyectoEvaluado;
	}
	public synchronized void setIdProyectoEvaluado(String idProyectoEvaluado) {
		this.idProyectoEvaluado = idProyectoEvaluado;
	}

	public synchronized String getValoracionProyecto() {
		return valoracionProyecto;
	}
	public synchronized void setValoracionProyecto(String valoracionProyecto) {
		this.valoracionProyecto = valoracionProyecto;
	}

}
