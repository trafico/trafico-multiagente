package cars;

public class SetCarGoalPlan extends SetParamPlan {

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
			String [] nombres={"x_fin", "y_fin"};
			actualizarParam(nombres, "int");
			int v1= Integer.parseInt(mensaje[1]);
			int v2= Integer.parseInt(mensaje[2]);
			ea.setXFin(v1);
			ea.setYFin(v2);
		}
	}

}