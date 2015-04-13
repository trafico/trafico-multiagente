package cars;

public class GetCarDirPlan extends GetParamPlan {

	@Override
	public void body() {
		// TODO Auto-generated method stub
		if(isCondicion()){
			crearMensajeSalida("inform", "direccion", "int");
		}
	}

}