import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import core.Argument;
import core.Configuration;

import dependences.Dependence;
import dependences.DependenceBuilder;

public class ConfigurationParser {

	private static final String ASSIGNMENT_PATTERN = "value\\(.*\\,[0-9]+(\\.[0-9])*\\)";
	private static final String ATTACK_PATTERN = "att\\(.*\\,.*\\)";
	private String[] dependence_patterns; 
	
	private Configuration configuration = new Configuration();
	private String dataRead;
	
	private HashMap<String, Argument> arguments = new HashMap<String, Argument>();
	private HashMap<String, ArrayList<Argument>> attacks = new HashMap<String, ArrayList<Argument>>(); 
	private HashMap<String, ArrayList<Dependence>> dependences;
	private DependenceBuilder cBuilder = new DependenceBuilder();
	
	public ConfigurationParser(String dataFile) 
	{
		Scanner s;
		try {
			s = new Scanner(new File(dataFile));
			dataRead = s.useDelimiter("\\Z").next();
			s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		dependence_patterns = cBuilder.getDEPENDENCES();
	}
	
	public Configuration buildConfiguration()
	{
		extractArguments();
		extractAttacks();
		extractDependences();
		updateRelations();

		configuration.setArguments(arguments);
		configuration.setAttacks(attacks);
		configuration.setDependences(dependences);
		
		//System.out.println("Configuration built.");
		return configuration;
	}



	private void updateRelations() {
		
		Set<String> clauseNames = dependences.keySet();
		for (String clauseName: clauseNames)
		{
			if (clauseName.equals("equivalence"))
				continue;
			else
			{
				for (Dependence c: dependences.get(clauseName))
				{
					Argument[] args = c.getInvolvedArguments();
					updateRelation(args);
				}	
			}
		}
	}

	private void updateRelation(Argument[] args) {
		for (int j = 0; j < args.length; j++)
			System.out.println((Argument)args[j]);
		for (int i = 0; i < args.length; i++)
		{
			Argument current = args[i];
			ArrayList<Argument> currentAttacks = current.getAttacked();
			if (! arguments.containsKey(current.getName()))
				arguments.put(current.getName(), args[i]);
			if (! attacks.containsKey(current.getName()))
				attacks.put(current.getName(), new ArrayList<Argument>());
			for (Argument target: currentAttacks)
			{
				if (! attacks.get(current.getName()).contains(target))
					attacks.get(current.getName()).add(target);
			}
		}
	}

	private void extractArguments() {	
		Pattern pattern = Pattern.compile(ASSIGNMENT_PATTERN);
		Matcher m = pattern.matcher(dataRead);
		while (m.find()) 
		{
			int startIndex = m.group(0).indexOf("(") + 1 ;
			int endIndex = m.group(0).indexOf(")");
			String assign = m.group(0).substring(startIndex,endIndex);
			String[] s = assign.split(",");
			String name = s[0];
			Double value = Double.parseDouble(s[1]);
			arguments.put(name, new Argument(name, value));
		}
	}

	private void extractAttacks()
	{
		Pattern pattern = Pattern.compile(ATTACK_PATTERN);
		Matcher m = pattern.matcher(dataRead);
		while (m.find()) 
		{
			int startIndex = m.group(0).indexOf("(") + 1 ;
			int endIndex = m.group(0).indexOf(")");
			String relation = m.group(0).substring(startIndex,endIndex);
			String[] s = relation.split(",");
			String attStr = s[0];
			String targetStr = s[1]; 
			addAttack(attStr,targetStr);
		}
	}
	

	private void extractDependences() 
	{ 
		dependences = new HashMap<String, ArrayList<Dependence>>();
		for (int i=0; i<dependence_patterns.length; i++)
		{
			String op = "\\s" + dependence_patterns[i] + "\\s";
			Pattern pattern = Pattern.compile("\\S*" + op + "\\S*");
			Matcher m = pattern.matcher(dataRead);
			while (m.find())
			{
				//remove last .
				String clauseString = m.group(0).substring(0,m.group(0).length()-1);
				String[] s = clauseString.split(op);
				Argument firstArg = arguments.get(s[0].trim());
				Argument secondArg = arguments.get(s[1].trim());
				Dependence d = cBuilder.createDependence(firstArg, secondArg, dependence_patterns[i]);
				if (d == null) 
				{
					continue;
				}
				if (dependence_patterns[i].equals("equivalence"))
					dependences.get("conflict").add(d);
				else 
				{
					if (! dependences.containsKey(dependence_patterns[i]))
						dependences.put(dependence_patterns[i], new ArrayList<Dependence>());
					dependences.get(dependence_patterns[i]).add(d);			
				}
			}
		}
	}

	private void addAttack(String arg1name, String arg2name) {
		Argument a = arguments.get(arg1name);
		Argument b = arguments.get(arg2name);
		a.addAttack(b);
		if (attacks.get(arg1name) == null)
		{
			ArrayList<Argument> tmp = new ArrayList<Argument>();
			tmp.add(b);
			attacks.put(arg1name, tmp);
		}
		else
			attacks.get(arg1name).add(b);
	}

}
