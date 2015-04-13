package cars;

public class GetCarVelMaxPlan extends GetParamPlan {

	@Override
	public void body() {
		// TODO Auto-generated method stub
		if(isCondicion()){
			crearMensajeSalida("inform", "vel_max", "int");
		}
	}

}
