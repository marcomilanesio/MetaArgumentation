package dependences;
import core.Argument;


public class EquivalenceDependence implements Dependence {

	private Argument firstArg;
	private Argument secondArg;
	private ConflictDependence conflict;
	
	public EquivalenceDependence(Argument arg1, Argument arg2, ConflictDependence c)
	{
		firstArg = arg1;
		secondArg = arg2;
		conflict = c;
	}
	
	@Override
	public void buildDependence(int i) { }

	@Override
	public Argument[] getInvolvedArguments() {
		Argument[] res = new Argument[2];
		res[0] = firstArg;
		res[1] = secondArg;
		return res;
	}
	
	public ConflictDependence getConflict()
	{
		return conflict;
	}

}
