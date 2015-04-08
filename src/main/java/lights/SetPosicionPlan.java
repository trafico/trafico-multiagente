package lights;

import jadex.bridge.fipa.SFipa;
import jadex.bdi.runtime.IMessageEvent;
import jadex.bdi.runtime.Plan;


public class SetPosicionPlan extends Plan {

	
	public SetPosicionPlan() {
	       // Initialization code.
		System.out.println("Created Peticion: "+this);
	}

	   public void body() {
		   IMessageEvent me = (IMessageEvent)getReason(); //El mensaje que recibe el agente
		   String valorNuevo = (String) me.getParameter(SFipa.CONTENT).getValue();
		   String posY = valorNuevo.substring(valorNuevo.lastIndexOf(" ")+1);
		   int i= valorNuevo.indexOf('n');
			
		   String posX = valorNuevo.substring(i+2,valorNuevo.lastIndexOf(" "));
		   getBeliefbase().getBelief("posX").setFact(new Integer(posX));
		   getBeliefbase().getBelief("posY").setFact(new Integer(posY));
			//System.out.println("Nuevo valor de la tangente:"+posX+","+posY+"size x:"+posX.length()+"size y:"+posY.length());
	   }

}
