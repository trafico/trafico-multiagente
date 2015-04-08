package lights;

import jadex.bridge.fipa.SFipa;
import jadex.bdi.runtime.IMessageEvent;
import jadex.bdi.runtime.Plan;


public class PeticionLineaOestePlan extends Plan {

	
	public PeticionLineaOestePlan() {
	       // Initialization code.
		System.out.println("Created Peticion: "+this);
	}

	   public void body() {
	       // Plan code.
			String	reply;
			String	content;
			IMessageEvent me = (IMessageEvent)getReason(); //El mensaje que recibe el agente
			int cnt = ((Integer)getBeliefbase().getBelief("traficoLineaOeste").getFact()).intValue(); //Recupero el estado del semaro
			//System.out.println("Estado Actual Trafico: "+cnt);
			reply = "inform";
			content = "Estado: "+cnt;
			IMessageEvent re = getEventbase().createReply(me, reply);
			re.getParameter(SFipa.CONTENT).setValue(content);
			sendMessage(re);
	   }

}
