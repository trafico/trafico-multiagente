package environment;

public class main {
	public static void main(String [] args){
		int[] vel= new int[36];
		for (int i=0; i<vel.length; i=i+1){
			vel[i]=i+1;
		}
		GrafoCalles gc=new GrafoCalles(vel);
		int [][] mat= gc.getGrafo();
		
		for(int i=0; i<mat.length; i=i+1){
			for(int j=0; j<mat[0].length; j=j+1){
				System.out.print(mat[i][j]+"  ");
			}
			System.out.println();
		}
		
	}
}
