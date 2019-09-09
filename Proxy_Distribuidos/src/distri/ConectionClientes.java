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
				if(data.contains("\\LOGIN")) {
					Cliente cliente = null;
					String usuario = usuarioS[1];
					Cliente esta = sc.buscarCliente(usuario);
					if(esta == null) {
						cliente = new Cliente(usuario);
						this.cliente = cliente;
						sc.getClientesID().add(cliente);
						out.writeUTF("\\REGISTRADO");
					}else {
						out.writeUTF("\\YA_REGISTRADO");
						this.cliente =esta;
					}
					
				}
				else if(data.contains("\\UBICACION")) {
					
					
					this.cliente.setDepartamento(usuarioS[1]);
					out.writeUTF("\\RUBICACION");
				}
				
				else if(data.contains("\\PING")&&this.cliente!=null) {
					out.writeUTF("\\PONG-"+this.cliente.getID());
				}else if(data.contains("\\REQUEST_PROYECTS")&&this.cliente!=null) {
					
					ArrayList<Proyecto> proyectos = leerProyectosP();
					
					
					for (Proyecto proyecto : proyectos) {
						if(proyecto.getDepartamento().equals(cliente.getDepartamento())) {
							out.writeUTF("\\PROYECT_REPLY--"+proyecto.getId()+"--"+proyecto.getNombre()+"--"+proyecto.getEntidad()+"--"+proyecto.getDepartamento()+"--"+proyecto.getDescripcion());
							data = in.readUTF();
						}
					}
					
					out.writeUTF("\\FIN");
					
				}
				else if(data.contains("\\EVALUAR_P")) {
					String[] eval = data.split("--");
					
					String idProyecto = eval[1];
					String calificacion = eval[2];

					
					Proyecto p = buscarProyecto(idProyecto, cliente.getDepartamento());
					
					EvaluacionCliente ev = new EvaluacionCliente(p, calificacion, cliente);
					
					p.getClientesEvaluacion().add(ev);
					
					while(!ev.isConfirmado()) {
						Thread.sleep(100);
						if(ev.isConfirmado()) {
							out.writeUTF("\\EVALUAR_AK--"+idProyecto);
						}
					}
					
					cliente.getEvaluaciones().add(ev);
					
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

	private synchronized Proyecto buscarProyecto(String idProyecto, String departamento) {
		
		return sc.buscarProyecto(idProyecto, departamento);
	}

	private synchronized ArrayList<Proyecto> leerProyectosP() {
		
		return sc.leerProyectosP(cliente);
	}



} 