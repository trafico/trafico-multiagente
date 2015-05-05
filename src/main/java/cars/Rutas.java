package cars;

import java.util.ArrayList;

public class Rutas {
	
	public static void getRutaDijstra(int mapa[][], int orig, int dest){
		boolean [] definitivos= iniciarDef(mapa.length);
		ArrayList<int[]> pasos= new ArrayList<int[]>();
		int [] paso0= marcarInf(mapa.length);
		paso0[orig]=0;
		definitivos[orig]=true;
		pasos.add(paso0);
		definitivos[orig]=true;
		boolean bandera= true;
		int paso=0;
		int nant= orig;
		int def= orig;
		int disacum=0;
		while(bandera){
			paso=paso+1;
			int [] dist= new int [mapa.length];
			dist= pasos.get(paso-1).clone();
			for(int i=0; i<dist.length; i=i+1){
				if (!definitivos[i]){
				if (dist[i]==-1 && mapa[def][i]!=0 && i!=nant){
					dist[i]=mapa[def][i]+disacum;
				}
				else if(dist[i]!=-1 && mapa[def][i]+disacum<dist[i] && i!= nant){
					dist[i]= mapa[def][i]+disacum;
				}
				
				}
			}
			int min= 1000000;
			for (int i=0; i<dist.length; i=i+1){
				if(definitivos[i]=true)
					continue;
				else{
					if (dist[i]<min){
						min=dist[i];
						def= i;
					}
				}
			}
			nant= def;
		}
	}
	
	public static int [] marcarInf(int tam){
		int [] aux= new int[tam];
		for(int i=0; i<aux.length; i=i+1){
			aux[i]=-1;
		}
		return aux;
	}
	
	public static boolean [] iniciarDef(int tam){
		boolean [] aux= new boolean[tam];
		for (int i=0; i<aux.length; i=i+1){
			aux[i]= false;
		}
		return aux;
	}
}
