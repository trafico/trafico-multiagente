package lights;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import cars.IEstadoAutoService;
import jadex.bdiv3.BDIAgent;
import jadex.bdiv3.annotation.Belief;
import jadex.bdiv3.annotation.Goal;
import jadex.bdiv3.annotation.GoalTargetCondition;
import jadex.bdiv3.annotation.Plan;
import jadex.bdiv3.annotation.Trigger;
import jadex.bdiv3.annotation.Goal.ExcludeMode;
import jadex.bdiv3.runtime.IPlan;
import jadex.bridge.service.annotation.Service;
import jadex.commons.future.DefaultResultListener;
import jadex.commons.future.Future;
import jadex.commons.future.IFuture;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import jadex.micro.annotation.AgentCreated;
import jadex.micro.annotation.Argument;
import jadex.micro.annotation.Arguments;
import jadex.micro.annotation.Description;
import jadex.micro.annotation.ProvidedService;
import jadex.micro.annotation.ProvidedServices;
import jadex.micro.annotation.RequiredServices;
import jadex.micro.annotation.RequiredService;
import jadex.micro.annotation.Binding;
import jadex.bridge.service.RequiredServiceInfo;



@Description("Semaforo ego’sta. <br> Tiene un temporizador, comunicaci—n con vecinos y toma de decisiones en base a su tr‡fico local.")
@Agent
@Service
@ProvidedServices( {@ProvidedService(type=IPosicionSemaforo.class),@ProvidedService(type=ITraficoService.class) })

@RequiredServices({@RequiredService(name="transser", type=IPosicionSemaforo.class, 
binding=@Binding(scope=RequiredServiceInfo.SCOPE_PLATFORM, dynamic=true), multiple=true) ,
@RequiredService(name="traficos", type=ITraficoService.class, 
binding=@Binding(scope=RequiredServiceInfo.SCOPE_PLATFORM,dynamic=true),multiple=true) ,
@RequiredService(name="autos", type=IEstadoAutoService.class, 
binding=@Binding(scope=RequiredServiceInfo.SCOPE_PLATFORM,dynamic=true),multiple=true) ,})

@Arguments({
	  @Argument(name="posX", clazz=Integer.class, defaultvalue="0"),
	  @Argument(name="posY", clazz=Integer.class, defaultvalue="0")
	})

public class SemaforoEgoistaBDI implements IPosicionSemaforo, ITraficoService {

	@Agent
	protected BDIAgent agent;

	@Belief
	protected boolean diezSegundos = false;

	@Belief
	protected int posX;

	@Belief
	protected int posY;
	
	@Belief
	protected int maxCarros;

	@Belief
	protected int lineaActual; //norte(1), sur(2), este(3), oeste(4)

	@Belief
	protected Integer traficoNorte; 

	@Belief
	protected Integer traficoSur; 

	@Belief
	protected Integer traficoEste; 

	@Belief
	protected Integer traficoOeste; 

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
	
	@Belief
	protected int carrosQuePasaron;


