package core;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import dependences.Dependence;

public class Configuration {
	
	private HashMap<String, Argument> arguments;
	private HashMap<String, ArrayList<Argument>> attacks;
	private HashMap<String, ArrayList<Dependence>> dependences;
	public static final int PRECISION = 1000;
	
	
	public Configuration() {}

	public String createCompleteInputFile() {
		String fname = "./results/complete.dl";
		File file = new File(fname);
        try {
			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			for (Argument a: arguments.values()){
				output.write("arg("+a.getName()+").\n");
				output.write("value("+a.getName()+","+a.getValue()+").\n");
			}
			for (String currentArg: attacks.keySet())
				for (Argument target: attacks.get(currentArg))
					output.write("att("+currentArg + "," + target.getName()+").\n");
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return fname;
	}
	
	public HashMap<String, Argument> getArguments() {
		return arguments;
	}

	public HashMap<String, ArrayList<Argument>> getAttacks() {
		return attacks;
	}

	public HashMap<String, ArrayList<Dependence>> getClauses() {
		return dependences;
	}

	public void setArguments(HashMap<String, Argument> arguments) {
		this.arguments = arguments;
	}

	public void setAttacks(HashMap<String, ArrayList<Argument>> attacks) {
		this.attacks = attacks;
	}

	public void setDependences(HashMap<String, ArrayList<Dependence>> clauses) {
		this.dependences = clauses;
	}

}
