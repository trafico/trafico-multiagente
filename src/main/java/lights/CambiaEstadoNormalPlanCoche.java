package lights;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import jadex.bridge.fipa.SFipa;
import jadex.bdi.runtime.IMessageEvent;
import jadex.bdi.runtime.Plan;
import jadex.commons.future.DefaultResultListener;
import jadex.commons.future.IFuture;
import jadex.commons.future.ITerminableFuture;

public class CambiaEstadoNormalPlanCoche extends Plan {

	public CambiaEstadoNormalPlanCoche() {
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
		
		 getBeliefbase().getBelief("estadoSemaforo").setFact(new Integer(cnt));
		 getBeliefbase().getBelief("lineaActual").setFact(new Integer(actual));
		 System.out.println("Pido a los semaforos su servicio: "+actual);
		 
		 IFuture<Collection<IEstadoSemaforoService>> servicioSemaforos = getServiceContainer().getRequiredServices("estadosDeSemaforos");
		 servicioSemaforos.addResultListener(new DefaultResultListener<Collection<IEstadoSemaforoService>>(){
			

			public void resultAvailable(Collection<IEstadoSemaforoService> result) {
				for(Iterator<IEstadoSemaforoService> it=result.iterator(); it.hasNext(); )
				{
					IEstadoSemaforoService cs = it.next();
					ITerminableFuture res = (ITerminableFuture) cs.getPosicion();
					PosicionSemaforo miPos  = (PosicionSemaforo) res.get();
					//cs.message(agent.getComponentIdentifier().getName(), "Hello");
					System.out.println("Yo"+ getComponentIdentifier().getName() +"Mi posicion recibida es: "+miPos);
					
				}
				
			}

		 });
		 
	}

}
