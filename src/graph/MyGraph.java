package graph;

import core.Argument;
import core.Configuration;
import core.MetaArgument;

import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Paint;

import javax.swing.JFrame;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

public class MyGraph {
	HashMap<String, MyNode> nodes = new HashMap<String, MyNode>();
	ArrayList<MyEdge> edges = new ArrayList<MyEdge>();
	DirectedSparseMultigraph<MyNode, MyEdge> g;
	
	public MyGraph(Configuration conf){
		g = new DirectedSparseMultigraph<MyNode, MyEdge>();
		for (Argument a: conf.getArguments().values()){
			nodes.put(a.getName(), new MyNode(a));
		}
		
		for (HashMap.Entry<String, ArrayList<Argument>> entry : conf.getAttacks().entrySet()) {
			String attName = entry.getKey();
			ArrayList<Argument> value = entry.getValue();
			MyNode att = nodes.get(attName);
			for (Argument d: value){
				MyNode def = nodes.get(d.getName());
				g.addEdge(new MyEdge(att,def), att, def);
			}
		}
	}
	
	public void addEdge(MyEdge e){
		g.addEdge(e, e.source, e.dest);
	}
	
	public void visualize(String label) {
		Layout<MyNode, MyEdge> layout = new CircleLayout<MyNode, MyEdge>(g);
		layout.setSize(new Dimension(300,300)); // sets the initial size of the space
		BasicVisualizationServer<MyNode,MyEdge> vv = new BasicVisualizationServer<MyNode,MyEdge>(layout);
		
		Transformer<MyNode,Paint> vertexPaint = new Transformer<MyNode,Paint>() {
            public Paint transform(MyNode n) {
            	if (MetaArgument.class.isInstance(n.arg))
            		return Color.GRAY;
            	return Color.RED;
            }
        };  
                
		vv.setPreferredSize(new Dimension(350,350)); //Sets the viewing area size
		
		vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.getRenderer().getVertexLabelRenderer().setPosition(Position.S);
		
		JFrame frame = new JFrame(label);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(vv);
		frame.pack();
		frame.setVisible(true); 
	}
	
}
