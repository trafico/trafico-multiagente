package lights;

import java.util.ArrayList;

public class PaqueteTrafico {
	
	ArrayList<TraficoSemaforo> listaTraficoSemaforo = new ArrayList<TraficoSemaforo>();
	int posX;
	int posY;

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public PaqueteTrafico(ArrayList<TraficoSemaforo> listaTraficoSemaforo,
			int posX, int posY) {
		super();
		this.listaTraficoSemaforo = listaTraficoSemaforo;
		this.posX = posX;
		this.posY = posY;
	}

	public ArrayList<TraficoSemaforo> getListaTraficoSemaforo() {
		return listaTraficoSemaforo;
	}

	public void setListaTraficoSemaforo(ArrayList<TraficoSemaforo> listaTraficoSemaforo) {
		this.listaTraficoSemaforo = listaTraficoSemaforo;
	}
	
	public void agregarTrafico(TraficoSemaforo nuevo){
		this.listaTraficoSemaforo.add(nuevo);
	}

	public PaqueteTrafico(ArrayList<TraficoSemaforo> listaTraficoSemaforo) {
		super();
		this.listaTraficoSemaforo = listaTraficoSemaforo;
	}

	public PaqueteTrafico(int posX, int posY) {
		super();
		this.posX = posX;
		this.posY = posY;
	}

	@Override
	public String toString() {
		return "PaqueteTrafico [listaTraficoSemaforo=" + listaTraficoSemaforo
				+ ", posX=" + posX + ", posY=" + posY + "]";
	}

}
