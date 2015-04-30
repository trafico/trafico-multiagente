package environment;

public class GrafoCalles {
	private int [][] grafo;
	private int [] velocidades;
	private int numCalles;
	private int numInt;
	
	public GrafoCalles(int [] vel){
		velocidades= vel;
		numCalles= velocidades.length;
		numInt= (int)Math.pow(Math.sqrt(numCalles)/2,2);
		grafo= new int[numInt][numInt];
		armarGrafo();
	}
	
	public int [][] getGrafo(){
		return grafo;
	}
	
	private void armarGrafo(){
		llenarCeros();
		armarNorteSur();
		armarEsteOeste();
	}
	
	private void llenarCeros(){
		for (int i=0; i<numInt; i=i+1){
			for (int j=0; j<numInt; j=j+1){
				grafo[i][j]=0;
			}
		}
	}
	
	private void armarEsteOeste(){
		int tope= numCalles;
		int inc= (int)Math.sqrt(numInt);
		int aux=0;
		int x=0;
		for (int i=tope/2; i<tope; i=i+2){
			int y= x;
			for (int j=0; j<inc; j=j+1){
				if(j==inc-1){
					grafo[x][y]=velocidades[i];
					grafo[y][x]=velocidades[i+1];
					x=y+1;
				}
				else{
					grafo[x][x+1]=velocidades[i];
					grafo[x+1][x]=velocidades[i+1];
					x=x+1;
				}
			}
		}
	}
	
	private void armarNorteSur(){
		int tope= numCalles/2;
		int inc= (int)Math.sqrt(numInt);
		int aux=0;
		int x=0;
		for (int i=0; i<tope; i=i+2){
			int y= x;
			for (int j=0; j<inc; j=j+1){
				if(j==inc-1){
					grafo[x][y]=velocidades[i];
					grafo[y][x]=velocidades[i+1];
					x=y+1;
				}
				else{
					grafo[x][x+inc]=velocidades[i];
					grafo[x+inc][x]=velocidades[i+1];
					x=x+inc;
				}
			}
		}
	}

}
