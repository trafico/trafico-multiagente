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
import jadex.extension.envsupport.environment.ISpaceObject;
import jadex.extension.envsupport.environment.space2d.Grid2D;
import jadex.extension.envsupport.math.IVector2;
import jadex.extension.envsupport.math.Vector2Double;
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
	  @Argument(name="pxin", clazz=Double.class, defaultvalue="0"),
	  @Argument(name="pyin", clazz=Double.class, defaultvalue="0"),
	  @Argument(name="map", clazz=GrafoCalles.class, defaultvalue="null"),
	  @Argument(name= "goalx", clazz=Double.class, defaultvalue="0"),
	  @Argument(name= "goaly", clazz= Double.class, defaultvalue="0"),
	  @Argument(name= "intel", clazz= Boolean.class, defaultvalue="false"),
	  @Argument(name= "ruta", clazz= String.class, defaultvalue= "random")
	})

@Plans({@Plan(body=@Body(DestinoPlan.class))})
public class SimpleCarBDI implements IEstadoAutoService {
	@Belief
	private double x_fin;
	@Belief
	private double y_fin;
	@Belief
	private double pox;
	@Belief
	private double poy;
	@Belief
	private double velocidad;
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
	@Belief
	private String [] ruta;

	@Agent
	BDIAgent agent;

	protected Grid2D env;
	protected ISpaceObject myself;
	protected Vector2Double area;
	
	public SimpleCarBDI() {
		
	}
	
	@AgentCreated
	public void init(){
		this.pox= (Double) agent.getArgument("pxin");
		this.poy= (Double) agent.getArgument("pyin");
		obtenerPos();
		this.x_fin= (Double) agent.getArgument("goalx");
		this.y_fin= (Double) agent.getArgument("goaly");
		this.tipoRuta= (String) agent.getArgument("ruta");
		this.gc= (GrafoCalles) agent.getArgument("map");
		this.status= true;
		this.direccion= 1;
		this.ea= new EstadoAuto(0, direccion, pox, poy, x_fin, y_fin, status);
		area= (Vector2Double) env.getAreaSize();
		env = (Grid2D)agent.getParentAccess().getExtension("my2dspace2").get();
		myself = env.getAvatar(agent.getComponentDescription(), agent.getModel().getFullName());
	}
	
	private void obtenerPos(){
		IVector2 mypos= (IVector2)myself.getProperty("position");
		pox= mypos.getXAsDouble();
		poy= mypos.getYAsDouble();
	}
	
	private PosicionSemaforo isSemaforo(int px, int py){
		ps=null;
		SServiceProvider.getServices(agent.getServiceProvider(), IPosicionSemaforo.class, RequiredServiceInfo.SCOPE_PLATFORM).addResultListener(new IResultListener <Collection<IPosicionSemaforo>>(){

			public void exceptionOccurred(Exception arg0) {
				// TODO Auto-generated method stub
				
			}

			public void resultAvailable(Collection<IPosicionSemaforo> arg0) {
				// TODO Auto-generated method stub
				for (Iterator<IPosicionSemaforo> iterator = arg0.iterator(); iterator.hasNext();) {
			        PosicionSemaforo psm = iterator.next().getPosicionSemaforo().get();
			        if(getDistancia(psm.getPosX(), pox, psm.getPosY(), poy)<=1.5)
			        	ps= psm;

			    }
			}
		});
		return ps;
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
		Vector2Double v2= new Vector2Double(pox, poy);
		myself.setProperty("position", v2);
		System.out.println("Se actualizaron los datos");
	}
	
	public void moverCoche(String ruta){
		String [] tokens= ruta.split(ruta);
		
	}
	
