package lights;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import jadex.bdiv3.BDIAgent;
import jadex.bdiv3.annotation.Belief;
import jadex.bdiv3.annotation.Goal;
import jadex.bdiv3.annotation.GoalMaintainCondition;
import jadex.bdiv3.annotation.GoalRecurCondition;
import jadex.bdiv3.annotation.GoalTargetCondition;
import jadex.bdiv3.annotation.Plan;
import jadex.bdiv3.annotation.PlanContextCondition;
import jadex.bdiv3.annotation.Trigger;
import jadex.bdiv3.annotation.Goal.ExcludeMode;
import jadex.bridge.IComponentStep;
import jadex.bridge.IInternalAccess;
import jadex.bridge.service.annotation.Service;
import jadex.bridge.service.search.SServiceProvider;
import jadex.bridge.service.types.clock.IClockService;
import jadex.commons.future.DefaultResultListener;
import jadex.commons.future.Future;
import jadex.commons.future.IFuture;
import jadex.commons.future.IIntermediateResultListener;
import jadex.commons.future.IResultListener;
import jadex.commons.future.TerminableFuture;
import jadex.commons.gui.future.SwingResultListener;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import jadex.micro.annotation.AgentCreated;
import jadex.micro.annotation.Description;
import jadex.micro.annotation.ProvidedService;
import jadex.micro.annotation.ProvidedServices;
import jadex.micro.annotation.RequiredServices;
import jadex.micro.annotation.RequiredService;
import jadex.micro.annotation.Binding;
import jadex.bridge.service.RequiredServiceInfo;



@Description("Semaforo no tiene comunicacion. <br> Tiene un temporizador.")
@Agent
@Service
@ProvidedServices( {@ProvidedService(type=IPosicionSemaforo.class),@ProvidedService(type=ITraficoService.class) })

@RequiredServices({@RequiredService(name="transser", type=IPosicionSemaforo.class, 
binding=@Binding(scope=RequiredServiceInfo.SCOPE_PLATFORM)) ,
@RequiredService(name="traficos", type=ITraficoService.class, 
binding=@Binding(scope=RequiredServiceInfo.SCOPE_PLATFORM))})

public class SemaforoSimpleBDI implements IPosicionSemaforo, ITraficoService {

	@Agent
	protected BDIAgent agent;

	@Belief
	protected boolean diezSegundos = false;

	@Belief
	protected int posX;

	@Belief
	protected int posY;

	@Belief
	protected int lineaActual; //norte(1), este(2), sur(3), oeste(4)

	@Belief
	protected int traficoNorte; 

	@Belief
	protected int traficoSur; 

	@Belief
	protected int traficoEste; 

	@Belief
	protected int traficoOeste; 

	@Belief
	protected int segundosTranscurridos;
	
	@Belief
	protected int traficoLineaNorte;
	
	@Belief
	protected int traficoLineaSur;
	
	@Belief
	protected int traficoLineaEste;
	
	@Belief
	protected int traficoLineaOeste;


	@AgentCreated
	public void init()
	{
		System.out.println("Created: "+this);
		this.posX= 0;
		this.posY = 0;
		this.lineaActual = 1; //Comienza siempre en norte
		this.traficoNorte = 0;
		this.traficoSur = 0;
		this.traficoEste = 0;
		this.traficoOeste = 0;
		this.segundosTranscurridos = 0;

	}

	/**
	 * Objetivo cambiar de reloj
	 * @author josue
	 *
	 */

	@Goal(excludemode=ExcludeMode.Never)
	public class Temporizador {

		@GoalTargetCondition(beliefs="segundosTranscurridos")
		public boolean	isAchieved()
		{
			return segundosTranscurridos > 100;
		}

	}


	/**
	 * Plan para contar. Cada diez segundos cambiar de linea
	 */

	@Plan(trigger=@Trigger(goals=Temporizador.class))
	protected void actualizaSegundos()
	{		
		if(segundosTranscurridos == 10) {
			segundosTranscurridos = 0;
			diezSegundos = true;
			agent.dispatchTopLevelGoal(new CambiaLinea());
		}
		else {
			diezSegundos = false;
		}
		segundosTranscurridos = segundosTranscurridos + 1;			
		//System.out.println(segundosTranscurridos);
	}

	/**
	 * Objetivo cambiar de linea
	 */
	@Goal(excludemode=ExcludeMode.Never)
	public class CambiaLinea {
		//Por ahora vacio
	}

