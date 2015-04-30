package lights;

import jadex.bridge.fipa.SFipa;
import jadex.bdi.runtime.IMessageEvent;
import jadex.bdi.runtime.Plan;

import java.util.*;

public class SemaforoSimpleCambioLuzTempoPlan extends Plan {
	
	protected String estadoSemaforo; // Puede ser solamente roja o verde
	
	public SemaforoSimpleCambioLuzTempoPlan() {
	       // Initialization code.
		estadoSemaforo = "ROJO";
		System.out.println("Created: "+this+"Estado: "+estadoSemaforo);
		
	}
	
	public String cambiarLuz(){
		if (estadoSemaforo.equals("ROJO"))
			estadoSemaforo = "VERDE";
		else {
			estadoSemaforo = "ROJO";
		}
		return estadoSemaforo;
	}
	
	   public void body() {
	       // Plan code.
		   while (true) {
			   IMessageEvent me = waitForMessageEvent("request_translation");
			    String eword = (String)me.getParameter(SFipa.CONTENT).getValue();
			    System.out.println(cambiarLuz());				
			    int cnt = ((Integer)getBeliefbase().getBelief("segundosTrans").getFact()).intValue();
			    getBeliefbase().getBelief("segundosTrans").setFact(new Integer(cnt+1));
			    System.out.println("Segundos transcurridos:"+(cnt+1));
		   }
	   }

}