	private int[] equivMap(int x1, int y1, int x2, int y2){
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
	
	private double getDistancia(double x1, double y1, double x2, double y2){
		double res= Math.sqrt(Math.pow(x2-x1, 2)+Math.pow(y2-y1, 2));
		return res;
	}
	
	public boolean isCarNext(double xn, double yn){
		status= true;
		SServiceProvider.getServices(agent.getServiceProvider(), IEstadoAutoService.class, RequiredServiceInfo.SCOPE_PLATFORM).addResultListener(new IResultListener <Collection<IEstadoAutoService>>(){
			
						public void resultAvailable(Collection<IEstadoAutoService> arg0) {
							for (Iterator<IEstadoAutoService> iterator = arg0.iterator(); iterator.hasNext();) {
						        EstadoAuto esa = iterator.next().getEstadoAuto().get();
						        System.out.println(esa.getPox()+"   "+esa.getPoy());
						        if(getDistancia(esa.getPox(),pox, esa.getPoy(), poy)<=1.5)
						        	status= false;
			
						    }
							
						}
			
						public void exceptionOccurred(Exception arg0) {
							// TODO Auto-generated method stub
							
						}
						
					});
		return status;
	}
	
	private void calcularRuta(int[][] mapa, int orig, int dest){
		String r= Rutas.getRutaRandom(mapa, orig, dest);
		ruta= r.split(" ");
	}
	
	@AgentBody
	public void body(){
		while(true){
			pox= pox+1;
			try {
				Thread.sleep(600);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

//	@AgentBody
//	public void body() {
//		
//		IVector2 posi= posDisponible.getPosicion();
//		pox= posi.getXAsInteger();
//		poy= posi.getYAsInteger();
//		System.out.println(pox+"  "+poy);
//		while (true){
//			
//			IVector2 myPosEnv = (IVector2) myself.getProperty("position");
//			this.pox= myPosEnv.getXAsInteger();
//			this.poy= myPosEnv.getYAsInteger();
//			System.out.println(pox+"   "+poy);
////			System.out.println(myself);
//			Vector2Double v2= new Vector2Double(pox+1, poy);
//			
//			myself.setProperty("position", v2);
//			
////			IVector2 posifin= posDisponible.getPosicion();
////			x_fin= posifin.getXAsInteger();
////			y_fin= posifin.getYAsInteger();
////			int [] emap= equivMap(pox, poy, x_fin, y_fin);
////			String ruta= Rutas.getRutaRandom(gc.getGrafo(), emap[0], emap[1]);
////			System.out.println(emap[0]+"   "+emap[1]);
////			moverCoche(ruta);
//			
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//		}
//		
////		agent.adoptPlan(new DetenersePlan(this));
////		agent.adoptPlan(new AvanzarPlan(this, 10));
////		agent.adoptPlan(new GirarDerechaPlan(this));
////		agent.adoptPlan(new GirarIzquierdaPlan(this));
//		//agent.adoptPlan(this.llegarDestino());
//		
////		System.out.println("Aqui");
////		SServiceProvider.getServices(agent.getServiceProvider(), IEstadoAutoService.class, RequiredServiceInfo.SCOPE_PLATFORM).addResultListener(new IResultListener <Collection<IEstadoAutoService>>(){
////
////			public void resultAvailable(Collection<IEstadoAutoService> arg0) {
////				System.out.println(arg0.size());
////				for (Iterator<IEstadoAutoService> iterator = arg0.iterator(); iterator.hasNext();) {
////			        EstadoAuto esa = iterator.next().getEstadoAuto().get();
////			        System.out.println(esa.getPox()+"   "+esa.getPoy());
////
////			    }
////				
////			}
////
////			public void exceptionOccurred(Exception arg0) {
////				// TODO Auto-generated method stub
////				
////			}
////			
////		});
//
////		SServiceProvider
////				.getServices(agent.getServiceProvider(),
////						IPositionService.class,
////						RequiredServiceInfo.SCOPE_PLATFORM)
////				.addResultListener(
////						new DefaultResultListener<IPositionService>() {
////
////							@Override
////							public void intermediateResultAvailable(
////									IPositionService arg0) {
////								System.out.println(arg0.avisoPos()
////										.getFirstResult().getDir());
////
////							}
////						});
//	}
	
	public IFuture<EstadoAuto> getEstadoAuto() {
		return new Future<EstadoAuto>(ea);
	}

}