	@Plan(trigger=@Trigger(goals=CambiaLinea.class))
	protected void cambiaLinea()
	{
		lineaActual = lineaActual +1;
		if(lineaActual > 4){
			lineaActual =1;
		}
		System.out.println("Linea actual: "+ lineaActual);
		//Mando llamar al servicio
		
		//IPosicionSemaforo cs = (IPosicionSemaforo)agent.getServiceContainer().getRequiredService("transser").get();
		
		//System.out.println(cs.getPosicionSemaforo().toString());
		
		IFuture<Collection<IPosicionSemaforo>> cs2 = agent.getServiceContainer().getRequiredServices("transser");
		System.out.println(cs2);
		
		cs2.addResultListener(new DefaultResultListener<Collection<IPosicionSemaforo>>()
		        {
		          public void resultAvailable(Collection<IPosicionSemaforo> result)
		          {
		            for(Iterator<IPosicionSemaforo> it=result.iterator(); it.hasNext(); )
		            {
		            	IPosicionSemaforo cs = it.next();
		              //cs.message(agent.getComponentIdentifier().getName(), text);
		            	System.out.println(cs.getPosicionSemaforo().get());
		            }
		          }
		        });
		
		/**
		 * Servicio de trafico
		 */
		IFuture<Collection<ITraficoService>> cs3 = agent.getServiceContainer().getRequiredServices("traficos");
		System.out.println(cs3);
		
		cs3.addResultListener(new DefaultResultListener<Collection<ITraficoService>>()
		        {
		          public void resultAvailable(Collection<ITraficoService> result)
		          {
		            for(Iterator<ITraficoService> it=result.iterator(); it.hasNext(); )
		            {
		            	ITraficoService cs = it.next();
		              //cs.message(agent.getComponentIdentifier().getName(), text);
		            	System.out.println(cs.getTraficoSemaforo().get());
		            }
		          }
		        });
		
		
		/*for(Iterator<IPosicionSemaforo> it=((Collection<IPosicionSemaforo>) cs2).iterator(); it.hasNext(); )
        {
			IPosicionSemaforo pos = it.next();
			System.out.println(pos.getPosicionSemaforo().toString());
          //cs.message(agent.getComponentIdentifier().getName(), text);
        } 
		
		/*IFuture<IPosicionSemaforo> fut = agent.getServiceContainer().getRequiredService("transser");
		fut.addResultListener(new DefaultResultListener<IPosicionSemaforo>()
		{
		  public void resultAvailable(IPosicionSemaforo cs)
		  {
		    System.out.println("Time for a chat, buddy: "+cs.getPosicionSemaforo());
		  }
		});
		
		
		/*SServiceProvider.getServices(agent.getServiceProvider(), IPosicionSemaforo.class, RequiredServiceInfo.SCOPE_PLATFORM)
		.addResultListener(new IIntermediateResultListener<IPosicionSemaforo>()
				{
			public void intermediateResultAvailable(IPosicionSemaforo ts)
			{
				// Invoke translate on the service.
				//ts.translateEnglishGerman(tfe.getText())
				//ts.getPosicionSemaforo();
				System.out.println("Me llaman servicio: "+ts.getPosicionSemaforo());
				//System.out.println("Me llaman servicio");
				
				
			}

			public void exceptionOccurred(Exception exception)
			{
				System.out.println(exception);
			}
			

			public void finished()
			{
			}

			public void resultAvailable(Collection<IPosicionSemaforo> result)
			{
				
			}
			
			
				});*/
	}

	@AgentBody
	public void body()
	{
		agent.dispatchTopLevelGoal(new Temporizador());
	}


	public IFuture<PosicionSemaforo> getPosicionSemaforo() {

		String estado="";
		switch (lineaActual)  {
		case 1: estado = "norte"; break;
		case 2: estado = "sur"; break;
		case 3: estado = "este"; break;
		case 4: estado = "oeste"; break;
		}
		PosicionSemaforo miPosicion = new PosicionSemaforo(posX,posY,estado);
		return new Future<PosicionSemaforo>(miPosicion);
		//return miPosicion;
	}

	public IFuture<PaqueteTrafico> getTraficoSemaforo() {
	
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
		return new Future<PaqueteTrafico>(paquete);
		//return new Future<PosicionSemaforo>(paquete);			
	}



}
