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
		int tope= numCalles/2;
		int inc= (int)Math.sqrt(numInt);
		int aux=0;
		int aux1= 0;
		int aux2=0;
		for (int i=tope; i<numCalles; i=i+2){
			aux2= aux1+1;
			if(aux2>= aux+inc){
				aux2= aux;
				aux= aux+inc;
			}
			grafo[aux1][aux2]= velocidades[i];
			grafo[aux2][aux1]= velocidades[i+1];
			aux1= aux1+1;
		}
	}
	
	private void armarNorteSur(){
		int tope= numCalles/2;
		int inc= (int)Math.sqrt(numInt);
		int aux=0;
		int aux1= 0;
		int aux2=0;
		for (int i=0; i<tope; i=i+2){
			aux2= aux1+inc;
			if(aux2>= numInt){
				aux2= aux;
				aux= aux+1;
			}
			grafo[aux1][aux2]= velocidades[i];
			grafo[aux2][aux1]= velocidades[i+1];
			aux1= aux1+inc;
			if(aux1>=numInt)
				aux1=aux;
		}
	}

}
