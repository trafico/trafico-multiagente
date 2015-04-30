package lights;

import jadex.bdi.runtime.IBDIInternalAccess;
import jadex.bdi.runtime.IBelief;
import jadex.bridge.service.annotation.Service;
import jadex.bridge.service.annotation.ServiceComponent;
import jadex.bridge.service.annotation.ServiceStart;
import jadex.commons.future.IFuture;
import jadex.commons.future.ITerminableFuture;
import jadex.commons.future.TerminableFuture;

@Service
public class PosicionAutoService implements IPosicionAutoService {
	@ServiceComponent
	protected IBDIInternalAccess agent; //Representa al agente
	
	@ServiceStart
	public IFuture<Void> startService()
	{
		System.out.println("Hola mundo");
		return null;
		
	}

	public ITerminableFuture<PosicionAuto> getPosicionAuto() {
		
		//Accedo a la base de creencias y despues convierto a valores numericos
		IBelief creenciaPosX = agent.getBeliefbase().getBelief("posX"); 
		IBelief creenciaPosY = agent.getBeliefbase().getBelief("posY");
		
		int posX = (Integer) creenciaPosX.getFact();
		int posY = (Integer) creenciaPosY.getFact();
		
		PosicionAuto miPosicion = new PosicionAuto(posX,posY);
		System.out.println("Posicion a enviar:"+miPosicion);
		
		TerminableFuture<PosicionAuto> ret =  new TerminableFuture<PosicionAuto>();
		ret.setResult(miPosicion);
		return ret;
		
	}
	
	
}
