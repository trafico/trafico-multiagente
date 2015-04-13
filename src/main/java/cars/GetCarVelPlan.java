package cars;

public class GetCarVelPlan extends GetParamPlan {

	@Override
	public void body() {
		// TODO Auto-generated method stub
		if(isCondicion()){
			crearMensajeSalida("CarInform", "velocidad", "int");
		}
	}

}
