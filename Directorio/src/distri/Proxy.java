package distri;

import java.util.ArrayList;

public class Proxy {

	
	private String ip;
	private int portCliente;
	private int portFuente;
	private ArrayList<Cliente> clientes;
	
	
	
	public Proxy(String ip, int portCliente, int portFuente) {
		this.ip = ip;
		this.setPortCliente(portCliente);
		this.setPortFuente(portFuente);
		setClientes(new ArrayList<Cliente>());
	}


	public String getIp() {
		return ip;
	}


	public void setIp(String ip) {
		this.ip = ip;
	}


	public int getPortCliente() {
		return portCliente;
	}


	public void setPortCliente(int portCliente) {
		this.portCliente = portCliente;
	}


	public int getPortFuente() {
		return portFuente;
	}


	public void setPortFuente(int portFuente) {
		this.portFuente = portFuente;
	}


	public ArrayList<Cliente> getClientes() {
		return clientes;
	}


	public void setClientes(ArrayList<Cliente> clientes) {
		this.clientes = clientes;
	}


	
}
