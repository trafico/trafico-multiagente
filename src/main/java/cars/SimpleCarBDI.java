package cars;

//import java.text.SimpleDateFormat;
//import java.util.Collection;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import environment.GrafoCalles;
import environment.posDisponible;
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
import jadex.extension.envsupport.math.IVector2;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import jadex.micro.annotation.AgentCreated;
import jadex.micro.annotation.Argument;
import jadex.micro.annotation.Arguments;
import jadex.micro.annotation.Implementation;
import jadex.micro.annotation.ProvidedService;
import jadex.micro.annotation.ProvidedServices;
import jadex.micro.annotation.RequiredService;
import jadex.micro.annotation.RequiredServices;
import lights.IEstadoSemaforoService;
import lights.IPosicionSemaforo;
import lights.PosicionSemaforo;

@Agent
@Service
@ProvidedServices(@ProvidedService(type=IEstadoAutoService.class))
@RequiredServices
({
	@RequiredService(type=IPosicionSemaforo.class, name = "estadoSemaforo"),
	@RequiredService(type=IEstadoAutoService.class, name = "estadoAutos")
})


@Arguments({
	  @Argument(name="pxin", clazz=Integer.class, defaultvalue="0"),
	  @Argument(name="pyin", clazz=Integer.class, defaultvalue="0"),
	  @Argument(name="map", clazz=GrafoCalles.class, defaultvalue="null"),
	  @Argument(name= "goalx", clazz=Integer.class, defaultvalue="0"),
	  @Argument(name= "goaly", clazz= Integer.class, defaultvalue="0"),
	  @Argument(name= "intel", clazz= Boolean.class, defaultvalue="false"),
	  @Argument(name= "ruta", clazz= String.class, defaultvalue= "random")
	})

@Plans({@Plan(body=@Body(DestinoPlan.class))})
public class SimpleCarBDI implements IEstadoAutoService {
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
	private int direccion; //1:Norte, 2:Este, 3:Sur, 4:Oeste.
	@Belief
	private EstadoAuto ea;
	@Belief
	private boolean intel;
	@Belief
	private GrafoCalles gc;
	@Belief
	private String tipoRuta;
	@Belief
	private PosicionSemaforo ps;

	@Agent
	BDIAgent agent;

	public SimpleCarBDI() {
		
	}
	
	public PosicionSemaforo isSemaforo(int px, int py){
		ps=null;
		SServiceProvider.getServices(agent.getServiceProvider(), IPosicionSemaforo.class, RequiredServiceInfo.SCOPE_PLATFORM).addResultListener(new IResultListener <Collection<IPosicionSemaforo>>(){

			public void exceptionOccurred(Exception arg0) {
				// TODO Auto-generated method stub
				
			}

			public void resultAvailable(Collection<IPosicionSemaforo> arg0) {
				// TODO Auto-generated method stub
				for (Iterator<IPosicionSemaforo> iterator = arg0.iterator(); iterator.hasNext();) {
			        PosicionSemaforo psm = iterator.next().getPosicionSemaforo().get();
			        if(Math.sqrt(Math.pow(psm.getPosX()-pox, 2)+Math.pow(psm.getPosY()-poy, 2))<=1.5)
			        	ps= psm;

			    }
			}
		});
		return ps;
		}
	
	@AgentCreated
	public void init()
	{
		this.pox= (Integer) agent.getArgument("pxin");
		this.poy= (Integer) agent.getArgument("pyin");
		this.x_fin= (Integer) agent.getArgument("goalx");
		this.y_fin= (Integer) agent.getArgument("goaly");
		this.tipoRuta= (String) agent.getArgument("ruta");
		this.gc= (GrafoCalles) agent.getArgument("map");
		this.status= true;
		this.direccion= 1;
		this.ea= new EstadoAuto(0, direccion, pox, poy, x_fin, y_fin, status);
	}
	
	@Plan(trigger=@Trigger(factchangeds={"pox", "poy", "x_fin", "y_fin","status","direccion","velocidad"}))
	public void actualizarEstado(){
		ea.setPox(pox);
		ea.setPoy(poy);
		ea.setDir(direccion);
		ea.setStatus(status);
		ea.setVel(velocidad);
		ea.setXFin(x_fin);
		ea.setYFin(y_fin);
		System.out.println("Se actualizaron los datos");
	}
	
	@Plan
	public void llegarDestino(){
		IVector2 posi= posDisponible.getPosicion();
		pox= posi.getXAsInteger();
		poy= posi.getYAsInteger();
		while (true){
			
			IVector2 posifin= posDisponible.getPosicion();
			x_fin= posifin.getXAsInteger();
			y_fin= posifin.getYAsInteger();
			int [] emap= equivMap(pox, poy, x_fin, y_fin);
			String ruta= Rutas.getRutaRandom(gc.getGrafo(), emap[0], emap[1]);
			System.out.println(emap[0]+"   "+emap[1]);
			moverCoche(ruta);
		}
	}
	
	public void moverCoche(String ruta){
		String [] tokens= ruta.split(ruta);
		
	}
	
	public int[] equivMap(int x1, int y1, int x2, int y2){
		int [] emap= new int[2];
		int nx1= (x1-1)/10;
		int ny1= (y1-1)/10;
		int nx2= (x2-1)/10;
		int ny2= (y2-1)/10;
		int tam= (int)Math.sqrt(gc.getGrafo().length);
		int [][] mapa2= new int[tam][tam];
		int cont=0;
		for(int i=0; i<tam; i=i+1){
			for(int j=0; j<tam; j=j+1){
				mapa2[i][j]=cont;
				cont=cont+1;
			}
		}
		emap[0]= mapa2[nx1][ny1];
		emap[1]= mapa2[nx2][ny2];
		return emap;
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