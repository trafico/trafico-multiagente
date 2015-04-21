package lights;

import java.util.ArrayList;

import jadex.bdi.runtime.IBDIInternalAccess;
import jadex.bdi.runtime.IBelief;
import jadex.bridge.service.annotation.Service;
import jadex.bridge.service.annotation.ServiceComponent;
import jadex.commons.future.ITerminableFuture;
import jadex.commons.future.TerminableFuture;

/**
 * Este servicio obtiene el trafico que percibe un semaforo
 * 
 * @author josue
 */

@Service
public class TraficoEstadoSemaforo implements ITraficoSemaforoService {
	

	
	@ServiceComponent
	protected IBDIInternalAccess agent; //Representa al agente

	public ITerminableFuture<PaqueteTrafico> getTraficoSemaforo() {
		
		//Obtener el conjunto de creencias que tienen informacion 
		// sobre el estado del semaforo y trafico
		
		IBelief creenciaTraficoNorte = agent.getBeliefbase().getBelief("traficoLineaNorte");
		IBelief creenciaTraficoSur = agent.getBeliefbase().getBelief("traficoLineaSur");
		IBelief creenciaTraficoEste = agent.getBeliefbase().getBelief("traficoLineaEste");
		IBelief creenciaTraficoOeste = agent.getBeliefbase().getBelief("traficoLineaOeste");
		
		IBelief creenciaNumeroTraficoNorte = agent.getBeliefbase().getBelief("traficoNorte");
		IBelief creenciaNumeroTraficoSur = agent.getBeliefbase().getBelief("traficoSur");
		IBelief creenciaNumeroTraficoOeste = agent.getBeliefbase().getBelief("traficoOeste");
		IBelief creenciaNumeroTraficoEste = agent.getBeliefbase().getBelief("traficoEste");
		
		IBelief creenciaPosX = agent.getBeliefbase().getBelief("posX"); 
		IBelief creenciaPosY = agent.getBeliefbase().getBelief("posY");
		
		int traficoLineaNorte = (Integer) creenciaTraficoNorte.getFact();
		int traficoLineaSur = (Integer) creenciaTraficoSur.getFact();
		int traficoLineaEste = (Integer) creenciaTraficoEste.getFact();
		int traficoLineaOeste = (Integer) creenciaTraficoOeste.getFact();
		
		int traficoNorte = (Integer) creenciaNumeroTraficoNorte.getFact();
		int traficoSur = (Integer) creenciaNumeroTraficoSur.getFact();
		int traficoOeste = (Integer) creenciaNumeroTraficoOeste.getFact();
		int traficoEste = (Integer) creenciaNumeroTraficoEste.getFact();
		
		int posX = (Integer) creenciaPosX.getFact();
		int posY = (Integer) creenciaPosY.getFact();
		
		TraficoSemaforo norte = new TraficoSemaforo(traficoLineaNorte,traficoNorte,"norte");
		TraficoSemaforo sur = new TraficoSemaforo(traficoLineaSur,traficoSur,"sur");
		TraficoSemaforo este = new TraficoSemaforo(traficoLineaEste,traficoEste,"este");
		TraficoSemaforo oeste = new TraficoSemaforo(traficoLineaOeste,traficoOeste,"oeste");
		
		ArrayList<TraficoSemaforo> listaTrafico = new ArrayList<TraficoSemaforo>();
		listaTrafico.add(norte); 
		listaTrafico.add(sur);
		listaTrafico.add(este);
		listaTrafico.add(oeste);
		
		PaqueteTrafico paquete = new PaqueteTrafico(listaTrafico,posX,posY);
		
		TerminableFuture<PaqueteTrafico> ret =  new TerminableFuture<PaqueteTrafico>();
		System.out.println("Mi trafico a enviar:"+paquete);
		ret.setResult(paquete);
		return ret;
	}

}
