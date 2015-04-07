package cars;

import jadex.bdiv3.BDIAgent;
import jadex.bridge.service.RequiredServiceInfo;
import jadex.bridge.service.search.SServiceProvider;
import jadex.commons.future.IntermediateDefaultResultListener;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;

@Agent
public class EscuchadorBDI
{
  @Agent
  protected BDIAgent agent;
	
  @AgentBody
  public void body()
  {
	  
	  SServiceProvider.getServices(agent.getServiceProvider(), IPositionService.class, RequiredServiceInfo.SCOPE_PLATFORM).addResultListener(new IntermediateDefaultResultListener<IPositionService>()
	{

	@Override
	public void intermediateResultAvailable(IPositionService arg0) {
		System.out.println(arg0.avisoPos().getFirstResult().getDir());
		
	}
	  }
	  );
  }
}