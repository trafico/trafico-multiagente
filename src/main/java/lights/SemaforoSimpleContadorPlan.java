
package lights;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

import cars.EstadoAuto;
import cars.EstadoAutoService;
import cars.IEstadoAutoService;
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

public class SemaforoSimpleContadorPlan extends Plan {
	
	int MAX_CARRO = 6; // Maximo seis carros si hay mas, entonces se considera trafico
	/*int norte = 0;
	int sur = 0;
	int este = 0;
	int oeste = 0;
	*/
	 private AtomicInteger norte = new AtomicInteger(0);
	 private AtomicInteger sur = new AtomicInteger(0);
	 private AtomicInteger oeste = new AtomicInteger(0);
	 private AtomicInteger este = new AtomicInteger(0);

	public SemaforoSimpleContadorPlan() {
		super();
		// TODO Auto-generated constructor stub
		int cnt = ((Integer)getBeliefbase().getBelief("estadoSemaforo").getFact()).intValue();
		System.out.println("Created: "+this+" Estado semaforo: "+cnt);
	}

	public void body() {
		while(true) {
			int cnt = ((Integer)getBeliefbase().getBelief("segundosTrans").getFact()).intValue();
			if (cnt == 10) {
				cnt = 0;
				//Cada diez segundo actualizo mi creencia de trafico
				//Actualizar el trafico en base a la posicion de cada automobil
				
				
					IFuture<Collection<IEstadoAutoService>> servicioAutos = getServiceContainer().getRequiredServices("posicionAutos");
					
					servicioAutos.addResultListener(new DefaultResultListener<Collection<IEstadoAutoService>>(){

						public void resultAvailable(Collection<IEstadoAutoService> result) {
							
							
							norte.getAndSet(0) ;
							sur.getAndSet(0) ;
							este.getAndSet(0) ;
							oeste.getAndSet(0) ;
							
							int posX = ((Integer)getBeliefbase().getBelief("posX").getFact()).intValue();
							int posY = ((Integer)getBeliefbase().getBelief("posY").getFact()).intValue();
							System.out.println("Nortssdsdsdsdsfdsfdfde: "+result.size());
							System.out.println("Sur: "+(EstadoAuto)result.iterator().next().getEstadoAuto());
//							for(Iterator<IEstadoAutoService> it=result.iterator(); it.hasNext();)
//							{
////								System.out.println("Nortssdsdsdsdsfdsfdfde: "+result.iterator().next().getEstadoAuto().get().getPox());
//								//result.iterator().next().getEstadoAuto().get().getPox()
//								IEstadoAutoService cs = it.next();
//								//ITerminableFuture res = cs.getEstadoAuto();
//								//EstadoAuto miPos  = (EstadoAuto) res.get();
//								//int posX_rec = miPos.getPox();
////								int posY_rec = miPos.getPoy();
////								System.out.println("Coche: "+miPos);
////								
////								if( (posY_rec - (posY-10) > 0) && (posX_rec == posX))
////									sur.incrementAndGet();
////								
////								if( ((posY + 10) - posY_rec > 0) && (posX_rec+1 == posX) )
////									norte.incrementAndGet();
////								
////								if( (posX_rec - (posX-10))>0 && (posY_rec == posY)   )
////									este.incrementAndGet();
////								
////								if( (posX+10 - posX_rec)>0 && (posY_rec == posY)   )
////									oeste.incrementAndGet();
////								
//								
//								//cs.message(agent.getComponentIdentifier().getName(), "Hello");
//								//System.out.println("Norte: "+norte+" Sur:"+sur+" Este:"+este+" Oeste:"+oeste);
//							}
							

						}

					});
					
					System.out.println("Norte: "+norte+" Sur:"+sur+" Este:"+este+" Oeste:"+oeste);
					
					if(norte.get() > MAX_CARRO) {
						getBeliefbase().getBelief("traficoLineaNorte").setFact(1);
						getBeliefbase().getBelief("traficoNorte").setFact(norte);					
					}
					
					if(sur.get() > MAX_CARRO) {
						getBeliefbase().getBelief("traficoLineaSur").setFact(1);
						getBeliefbase().getBelief("traficoSur").setFact(sur);					
					}
					
					if(este.get() > MAX_CARRO) {
						getBeliefbase().getBelief("traficoLineaEste").setFact(1);
						getBeliefbase().getBelief("traficoEste").setFact(este);					
					}
					
					if(oeste.get() > MAX_CARRO) {
						getBeliefbase().getBelief("traficoLineaOeste").setFact(1);
						getBeliefbase().getBelief("traficoOeste").setFact(oeste);					
					}
					
				
				
			}
			else 
				cnt = cnt +1;
			getBeliefbase().getBelief("segundosTrans").setFact(new Integer(cnt));
			try {
				Thread.sleep(1000);                 //1000 millisegundos un segundo : )
			} catch(InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			System.out.println("Segundos transcurridos:"+(cnt+1));

			//Actualizar el trafico en base a la posicion de cada automobil
			/*try{
				IFuture<Collection<IPosicionAutoService>> servicioAutos = getServiceContainer().getRequiredServices("posicionAutos");
				servicioAutos.addResultListener(new DefaultResultListener<Collection<IPosicionAutoService>>(){

					public void resultAvailable(Collection<IPosicionAutoService> result) {
						
						norte.getAndSet(0) ;
						sur.getAndSet(0) ;
						este.getAndSet(0) ;
						oeste.getAndSet(0) ;
						
						int posX = ((Integer)getBeliefbase().getBelief("posX").getFact()).intValue();
						int posY = ((Integer)getBeliefbase().getBelief("posY").getFact()).intValue();
						
						for(Iterator<IPosicionAutoService> it=result.iterator(); it.hasNext(); )
						{
							IPosicionAutoService cs = it.next();
							ITerminableFuture res = cs.getPosicionAuto();
							PosicionAuto miPos  = (PosicionAuto) res.get();
							int posX_rec = miPos.getPosX();
							int posY_rec = miPos.getPosY();
							
							if( (posY_rec - (posY-10) > 0) && (posX_rec == posX))
								sur.incrementAndGet();
							
							if( ((posY + 10) - posY_rec > 0) && (posX_rec+1 == posX) )
								norte.incrementAndGet();
							
							if( (posX_rec - (posX-10))>0 && (posY_rec == posY)   )
								este.incrementAndGet();
							
							if( (posX+10 - posX_rec)>0 && (posY_rec == posY)   )
								oeste.incrementAndGet();
							
							
							//cs.message(agent.getComponentIdentifier().getName(), "Hello");
							//System.out.println("Norte: "+norte+" Sur:"+sur+" Este:"+este+" Oeste:"+oeste);
						}

					}

				});
				
				System.out.println("Norte: "+norte+" Sur:"+sur+" Este:"+este+" Oeste:"+oeste);
				
				if(norte.get() > MAX_CARRO) {
					getBeliefbase().getBelief("traficoLineaNorte").setFact(1);
					getBeliefbase().getBelief("traficoNorte").setFact(norte);					
				}
				
				if(sur.get() > MAX_CARRO) {
					getBeliefbase().getBelief("traficoLineaSur").setFact(1);
					getBeliefbase().getBelief("traficoSur").setFact(sur);					
				}
				
				if(este.get() > MAX_CARRO) {
					getBeliefbase().getBelief("traficoLineaEste").setFact(1);
					getBeliefbase().getBelief("traficoEste").setFact(este);					
				}
				
				if(oeste.get() > MAX_CARRO) {
					System.out.println("El poder de todo");
					getBeliefbase().getBelief("traficoLineaOeste").setFact(1);
					getBeliefbase().getBelief("traficoOeste").setFact(oeste);					
				}
				
				
				
			} catch(ServiceNotFoundException ex){

			}*/
		}
	}



}
