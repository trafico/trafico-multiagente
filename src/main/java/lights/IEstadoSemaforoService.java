package lights;

import jadex.commons.future.ITerminableFuture;

public interface IEstadoSemaforoService {
	
	public ITerminableFuture<PosicionSemaforo> getPosicion();

}
