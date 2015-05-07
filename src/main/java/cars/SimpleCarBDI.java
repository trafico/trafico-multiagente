package cars;

//import java.text.SimpleDateFormat;
//import java.util.Collection;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

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
	  @Argument(name="pxin", clazz=Double.class, defaultvalue="0.0"),
	  @Argument(name="pyin", clazz=Double.class, defaultvalue="0.0"),
	  @Argument(name= "goalx", clazz=Double.class, defaultvalue="0.0"),
	  @Argument(name= "goaly", clazz= Double.class, defaultvalue="0.0"),
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
	private int [][] gc;
	@Belief
	private String tipoRuta;
	@Belief
	private PosicionSemaforo ps;
	@Belief
	private String [] ruta;
	@Belief
	boolean carSig;

	@Agent
	BDIAgent agent;

	protected Grid2D env;
	protected ISpaceObject myself;
	protected IVector2 area;
	
	public SimpleCarBDI() {
		
	}
	
	@AgentCreated
	public void init(){
//		System.out.println("Aquí");
		this.pox= (Double) agent.getArgument("pxin");
		this.poy= (Double) agent.getArgument("pyin");
		
		this.x_fin= (Double) agent.getArgument("goalx");
		this.y_fin= (Double) agent.getArgument("goaly");
		this.tipoRuta= (String) agent.getArgument("ruta");
		this.status= true;
//		this.direccion= 1;
		this.ea= new EstadoAuto(0, direccion, pox, poy, x_fin, y_fin, status);
		
		env = (Grid2D)agent.getParentAccess().getExtension("my2dspace2").get();
		myself = env.getAvatar(agent.getComponentDescription(), agent.getModel().getFullName());
		area= (IVector2) env.getAreaSize();
//		System.out.println("Aquí...");
	}
	
	private void obtenerPos(){
//		IVector2 mypos=posDisponible.getPosicion();
//		myself.setProperty("position", mypos);
		IVector2 mypos= (IVector2)myself.getProperty("position");
		this.direccion= (Integer) myself.getProperty("direccion");
//		System.out.println("Aquí");
		pox= mypos.getXAsDouble();
		poy= mypos.getYAsDouble();
	}
	
	private PosicionSemaforo isSemaforo(double px, double py){
		ps=null;
		System.out.println("Servicio semáforo");
		SServiceProvider.getServices(agent.getServiceProvider(), IPosicionSemaforo.class, RequiredServiceInfo.SCOPE_PLATFORM).addResultListener(new IResultListener <Collection<IPosicionSemaforo>>(){

			public void exceptionOccurred(Exception arg0) {
				// TODO Auto-generated method stub
				
			}

			public void resultAvailable(Collection<IPosicionSemaforo> arg0) {
				System.out.println(arg0.size());
				// TODO Auto-generated method stub
				for (Iterator<IPosicionSemaforo> iterator = arg0.iterator(); iterator.hasNext();) {
			        PosicionSemaforo psm = iterator.next().getPosicionSemaforo().get();
			        if(getDistancia(psm.getPosX(), pox, psm.getPosY(), poy)<1.5)
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
		IVector2 v2= (IVector2)new Vector2Double(pox, poy);
		myself.setProperty("position", v2);
		myself.setProperty("direccion", direccion);
	}
	
	public void moverCoche(String ruta){
		String [] tokens= ruta.split(ruta);
		
	}
	
	private int[] equivMap(double x1, double y1, double x2, double y2){
		int [] emap= new int[2];
		int nx1= (int)(x1-1)/10;
		int ny1= (int)(y1-1)/10;
		int nx2= (int)(x2-1)/10;
		int ny2= (int)(y2-1)/10;
		int tam= (int)Math.sqrt(gc.length);
		int [][] mapa2= new int[tam][tam];
		int cont=0;
		for(int i=0; i<tam; i=i+1){
			for(int j=0; j<tam; j=j+1){
				mapa2[i][j]=cont;
				cont=cont+1;
			}
		}
		emap[0]= mapa2[(int)nx1][(int)ny1];
		emap[1]= mapa2[(int)nx2][(int)ny2];
		return emap;
	}
	
	private double getDistancia(double x1, double y1, double x2, double y2){
		double res= Math.sqrt(Math.pow(x2-x1, 2)+Math.pow(y2-y1, 2));
		return res;
	}
	
	public boolean isCarNext(double xn, double yn){
		carSig= false;
		SServiceProvider.getServices(agent.getServiceProvider(), IEstadoAutoService.class, RequiredServiceInfo.SCOPE_PLATFORM).addResultListener(new IResultListener <Collection<IEstadoAutoService>>(){
			
						public void resultAvailable(Collection<IEstadoAutoService> arg0) {
							for (Iterator<IEstadoAutoService> iterator = arg0.iterator(); iterator.hasNext();) {
						        EstadoAuto esa = iterator.next().getEstadoAuto().get();
						        if(getDistancia(esa.getPox(),pox, esa.getPoy(), poy)<1 && esa.getDir()==direccion){
						        	System.out.println("Se saltó a sí mismo");
						        	continue;
						     
						        }
						        if(getDistancia(esa.getPox(),pox, esa.getPoy(), poy)<=1.3 && esa.getDir()==direccion){
						        	System.out.println(getDistancia(esa.getPox(),pox, esa.getPoy(), poy));
						        	carSig= true;
						        }
			
						    }
							
						}
			
						public void exceptionOccurred(Exception arg0) {
							// TODO Auto-generated method stub
							
						}
						
					});
		return carSig;
	}
	
	private void calcularRuta(int[][] mapa, int orig, int dest){
		String r= Rutas.getRutaRandom(mapa, orig, dest);
//		String r= Rutas.getRutaDijstra(mapa, orig, dest);
		ruta= r.split(" ");
	}
	
	private double analizarX(double x){
		double nx=x;
		int xtot= area.getXAsInteger();
//		System.out.println(xtot);
		if(nx>xtot)
			nx=1;
		if(nx<0)
			nx=xtot;
		return nx;
	}
	
	private double analizarY(double y){
		double ny=y;
		int ytot= area.getYAsInteger();
		if(ny> ytot)
			ny=1;
		if(ny<0)
			ny=ytot;
		return ny;
	}
	
	private void analizarPos(){
		int xtot= area.getXAsInteger();
		int ytot= area.getYAsInteger();
		if(pox>=xtot)
			pox=0;
		if(poy>=ytot)
			poy=0;
		if(pox<0)
			pox=xtot-1;
		if(poy<0)
			poy=ytot-1;
	}
	
	private synchronized void crearMapa(){
		if (!GrafoCalles.isCreado()){
			int[] vel= new int[36];
			for (int i=0; i<vel.length; i=i+1){
				vel[i]=i+1;
			}
			GrafoCalles.hacerGrafoCalles(vel);
		}
		gc= GrafoCalles.getGrafo();
		
	}
	
	private int [] convertirRuta(String [] r){
		int [] nr= new int[r.length];
		for (int i=0; i<nr.length; i=i+1){
			if (r[i].equals("n"))
				nr[i]=1;
			else if (r[i].equals("e"))
				nr[i]=2;
			else if (r[i].equals("s"))
				nr[i]=3;
			else if (r[i].equals("o"))
				nr[i]=4;
		}
		return nr;
	}
	
	@AgentBody
	public void body(){
		obtenerPos();
		crearMapa();
		while(true){
			IVector2 goal=posDisponible.getPosicion();
			this.x_fin= goal.getXAsDouble();
			this.y_fin= goal.getYAsDouble();
			
			int [] puntos= this.equivMap(pox, poy, x_fin, y_fin);
			calcularRuta(gc,puntos[0], puntos[1]);
			int [] r2= convertirRuta(ruta); 
			
			int paso=0;
			while(pox!=x_fin && poy!=y_fin){
				if(moverSig(r2[paso]))
					paso=paso+1;
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(pox+"  "+poy);
				System.out.println(x_fin+"  "+y_fin);
			}
			
			
//			System.out.println(pox);
		}
	}
	
	private boolean moverSig(int dir){
		boolean res= false;
		double nx=0;
		double ny=0;
		if(dir==1){
			nx=pox;
			ny= analizarY(poy-1);
		}
		else if(dir==2){
			nx= analizarX(pox+1);
			ny=poy;
		}
		else if(dir==3){
			nx= pox;
			ny= analizarY(poy+1);
		}
		else if(dir==4){
			nx= analizarX(pox-1);
			ny= poy;
		}
		
		PosicionSemaforo sem= this.isSemaforo(nx, ny);
		System.out.println(sem);
		
		if(!this.isCarNext(nx, ny)){
			pox= nx;
			poy= ny;
			this.direccion=dir;
			actualizarEstado();
			res=true;	
		}
		else if(isCarNext(nx, ny)){
//			System.out.println(sem.getEstadoActual());
			res=false;
		}
		
		
		return res;
		
	}
	
	public IFuture<EstadoAuto> getEstadoAuto() {
		return new Future<EstadoAuto>(ea);
	}

}