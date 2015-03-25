package dependences;
import core.Argument;
import core.MetaArgument;


public class AndDependence implements Dependence {
	
	public final String labelStub = "T";
	private Argument firstArg;
	private Argument secondArg;
	private MetaArgument t;
	
	public AndDependence(Argument arg1, Argument arg2) {
		firstArg = arg1;
		secondArg = arg2;
	}
	
	public void buildDependence(int i)
	{
		t = new MetaArgument(labelStub+i);
		firstArg.addAttack(t);
		t.addAttack(secondArg);
	}

	public Argument[] getInvolvedArguments() {
		Argument[] res = new Argument[3];
		res[0] = firstArg;
		res[1] = secondArg;
		res[2] = t;
		return res;
	}

}
