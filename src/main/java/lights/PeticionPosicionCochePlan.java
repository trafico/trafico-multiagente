package lights;

import jadex.bridge.fipa.SFipa;
import jadex.bdi.runtime.IMessageEvent;
import jadex.bdi.runtime.Plan;


public class PeticionPosicionCochePlan extends Plan {

	
	public PeticionPosicionCochePlan() {
	       // Initialization code.
		System.out.println("Created PeticionPosCOche: "+this);
	}

	   public void body() {
	       // Plan code.
		   String	reply;
			String	content;
			System.out.println("Poder poderoso");
			/*
			IMessageEvent me = (IMessageEvent)getReason(); //El mensaje que recibe el agente
			int posX = ((Integer)getBeliefbase().getBelief("posX").getFact()).intValue(); //Recupero el estado del semaro
			int posY = ((Integer)getBeliefbase().getBelief("posY").getFact()).intValue(); //Recupero el estado del semaro
			System.out.println("Estado Actual Trafico: "+posX);
			reply = "inform";
			content = "Posicion: "+posX+","+posY;
			IMessageEvent re = getEventbase().createReply(me, reply);
			re.getParameter(SFipa.CONTENT).setValue(content);
			sendMessage(re);
			*/
	   }

}
