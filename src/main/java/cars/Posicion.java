package cars;

public class Posicion {
	private int direccion; //1:Norte, 2:Este, 3:Sur, 4:Oeste.
	private int pox;
	private int poy;
	
	public Posicion(int x, int y, int d){
		pox= x;
		poy= y;
		direccion= d;
	}
	
	public int getDir(){
		return direccion;
	}
	
	public void setDir(int d){
		direccion= d;
	}
	
	public void setPos(int x, int y){
		pox= x;
		poy= y;
	}
	
	public int getPox(){
		return pox;
	}
	
	public int getPoy(){
		return poy;
	}
	
}
