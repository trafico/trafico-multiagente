package cars;

//import java.text.SimpleDateFormat;
//import java.util.Collection;

import java.util.Collection;
import java.util.Iterator;

import jadex.bdiv3.BDIAgent;
import jadex.bdiv3.annotation.Belief;
import jadex.bdiv3.annotation.Body;
import jadex.bdiv3.annotation.Plan;
import jadex.bdiv3.annotation.PlanBody;
import jadex.bdiv3.annotation.PlanPrecondition;
import jadex.bdiv3.annotation.Plans;
import jadex.bdiv3.annotation.ServiceTrigger;
import jadex.bdiv3.annotation.Trigger;
import jadex.bridge.service.RequiredServiceInfo;
import jadex.bridge.service.annotation.Service;
import jadex.bridge.service.search.SServiceProvider;
import jadex.commons.future.Future;
import jadex.commons.future.IFuture;
import jadex.commons.future.IResultListener;
import jadex.commons.future.ITuple2Future;
import jadex.commons.future.IntermediateDefaultResultListener;
import jadex.commons.future.Tuple2Future;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import jadex.micro.annotation.AgentCreated;
import jadex.micro.annotation.Implementation;
import jadex.micro.annotation.ProvidedService;
import jadex.micro.annotation.ProvidedServices;
import jadex.micro.annotation.RequiredService;
import jadex.micro.annotation.RequiredServices;
import lights.IEstadoSemaforoService;

@Agent
@Service
@ProvidedServices(@ProvidedService(type=IEstadoAutoService.class))
@RequiredServices
({
//	@RequiredService(type=IPosicionSemaforo.class, name = "estadoSemaforo"),
	@RequiredService(type=IEstadoAutoService.class, name = "estadoAutos")
})
@Plans({@Plan(body=@Body(DestinoPlan.class))})
public class SimpleCarBDI implements IEstadoAutoService {
	@Belief
	private int x_ini;
	@Belief
	private int y_ini;
	@Belief
	private int x_fin;
	@Belief
	private int y_fin;
	@Belief
	private int pox;
	@Belief
	private int poy;
	@Belief
	private int velocidad;
	@Belief
	private int v_max;
	@Belief
	private boolean status= true;
	@Belief
	private int direccion; //0:Norte, 1:Este, 2:Sur, 3:Oeste.
	@Belief
	private EstadoAuto ea;

	@Agent
	BDIAgent agent;

	public SimpleCarBDI() {
		x_ini = 0;
		y_ini = 0;
		x_fin = 0;
		y_fin = 0;
		pox = x_ini;
		poy = y_ini;
		direccion=0;
		ea= new EstadoAuto(velocidad, direccion, pox, poy, x_fin, y_fin, status);
	}

	// public SimpleCarBDI(int x, int y, int x2, int y2, int d){
	// x_ini= x;
	// y_ini= y;
	// x_fin= x2;
	// y_fin= y2;
	// pox= x_ini;
	// poy= y_ini;
	// direccion= d;
	// }
	
	@AgentCreated
	public void init(){
		
	}

	@AgentBody
	public void body() {
		
//		agent.adoptPlan(new DetenersePlan(this));
//		agent.adoptPlan(new AvanzarPlan(this, 10));
//		agent.adoptPlan(new GirarDerechaPlan(this));
//		agent.adoptPlan(new GirarIzquierdaPlan(this));
		System.out.println("Aqui");
		SServiceProvider.getServices(agent.getServiceProvider(), IEstadoAutoService.class, RequiredServiceInfo.SCOPE_PLATFORM).addResultListener(new IResultListener <Collection<IEstadoAutoService>>(){

			public void resultAvailable(Collection<IEstadoAutoService> arg0) {
				System.out.println(arg0.size());
				for (Iterator<IEstadoAutoService> iterator = arg0.iterator(); iterator.hasNext();) {
			        EstadoAuto esa = iterator.next().getEstadoAuto().get();
			        System.out.println(esa.getPox()+"   "+esa.getPoy());

			    }
				
			}

			public void exceptionOccurred(Exception arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});

//		SServiceProvider
//				.getServices(agent.getServiceProvider(),
//						IPositionService.class,
//						RequiredServiceInfo.SCOPE_PLATFORM)
//				.addResultListener(
//						new DefaultResultListener<IPositionService>() {
//
//							@Override
//							public void intermediateResultAvailable(
//									IPositionService arg0) {
//								System.out.println(arg0.avisoPos()
//										.getFirstResult().getDir());
//
//							}
//						});
	}
	
	@Belief
	public void setDireccion(int d){
		direccion= d;
	}
	
	@Belief
	public int getDireccion(){
		return direccion;
	}

	@Belief
	public void setVMax(int v) {
		v_max = v;
	}

	@Belief
	public int getVelocidad() {
		return velocidad;
	}

	@Belief
	public void setVelocidad(int v) {
		velocidad = v;
	}

	@Belief
	public int getX() {
		return pox;
	}

	@Belief
	public void setX(int x) {
		pox = x;
	}

	@Belief
	public int getY() {
		return poy;
	}

	@Belief
	public void setY(int y) {
		poy = y;
	}

	public IFuture<EstadoAuto> getEstadoAuto() {
		return new Future<EstadoAuto>(ea);
	}

}