package lights;

import jadex.bdiv3.BDIAgent;
import jadex.bdiv3.annotation.Belief;
import jadex.bdiv3.annotation.Body;
import jadex.bdiv3.annotation.Plan;
import jadex.bdiv3.annotation.PlanAPI;
import jadex.bdiv3.annotation.PlanBody;
import jadex.bdiv3.annotation.PlanContextCondition;
import jadex.bdiv3.annotation.Plans;
import jadex.bdiv3.runtime.IPlan;
import jadex.bridge.service.annotation.Service;
import jadex.commons.future.Future;
import jadex.commons.future.IFuture;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentCreated;
import jadex.micro.annotation.Implementation;
import jadex.micro.annotation.ProvidedService;
import jadex.micro.annotation.ProvidedServices;

import java.util.HashMap;
import java.util.Map;

/**
 *  The translation agent B1.
 *  
 *  Translation agent that implements itself the translation
 *  service. Just looks up translation word in hashtable and
 *  returns the corresponding entry.
 */
@Agent
@Service
@ProvidedServices(@ProvidedService(type=ITranslationService.class, 
	implementation=@Implementation(expression="$pojoagent")))

public class TranslationB1BDI implements ITranslationService
{
	//-------- attributes --------

	@Agent
	protected BDIAgent agent;
	
	/** The wordtable. */
	protected Map<String, String> wordtable;
	
	@Belief
	  protected boolean context = true;

	//-------- methods --------

	@AgentCreated
	public void init()
	{
		System.out.println("Created: "+this);
		/*this.wordtable = new HashMap<String, String>();
		this.wordtable.put("coffee", "Kaffee");
		this.wordtable.put("milk", "Milch");
		this.wordtable.put("cow", "Kuh");
		this.wordtable.put("cat", "Katze");
		this.wordtable.put("dog", "Hund");
		agent.adoptPlan("translateEnglishGerman");
		*/
		
		agent.adoptPlan(new TranslationPlan());
		  agent.waitForDelay(1000).get();
		  context = false;
		  System.out.println("context set to false");
	}
	
	/**
	 *  Translate an English word to German.
	 *  @param eword The english word.
	 *  @return The german translation.
	 */
	public IFuture<String> translateEnglishGerman(String eword)
	{
		String gword = wordtable.get(eword);
		return new Future<String>(gword);
	}
	
	@Plan
	public void translateEnglishGerman()
	{
		String eword = "dog";
		String gword = wordtable.get(eword);
		System.out.println("Translated: "+eword+" - "+gword);
	}
	
	@Plan
	public class TranslationPlan {
		
		@PlanAPI
		protected IPlan plan;
		

		@PlanContextCondition(beliefs="context")
		public boolean checkCondition()
		{
			return context;
		}

		protected Map<String, String> wordtable;
		
		public TranslationPlan(){
			this.wordtable = new HashMap<String, String>();
			this.wordtable.put("coffee", "Kaffee");
			this.wordtable.put("milk", "Milch");
			this.wordtable.put("cow", "Kuh");
			this.wordtable.put("cat", "Katze");
			this.wordtable.put("dog", "Hund");
		}
		
		/*@PlanBody
		  public void translateEnglishGerman()
		  {
		    // Fetch some word from the table and print the translation
			String eword = "dog";
			String gword = wordtable.get(eword);
			System.out.println("Translated: "+eword+" - "+gword);
		  }
		  */
		@PlanBody
		public String translateEnglishGerman()
		{
			String eword = "dog";
			String gword = wordtable.get(eword);
			System.out.println("Plan started.");
			plan.waitFor(10000).get();
			System.out.println("Plan resumed.");
			return wordtable.get(gword);
		}
		
	}
	
}


