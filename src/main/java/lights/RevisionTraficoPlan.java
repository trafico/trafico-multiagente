package lights;

import jadex.bridge.fipa.SFipa;
import jadex.bdi.runtime.IMessageEvent;
import jadex.bdi.runtime.Plan;


public class RevisionTraficoPlan extends Plan {
	
	int radioVision = 10;

	
	public RevisionTraficoPlan() {
	       // Initialization code.
		System.out.println("Revisi—n tr‡fico: "+this);
	}

	   public void body() {
	       // Plan code.
			
			int posX = ((Integer)getBeliefbase().getBelief("posX").getFact()).intValue(); //Recupero el estado del semaro
			int posY = ((Integer)getBeliefbase().getBelief("posY").getFact()).intValue(); //Recupero el estado del semaro
			
			//System.out.println("Estado Actual del Semaforo: "+cnt);
			
	   }
	   
	   /**
	    * Revisa diez cuadros arriba, diez abajo (default) para saber si hay trafico. 
	    * @param posX
	    * @param posY
	    * @return
	    */
	   protected int revisionNorte(int posX, int posY){
		   
		   
		   
		   return 0;
	   }

}
