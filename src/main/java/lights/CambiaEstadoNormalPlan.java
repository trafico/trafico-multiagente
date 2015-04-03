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
		if (cnt == 0)
			cnt = 1;
		else
			cnt = 0;
		 getBeliefbase().getBelief("estadoSemaforo").setFact(new Integer(cnt));
		    System.out.println("Estado semaforo: "+cnt);
	}

}
