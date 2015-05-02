package lights;

import jadex.commons.future.IFuture;
import jadex.commons.future.ITerminableFuture;

public interface IEstadoSemaforoService {
	
	public IFuture<PosicionSemaforo> getPosicion();

}
