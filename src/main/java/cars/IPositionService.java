package cars;

import jadex.commons.future.ITuple2Future;

public interface IPositionService {
	
	public ITuple2Future<Posicion, SimpleCarBDI> avisoPos();
}
