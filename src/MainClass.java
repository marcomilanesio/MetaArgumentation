import java.awt.Dimension;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
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
		conf.buildGraphicStrings(null);
		buildGraph(conf.buildGraphicStrings(null), resultDir+"/InitialFramework");
		String completeInputFile = conf.createCompleteInputFile();
		MetaArgumentationFramework maf = new MetaArgumentationFramework(conf);
		@SuppressWarnings("unchecked")
		ArrayList<Argument> preferred = maf.computePreferredExtension(conargExe, completeInputFile);
		buildGraph(conf.buildGraphicStrings(preferred), resultDir+"/EndingFramework");
	}		

	private static void buildGraph(ArrayList<String> graphicString, String outFileName) 
	{
		GraphViz gv = new GraphViz();
		gv.addln(gv.start_graph());
		for (String line: graphicString)
			gv.addln(line);
		gv.addln(gv.end_graph());
		String dotSource = gv.getDotSource();
		String outDotSource = outFileName + ".dot";
		writeDotToFile(dotSource, outDotSource);
        String type = "png";
        File out = new File(outFileName + "." + type);
        gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), type ), out );
	}

	private static void writeDotToFile(String dotSource, String outDotSource) {
		Writer writer = null;

		try {
		    writer = new BufferedWriter(new OutputStreamWriter(
		          new FileOutputStream(outDotSource), "utf-8"));
		    writer.write(dotSource);
		} catch (IOException ex) {
		  System.out.println("IOException caught: unable to write dot file.");
		} finally {
		   try {writer.close();} catch (Exception ex) {}
		}
		
	}
	

}
