package core;
import java.util.HashMap;
import java.util.Set;


public class Labelling {
	
	private HashMap<String, Double> labelling;
	private HashMap<String, Argument> arguments;
	
	public Labelling (HashMap<String, Argument> a) 
	{
		arguments = a;
		labelling = saveLabels();
	}
	
	private HashMap<String, Double> saveLabels() {
		HashMap<String, Double> res = new HashMap<String, Double>();
		Set<String> names = arguments.keySet();
		for (String argName: names)
		{
			res.put(argName, arguments.get(argName).getValue());
		}
		return res;
	}
	
	public HashMap<String, Double> getLabelling()
	{
		return labelling;
	}
	
	
}
