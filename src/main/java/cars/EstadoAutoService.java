package cars;

import jadex.bdi.runtime.IBDIInternalAccess;
import jadex.bdi.runtime.IBelief;
import jadex.bridge.service.annotation.Service;
import jadex.bridge.service.annotation.ServiceComponent;
import jadex.commons.future.ITerminableFuture;
import jadex.commons.future.TerminableFuture;

@Service
public class EstadoAutoService implements IEstadoAutoService {

	@ServiceComponent
	protected IBDIInternalAccess agent;

	public ITerminableFuture<EstadoAuto> getEstadoAuto() {

		IBelief estadoAuto = agent.getBeliefbase().getBelief("estadoAuto");

		EstadoAuto ea = (EstadoAuto) estadoAuto.getFact();

		TerminableFuture<EstadoAuto> eaf = new TerminableFuture<EstadoAuto>();
		eaf.setResult(ea);
		return eaf;
	}

}