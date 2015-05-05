package lights;

import java.util.HashMap;
import java.util.Map;

import jadex.bdiv3.BDIAgent;
import jadex.bdiv3.annotation.Belief;
import jadex.bridge.service.annotation.Service;
import jadex.commons.future.Future;
import jadex.commons.future.IFuture;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentCreated;
import jadex.micro.annotation.ProvidedService;
import jadex.micro.annotation.ProvidedServices;

@Agent
@Service
@ProvidedServices(@ProvidedService(type=ITranslationService.class))
public class TranslationF1BDI implements ITranslationService {
	
	@Agent
	protected BDIAgent agent;

	@Belief
	protected Map<String, String> wordtable;
	

	@AgentCreated
	public void init()
	{
		System.out.println("Created: "+this);
		this.wordtable = new HashMap<String, String>(); ;
		wordtable.put("coffee", "Kaffee");
		wordtable.put("milk", "Milch");
		wordtable.put("cow", "Kuh");
		wordtable.put("cat", "Katze");
		wordtable.put("dog", "Hund");
		//wordtable.put("bugger", "Flegel");
	}

	public IFuture<String> translateEnglishGerman(String eword) {
		// TODO Auto-generated method stub
		  String gword = wordtable.get(eword);
		  return new Future<String>(gword);
	}

}
