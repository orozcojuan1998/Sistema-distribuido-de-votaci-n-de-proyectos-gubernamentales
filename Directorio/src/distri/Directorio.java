package distri;

import java.util.ArrayList;

public class Directorio {

	
	private ArrayList<Proxy> proxies;
	private ArrayList<Cliente> clientes;
	
	public Directorio() {
		proxies = new ArrayList<Proxy>();	
		clientes = new ArrayList<Cliente>();
		
		
		ServidorProxies sp = new ServidorProxies(this);
		ServidorClientes sc = new ServidorClientes(this);
		ServidorFuentes sf = new ServidorFuentes(this);
		
		
		sp.start();
		sc.start();
		sf.start();
		
	}
	
	
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Directorio d= new Directorio();
	}


	public Proxy buscarProxy(String ip, int portClient,int portProxy) {
		for (Proxy proxy : proxies) {
			if(proxy.getIp().equals(ip)&&proxy.getPortCliente()==portClient&&proxy.getPortFuente()==portProxy) {
				return proxy;
			}
		}
		return null;
	}


	public ArrayList<Proxy> getProxies() {
		return proxies;
	}


	public void setProxies(ArrayList<Proxy> proxies) {
		this.proxies = proxies;
	}


	public Cliente buscarCliente(String usuario) {
		for (Cliente cliente : clientes) {
			if(cliente.getID().equals(usuario)) {
				return cliente;
			}
		}
		return null;
	}


	public void adicionarCliente(Cliente cliente) {
		clientes.add(cliente);
	}


	public Proxy asignarProxy(Cliente cliente) {

		Proxy menor = proxies.get(0);
		
		for (Proxy proxy: proxies) {
			if(menor.getClientes().size()>proxy.getClientes().size()) {
				menor = proxy;
			}
		}
		return menor;
	}
	
	public synchronized void quitarProxy(Proxy p) {
		for (Proxy proxy: proxies) {
			if(proxy.getIp().equals(p.getIp())&&proxy.getPortCliente()==p.getPortCliente()&&proxy.getPortFuente()==p.getPortFuente()) {
				if(proxies.remove(proxy)) {
					System.out.println("Se quitó un proxy");
				}
			}
		}
		for (Cliente cliente : clientes) {
			if(cliente.getP().equals(p)) {
				cliente.setP(null);
			}
		}
	}

}
