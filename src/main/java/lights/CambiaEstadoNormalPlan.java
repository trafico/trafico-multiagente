package lights;

import jadex.bridge.fipa.SFipa;
import jadex.bdi.runtime.IMessageEvent;
import jadex.bdi.runtime.Plan;

public class CambiaEstadoNormalPlan extends Plan {

	public CambiaEstadoNormalPlan() {
		super();
		// TODO Auto-generated constructor stub
		System.out.println("Created: "+this);
	}
	
	public void body() {
		int cnt = ((Integer)getBeliefbase().getBelief("estadoSemaforo").getFact()).intValue();
		int actual =  ((Integer)getBeliefbase().getBelief("lineaActual").getFact()).intValue();
		
		if(actual == 4)
			actual = 1;
		else
			actual = actual + 1;
		
		if (cnt == 0)
			cnt = 1;
		else
			cnt = 0;
		 getBeliefbase().getBelief("estadoSemaforo").setFact(new Integer(cnt));
		 getBeliefbase().getBelief("lineaActual").setFact(new Integer(actual));
		    System.out.println("Estado semaforo: "+actual);
	}

}
