package lights;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import jadex.bdi.runtime.Plan;
import jadex.commons.future.DefaultResultListener;
import jadex.commons.future.IFuture;
import jadex.commons.future.ITerminableFuture;

/**
 * Este plan manda llamar el servicio de trafico de los demas
 * semaforos.
 * @author josue
 *
 */

public class ComunicacionVecinosPlan extends Plan {
	
	public ComunicacionVecinosPlan() {
		super();
		// TODO Auto-generated constructor stub
		//System.out.println("Created: "+this+" Estado semaforo: "+cnt);
	}

	@Override
	public void body() {
		// TODO Auto-generated method stub
		System.out.println("TENGO TRAFICO");
		IFuture<Collection<ITraficoSemaforoService>> servicioTrafico = getServiceContainer().getRequiredServices("servicioTrafico");
		servicioTrafico.addResultListener(new DefaultResultListener<Collection<ITraficoSemaforoService>>(){
			public void resultAvailable(Collection<ITraficoSemaforoService> result) {
				System.out.println("tra");
				for(Iterator<ITraficoSemaforoService> it=result.iterator(); it.hasNext(); )
				{
					ITraficoSemaforoService cs = it.next();
					ITerminableFuture res = cs.getTraficoSemaforo();
					PaqueteTrafico miPos  = (PaqueteTrafico) res.get();
					
					ArrayList<TraficoSemaforo> listaSemaforo = miPos.getListaTraficoSemaforo();
					int posX_rec = miPos.getPosX();
					int posY_rec = miPos.getPosY();
					
					/**
					 * Implementar la logica de semaforos
					 */
				}
			}
		});
	}
	
	

}
