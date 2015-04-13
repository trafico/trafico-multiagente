package lights;

/**
 * Esta clase tiene la posicion del semaforo y se utiliza
 * como contenedor para ser mandado a los autos.
 * @author josue
 *
 */

public class PosicionSemaforo {
	
	int posX;
	int posY;
	
	public PosicionSemaforo(int posX2, int posY2) {
		super();
		this.posX = posX2;
		this.posY = posY2;
	}

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

	@Override
	public String toString() {
		return "PosicionSemaforo [posX=" + posX + ", posY=" + posY + "]";
	}

	

}
