package cars;

public class GetPosicionPlan extends GetParamPlan {

	@Override
	public void body() {
		// TODO Auto-generated method stub
		if(isCondicion()){
			String [] params={"pox", "poy"};
			crearMensajeSalida("inform", params, "int");
		}
	}

}
