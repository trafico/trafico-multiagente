package lights;

import jadex.base.Starter;
import jadex.bridge.IComponentIdentifier;
import jadex.bridge.IExternalAccess;
import jadex.bridge.service.RequiredServiceInfo;
import jadex.bridge.service.search.SServiceProvider;
import jadex.bridge.service.types.cms.IComponentManagementService;
import jadex.commons.future.IFuture;
import jadex.commons.future.ThreadSuspendable;

public class Experimento {

	public static void main(String [ ] args)
	{
		// Merge arguments and default arguments.
		String[]	defargs	= new String[]
				{
				"-gui", "false",
				"-welcome", "false",
				"-cli", "false",
				"-printpass", "false"
				};
		String[]	newargs	= new String[defargs.length+args.length];
		System.arraycopy(defargs, 0, newargs, 0, defargs.length);
		System.arraycopy(args, 0, newargs, defargs.length, args.length);

		// Start the platform with the arguments.
		IFuture<IExternalAccess>	platfut	= Starter.createPlatform(newargs);

		// Wait until the platform has started and retrieve the platform access.
		ThreadSuspendable	sus	= new ThreadSuspendable();
		IExternalAccess	platform	= platfut.get(sus);
		System.out.println("Started platform: "+platform.getComponentIdentifier());

		// Get the CMS service from the platform
		IComponentManagementService	cms	= SServiceProvider.getService(platform.getServiceProvider(),
				IComponentManagementService.class, RequiredServiceInfo.SCOPE_PLATFORM).get(sus);

		//Inicializo el mapa en ceros
		int [][] mapa = new int[50][50];
		int vision = 5; //Vision de los semaforos, en realidad es 10
		int poner = 1; //Debo poner semaforos
		int con_col = 0; //Cuenta el numero de columnas que he avanzado para poner semaforos
		int con_ren = 0; //Cuenta el numero de renglones para poner el semaforo
		int ponerB = 0; //Debo poner Pared
		int conB_col = 0; //Cuantas columnas he avanzado para poner columnas
		int conB_ren = vision -2; //Cuantos renlgones he avanzado
		
		//Lleno de ceros
		for(int i=0; i<50;i++){
			for(int j=0; j<50; j++) {
				
				//Poner las barreras
				if(ponerB == 1 && (conB_col > 1)  ) {
					mapa[i-1][j]=2;
					conB_col = conB_col + 1;
					//System.out.println(i+" "+j+" "+conB_col);
				}
				else {
					conB_col = conB_col + 1;
					//System.out.println(conB_col);
				}
				
				if(conB_col == vision) {
					//ponerB =0;
					conB_col = 0;
				}
				
				
				if(con_ren == vision){
					poner =1;
					con_ren =0;
				}
				
				if(con_col == vision)
					con_col =0;
				
				if(con_col == 0 && poner == 1){
					mapa[i][j]=1;
					con_col = con_col + 1;
				}
				else {
					con_col = con_col + 1;
				}
				
			}
			if(ponerB == 1 && conB_ren>1) {
				conB_ren = conB_ren -1;
				
			}else {
				ponerB =0;
			}
			
			
			if(poner == 1)
				poner = 0;
			con_ren = con_ren + 1;
			
			if(con_ren == 3) {
				ponerB =1;
				conB_ren = vision -2;
				//mapa[i][0]=2;
			}
		}
		
		
		
		
		/*for(int i=0; i<50;i++){
			for(int j=0; j<50; j++) {
				System.out.print(mapa[i][j]);
			}
			System.out.println();
		}*/
		
			//System.out.println(mapa[i][1]);;
		
		// Start the chat component
		IComponentIdentifier	cid	= cms.createComponent(null,"lights/SemaforoInteligenteNoComunicacion.agent.xml", null, null).get(sus);
		//System.out.println("Started chat component: "+cid);
	}


}
