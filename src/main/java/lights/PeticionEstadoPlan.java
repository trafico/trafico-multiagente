package lights;

import jadex.bridge.fipa.SFipa;
import jadex.bdi.runtime.IMessageEvent;
import jadex.bdi.runtime.Plan;


public class PeticionEstadoPlan extends Plan {

	
	public PeticionEstadoPlan() {
	       // Initialization code.
		System.out.println("Created Peticion: "+this);
	}

	   public void body() {
	       // Plan code.
			String	reply;
			String	content;
			IMessageEvent me = (IMessageEvent)getReason(); //El mensaje que recibe el agente
			int cnt = ((Integer)getBeliefbase().getBelief("estadoSemaforo").getFact()).intValue(); //Recupero el estado del semaro
			System.out.println("Estado Actual del Semaforo: "+cnt);
			reply = "inform";
			content = "EstadoPeticionEstadoPlan.java: "+cnt;
			IMessageEvent re = getEventbase().createReply(me, reply);
			re.getParameter(SFipa.CONTENT).setValue(content);
			sendMessage(re);
	   }

}
