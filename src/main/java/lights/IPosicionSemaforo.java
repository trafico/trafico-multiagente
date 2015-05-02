package lights;

import jadex.commons.future.IFuture;

public interface IPosicionSemaforo {

	public IFuture<PosicionSemaforo> getPosicionSemaforo();
}
