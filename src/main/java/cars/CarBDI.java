package cars;

import java.text.SimpleDateFormat;

import jadex.bdiv3.annotation.Belief;
import jadex.bdiv3.annotation.Plan;
import jadex.bdiv3.annotation.Trigger;
import jadex.bridge.service.annotation.Service;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;


@Agent
@Service
public class CarBDI {
	
	@Belief
	private long time;
	SimpleDateFormat formatter= new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	
	@Belief
	public long getTime()
	{
	  return time;
	}

	@Belief
	public void setTime(long time)
	{
	  this.time = time;
	}

	@Plan(trigger=@Trigger(factchangeds="time"))
	protected void printTime()
	{
	  System.out.println(formatter.format(getTime()));
	}
	
	@AgentBody
	public void Body(){
		this.setTime(System.currentTimeMillis());
	}
}
