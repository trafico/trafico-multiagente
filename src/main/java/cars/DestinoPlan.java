package cars;

import java.util.Collection;
import java.util.Iterator;

import jadex.bdi.runtime.Plan;
import jadex.commons.future.DefaultResultListener;
import jadex.commons.future.IFuture;
import jadex.commons.future.ITerminableFuture;
import lights.IEstadoSemaforoService;
import lights.PosicionSemaforo;

public class DestinoPlan extends Plan {
	PosicionSemaforo sp;
	
	public DestinoPlan(){
		super();
		EstadoAuto ea= new EstadoAuto("", 0, 0, 0, 0, 0, 0, true);
		getBeliefbase().getBelief("estadoAuto").setFact(ea);
		System.out.println("jolines");
	}

	@Override
	public void body() {
		while(true){
		try{
			IFuture<Collection<IEstadoSemaforoService>> servicioSem = getServiceContainer().getRequiredServices("estadoSemaforo");
			servicioSem.addResultListener(new DefaultResultListener<Collection<IEstadoSemaforoService>>(){

				public void resultAvailable(
						Collection<IEstadoSemaforoService> result) {
					// TODO Auto-generated method stub
					for(Iterator<IEstadoSemaforoService> it=result.iterator(); it.hasNext(); )
					{
						IEstadoSemaforoService cs = it.next();
						ITerminableFuture res = cs.getPosicion();
						PosicionSemaforo miPos  = (PosicionSemaforo) res.get();
						System.out.println(miPos.getPosX()+"   "+miPos.getPosY());
					}
					
				}
				
			});
		}
		catch (Exception e){
			
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}

	}

}