package graph;

import core.Argument;

public class MyNode {
	Argument arg;
	
	public MyNode(Argument a){
		this.arg = a;
	}
	
	public String toString(){
		return this.arg.getName();
	}
}
