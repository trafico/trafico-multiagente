package cars;

public class SetCarPosIniPlan extends SetParamPlan {

	@Override
	protected boolean isCondicion(){
		String id1= mensaje[0];
		String id2= (String)getBeliefbase().getBelief("ID_CAR").getFact();
		if(id1.equals(id2) && nParam==3)
			return true;
		else
			return false;
	}

	@Override
	public void body() {
		// TODO Auto-generated method stub
		if(isCondicion()){
			String [] nombres={"x_ini", "y_ini"};
			actualizarParam(nombres, "int");
			int v1= Integer.parseInt(mensaje[1]);
			int v2= Integer.parseInt(mensaje[2]);
			ea.setPox(v1);
			ea.setPoy(v2);
		}
	}

}