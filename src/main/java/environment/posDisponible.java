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
	static IVector2[] pos = new IVector2[100];

	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		coord[0] = new ArrayList<Double>();
		coord[1] = new ArrayList<Double>();
		llenarLista();
		crearVectores();
		//Map a = obtenerPosicion();
		obtenerPosicion();
	}*/
	

	public static void llenarLista() {
		// TODO Auto-generated method stub
		
		coord[0].add(1.0);coord[1].add(1.0);
		coord[0].add(11.0);coord[1].add(1.0);
		coord[0].add(21.0);coord[1].add(1.0);
		coord[0].add(31.0);coord[1].add(1.0);
		coord[0].add(41.0);coord[1].add(1.0);
		coord[0].add(51.0);coord[1].add(1.0);
		coord[0].add(61.0);coord[1].add(1.0);
		coord[0].add(71.0);coord[1].add(1.0);
		coord[0].add(81.0);coord[1].add(1.0);
		coord[0].add(91.0);coord[1].add(1.0);
		coord[0].add(1.0);coord[1].add(11.0);
		coord[0].add(11.0);coord[1].add(11.0);
		coord[0].add(21.0);coord[1].add(11.0);
		coord[0].add(31.0);coord[1].add(11.0);
		coord[0].add(41.0);coord[1].add(11.0);
		coord[0].add(51.0);coord[1].add(11.0);
		coord[0].add(61.0);coord[1].add(11.0);
		coord[0].add(71.0);coord[1].add(11.0);
		coord[0].add(81.0);coord[1].add(11.0);
		coord[0].add(91.0);coord[1].add(11.0);
		coord[0].add(1.0);coord[1].add(21.0);
		coord[0].add(11.0);coord[1].add(21.0);
		coord[0].add(21.0);coord[1].add(21.0);
		coord[0].add(31.0);coord[1].add(21.0);
		coord[0].add(41.0);coord[1].add(21.0);
		coord[0].add(51.0);coord[1].add(21.0);
		coord[0].add(61.0);coord[1].add(21.0);
		coord[0].add(71.0);coord[1].add(21.0);
		coord[0].add(81.0);coord[1].add(21.0);
		coord[0].add(91.0);coord[1].add(21.0);
		coord[0].add(1.0);coord[1].add(21.0);
		coord[0].add(11.0);coord[1].add(31.0);
		coord[0].add(21.0);coord[1].add(31.0);
		coord[0].add(31.0);coord[1].add(31.0);
		coord[0].add(41.0);coord[1].add(31.0);
		coord[0].add(51.0);coord[1].add(31.0);
		coord[0].add(61.0);coord[1].add(31.0);
		coord[0].add(71.0);coord[1].add(31.0);
		coord[0].add(81.0);coord[1].add(31.0);
		coord[0].add(91.0);coord[1].add(31.0);
		coord[0].add(1.0);coord[1].add(41.0);
		coord[0].add(11.0);coord[1].add(41.0);
		coord[0].add(21.0);coord[1].add(41.0);
		coord[0].add(31.0);coord[1].add(41.0);
		coord[0].add(41.0);coord[1].add(41.0);
		coord[0].add(51.0);coord[1].add(41.0);
		coord[0].add(61.0);coord[1].add(41.0);
		coord[0].add(71.0);coord[1].add(41.0);
		coord[0].add(81.0);coord[1].add(41.0);
		coord[0].add(91.0);coord[1].add(41.0);
		coord[0].add(1.0);coord[1].add(51.0);
		coord[0].add(11.0);coord[1].add(51.0);
		coord[0].add(21.0);coord[1].add(51.0);
		coord[0].add(31.0);coord[1].add(51.0);
		coord[0].add(41.0);coord[1].add(51.0);
		coord[0].add(51.0);coord[1].add(51.0);
		coord[0].add(61.0);coord[1].add(51.0);
		coord[0].add(71.0);coord[1].add(51.0);
		coord[0].add(81.0);coord[1].add(51.0);
		coord[0].add(91.0);coord[1].add(51.0);
		coord[0].add(1.0);coord[1].add(61.0);
		coord[0].add(11.0);coord[1].add(61.0);
		coord[0].add(21.0);coord[1].add(61.0);
		coord[0].add(31.0);coord[1].add(61.0);
		coord[0].add(41.0);coord[1].add(61.0);
		coord[0].add(51.0);coord[1].add(61.0);
		coord[0].add(61.0);coord[1].add(61.0);
		coord[0].add(71.0);coord[1].add(61.0);
		coord[0].add(81.0);coord[1].add(61.0);
		coord[0].add(91.0);coord[1].add(61.0);
		coord[0].add(1.0);coord[1].add(71.0);
		coord[0].add(11.0);coord[1].add(71.0);
		coord[0].add(21.0);coord[1].add(71.0);
		coord[0].add(31.0);coord[1].add(71.0);
		coord[0].add(41.0);coord[1].add(71.0);
		coord[0].add(51.0);coord[1].add(71.0);
		coord[0].add(61.0);coord[1].add(71.0);
		coord[0].add(71.0);coord[1].add(71.0);
		coord[0].add(81.0);coord[1].add(71.0);
		coord[0].add(91.0);coord[1].add(71.0);
		coord[0].add(1.0);coord[1].add(81.0);
		coord[0].add(11.0);coord[1].add(81.0);
		coord[0].add(21.0);coord[1].add(81.0);
		coord[0].add(31.0);coord[1].add(81.0);
		coord[0].add(41.0);coord[1].add(81.0);
		coord[0].add(51.0);coord[1].add(81.0);
		coord[0].add(61.0);coord[1].add(81.0);
		coord[0].add(71.0);coord[1].add(81.0);
		coord[0].add(81.0);coord[1].add(81.0);
		coord[0].add(91.0);coord[1].add(81.0);
		coord[0].add(1.0);coord[1].add(91.0);
		coord[0].add(11.0);coord[1].add(91.0);
		coord[0].add(21.0);coord[1].add(91.0);
		coord[0].add(31.0);coord[1].add(91.0);
		coord[0].add(41.0);coord[1].add(91.0);
		coord[0].add(51.0);coord[1].add(91.0);
		coord[0].add(61.0);coord[1].add(91.0);
		coord[0].add(71.0);coord[1].add(91.0);
		coord[0].add(81.0);coord[1].add(91.0);
		coord[0].add(91.0);coord[1].add(91.0);
		
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
		
		for (int i=0;i<pos.length;i++){
			position.add(pos[i]);
			System.out.println("position: "+pos[i]);
		}
		
		Random randCoord = new Random();
		
		position.add(pos[(int)(randCoord.nextDouble()*pos.length)]);
		
		
		//position.randomX(distance.getX(), position.getX());
		//position.randomY(distance.getY(), position.getY());
		
		System.out.println("position: "+position);
		
		coord[0].clear();
		coord[1].clear();
		
		Map	ret	= new HashMap();
		
		ret.put("position", position);
		
		
		return ret;
		
	}
	
	
/*public static  void obtenerPosicion(){
		
		
		IVector2 position = Vector2Double.ZERO;

		Random randCoord = new Random();
		
		position.add(pos[(int)(randCoord.nextDouble()*pos.length)]);
		
		
		//position.randomX(distance.getX(), position.getX());
		//position.randomY(distance.getY(), position.getY());
		
		System.out.println("position: "+position);
		
		coord[0].clear();
		coord[1].clear();		
		
	}*/

}
