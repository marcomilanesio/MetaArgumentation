package graph;

public class MyEdge {
	MyNode source;
	MyNode dest;
	String dependence = "";
	
	public MyEdge(MyNode a, MyNode b, String depName){
		this.source = a;
		this.dest = b;
		this.dependence = depName;
	}
	
	public String toString(){
		return dependence;
	}
}
