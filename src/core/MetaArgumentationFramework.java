package core;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;


public class MetaArgumentationFramework {
	private HashMap<String, Argument> arguments = new HashMap<String, Argument>();
	private HashMap<String, ArrayList<Argument>> attacks = new HashMap<String,ArrayList<Argument>>();
	private ArrayList<Labelling> history = new ArrayList<Labelling>();
	protected Configuration status;

	public MetaArgumentationFramework(Configuration conf)
	{
		status = conf;
		arguments = status.getArguments();
		attacks = status.getAttacks();
		//supports = status.getSupports();
		history.add(new Labelling(arguments));
	}
	
	public HashMap<String, Argument> getArguments()
	{
		return arguments;
	}

	public void saveHistoryToFile(String fileName)
	{
		Writer writer = null;
		String historyStr = "";

		for (int i = 0; i < history.size(); i++)
		{
			//			historyStr += i;
			ArrayList<String> keyset = new ArrayList<String> (history.get(i).getLabelling().keySet());
			Collections.sort(keyset);

			for (String key: keyset)
				historyStr += " "+ String.format("%.6f", history.get(i).getLabelling().get(key));
			//				historyStr += " " + key + ": " + String.format("%.3f", history.get(i).getLabelling().get(key));
			historyStr += "\n";
		}

		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "utf-8"));
			writer.write(historyStr);
		} catch (IOException ex) {
			System.out.println("IOException caught: unable to write history file.");
		} finally {
			try {writer.close();} catch (Exception ex) {}
		}

	}

	public ArrayList<Argument> computePreferredExtension(String conargExe, String inputfile) {
		Process process;
		ArrayList<String> results = new ArrayList<String>();
		String result = "";
		try {
			process = new ProcessBuilder(conargExe,"-e admissible",inputfile).start();
			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line;
			while ((line = br.readLine()) != null) {
				if (line.startsWith("\"")) {
					result = line.substring(1,line.length()-1);
					results.add(result);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (String s: results)
			System.out.println("Result: " + s);
		//TODO return more than one result, if any
		ArrayList<Argument> preferred = new ArrayList<Argument>();
		for (String argName: result.split(" ")) {
			preferred.add(arguments.get(argName));
		}
		return preferred;
	} 
	
	
	public ArrayList<Argument> findAttacksFromArgument(Argument a){
		return attacks.get(a.getName());
	}
	
	public ArrayList<Argument> findAttacksToArgument(Argument a){
		ArrayList<Argument> res = new ArrayList<Argument>();
		Iterator<HashMap.Entry<String,ArrayList<Argument>>> iter = attacks.entrySet().iterator();
		while (iter.hasNext()) {
			HashMap.Entry<String,ArrayList<Argument>> entry = iter.next();
		    if(entry.getKey() == a.getName()){
		    	continue;
		    }
		    if (entry.getValue().contains(a)){
		    	res.add(arguments.get(entry.getKey()));
		    }
		}
		return res;		
	}
	
	/*
	@Override
	public void saveHistoryToFile(String fileName) {
		Writer writer = null;
		String historyStr = "";
		ArrayList<String> keyset = new ArrayList<String> (this.getHistory().get(0).getLabelling().keySet());
		Collections.sort(keyset);
		
		for (int i = 0; i < this.getHistory().size(); i++)
		{
			historyStr += i;
			for (String key: keyset)
			{
				int val = (int) Math.round(this.getHistory().get(i).getLabelling().get(key));
				historyStr += " " + key + ": " + val;
			}
			historyStr += "\n";
		}
			
		try {
		    writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "utf-8"));
			writer.write(historyStr);
		} catch (IOException ex) {
			System.out.println("IOException caught: unable to write history file.");
		} finally {
		   try {writer.close();} catch (Exception ex) {}
			}
			
		}
	*/
}
	
