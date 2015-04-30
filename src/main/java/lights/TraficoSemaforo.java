package lights;

public class TraficoSemaforo {
	
	int activo;
	int numeroAutos;
	String direccion;
	
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public int isActivo() {
		return activo;
	}
	public void setActivo(int activo) {
		this.activo = activo;
	}
	public int getNumeroAutos() {
		return numeroAutos;
	}
	public void setNumeroAutos(int numeroAutos) {
		this.numeroAutos = numeroAutos;
	}
	
	@Override
	public String toString() {
		return "Trafico [activo=" + activo + ", numeroAutos=" + numeroAutos
				+ ", direccion=" + direccion + "]";
	}
	
	public TraficoSemaforo(int activo, int numeroAutos, String direccion) {
		super();
		this.activo = activo;
		this.numeroAutos = numeroAutos;
		this.direccion = direccion;
	}
	
	
	
}
