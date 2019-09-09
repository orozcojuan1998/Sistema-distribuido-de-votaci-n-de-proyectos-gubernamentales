package distri;



public class Cliente {

	private String ID;
	private Proxy p;
	

	public Cliente(String usuario) {
		this.ID = usuario;
		
		
	}
	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}
	public Proxy getP() {
		return p;
	}
	public void setP(Proxy p) {
		this.p = p;
	}

}
