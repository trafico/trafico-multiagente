package lights;

import jadex.bdi.runtime.IBDIInternalAccess;
import jadex.bdi.runtime.IBelief;
import jadex.bridge.service.annotation.Service;
import jadex.bridge.service.annotation.ServiceComponent;
import jadex.commons.future.ITerminableFuture;
import jadex.commons.future.TerminableFuture;

@Service
public class SetPosicionSemaforosService implements ISetPosicionSemaforos {
	@ServiceComponent
	protected IBDIInternalAccess agent; //Representa al agente

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

	public void setPosicionSemaforo(int posX, int posY) {
		// TODO Auto-generated method stub
		
		agent.getBeliefbase().getBelief("posX").setFact(new Integer(posX));
		agent.getBeliefbase().getBelief("posY").setFact(new Integer(posY));
		IBelief creenciaPosX = agent.getBeliefbase().getBelief("posX"); 
		IBelief creenciaPosY = agent.getBeliefbase().getBelief("posY");
		//int posX2 = (Integer) creenciaPosX.getFact();
		//int posY2 = (Integer) creenciaPosY.getFact();
		
		//System.out.println("Mi posicion es:"+posX2+","+posY2);
		
	
		
	}
	
	
}
