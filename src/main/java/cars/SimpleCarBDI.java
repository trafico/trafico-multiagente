package cars;

//import java.text.SimpleDateFormat;
//import java.util.Collection;

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
import jadex.commons.future.ITuple2Future;
import jadex.commons.future.IntermediateDefaultResultListener;
import jadex.commons.future.Tuple2Future;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import jadex.micro.annotation.AgentCreated;
import jadex.micro.annotation.Implementation;
import jadex.micro.annotation.ProvidedService;
import jadex.micro.annotation.ProvidedServices;

@Agent
@Service
//@ProvidedServices(@ProvidedService(type = IPositionService.class))
@ProvidedServices(@ProvidedService(name="posser", type=IPositionService.class, implementation=@Implementation(BDIAgent.class)))
@Plans({@Plan(body=@Body(DetenersePlan.class)), @Plan(body=@Body(AvanzarPlan.class)), @Plan(body=@Body(GirarDerechaPlan.class)), @Plan(body=@Body(GirarIzquierdaPlan.class))})
public class SimpleCarBDI implements IPositionService {
	private int x_ini;
	private int y_ini;
	private int x_fin;
	private int y_fin;
	private int pox;
	private int poy;
	private int velocidad;
	private int v_max;
	private int direccion; //0:Norte, 1:Este, 2:Sur, 3:Oeste.
	private Posicion pos;
	private Tuple2Future<Posicion, SimpleCarBDI> ret;

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
		pos= new Posicion(pox, poy, direccion);
		ret = new Tuple2Future<Posicion, SimpleCarBDI>();
		ret.setFirstResult(pos);
		ret.setSecondResult(this);
	}

	@AgentBody
	public void body() {
		
//		agent.adoptPlan(new DetenersePlan(this));
//		agent.adoptPlan(new AvanzarPlan(this, 10));
//		agent.adoptPlan(new GirarDerechaPlan(this));
//		agent.adoptPlan(new GirarIzquierdaPlan(this));

		SServiceProvider
				.getServices(agent.getServiceProvider(),
						IPositionService.class,
						RequiredServiceInfo.SCOPE_PLATFORM)
				.addResultListener(
						new IntermediateDefaultResultListener<IPositionService>() {

							@Override
							public void intermediateResultAvailable(
									IPositionService arg0) {
								System.out.println(arg0.avisoPos()
										.getFirstResult().getDir());

							}
						});
	}
	
	@Belief
	public void setPosicion(){
		pos.setPos(pox, poy);
		pos.setDir(direccion);
	}
	
	@Belief
	public Posicion getPosicion(){
		return pos;
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

	@Override
	public ITuple2Future<Posicion, SimpleCarBDI> avisoPos() {
		setPosicion();
		return ret;
	}

}