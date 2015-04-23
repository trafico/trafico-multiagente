package cars;

import jadex.bridge.fipa.SFipa;
import jadex.bdi.runtime.IMessageEvent;
import jadex.bdi.runtime.Plan;

public abstract class SetParamPlan extends Plan {
	protected String[] mensaje;
	protected int nParam;
	protected IMessageEvent me;
	protected EstadoAuto ea= (EstadoAuto)getBeliefbase().getBelief("estadoAuto").getFact();

	public SetParamPlan() {
		// Initialization code.
		recuperarMensaje();
	}

	protected void recuperarMensaje() {
		me = (IMessageEvent) getReason();
		String lmen = (String) me.getParameter(SFipa.CONTENT).getValue();
		SepararMensaje sp = new SepararMensaje(lmen);
		mensaje = sp.getResultado();
		nParam = sp.getNDatos();
	}

	protected synchronized void actualizarParam(String [] nombre, String type) {
		for (int i = 0; i < nombre.length; i = i + 1) {
			if (type.equals("int")) {
				int par = Integer.parseInt(mensaje[i+1]);
				getBeliefbase().getBelief(nombre[i]).setFact(new Integer(par));
			} else if (type.equals("boolean")) {
				boolean par = Boolean.getBoolean(mensaje[i+1]);
				getBeliefbase().getBelief(nombre[i]).setFact(new Boolean(par));
			} else if (type.equals("String")) {
				String par = mensaje[i+1];
				getBeliefbase().getBelief(nombre[i]).setFact(par);
			}
		}
	}

	protected abstract boolean isCondicion();

	public abstract void body();

}