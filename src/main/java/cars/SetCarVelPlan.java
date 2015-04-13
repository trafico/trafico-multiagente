package cars;

public class SetCarVelPlan extends SetParamPlan {

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
		// TODO Auto-generated method stub
		if(isCondicion()){
			String [] nombres={"velocidad"};
			actualizarParam(nombres, "int");
		}
	}
}