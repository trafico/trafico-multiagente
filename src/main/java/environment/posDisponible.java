package environment;

import jadex.extension.envsupport.math.IVector2;
import jadex.extension.envsupport.math.Vector2Double;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class posDisponible 
{
	
	static ArrayList<Double>[] coord = new ArrayList[3];
	static IVector2[] pos = new IVector2[7];

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		coord[0] = new ArrayList<Double>();
		coord[1] = new ArrayList<Double>();
		llenarLista();
		crearVectores();
		Map a = obtenerPosicion();
		
	}
	

	public static void llenarLista() {
		// TODO Auto-generated method stub
		
		coord[0].add(7.0);coord[1].add(1.5);
		coord[0].add(15.0);coord[1].add(1.5);
		coord[0].add(21.0);coord[1].add(1.5);
		coord[0].add(3.0);coord[1].add(11.5);
		coord[0].add(12.0);coord[1].add(11.5);
		coord[0].add(19.0);coord[1].add(11.5);
		coord[0].add(27.0);coord[1].add(11.5);
	}
	
	private static void crearVectores() {
		// TODO Auto-generated method stub
		
		for (int i=0;i<coord[0].size();i++){
			pos[i]= Vector2Double.getVector2(coord[0].get(i),coord[1].get(i));
			//System.out.println(pos[i]);
		}
		
	}
	
	public static Map obtenerPosicion(){
		
		
		IVector2 position = Vector2Double.ZERO;
		coord[0] = new ArrayList<Double>();
		coord[1] = new ArrayList<Double>();
		
		llenarLista();
		crearVectores();
		
		/*for (int i=0;i<pos.length;i++){
			position.add(pos[i]);
			System.out.println("position: "+pos[i]);
		}*/
		
		Random randCoord = new Random();
		
		position.add(pos[(int)(randCoord.nextDouble()*pos.length)]);
		
		
		//position.randomX(distance.getX(), position.getX());
		//position.randomY(distance.getY(), position.getY());
		
		System.out.println("position: "+position);
		
		Map	ret	= new HashMap();
		
		ret.put("position", position);
		
		return ret;
		
	}	

}
