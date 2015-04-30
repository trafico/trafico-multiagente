package cars;

public class SetCarDirPlan extends SetParamPlan {

	@Override
	protected boolean isCondicion(){
		String id1= mensaje[0];
		String id2= (String)getBeliefbase().getBelief("ID_CAR").getFact();
		if(id1.equals(id2) && nParam==2)
			return true;
		else
			return false;
	}

	@Override
	public void body() {
		if(isCondicion()){
			String nombres[]={"direccion"};
			actualizarParam(nombres, "int");
		}
	}

}