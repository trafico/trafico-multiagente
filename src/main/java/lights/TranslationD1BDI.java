package lights;

import java.util.HashMap;
import java.util.Map;

import jadex.bdiv3.BDIAgent;
import jadex.bdiv3.annotation.Belief;
import jadex.bdiv3.annotation.Goal;
import jadex.bdiv3.annotation.GoalCreationCondition;
import jadex.bdiv3.annotation.GoalParameter;
import jadex.bdiv3.annotation.GoalResult;
import jadex.bdiv3.annotation.Plan;
import jadex.bdiv3.annotation.Trigger;
import jadex.bdiv3.runtime.ChangeEvent;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import jadex.micro.annotation.AgentCreated;


@Agent
public class TranslationD1BDI {

	@Agent
	protected BDIAgent agent;
	
	@Belief
	protected Map<String, String> wordtable= new HashMap<String, String>();
	
	@Belief(dynamic=true)
	protected boolean alarm = wordtable.containsKey("bugger");
	
	@Belief
	protected String eword;
	
	@AgentCreated
	public void init()
	{
		System.out.println("Created: "+this);
		//this.wordtable = ;
		wordtable.put("coffee", "Kaffee");
		wordtable.put("milk", "Milch");
		wordtable.put("cow", "Kuh");
		wordtable.put("cat", "Katze");
		wordtable.put("dog", "Hund");
		wordtable.put("bugger", "Flegel");
	}
	
	/*@Plan(trigger=@Trigger(factaddeds="wordtable"))
	public void checkWordPairPlan(Map.Entry<String, String> wordpair)
	{
	  if(wordpair.getKey().equals("bugger"))
	   System.out.println("Warning, a colloquial word pair has been added: "+wordpair.getKey()+" "+wordpair.getValue());
	}*/
	@Plan(trigger=@Trigger(factchangeds="alarm"))
	public void checkWordPairPlan(ChangeEvent event)
	{
		if(alarm == true)
	    System.out.println("Warning, a colloquial word pair has been added.");
	  
	}
	
	@Goal
	public class Translate
	{
	  //protected String eword;
	  
	  //protected String gword;

		  @GoalParameter
		  protected String eword;
				
		  @GoalResult
		  protected String gword;
			
		  @GoalCreationCondition(beliefs="eword")	  
	  public Translate(String eword)
	  {
	    this.eword = eword;
	    System.out.println(eword);
	  }

	  /*public String getEWord()
	  {
	    return eword;
	  }

	  public String getGWord()
	  {
	    return gword;
	  }

	  public void setGWord(String gword)
	  {
	    this.gword = gword;
	  }
	  */
	}
	
	/*@Plan(trigger=@Trigger(goals=Translate.class))
	protected void translate(Translate goal)
	{
	  String eword = goal.getEWord();
	  String gword = wordtable.get(eword);
	  goal.setGWord(gword);
	}*/
	@Plan(trigger=@Trigger(goals=Translate.class))
	protected String translate(String eword)
	{
	  return wordtable.get(eword);
	}
	
	@AgentBody
	public void body()
	{
	  eword = "cat";
	  eword = "milk";
	  //Translate goal = (Translate)agent.dispatchTopLevelGoal(new Translate(eword)).get();
	  //System.out.println("Translated: "+eword+" "+goal.getGWord());
	  //String gword = (String)agent.dispatchTopLevelGoal(new Translate(eword)).get();
	  //System.out.println("Translated: "+eword+" "+gword);
	} 
}
