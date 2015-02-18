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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

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

	public ArrayList computePreferredExtension(String conargExe, String inputfile) {
		Process process;
		String result = "";
		try {
			process = new ProcessBuilder(conargExe,"-e preferred",inputfile).start();
			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line;
			while ((line = br.readLine()) != null) {
				if (line.startsWith("\"")) {
					result = line.substring(1,line.length()-1);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Result: " + result);
		ArrayList<Argument> preferred = new ArrayList<Argument>();
		for (String argName: result.split(" ")) {
			preferred.add(arguments.get(argName));
		}
		return preferred;
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
	
