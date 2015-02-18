package core;

public class MetaArgument extends Argument {

	private static final String META_ARGUMENT_SHAPE = "ellipse";

	public MetaArgument(String name) {
		super(META_ARGUMENT_SHAPE, name, (double)1);
	}
	
}
