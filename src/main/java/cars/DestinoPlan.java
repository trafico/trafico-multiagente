package cars;

import jadex.bdi.runtime.Plan;

public class DestinoPlan extends Plan {
	
	public DestinoPlan(){
		super();
		EstadoAuto ea= new EstadoAuto("", 0, 0, 0, 0, 0, 0, true);
		getBeliefbase().getBelief("estadoAuto").setFact(ea);
		System.out.println("jolines");
	}

	@Override
	public void body() {
		// TODO Auto-generated method stub
		// Un día de estos habrá un plan genial aquí para que avance el coche. :)
	}

}
