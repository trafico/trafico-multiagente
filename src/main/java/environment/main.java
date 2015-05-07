package environment;

import jadex.extension.envsupport.math.Vector2Double;

import java.util.Random;

public class main {
	public static void main(String [] args){
		int[] vel= new int[36];
		Random randNum = new Random();
		
		for (int i=0; i<vel.length; i=i+1){
			vel[i]=i+1;
		}
//		GrafoCalles gc=new GrafoCalles(vel);
		System.out.println(GrafoCalles.isCreado());
		GrafoCalles.hacerGrafoCalles(vel);
		int [][] mat= GrafoCalles.getGrafo();
		System.out.println(GrafoCalles.isCreado());
		
		for(int i=0; i<mat.length; i=i+1){
			for(int j=0; j<mat[0].length; j=j+1){
				System.out.print(mat[i][j]+"  ");
			}
			System.out.println();
		}
		/*int numInt= (int)Math.pow(Math.sqrt(vel.length)/2,2);
		for (int i=0; i<50; i=i+1){
			boolean disp = gc.estaDisponible((int)(randNum.nextDouble()*numInt),(int)(randNum.nextDouble()*numInt));
			System.out.println(disp);
		}*/
		
		//boolean disp = gc.DisponiblePos(new Vector2Double(11.5, 16),2);
		//boolean disp2 = gc.DisponiblePos(new Vector2Double(21.5, 26),2);
		//boolean disp3 = gc.DisponiblePos(new Vector2Double(0.5, 6),2);
		
		
		
		
	}
	//pruebas de grafo inicial...
}
