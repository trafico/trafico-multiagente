package cars;

public class EstadoAuto {
	private double velocidad;
	private int direccion;
	private boolean status;
	private double pox;
	private double poy;
	private double xfin;
	private double yfin;
	
	public EstadoAuto(double v, int d, double px, double py, double xf, double yf, boolean s){
		velocidad= v;
		direccion= d;
		pox= px;
		poy= py;
		xfin= xf;
		yfin= yf;
		status= s;
	}
	
	public void setStatus(boolean s){
		status= s;
	}
	
	public boolean isStatus(){
		return status;
	}
	
	public void setYFin(double y){
		yfin= y;
	}
	
	public double getYFin(){
		return yfin;
	}
	
	public void setXFin(double x){
		xfin= x;
	}
	
	public double getXFin(){
		return xfin;
	}
	
	public void setVel(double v){
		velocidad= v;
	}
	
	public double getVel(){
		return velocidad;
	}
	
	public void setDir(int d){
		direccion= d;
	}
	
	public int getDir(){
		return direccion;
	}
	
	public void setPox(double p){
		pox= p;
	}
	
	public double getPox(){
		return pox;
	}
	
	public void setPoy(double p){
		poy= p;
	}
	
	public double getPoy(){
		return poy;
	}

}