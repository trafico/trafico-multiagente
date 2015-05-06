package environment;

import java.util.Random;

public class GrafoCalles {
	private int [][] grafo;
	private boolean [][] disponibilidad;
	private int [] velocidades;
	private int numCalles;
	private int callesCerradas;
	private int numInt;
	private int contador = 0;
	private Random randNum = new Random();
	
	public GrafoCalles(int [] vel){
		velocidades= vel;
		numCalles= velocidades.length;
		numInt= (int)Math.pow(Math.sqrt(numCalles)/2,2);
		grafo= new int[numInt][numInt];
		disponibilidad= new boolean[numInt][numInt];
		armarGrafo();
	}
	//Hacemos al grafo o mapa inicial para los coches..
	public int [][] getGrafo(){
		return grafo;
	}
	
	public boolean estaDisponible (int fil, int col){
		contador += 1;
		if (contador > 5){
			llenarDisponibles();
			contador = 0;
			callesCerradas = (int)(randNum.nextDouble()*8);
			for (int i=0; i<callesCerradas; i++){
				disponibilidad[(int)(randNum.nextDouble()*numInt)][(int)(randNum.nextDouble()*numInt)] = false;
			}		
		}
		boolean disponible = disponibilidad[fil][col];

		return disponible;	
	}
	
	private void armarGrafo(){
		llenarCeros();
		llenarDisponibles();
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
	
	private void llenarDisponibles(){
		for (int i=0; i<numInt; i=i+1){
			for (int j=0; j<numInt; j=j+1){
				disponibilidad[i][j]=true;
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
