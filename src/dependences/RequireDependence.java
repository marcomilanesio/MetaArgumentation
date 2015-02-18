package dependences;
import core.Argument;
import core.MetaArgument;


public class RequireDependence implements Dependence {
	
	public final String labelStub = "Z";
	private Argument firstArg;
	private Argument secondArg;
	private MetaArgument z;
	
	public RequireDependence(Argument arg1, Argument arg2)
	{
		firstArg = arg1;
		secondArg = arg2;
	}
	
	@Override
	public void buildDependence(int i) {
		z = new MetaArgument(labelStub+i);
		secondArg.addAttack(z);
		z.addAttack(firstArg);

	}

	@Override
	public Argument[] getInvolvedArguments() {
		Argument[] res = new Argument[3];
		res[0] = firstArg;
		res[1] = secondArg;
		res[2] = z;
		return res;
	}

}
