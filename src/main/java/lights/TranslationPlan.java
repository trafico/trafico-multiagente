package lights;

import java.util.HashMap;
import java.util.Map;

import jadex.bdiv3.annotation.Plan;
import jadex.bdiv3.annotation.PlanBody;

@Plan
public class TranslationPlan {

	protected Map<String, String> wordtable;
	
	public TranslationPlan(){
		this.wordtable = new HashMap<String, String>();
		this.wordtable.put("coffee", "Kaffee");
		this.wordtable.put("milk", "Milch");
		this.wordtable.put("cow", "Kuh");
		this.wordtable.put("cat", "Katze");
		this.wordtable.put("dog", "Hund");
	}
	
	@PlanBody
	  public void translateEnglishGerman()
	  {
	    // Fetch some word from the table and print the translation
		String eword = "dog";
		String gword = wordtable.get(eword);
		System.out.println("Translated: "+eword+" - "+gword);
	  }
	
}
