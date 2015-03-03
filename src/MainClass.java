import graph.MyGraph;

import java.io.File;
import java.util.ArrayList;

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
		ArrayList<Argument> preferred = maf.computePreferredExtension(conargExe, completeInputFile);
		g.addResult(preferred);
		g.visualize("final");
	}		

}
