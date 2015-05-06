package cars;
import java.util.ArrayList;

public class Rutas {
	
	public static String devolverDir(String dir){
		String res="";
		for (int i=0;i<10; i=i+1){
			res= res+dir+" ";
		}
		return res;
	}
	
	public static String devolverRuta(int [][] mapa, int[] camino){
		String ruta="";
		int tam= (int)Math.sqrt(mapa.length);
		int actual= camino[0];
		for (int i=1; i<camino.length; i=i+1){
			int sig= camino[i];
			String dir="";
			if(sig==actual+1 || sig== actual-(tam-1))
				dir="e";
			else if(sig== actual-1 || sig== actual+(tam-1))
				dir= "o";
			else if(sig== actual-tam || sig== ((tam*(tam-1))+actual))
				dir= "n";
			else if(sig== actual+tam || sig== (actual-(tam*(tam-1))))
				dir= "s";
			ruta= ruta+ devolverDir(dir);
			actual=sig;
		}

		return ruta;
	}
	
	public static int[] devolverPosibles(int pos, int ant, int [][]mapa){
		int [] posibles= new int[3];
		int cont=0;
		for (int i=0; i<mapa.length; i=i+1){
			if(mapa[pos][i]>0 && i!= ant){
				posibles[cont]= i;
				cont=cont+1;
			}
			if (cont==3)
				break;
		}
		return posibles;
	}
	
	public static String getRutaRandom(int mapa[][], int orig, int dest){
		//int tam= (int)Math.sqrt(mapa.length);
		int pos= orig;
		int ant= orig;
		ArrayList<Integer> camino= new ArrayList<Integer>();
		camino.add(new Integer(pos));
		while(pos!= dest){
			int [] posibles= devolverPosibles(pos, ant, mapa);
			int op= (int)Math.round(Math.random()*(posibles.length-1));
			camino.add(new Integer(posibles[op]));
			ant= pos;
			pos= posibles[op];
		}
		int tam= camino.size();
		int [] cam= new int [tam];
		for (int i=0; i<tam; i=i+1){
			cam[i]= camino.get(i).intValue();
			//System.out.println(cam[i]);
		}
		return devolverRuta(mapa, cam);
	}
	
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
			int [] dist= pasos.get(paso-1).clone();
			for(int i=0; i<dist.length; i=i+1){
				if (!definitivos[i]){
				if (dist[i]==-1 && mapa[def][i]!=0){
					dist[i]=mapa[def][i]+disacum;
				}
				else if(dist[i]!=-1 && mapa[def][i]!=0 && mapa[def][i]+disacum<dist[i]){
					dist[i]= mapa[def][i]+disacum;
				}
				
				}
			}
			imprimirPaso(dist);
			int min= 1000000;
			for (int i=0; i<dist.length; i=i+1){
				if(definitivos[i])
					continue;
				else{
					if (dist[i]!=-1 && dist[i]<min){
						min=dist[i];
						def= i;
					}
				}
			}
			System.out.println(def);
			
			definitivos[def]=true;
			nant= def;
			pasos.add(dist);
			if (isCondicion(definitivos)){
				bandera=false;
			}
			disacum=disacum+dist[def];
		}
	}
	
	public static void imprimirPaso(int [] mat){
		for (int i=0; i<mat.length; i=i+1){
			System.out.print(mat[i]+"   ");
		}
		System.out.println();
	}
	
	public static boolean isCondicion (boolean[] mat){
		boolean res= true;
		for (int i=0; i<mat.length; i=i+1){
//			System.out.println(mat[i]);
			if (!mat[i]){
				res= false;
				break;
			}
		}
		return res;
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
