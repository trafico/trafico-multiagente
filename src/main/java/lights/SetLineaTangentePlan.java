package lights;

import jadex.bridge.fipa.SFipa;
import jadex.bdi.runtime.IMessageEvent;
import jadex.bdi.runtime.Plan;


public class SetLineaTangentePlan extends Plan {

	
	public SetLineaTangentePlan() {
	       // Initialization code.
		System.out.println("Created Peticion: "+this);
	}

	   public void body() {
	       // Plan code.
			String	reply;
			String	content;
			IMessageEvent me = (IMessageEvent)getReason(); //El mensaje que recibe el agente
			String valorNuevo = (String) me.getParameter(SFipa.CONTENT).getValue();
			valorNuevo = valorNuevo.substring(valorNuevo.lastIndexOf(" ")+1);
			getBeliefbase().getBelief("traficoLineaTangente").setFact(new Integer(valorNuevo));
			System.out.println("Nuevo valor de la tangente:"+valorNuevo+" size:"+valorNuevo.length());
	   }

}
