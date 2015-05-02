package cars;

import jadex.bdi.runtime.IBDIInternalAccess;
import jadex.bdi.runtime.IBelief;
import jadex.bridge.service.annotation.Service;
import jadex.bridge.service.annotation.ServiceComponent;
import jadex.commons.future.Future;
import jadex.commons.future.IFuture;
import jadex.commons.future.ITerminableFuture;
import jadex.commons.future.TerminableFuture;

@Service
public class EstadoAutoService implements IEstadoAutoService {

	@ServiceComponent
	protected IBDIInternalAccess agent;
	
	public IFuture	startService()
	{
		System.out.println("start: "+this);
		Future<EstadoAuto> ret = new Future();
		
		return ret;
		
	}

	public IFuture<EstadoAuto> getEstadoAuto() {

		IBelief estadoAuto = agent.getBeliefbase().getBelief("estadoAuto");

		EstadoAuto ea = (EstadoAuto) estadoAuto.getFact();
		System.out.println(ea.getPox());
		Future<EstadoAuto> ret = new Future<EstadoAuto>(ea);
		return ret;

		
	}

}