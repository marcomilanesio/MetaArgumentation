package dependences;
import core.Argument;
import core.MetaArgument;


public class EquivalenceDependence implements Dependence {

	private Argument firstArg;
	private Argument secondArg;
	private MetaArgument mz;
	
	public EquivalenceDependence(Argument arg1, Argument arg2)
	{
		firstArg = arg1;
		secondArg = arg2;
	}
	
	@Override
	public void buildDependence(int i) { 
		mz = new MetaArgument("z");
		firstArg.addAttack(mz);
		mz.addAttack(secondArg);
	}

	@Override
	public Argument[] getInvolvedArguments() {
		Argument[] res = new Argument[3];
		res[0] = firstArg;
		res[1] = secondArg;
		res[2] = mz;
		return res;
	}

}
