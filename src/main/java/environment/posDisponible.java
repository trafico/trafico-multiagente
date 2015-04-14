package environment;

import java.util.ArrayList;

public class posDisponible {
	
	static ArrayList<Integer>[] coord = new ArrayList[3];

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		coord[0] = new ArrayList<Integer>();
		coord[1] = new ArrayList<Integer>();
		llenarLista();
		
		

	}

	public static void llenarLista() {
		// TODO Auto-generated method stub
		
		coord[0].add(123);coord[1].add(163);
		coord[0].add(23);coord[1].add(23789);
		
				
		
	}

}
