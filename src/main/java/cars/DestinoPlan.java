package cars;

import java.util.Collection;
import java.util.Iterator;

import jadex.bdi.runtime.Plan;
import jadex.bridge.service.types.clock.IClockService;
import jadex.commons.future.DefaultResultListener;
import jadex.commons.future.DelegationResultListener;
import jadex.commons.future.Future;
import jadex.commons.future.IFuture;
import jadex.commons.future.ITerminableFuture;
import lights.IEstadoSemaforoService;
import lights.PosicionSemaforo;

public class DestinoPlan extends Plan {
	PosicionSemaforo sp;
	
	public DestinoPlan(){
		super();
		EstadoAuto ea= new EstadoAuto(0, 0, 0, 0, 0, 0, true);
		getBeliefbase().getBelief("estadoAuto").setFact(ea);
		System.out.println("jolines");
	}

	@Override
	public void body() {
		System.out.println("jolines22");
		while(true){
		try{
			
			final Future ret = new Future();
			
			IFuture<Collection<IEstadoSemaforoService>> servicioSem = getServiceContainer().getRequiredServices("estadoSemaforo");
			servicioSem.addResultListener(new  DelegationResultListener<Collection<IEstadoSemaforoService>>(ret){
//			servicioSem.addResultListener(new DelegationResultListener<IClockService>(ret)){
//			servicioSem.addResultListener(new DelegationResultListener<IClockService>(ret)
					
//				public void resultAvailable(
//						Collection<IEstadoSemaforoService> result) {
//					// TODO Auto-generated method stub
//					for(Iterator<IEstadoSemaforoService> it=result.iterator(); it.hasNext(); )
//					{
//						IEstadoSemaforoService cs = it.next();
//						IFuture<PosicionSemaforo> res = cs.getPosicion();
//						PosicionSemaforo miPos  = (PosicionSemaforo) res.get();
//						System.out.println(miPos.getPosX()+"   "+miPos.getPosY());
//					}
//					
//				}
				
				public void  customResulAvailable(Collection<IEstadoSemaforoService> result) {
					// TODO Auto-generated method stub
					for(Iterator<IEstadoSemaforoService> it=result.iterator(); it.hasNext(); )
					{
						IEstadoSemaforoService cs = it.next();
						IFuture<PosicionSemaforo> res = cs.getPosicion();
						PosicionSemaforo miPos  = (PosicionSemaforo) res.get();
						System.out.println(miPos.getPosX()+"   "+miPos.getPosY());
						super.customResultAvailable(null);
					}
					
				}
				
				public void exceptionOccurred(Exception exception)
				  {
				    exception.printStackTrace();
				  }	
				
			});
			
		}
		catch (Exception e){
			System.out.println(e.getMessage());
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