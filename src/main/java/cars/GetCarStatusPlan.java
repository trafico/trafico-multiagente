package cars;

public class GetCarStatusPlan extends GetParamPlan {

	@Override
	public void body() {
		// TODO Auto-generated method stub
		if(isCondicion()){
			crearMensajeSalida("inform", "isAvanzando", "boolean");
		}
	}

}
