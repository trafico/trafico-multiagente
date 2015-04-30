package lights;

public class PosicionAuto {
	
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
	
	public PosicionAuto(int posX, int posY) {
		super();
		this.posX = posX;
		this.posY = posY;
	}
	@Override
	public String toString() {
		return "PosicionAuto [posX=" + posX + ", posY=" + posY + "]";
	}
	
	
	

}
