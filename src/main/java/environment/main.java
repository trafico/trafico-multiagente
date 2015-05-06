package environment;

import java.util.Random;

public class main {
	public static void main(String [] args){
		int[] vel= new int[36];
		Random randNum = new Random();
		
		for (int i=0; i<vel.length; i=i+1){
			vel[i]=i+1;
		}
		GrafoCalles gc=new GrafoCalles(vel);
		int [][] mat= gc.getGrafo();
		
		/*for(int i=0; i<mat.length; i=i+1){
			for(int j=0; j<mat[0].length; j=j+1){
				System.out.print(mat[i][j]+"  ");
			}
			System.out.println();
		}*/
		int numInt= (int)Math.pow(Math.sqrt(vel.length)/2,2);
		for (int i=0; i<50; i=i+1){
			boolean disp = gc.estaDisponible((int)(randNum.nextDouble()*numInt),(int)(randNum.nextDouble()*numInt));
			System.out.println(disp);
		}
		
		
	}
	//pruebas de grafo inicial...
}
