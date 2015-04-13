package cars;

import jadex.bridge.fipa.SFipa;
import jadex.bdi.runtime.IMessageEvent;
import jadex.bdi.runtime.Plan;

public abstract class GetParamPlan extends Plan {
	private String[] mensaje;
	private int nParam;
	private IMessageEvent me;

	public GetParamPlan() {
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

	protected boolean isCondicion() {
		String id1 = mensaje[0];
		String id2 = (String) getBeliefbase().getBelief("ID_CAR").getFact();
		if (!id2.equals("") && id1.equals(id2) && nParam == 1)
			return true;
		else
			return false;
	}

	protected synchronized void crearMensajeSalida(String reply,
			String[] param, String type) {
		String res = (String) getBeliefbase().getBelief("ID_CAR").getFact()
				+ " ";

		for (int i = 0; i < param.length; i = i + 1) {

			if (type.equals("int"))
				res = res
						+ ((Integer) getBeliefbase().getBelief(param[i])
								.getFact()).intValue();
			else if (type.equals("String"))
				res = res
						+ (String) getBeliefbase().getBelief(param[i])
								.getFact();
			else if (type.equals("boolean"))
				res = res
						+ ((Boolean) getBeliefbase().getBelief(param[i])
								.getFact()).booleanValue();
		}
		IMessageEvent ime = getEventbase().createReply(me, reply);
		ime.getParameter(SFipa.CONTENT).setValue(res);
		sendMessage(ime);
	}

	protected synchronized void crearMensajeSalida(String reply, String param,
			String type) {
		String res = (String) getBeliefbase().getBelief("ID_CAR").getFact()
				+ " ";
		if (type.equals("int"))
			res = res
					+ ((Integer) getBeliefbase().getBelief(param).getFact())
							.intValue();
		else if (type.equals("String"))
			res = res + (String) getBeliefbase().getBelief(param).getFact();
		else if (type.equals("boolean"))
			res = res
					+ ((Boolean) getBeliefbase().getBelief(param).getFact())
							.booleanValue();
		IMessageEvent ime = getEventbase().createReply(me, reply);
		ime.getParameter(SFipa.CONTENT).setValue(res);
		sendMessage(ime);
	}

	public abstract void body();

}