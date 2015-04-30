
package lights;

import java.util.Collection;
import java.util.Iterator;

import jadex.bridge.fipa.SFipa;
import jadex.bridge.service.RequiredServiceInfo;
import jadex.bridge.service.search.ServiceNotFoundException;
import jadex.bdi.runtime.IMessageEvent;
import jadex.bdi.runtime.Plan;
import jadex.bdiv3.BDIAgent;
import jadex.commons.future.DefaultResultListener;
import jadex.commons.future.IFuture;
import jadex.commons.future.ITerminableFuture;
import jadex.commons.future.ITerminableIntermediateFuture;
import jadex.micro.annotation.Agent;

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
		
		
		//while(true) {
			
			/* Leer primero el semaforo, por medio de servicio y obtener su 
			 * estado si estan en mi vecindad.
			 */
			int posX = ((Integer)getBeliefbase().getBelief("posX").getFact()).intValue();
			int posY = ((Integer)getBeliefbase().getBelief("posY").getFact()).intValue();
			System.out.println("Hola mi posici—n es: "+posX+","+posY);
			
			//System.out.println( getServiceContainer().searchServices(IPosicionAutoService.class).getIntermediateResults());
			RequiredServiceInfo[] bd = getServiceContainer().getRequiredServiceInfos();
			bd[0].getName();
			System.out.println(bd[0].getName() );
			
			//ITerminableIntermediateFuture<Object> servicio = getServiceContainer().getRequiredServices("semEstado");
			
			IFuture<Collection<IEstadoSemaforoService>>	chatservices	= getServiceContainer().getRequiredServices("semEstado");
			chatservices.addResultListener(new DefaultResultListener<Collection<IEstadoSemaforoService>>()
			{
				public void resultAvailable(Collection<IEstadoSemaforoService> result)
				{
					for(Iterator<IEstadoSemaforoService> it=result.iterator(); it.hasNext(); )
					{
						IEstadoSemaforoService cs = it.next();
						cs.message();
					}
				}
			});
			
			
			//IFuture<Collection<IEstadoSemaforoService>> servicioSemaforos = getServiceContainer().getRequiredServices("semEstado");
			/*
			servicioSemaforos.addResultListener(new DefaultResultListener<Collection<IEstadoSemaforoService>>(){
				
				public void resultAvailable(Collection<IEstadoSemaforoService> result) {
					
					int posX = ((Integer)getBeliefbase().getBelief("posX").getFact()).intValue();
					int posY = ((Integer)getBeliefbase().getBelief("posY").getFact()).intValue();
					String dir = ((String)getBeliefbase().getBelief("direccion").getFact());
					System.out.println("Hola mi posici—n2 es"+posX+","+posY);
					
					
					for(Iterator<IEstadoSemaforoService> it=result.iterator(); it.hasNext(); )
					{
						IEstadoSemaforoService cs = it.next();
						ITerminableFuture res = cs.getPosicion();
						PosicionSemaforo miPos  = (PosicionSemaforo) res.get();
						int posX_rec = miPos.getPosX();
						int posY_rec = miPos.getPosY();
						String dir_rec = miPos.getEstadoActual();
						
						if(dir.equals("norte")){
							if(posY-1 == posY_rec){
								if(dir_rec.equals("norte"))
									avanzarNorte();
								else
									esperar();
							}
						}
						
						if(dir.equals("sur")){
							if(posY+1 == posY_rec){
								if(dir_rec.equals("sur"))
									avanzarSur();
								else
									esperar();
							}
						}
						
						if(dir.equals("este")){
							if(posX+1 == posX_rec){
								if(dir_rec.equals("este"))
									avanzarEste();
								else
									esperar();
							}
						}
						
						if(dir.equals("oeste")){
							if(posX+1 == posX_rec){
								if(dir_rec.equals("oeste"))
									avanzarOeste();
								else
									esperar();
							}
						}
						
					}
				}
				
			});
			
			try {
				Thread.sleep(1000);
				//Thread.sleep(500);                 //1000 millisegundos un segundo : )
			} catch(InterruptedException ex) {
				Thread.currentThread().interrupt();
			}*/
			//System.out.println("Segundos transcurridos:"+(cnt+1));
		/*	try {
				Thread.sleep(1000);
				//Thread.sleep(500);                 //1000 millisegundos un segundo : )
			} catch(InterruptedException ex) {
				Thread.currentThread().interrupt();
			}

		}*/
	}
	
	public void avanzarNorte(){
		int cnt = ((Integer)getBeliefbase().getBelief("tiempoDestino").getFact()).intValue();
		cnt = cnt +1;
		getBeliefbase().getBelief("tiempoDestino").setFact(new Integer(cnt));
		int posY = ((Integer)getBeliefbase().getBelief("posY").getFact()).intValue();
		posY = posY -1;
		getBeliefbase().getBelief("posY").setFact(new Integer(posY));
	}
	
	public void avanzarOeste(){
		int cnt = ((Integer)getBeliefbase().getBelief("tiempoDestino").getFact()).intValue();
		cnt = cnt +1;
		getBeliefbase().getBelief("tiempoDestino").setFact(new Integer(cnt));
		int posY = ((Integer)getBeliefbase().getBelief("posX").getFact()).intValue();
		posY = posY -1;
		getBeliefbase().getBelief("posX").setFact(new Integer(posY));
	}
	
	public void avanzarSur(){
		int cnt = ((Integer)getBeliefbase().getBelief("tiempoDestino").getFact()).intValue();
		cnt = cnt +1;
		getBeliefbase().getBelief("tiempoDestino").setFact(new Integer(cnt));
		int posY = ((Integer)getBeliefbase().getBelief("posY").getFact()).intValue();
		posY = posY + 1;
		getBeliefbase().getBelief("posY").setFact(new Integer(posY));
	}
	

	public void avanzarEste(){
		int cnt = ((Integer)getBeliefbase().getBelief("tiempoDestino").getFact()).intValue();
		cnt = cnt +1;
		getBeliefbase().getBelief("tiempoDestino").setFact(new Integer(cnt));
		int posY = ((Integer)getBeliefbase().getBelief("posX").getFact()).intValue();
		posY = posY + 1;
		getBeliefbase().getBelief("posX").setFact(new Integer(posY));
	}
	
	
	public void esperar() {
		int cnt = ((Integer)getBeliefbase().getBelief("tiempoParado").getFact()).intValue();
		cnt = cnt +1;
		getBeliefbase().getBelief("tiempoParado").setFact(new Integer(cnt));
		int cnt2 = ((Integer)getBeliefbase().getBelief("tiempoDestino").getFact()).intValue();
		cnt2 = cnt2 +1;
		getBeliefbase().getBelief("tiempoDestino").setFact(new Integer(cnt2));
		
	}



}
