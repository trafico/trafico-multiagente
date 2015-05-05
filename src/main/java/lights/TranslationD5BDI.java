package lights;

import java.util.HashMap;
import java.util.Map;

import jadex.bdiv3.BDIAgent;
import jadex.bdiv3.annotation.Belief;
import jadex.bdiv3.annotation.Goal;
import jadex.bdiv3.annotation.GoalCreationCondition;
import jadex.bdiv3.annotation.GoalParameter;
import jadex.bdiv3.annotation.GoalRecurCondition;
import jadex.bdiv3.annotation.GoalResult;
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
public class TranslationD5BDI {

	@Agent
	protected BDIAgent agent;

	@Belief
	protected Map<String, String> wordtable;//= new HashMap<String, String>();

	//@Belief(dynamic=true)
	//protected boolean alarm = wordtable.containsKey("bugger");

	@Belief
	protected String eword;

	@AgentCreated
	public void init()
	{
		System.out.println("Created: "+this);
		this.wordtable = new HashMap<String, String>(); ;
		/*wordtable.put("coffee", "Kaffee");
		wordtable.put("milk", "Milch");
		wordtable.put("cow", "Kuh");
		wordtable.put("cat", "Katze");
		wordtable.put("dog", "Hund");*/
		//wordtable.put("bugger", "Flegel");
	}

	/*@Plan(trigger=@Trigger(factaddeds="wordtable"))
	public void checkWordPairPlan(Map.Entry<String, String> wordpair)
	{
	  if(wordpair.getKey().equals("bugger"))
	   System.out.println("Warning, a colloquial word pair has been added: "+wordpair.getKey()+" "+wordpair.getValue());
	}*/
	/*@Plan(trigger=@Trigger(factchangeds="alarm"))
	public void checkWordPairPlan(ChangeEvent event)
	{
		if(alarm == true)
	    System.out.println("Warning, a colloquial word pair has been added.");

	}*/

	@Goal(recur=true)
	public class Translate
	{

		@GoalParameter
		protected String eword;

		@GoalResult
		protected String gword;


		public Translate(String eword)
		{
			this.eword = eword;
		}

		@GoalRecurCondition(beliefs="wordtable")
		public boolean checkRecur()
		{
			System.out.println("Cambiado recur");
			return true;
		} 

	}

	@Plan(trigger=@Trigger(goals=Translate.class))
	protected String translate(String eword)
	{
		//return wordtable.get(eword);
		System.out.println("Llamado");
		String ret = wordtable.get(eword);
		if(ret==null)
			throw new PlanFailureException();
		return ret;
	}

	@AgentBody
	public void body()
	{
		//String eword = "cat";
		//String gword = (String)agent.dispatchTopLevelGoal(new Translate(eword)).get();
		//System.out.println("Translated: "+eword+" "+gword);
		agent.scheduleStep(new IComponentStep<Void>()
				{
			public IFuture<Void> execute(IInternalAccess ia)
			{
				wordtable.put("bugger", "Flegel");
				return IFuture.DONE;
			}
				}, 3000);

		String eword = "bugger";
		String gword = (String)agent.dispatchTopLevelGoal(new Translate(eword)).get();
		System.out.println("Translated: "+eword+" "+gword);


	} 
}
