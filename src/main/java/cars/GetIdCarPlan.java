package cars;

public class GetIdCarPlan extends GetParamPlan {

	@Override
	public void body() {
		// TODO Auto-generated method stub
		if(isCondicion()){
			crearMensajeSalida("inform", "ID_CAR", "String");
		}
	}

}
