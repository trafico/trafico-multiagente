package cars;


import jadex.commons.future.ITerminableFuture;

public interface IEstadoAutoService {
	
	public ITerminableFuture<EstadoAuto> getEstadoAuto();

}
