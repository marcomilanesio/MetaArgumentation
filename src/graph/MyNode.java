package graph;

import core.Argument;
import core.MetaArgument;

public class MyNode {
	Argument arg;
	
	public MyNode(Argument a){
		this.arg = a;
	}
	
	public String toString(){
		return this.arg.getName();
		/*
		if (MetaArgument.class.isInstance(this.arg))
			return this.arg.getName();
		else
			return this.arg.getName()+" ["+this.arg.getValue()+"]";
		*/
	}
}
