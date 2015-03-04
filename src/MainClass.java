import graph.MyGraph;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import core.Configuration;
import core.MetaArgumentationFramework;
import core.Argument;


public class MainClass {

	public static void main(String[] args) {
		if (args.length != 1)
		{
			System.out.println("Specify an inputfile");
			System.exit(0);
		}
		File resultDir = new File("./results");
		if (! resultDir.exists())
		{
			if (! resultDir.mkdir())
			{
				System.out.println("Unable to create result directory.");
				System.exit(0);
			}
		}
		String inputfileName = args[0];
		String conargExe = "./conarg/conarg_gecode64";
		ConfigurationParser c = new ConfigurationParser(inputfileName);
		Configuration conf = c.buildConfiguration();
		MyGraph g = new MyGraph(conf);
		g.visualize("init");
		String completeInputFile = conf.createCompleteInputFile();
		MetaArgumentationFramework maf = new MetaArgumentationFramework(conf);
		HashMap<String, ArrayList<Argument>> preferred = maf.computeExtension(conargExe, completeInputFile, "admissible");
		Iterator<HashMap.Entry<String,ArrayList<Argument>>> iter = preferred.entrySet().iterator();
		int i = 0;
		while (iter.hasNext()) {
			HashMap.Entry<String,ArrayList<Argument>> entry = iter.next();
			g.addResult(entry.getValue(), conf);
		    g.visualize("Solution "+i);
		    i++;
		    g.resetView(conf);
		}
	}		

}
