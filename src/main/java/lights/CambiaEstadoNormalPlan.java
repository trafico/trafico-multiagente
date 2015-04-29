package lights;

import jadex.bridge.fipa.SFipa;
import jadex.bdi.runtime.IMessageEvent;
import jadex.bdi.runtime.Plan;

public class CambiaEstadoNormalPlan extends Plan {

	public CambiaEstadoNormalPlan() {
		super();
		// TODO Auto-generated constructor stub
		//System.out.println("Created: "+this);
	}
	
	public void body() {
		int cnt = ((Integer)getBeliefbase().getBelief("estadoSemaforo").getFact()).intValue();
		int actual =  ((Integer)getBeliefbase().getBelief("lineaActual").getFact()).intValue();
		
		if(actual == 4)
			actual = 1;
		else
			actual = actual + 1;
		
		 getBeliefbase().getBelief("estadoSemaforo").setFact(new Integer(cnt));
		 getBeliefbase().getBelief("lineaActual").setFact(new Integer(actual));
		 switch (actual) {
		 	case 1: System.out.println("Linea verde estado semaforo: Norte "); break;
		 	case 2: System.out.println("Linea verde estado semaforo: Este "); break;
		 	case 3: System.out.println("Linea verde estado semaforo: Sur "); break;
		 	case 4: System.out.println("Linea verde estado semaforo: Oeste "); break;
		 }
			 
		
	}

}
