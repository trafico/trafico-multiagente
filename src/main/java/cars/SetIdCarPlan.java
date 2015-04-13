package cars;

public class SetIdCarPlan extends SetParamPlan {

	@Override
	protected boolean isCondicion(){
		String id1= mensaje[0];
		String id2= (String)getBeliefbase().getBelief("ID_CAR").getFact();
		if(id2.equals("") && nParam==1)
			return true;
		else
			return false;
	}

	@Override
	public void body() {
		// TODO Auto-generated method stub
		if(isCondicion()){
			String[] nombres={"ID_CAR"};
			String[] mensaje2={mensaje[0], mensaje[0]};
			mensaje= mensaje2;
			actualizarParam(nombres, "String");
		}
	}

}