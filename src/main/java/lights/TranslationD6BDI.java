package lights;

import java.util.HashMap;
import java.util.Map;

import jadex.bdiv3.BDIAgent;
import jadex.bdiv3.annotation.Belief;
import jadex.bdiv3.annotation.Goal;
import jadex.bdiv3.annotation.Goal.ExcludeMode;
import jadex.bdiv3.annotation.GoalCreationCondition;
import jadex.bdiv3.annotation.GoalMaintainCondition;
import jadex.bdiv3.annotation.GoalParameter;
import jadex.bdiv3.annotation.GoalRecurCondition;
import jadex.bdiv3.annotation.GoalResult;
import jadex.bdiv3.annotation.GoalTargetCondition;
import jadex.bdiv3.annotation.Plan;
import jadex.bdiv3.annotation.Trigger;
import jadex.bdiv3.runtime.ChangeEvent;
import jadex.bdiv3.runtime.impl.PlanFailureException;
import jadex.bridge.IComponentStep;
import jadex.bridge.IInternalAccess;
import jadex.commons.future.IFuture;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import jadex.micro.annotation.AgentCreated;


@Agent
public class TranslationD6BDI {

	@Agent
	protected BDIAgent agent;

	@Belief
	protected Map<String, String> egwords;//= new HashMap<String, String>();

	//@Belief(dynamic=true)
	//protected boolean alarm = wordtable.containsKey("bugger");

	@Belief
	protected String eword;

	//@Belief
	//protected String egwords;

	@AgentCreated
	public void init()
	{
		System.out.println("Created: "+this);
		//this.wordtable = new HashMap<String, String>(); ;
	}


	@Goal(excludemode=ExcludeMode.Never)
	public class MaintainStorageGoal
	{
		@GoalMaintainCondition(beliefs="egwords")
		protected boolean maintain()
		{
			return egwords.size()<=4;
		}

		@GoalTargetCondition(beliefs="egwords")
		protected boolean target()
		{
			return egwords.size()<3;
		}
	} 

	/*@Plan(trigger=@Trigger(goals=Translate.class))
	protected String translate(String eword)
	{
		//return wordtable.get(eword);
		System.out.println("Llamado");
		String ret = wordtable.get(eword);
		if(ret==null)
			throw new PlanFailureException();
		return ret;
	}*/

	@Plan(trigger=@Trigger(goals=MaintainStorageGoal.class))
	protected void removeEntry()
	{
		//return wordtable.get(eword);
		//System.out.println(egwords.remove("milk"));
		int ultimo = egwords.size();
		String lo = egwords.get(ultimo-1);
		lo = egwords.values().iterator().next();
		System.out.println(lo);
		//egwords.remove("milk");
		//return ret;
	}



	@AgentBody
	public void body()
	{
		//String eword = "cat";
		//String gword = (String)agent.dispatchTopLevelGoal(new Translate(eword)).get();
		//System.out.println("Translated: "+eword+" "+gword);
		agent.dispatchTopLevelGoal(new MaintainStorageGoal());
		egwords = new HashMap<String, String>();
		egwords.put("milk", "Milch");
		egwords.put("cow", "Kuh");
		egwords.put("cat", "Katze");
		egwords.put("dog", "Hund");

		IComponentStep<Void> step = new IComponentStep<Void>()
				{
			int cnt = 0;
			public IFuture<Void> execute(IInternalAccess ia)
			{
				egwords.put("eword_#"+cnt, "gword_#"+cnt++);
				System.out.println("egwords: "+egwords);
				agent.waitFor(2000, this);
				return IFuture.DONE;
			}
				};

		agent.waitFor(2000, step);


	} 
}
