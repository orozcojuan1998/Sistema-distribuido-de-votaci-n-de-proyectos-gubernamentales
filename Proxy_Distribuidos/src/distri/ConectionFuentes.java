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
	private ServidorFuentes sf;
	private Fuente fuente;

	public ConectionFuentes(Socket aClientSocket, ServidorFuentes sf) {
		try {

			this.sf = sf;
			clientSocket = aClientSocket;
			in = new DataInputStream(clientSocket.getInputStream());
			out = new DataOutputStream(clientSocket.getOutputStream());
			this.start();
		} catch (IOException e) {
			System.out.println("Connection:" + e.getMessage());
		}
	} // end Connection

	public void run() {
		try { // an echo server

			String data = null;
			while (true) {
				data = in.readUTF();

				// System.out.println("Receveid "+data);
				if (data.contains("\\LOGIN")) {

					String[] dataSeparado = data.split("--");
					String id = dataSeparado[1];
					boolean esta = sf.buscarFuente(id);
					if (!esta) {
						Fuente fuente = new Fuente(id);
						this.fuente = fuente;
						sf.getFuentes().add(fuente);
						out.writeUTF("\\REGISTRADO");
					} else {
						out.writeUTF("\\YA_REGISTRADO");
					}

				}

				else if (data.contains("\\PING") && fuente != null) {
					out.writeUTF("\\PONG-" + fuente.getID());
				} else if (data.contains("\\ADDP") && fuente != null) {
					String[] dataSeparado = data.split("--");
					String id = dataSeparado[1];
					String nombre = dataSeparado[2];
					String entidad = dataSeparado[3];
					String departamento = dataSeparado[4];
					String descripcion = dataSeparado[5];

					Proyecto encontrado = buscarProyecto(id, departamento);

					if (encontrado == null) {
						Proyecto p = new Proyecto(id, nombre, entidad, departamento, descripcion);
						fuente.getProyectos().add(p);
					}

				}
				// por cada fuente verificar
				if(fuente!=null) {
					ArrayList<EvaluacionCliente> evaluaciones = revisarConfirmacionesCalificaciones();
					if (evaluaciones.size() != 0) {


					
						for (EvaluacionCliente evaluacionCliente : evaluaciones) {
							out.writeUTF("\\EVALUAR_P--" + evaluacionCliente.getProyecto().getId() + "--"
									+ evaluacionCliente.getCalificación() + "--" + evaluacionCliente.getCliente().getID());

							data = in.readUTF();

							
							evaluacionCliente.setConfirmado(true);
							out.writeUTF("\\FIN-" + fuente.getID());
							

						}

					}
				}


			}

		} catch (EOFException e) {
			System.out.println("EOF:" + e.getMessage());
		} catch (IOException e) {
			System.out.println("readline:" + e.getMessage());
		} finally {
			try {

				clientSocket.close();
			} catch (IOException e) {
			/* close failed */}
		}
	} // end run

	private synchronized ArrayList<EvaluacionCliente> revisarConfirmacionesCalificaciones() {

		return sf.revisarConfirmacionesCalificaciones(fuente);
	}

	private synchronized Proyecto buscarProyecto(String id, String departamento) {
		return sf.buscarProyecto(id, departamento);
	}

}