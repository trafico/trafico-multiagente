package lights;

import jadex.commons.future.IFuture;

public interface ITraficoService {

	public IFuture<PaqueteTrafico> getTraficoSemaforo();
}
