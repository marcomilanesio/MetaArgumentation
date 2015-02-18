package dependences;
import core.Argument;
import core.MetaArgument;

public class OrDependence implements Dependence {

	public final String labelStub = "K";
	private Argument firstArg;
	private Argument secondArg;
	private MetaArgument k;
	
	public OrDependence(Argument arg1, Argument arg2) {
		firstArg = arg1;
		secondArg = arg2;
	}

	public OrDependence(Argument arg1, Argument arg2, MetaArgument m) {
		firstArg = arg1;
		secondArg = arg2;
		k = m;
	}

	
	@Override
	public void buildDependence(int i)
	{
		if (k == null)
			k = new MetaArgument(labelStub+i);
		firstArg.addAttack(k);
		k.addAttack(secondArg);
	}

	public void buildClause() {
		firstArg.addAttack(k);
	}
	
	@Override
	public Argument[] getInvolvedArguments() {
		Argument[] res = new Argument[3];
		res[0] = firstArg;
		res[1] = secondArg;
		res[2] = k;
		return res;
	}

	public Argument getSecondArgument() {
		return secondArg;
	}

	public Argument getFirstArgument() {
		return firstArg;
	}
	
	public MetaArgument getMetaArgument()
	{
		return k;
	}
	
	@Override
	public String toString() {
		return firstArg.getName() + "," + k.getName() + "," + secondArg.getName();
	}

	
	
	
}
