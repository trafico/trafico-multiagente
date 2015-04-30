package cars;

public class GetCarGoalPlan extends GetParamPlan {

	@Override
	public void body() {
		// TODO Auto-generated method stub
		if(isCondicion()){
			String [] params={"x_fin", "y_fin"};
			crearMensajeSalida("CarInform", params, "int");
		}
	}

}