package cars;

public class GetCarVelMaxPlan extends GetParamPlan {

	@Override
	public void body() {
		// TODO Auto-generated method stub
		if(isCondicion()){
			crearMensajeSalida("CarInform", "vel_max", "int");
		}
	}

}
