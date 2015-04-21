
package lights;

import java.util.Collection;
import java.util.Iterator;

import jadex.bridge.fipa.SFipa;
import jadex.bridge.service.search.ServiceNotFoundException;
import jadex.bdi.runtime.IMessageEvent;
import jadex.bdi.runtime.Plan;
import jadex.commons.future.DefaultResultListener;
import jadex.commons.future.IFuture;
import jadex.commons.future.ITerminableFuture;

/**
 * Este plan aumenta el contador de segundos de cada semarofo, es el 
 * plan por default que tiene un semaforo. Cada iteracion aumenta el 
 * contador que representa la creencia del semaforo
 * @author josue
 *
 */

public class AutoSimpleContadorPlan extends Plan {

	public AutoSimpleContadorPlan() {
		super();
		// TODO Auto-generated constructor stub
		int cnt = ((Integer)getBeliefbase().getBelief("estadoSemaforo").getFact()).intValue();
		System.out.println("Created: "+this+" Estado semaforo: "+cnt);
	}

	public void body() {
		while(true) {
			int cnt = ((Integer)getBeliefbase().getBelief("segundosTrans").getFact()).intValue();
			if (cnt == 10)
				cnt = 0;
			else 
				cnt = cnt +1;
			getBeliefbase().getBelief("segundosTrans").setFact(new Integer(cnt));
			try {
				Thread.sleep(1000);
				//Thread.sleep(500);                 //1000 millisegundos un segundo : )
			} catch(InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			//System.out.println("Segundos transcurridos:"+(cnt+1));

		}
	}



}
