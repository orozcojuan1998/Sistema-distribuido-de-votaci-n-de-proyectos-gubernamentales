package distri;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class Proxy {



	private ArrayList<ConectionClientes> hilosServidorCliente;
	
	private ArrayList<Cliente> clientesID;

	private ArrayList<Fuente> fuentes;

	private int puertoClientes;
	private int puertoFuentes;
	private String ip;
	
	
	
	public Proxy() {
		
		try {
			ip= InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
		
		//CREAR UN METODO PARA PEDIR CUAL PUERTO USAR PARA LOS CLIENTES Y CUAL PARA LAS FUENTES DEBAJO DE ESTE COMENTARIO VA ESE METODO
		
		System.out.println("Por favor ingrese el puerto que usará para las fuentes");
		puertoFuentes = leerInt();
		System.out.println("Por favor ingrese el puerto que usará para los clientes");
		puertoClientes = leerInt();
		
		
		hilosServidorCliente = new ArrayList<ConectionClientes>();
		clientesID = new ArrayList<Cliente>();

		fuentes = new ArrayList<Fuente>();


		TCPDirectorio tcpD = new TCPDirectorio(puertoClientes, puertoFuentes, ip);
		
		tcpD.start();
		
		
		
		ServidorFuentes sf = new ServidorFuentes(this, fuentes, puertoFuentes);
		sf.start();



		ServidorClientes sc = new ServidorClientes(hilosServidorCliente, this, clientesID, puertoClientes);
		sc.start();
	}





	private String leerString() {
		Scanner scan = new Scanner(System.in);
		return scan.next();
	}


	private int leerInt() {
		Scanner scan = new Scanner(System.in);
		return scan.nextInt();
	}




	public static void main (String args[]) {


		@SuppressWarnings("unused")
		Proxy p = new Proxy();

	}

	public synchronized ArrayList<Proyecto> leerProyectosP(Cliente c) {

		ArrayList<Proyecto> resultado = new ArrayList<Proyecto>();
		for (Fuente fuente : fuentes) {
			ArrayList<Proyecto> proyectos = fuente.getProyectos();
			for (Proyecto proyecto : proyectos) {
				ArrayList<EvaluacionCliente> evaluaciones = proyecto.getClientesEvaluacion();
				if(proyecto.getDepartamento().equals(c.getDepartamento())) {
					boolean existeCalificacion = false;
					for (EvaluacionCliente evaluacionCliente : evaluaciones) {
						if(evaluacionCliente.getCliente().getID().equals(c.getID())) {
							existeCalificacion = true;
							break;
						}
					}
					if(!existeCalificacion) {
						resultado.add(proyecto);
					}
				}
				

			}

		}



		return resultado;
	}







	public Proyecto buscarProyecto(String id, String departamento) {
		for (Fuente fuente : fuentes) {

			ArrayList<Proyecto> proyectos = fuente.getProyectos();


			for (Proyecto proyecto : proyectos) {
				if(id.equals(proyecto.getId())&&departamento.equals(proyecto.getDepartamento())) {
					return proyecto;
				}
			}


		}
		return null;
	}





	public synchronized ArrayList<EvaluacionCliente> revisarConfirmacionesCalificaciones(Fuente fuente2) {

		ArrayList<EvaluacionCliente> resultados = new ArrayList<EvaluacionCliente>();	
			
		ArrayList<Proyecto> proyectos = fuente2.getProyectos();


			for (Proyecto proyecto : proyectos) {
				ArrayList<EvaluacionCliente> evaluaciones = proyecto.getClientesEvaluacion();
				for(EvaluacionCliente evaluacion : evaluaciones) {

					if(!evaluacion.isConfirmado()) {
						resultados.add(evaluacion);
					}

				}

			}


		return resultados;
	}


}
