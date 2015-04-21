package lights;

import jadex.commons.future.ITerminableFuture;

public interface ITraficoSemaforoService {
	
	public ITerminableFuture<PaqueteTrafico> getTraficoSemaforo();

}
