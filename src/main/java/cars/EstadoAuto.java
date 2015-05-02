package cars;

public class EstadoAuto {
	private int velocidad;
	private int direccion;
	private boolean status;
	private int pox;
	private int poy;
	private int xfin;
	private int yfin;
	
	public EstadoAuto(int v, int d, int px, int py, int xf, int yf, boolean s){
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
	
	public void setYFin(int y){
		yfin= y;
	}
	
	public int getYFin(){
		return yfin;
	}
	
	public void setXFin(int x){
		xfin= x;
	}
	
	public int getXFin(){
		return xfin;
	}
	
	public void setVel(int v){
		velocidad= v;
	}
	
	public int getVel(){
		return velocidad;
	}
	
	public void setDir(int d){
		direccion= d;
	}
	
	public int getDir(){
		return direccion;
	}
	
	public void setPox(int p){
		pox= p;
	}
	
	public int getPox(){
		return pox;
	}
	
	public void setPoy(int p){
		poy= p;
	}
	
	public int getPoy(){
		return poy;
	}

}