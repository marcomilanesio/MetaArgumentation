package dependences;

import java.util.ArrayList;
import java.util.HashMap;

import core.Argument;
import core.MetaArgument;


public class DependenceBuilder {

	private HashMap<String, ArrayList<Dependence>> clauseMap = new HashMap<String, ArrayList<Dependence>>();
	private HashMap<Argument, MetaArgument> incidents; 
	private final String[] DEPENDENCES = {"and","or","require","conflict","equivalence"};
	
	public String[] getDEPENDENCES() {
		return DEPENDENCES;
	}

	public DependenceBuilder(){
		for (int i = 0; i < DEPENDENCES.length; i++)
			clauseMap.put(DEPENDENCES[i], new ArrayList<Dependence>());
		incidents = new HashMap<Argument, MetaArgument>();
	}
	
	public Dependence createDependence(Argument firstArg, Argument secondArg, String clauseName)
	{
		Dependence d = null;
		switch (clauseName)
		{
		case "or": d = createOrDependence(firstArg, secondArg);
					break;
		case "and": d = createAndDependence(firstArg, secondArg);
					break;
		case "require": d = createRequireDependence(firstArg, secondArg);
					break;
		case "conflict": d = createConflictDependence(firstArg, secondArg);
					break;
		case "equivalence": d = createEquivalenceDependence(firstArg, secondArg);
					break;
		}
		
		return d;
	}
	

	private Dependence createEquivalenceDependence(Argument firstArg, Argument secondArg) 
	{		
		Argument[] res = new Argument[2];
		//System.out.println("Equivalence clause: Equivalence to be set between " + firstArg + " and " + secondArg);
		//System.out.println("Found num conflicts = " + clauseMap.get("conflict").size());
		for (int i = 0; i < clauseMap.get("conflict").size(); i++)
		{
			ConflictDependence c = (ConflictDependence) clauseMap.get("conflict").get(i);
			Argument[] args = c.getInvolvedArguments();
			Argument c1 = args[0];
			Argument c2 = args[1];
			
			//System.out.println("Conflict found: " + c1 + " conflict " + c2);
			//System.out.println("Setting equivalence between: " + firstArg + " and " + secondArg);
			
			if (c1 != firstArg && c1 != secondArg && c2 != firstArg && c2 != secondArg)
			{
				//System.out.println("No conflict found on the involved arguments. Checking next...");
				continue;
			}
			
			if (c1 == firstArg)
			{
				//System.out.println("Case 1: " + c1 + " = " + firstArg);
				//System.out.println("Creating conflict between: " + secondArg + " and " + c2);
				res[0] = secondArg;
				res[1] = c2;
			}
			else if (c1 == secondArg)
			{
				//System.out.println("Case 2: " + c1 + " = " + secondArg);
				//System.out.println("Creating conflict between: " + firstArg + " and " + c2);
				res[0] = firstArg;
				res[1] = c2;
			}
			else if (c2 == firstArg)
			{
				//System.out.println("Case 3: " + c2 + " = " + firstArg);
				//System.out.println("Creating conflict between: " + c1 + " and " + secondArg);
				res[0] = c1;
				res[1] = secondArg;
			}
			else if (c2 == secondArg)
			{
				//System.out.println("Case 4: " + c2 + " = " + secondArg);
				//System.out.println("Creating conflict between: " + c1 + " and " + firstArg);
				res[0] = c1;
				res[1] = firstArg;
			}	
			else 
			{
				System.out.println("Error resolving the already built conflicts.");
				System.exit(0);
			}
		
			//System.out.println("New conflict to be added: " + res[0] + " - " + res[1]);
		}
		
		//System.out.println("All Passed... returning");
		//System.out.println("res = " + res[0] + " - " + res[1]);
		return createConflictForEquivalence(res);
	}

	private Dependence createConflictForEquivalence(Argument[] res) {
		return createConflictDependence(res[0], res[1]);
	}

	private Dependence createConflictDependence(Argument firstArg, Argument secondArg)
	{
		if ((firstArg == null) && (secondArg == null))
		{
			//System.out.println("found null clause arguments... skipping.");
			return null;
		}
		int start = clauseMap.get("conflict").size();
		ConflictDependence c = new ConflictDependence(firstArg, secondArg, start);
		c.buildDependence(start);
		clauseMap.get("conflict").add(c);
		return c;
	}

	private Dependence createRequireDependence(Argument firstArg, Argument secondArg) 
	{
		RequireDependence r = new RequireDependence(firstArg, secondArg);
		r.buildDependence(clauseMap.get("require").size());
		clauseMap.get("require").add(r);
		return r;
	}

	private Dependence createAndDependence(Argument firstArg, Argument secondArg)
	{
		AndDependence a = new AndDependence(firstArg, secondArg);
		a.buildDependence(clauseMap.get("and").size());
		clauseMap.get("and").add(a);
		return a;
	}

	public Dependence createOrDependence(Argument firstArg, Argument secondArg)
	{
		OrDependence c = null;
		if (incidents.containsKey(secondArg))
		{
			c = new OrDependence(firstArg, secondArg, incidents.get(secondArg));
			c.buildClause();
		}
		else 
		{
			c = new OrDependence(firstArg, secondArg);
			c.buildDependence(clauseMap.get("or").size());
			MetaArgument k = c.getMetaArgument();
			incidents.put(secondArg, k);
		}
		clauseMap.get("or").add(c);
		return c;
	}

}
