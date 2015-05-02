package cars;

import jadex.commons.future.IFuture;


public interface IEstadoAutoService {
	
	public IFuture<EstadoAuto> getEstadoAuto();

}