	@AgentCreated
	public void init()
	{
		System.out.println("Created: "+this);
		this.posX= (Integer) agent.getArgument("posX");
		this.posY = (Integer) agent.getArgument("posY");
		this.lineaActual = 1; //Comienza siempre en norte
		this.traficoNorte = 0;
		this.traficoSur = 0;
		this.traficoEste = 0;
		this.traficoOeste = 0;
		this.segundosTranscurridos = 0;
		this.traficoLineaEste = 0;
		this.traficoLineaOeste = 0;
		this.traficoLineaSur = 0;
		this.traficoLineaNorte = 0;
		this.maxCarros = 6;
		System.out.println(posX+","+posY);

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
		try {
			Thread.sleep(600);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		
		IFuture<Collection<IPosicionSemaforo>> cs2 = agent.getServiceContainer().getRequiredServices("transser");
		
		cs2.addResultListener(new DefaultResultListener<Collection<IPosicionSemaforo>>()
		        {
		          public void resultAvailable(Collection<IPosicionSemaforo> result)
		          {
		            for(Iterator<IPosicionSemaforo> it=result.iterator(); it.hasNext(); )
		            {
		            	IPosicionSemaforo cs = it.next();
		              //cs.message(agent.getComponentIdentifier().getName(), text);
		            	
		            	
		            	
		            	//System.out.println(cs.getPosicionSemaforo().get());
		            }
		          }
		        });
		
		/**
		 * Servicio de autos
		 */
		
		IFuture<Collection<IEstadoAutoService>> cs4 = agent.getServiceContainer().getRequiredServices("autos");
		
		cs4.addResultListener(new DefaultResultListener<Collection<IEstadoAutoService>>()
		        {
		          public void resultAvailable(Collection<IEstadoAutoService> result)
		          {
		        	  
		        	int norte = 0;
		        	int sur = 0;
		        	int oeste = 0;
		        	int este =0;
		        	  
		            for(Iterator<IEstadoAutoService> it=result.iterator(); it.hasNext(); )
		            {
		            	IEstadoAutoService cs = it.next();
		            	int posX_rec = cs.getEstadoAuto().get().getPox();
		            	int posY_rec = cs.getEstadoAuto().get().getPoy();
		            	
		            	if( (posY_rec - (posY-10) > 0) && (posX_rec == posX))
							sur= sur +1;
						
						//if( ((posY + 10) - posY_rec > 0) && (posX_rec+1 == posX) )
		            	if( ((posY + 10) - posY_rec > 0) && (posX_rec-1	 == posX) )
							norte = norte +1;
						
						//if( (posX_rec - (posX-10))>0 && (posY_rec == posY)   )
						if( (posX_rec - (posX-10))>-1 && (posX_rec <= posX) && (posY_rec == posY)  )
							este = este +1;
						
						//if( (posX+10 - posX_rec)>0 && (posY_rec == posY)   )
						if( (posX_rec - posX)<11 && (posX_rec - posX)>-1 && (posY_rec == posY) )
							oeste= oeste +1;
		            	
		            	//System.out.println(cs.getEstadoAuto().get());
		            }
		            // Checo mi lista
		            System.out.println("Norte: "+norte+" Sur:"+sur+" Este:"+este+" Oeste:"+oeste);
		            if(norte > maxCarros) {
		            	traficoLineaNorte = 1;
						traficoNorte = norte;
		            }
		            if(sur > maxCarros) {
		            	traficoLineaSur = 1;
						traficoSur = sur;
		            }
		            if(este > maxCarros) {
		            	traficoLineaEste = 1;
						traficoEste = este;
		            }
		            if(oeste > maxCarros) {
		            	traficoLineaOeste = 1;
						traficoOeste = oeste;
		            }
		            
		            carrosQuePasaron = carrosQuePasaron + traficoNorte + traficoSur + traficoEste + traficoOeste;
		            
		            //traficoOeste = 0;
		            //traficoNorte = 1;
		            //traficoEste = 4;
		            //System.out.println("Norte: "+traficoNorte+" Sur:"+traficoSur+" Este:"+traficoEste+" Oeste:"+traficoOeste);	
		            
		            //Obtengo el mayor y cambio mi linea a ese
		           if(traficoLineaOeste == 0 && traficoLineaEste == 0 && traficoLineaSur == 0 && traficoLineaNorte == 0) {
		        	   //System.out.println("Norte: "+traficoNorter+" Sur:"+traficoSur+" Este:"+traficoEste+" Oeste:"+traficoOeste);			        
		        	   lineaActual = lineaActual;
		           } else {
		        	
		        	  // System.out.println(lineaMasCargada());
		            String nuevaLinea = lineaMasCargada().getNombre();		          
		            
		            if(nuevaLinea.equals("norte")){
		            	lineaActual = 1;
		            }
		            else {
		            	if(nuevaLinea.equals("sur")) {
		            		lineaActual = 2;
		            	}
		            	else {
		            		if(nuevaLinea.equals("este")) {
		            			lineaActual = 3;
		            		}
		            		else {
		            			lineaActual = 4;
		            		}
		            	}
		            }
		          }
		            
		          }
		        });
		
		/**
		 * Servicio de trafico
		 */
		IFuture<Collection<ITraficoService>> cs3 = agent.getServiceContainer().getRequiredServices("traficos");
		//System.out.println(cs3);
		
		
		cs3.addResultListener(new DefaultResultListener<Collection<ITraficoService>>()
		        {
		          public void resultAvailable(Collection<ITraficoService> result)
		          {
		        	int traficoVecinos = 0;
		        	LineaTrafico lineaTrafico = lineaMasCargada();
	            	String lineaCargada = lineaTrafico.getNombre();
		            for(Iterator<ITraficoService> it=result.iterator(); it.hasNext(); )
		            {
		            	ITraficoService cs = it.next();
		             	
		            	//LineaTrafico lineaTrafico = lineaMasCargada();
		            	//String lineaCargada = lineaTrafico.getNombre();
		            	if(lineaCargada.equals("norte") || lineaCargada.equals("sur")) {
		            		int posX_rec = cs.getTraficoSemaforo().get().getPosX();
		            		if( posX_rec == posX+10){ // || (posX_rec == posX-10 ) ){
		            			PaqueteTrafico paqueteTrafico = cs.getTraficoSemaforo().get();
		            			ArrayList<TraficoSemaforo> pa = paqueteTrafico.getListaTraficoSemaforo();
		            			for (int i = 1; i<pa.size(); i++) {
		            				TraficoSemaforo traficoSemaforo = pa.get(i);
		            				if(traficoSemaforo.getDireccion().equals("oeste")){
		            					traficoVecinos = traficoVecinos + traficoSemaforo.getNumeroAutos();
		            				}
		            			}
		            		}
		            		else {
		            			if (posX_rec == posX-10 ) {
		            				PaqueteTrafico paqueteTrafico = cs.getTraficoSemaforo().get();
			            			ArrayList<TraficoSemaforo> pa = paqueteTrafico.getListaTraficoSemaforo();
			            			for (int i = 1; i<pa.size(); i++) {
			            				TraficoSemaforo traficoSemaforo = pa.get(i);
			            				if(traficoSemaforo.getDireccion().equals("este")){
			            					traficoVecinos = traficoVecinos + traficoSemaforo.getNumeroAutos();
			            				}
			            			}
		            			}
		            		}
		            	}
		            	else {
		            		if(lineaCargada.equals("este") || lineaCargada.equals("oeste")) {
		            			int posY_rec = cs.getTraficoSemaforo().get().getPosY();
		            			if( posY_rec == posY+10){
		            				PaqueteTrafico paqueteTrafico = cs.getTraficoSemaforo().get();
			            			ArrayList<TraficoSemaforo> pa = paqueteTrafico.getListaTraficoSemaforo();
			            			for (int i = 1; i<pa.size(); i++) {
			            				TraficoSemaforo traficoSemaforo = pa.get(i);
			            				if(traficoSemaforo.getDireccion().equals("sur")){
			            					traficoVecinos = traficoVecinos + traficoSemaforo.getNumeroAutos();
			            				}
			            			}
			            		}
		            			else {
		            				if( posY_rec == posY-10){
		            					PaqueteTrafico paqueteTrafico = cs.getTraficoSemaforo().get();
				            			ArrayList<TraficoSemaforo> pa = paqueteTrafico.getListaTraficoSemaforo();
				            			for (int i = 1; i<pa.size(); i++) {
				            				TraficoSemaforo traficoSemaforo = pa.get(i);
				            				if(traficoSemaforo.getDireccion().equals("norte")){
				            					traficoVecinos = traficoVecinos + traficoSemaforo.getNumeroAutos();
				            				}
				            			}
		            				}
		            			}
		            		}
		            	}
		            	
		            	//System.out.println(cs.getTraficoSemaforo().get());
		            	
		            }
		            
		            //Reviso si el trafico de mis vecinos es mayor que el mio, sino no hago nada
		            if(traficoVecinos > lineaTrafico.getNumeroCarros()) {
		            	Random rn = new Random();
		            	int answer = rn.nextInt(3) + 1;
		            	
		            	switch (answer) {
		            	case 1:
		            		if(lineaTrafico.getNombre().equals("norte") || lineaTrafico.getNombre().equals("sur")  ){
		            			lineaActual = 3; //norte(1), sur(2), este(3), oeste(4)
		            		}
		            		else {
		            			lineaActual = 1;
		            		}
		            		break;
		            	case 2:
		            		if(lineaTrafico.getNombre().equals("norte") || lineaTrafico.getNombre().equals("sur")  ){
		            			lineaActual = 4; //norte(1), sur(2), este(3), oeste(4)
		            		}
		            		else {
		            			lineaActual = 2;
		            		}
		            		break;
		            	case 3:
		            		// Aqui no hace nada el agente
		            		break;
		            	}
		            	
		            }
		            
		            //System.out.println(traficoVecinos);
		          }
		        });
		System.out.println("Linea Actual: "+ lineaActual);
	}

	@AgentBody
	public void body()
	{
		agent.dispatchTopLevelGoal(new Temporizador());	
		
	}
	
	@Plan
	public void methodPlan(IPlan plan) {
		System.out.println("Executing a method plan.");
		plan.waitFor(5000).get();
		System.out.println("Method plan done waiting");
	}
	
	protected LineaTrafico lineaMasCargada(){
		//System.out.println("Norte: "+traficoNorte+" Sur:"+traficoSur+" Este:"+traficoEste+" Oeste:"+traficoOeste);	
         
		LineaTrafico norte = new LineaTrafico(traficoNorte,"norte");
		LineaTrafico sur = new LineaTrafico(traficoSur,"sur");
		LineaTrafico este = new LineaTrafico(traficoEste,"este");
		LineaTrafico oeste = new LineaTrafico(traficoOeste,"oeste");
		ArrayList<LineaTrafico> lineas = new ArrayList<LineaTrafico>();
		lineas.add(norte);
		lineas.add(sur);
		lineas.add(este);
		lineas.add(oeste);
		//System.out.println("Le habla al Sur: "+sur);
		
		Comparator<LineaTrafico> cmp = new Comparator<LineaTrafico>() {
		        public int compare(LineaTrafico o1, LineaTrafico o2) {
		        	//return Math.max(o1.getNumeroCarros(), o2.getNumeroCarros());
		        	return o1.getNumeroCarros().compareTo(o2.getNumeroCarros());
		        }
		};
		
		LineaTrafico maximo = Collections.max(lineas,cmp);
		//System.out.println(maximo);
		return maximo;
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
