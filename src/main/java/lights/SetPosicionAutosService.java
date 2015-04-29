package lights;

import jadex.bdi.runtime.IBDIInternalAccess;
import jadex.bdi.runtime.IBelief;
import jadex.bridge.service.annotation.Service;
import jadex.bridge.service.annotation.ServiceComponent;
import jadex.commons.future.ITerminableFuture;
import jadex.commons.future.TerminableFuture;

@Service
public class SetPosicionAutosService implements ISetPosicionAutos {
	@ServiceComponent
	protected IBDIInternalAccess agent; //Representa al agente

	public void setPosicionAuto(int posX, int posY,String direccion) {
		// TODO Auto-generated method stub
		
		agent.getBeliefbase().getBelief("posX").setFact(new Integer(posX));
		agent.getBeliefbase().getBelief("posY").setFact(new Integer(posY));
		agent.getBeliefbase().getBelief("direccion").setFact(direccion);
		IBelief creenciaPosX = agent.getBeliefbase().getBelief("posX"); 
		IBelief creenciaPosY = agent.getBeliefbase().getBelief("posY");
		int posX2 = (Integer) creenciaPosX.getFact();
		int posY2 = (Integer) creenciaPosY.getFact();
		System.out.println("Mi posicion es:"+posX2+","+posY2);
			
	}
	
	
}
