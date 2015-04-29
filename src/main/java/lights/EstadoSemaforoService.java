package lights;

import jadex.bdi.runtime.IBDIInternalAccess;
import jadex.bdi.runtime.IBelief;
import jadex.bridge.service.annotation.Service;
import jadex.bridge.service.annotation.ServiceComponent;
import jadex.commons.future.ITerminableFuture;
import jadex.commons.future.TerminableFuture;

@Service
public class EstadoSemaforoService implements IEstadoSemaforoService {
	
	@ServiceComponent
	protected IBDIInternalAccess agent; //Representa al agente

	/**
	 * @author josue
	 * @return Future, con una posicion
	 */
	public ITerminableFuture<PosicionSemaforo> getPosicion() {
		
		System.out.println("Hola chavo");
		
		//Accedo a la base de creencias y despues convierto a valores numericos
		IBelief creenciaPosX = agent.getBeliefbase().getBelief("posX"); 
		IBelief creenciaPosY = agent.getBeliefbase().getBelief("posY");
		
		IBelief lineaActualCreencia = agent.getBeliefbase().getBelief("lineaActual");
		
		
		int posX = (Integer) creenciaPosX.getFact();
		int posY = (Integer) creenciaPosY.getFact();
		int lineaActual = (Integer) lineaActualCreencia.getFact();
		String estado="";
		
		switch (lineaActual)  {
			case 1: estado = "norte"; break;
			case 2: estado = "sur"; break;
			case 3: estado = "este"; break;
			case 4: estado = "oeste"; break;
		}
		
		
		PosicionSemaforo miPosicion = new PosicionSemaforo(posX,posY,estado);
		System.out.println("Posicion a enviar:"+miPosicion);
		
		TerminableFuture<PosicionSemaforo> ret =  new TerminableFuture<PosicionSemaforo>();
		ret.setResult(miPosicion);
		return ret;
		
	}

}
