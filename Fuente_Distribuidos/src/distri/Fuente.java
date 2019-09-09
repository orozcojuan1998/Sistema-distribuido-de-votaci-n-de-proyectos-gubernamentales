package distri;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.text.StyledEditorKit.ForegroundAction;
public class Fuente {
	private ArrayList<Proyecto> proyectos;




	private String idFuente;
	private ArrayList<Proxy> proxies;
	

	public static void main (String[] args) throws IOException {

		//PEDIR POR CONSOLA EL ID DE LA FUENTE
		
		

		@SuppressWarnings("unused")
		Fuente fuente = new Fuente();
	}


	public Fuente() {
		System.out.println("Ingrese el id de la fuente");
		String idFuente = leerString();
		this.idFuente=idFuente;
		setProxies(new ArrayList<Proxy>());
		try {
			leerArchivo("proyectos"+idFuente+".txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		@SuppressWarnings("unused")

		TCPDirectorio tcpd = new TCPDirectorio(this);
		tcpd.obtenerProxies();


		for (Proxy proxy : proxies) {
			TCPFuente tcp = new TCPFuente(this, proxy);
			tcp.start();
		}	
		boolean salir=false;
		do {
			System.out.println("Pulse 2. para imprimir el resultado de las votaciones");
			System.out.println("Pulse 3. para salir");
			int d = leerInt();
			switch(d) {
			case 2:
				for (Proyecto proyecto : proyectos) {
					proyecto.conteoCalificaciones();
					proyecto.imprimirResultados();
				}
				break;
			case 3:
				salir=true;
				break;
			}
			
		}while(salir==false);
	}


	private static String leerString() {
		Scanner scan = new Scanner(System.in);
		return scan.next();
	}
	private static int leerInt() {
		Scanner scan = new Scanner(System.in);
		return scan.nextInt();
	}

	public ArrayList<Proyecto> getProyectos() {
		return proyectos;
	}


	public void setProyectos(ArrayList<Proyecto> proyectos) {
		this.proyectos = proyectos;
	}


	public String getIdFuente() {
		return idFuente;
	}


	public void setIdFuente(String idFuente) {
		this.idFuente = idFuente;
	}


	public  void imprimirProyectos(ArrayList<Proyecto> proyectos) {
		for (Proyecto proyecto : proyectos) {
			System.out.println(proyecto.getId());
			System.out.println(proyecto.getNombre());
			System.out.println(proyecto.getEntidad());
			System.out.println(proyecto.getDepartamento());
			System.out.println(proyecto.getDescripcion());
		}
	}
	public  void leerArchivo(String nombrearchivo) throws IOException {
		String linea;
		int nProyectos=3;
		proyectos = new ArrayList<Proyecto>();
		try {
			FileReader fr = new FileReader(nombrearchivo);
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(fr);
			linea = br.readLine();
			linea = br.readLine();
			nProyectos=Integer.parseInt(linea);
			for(int i=0;i<nProyectos;i++) {
				Proyecto proyecto = new Proyecto();
				linea = br.readLine();
				proyecto.setId(linea);
				linea = br.readLine();
				proyecto.setEntidad(linea);
				linea = br.readLine();
				proyecto.setDepartamento(linea);
				linea = br.readLine();
				proyecto.setNombre(linea);
				linea = br.readLine();
				proyecto.setDescripcion(linea);
				proyecto.setDescripcion(linea);
				proyectos.add(proyecto);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}


	}


	public synchronized Proyecto buscarProyecto(String idProyecto) {
		Proyecto r = null;
		for (Proyecto proyecto : proyectos) {

			if(proyecto.getId().equals(idProyecto)) {
				r = proyecto;
				break;
			}
		}
		return r;
	}


	public ArrayList<Proxy> getProxies() {
		return proxies;
	}


	public void setProxies(ArrayList<Proxy> proxies) {
		this.proxies = proxies;
	}

}
