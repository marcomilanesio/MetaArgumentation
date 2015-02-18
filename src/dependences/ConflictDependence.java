package dependences;
import core.Argument;
import core.MetaArgument;


public class ConflictDependence implements Dependence {

	public final String labelStub1 = "X";
	public final String labelStub2 = "Y";
	private Argument firstArg;
	private Argument secondArg;
	private MetaArgument[] conflictMetaArguments = new MetaArgument[4];
	private int startNum;
	
	public ConflictDependence(Argument arg1, Argument arg2, int s)
	{
		firstArg = arg1;
		secondArg = arg2;
		startNum = s;
	}
	
	@Override
	public void buildDependence(int i) {
		MetaArgument x1 = new MetaArgument(labelStub1+startNum+"_"+i);
		MetaArgument y1 = new MetaArgument(labelStub2+startNum+"_"+i);
		int j = i+1;
		MetaArgument x2 = new MetaArgument(labelStub1+startNum+"_"+j);
		MetaArgument y2 = new MetaArgument(labelStub2+startNum+"_"+j);
		
		conflictMetaArguments[0] = x1;
		conflictMetaArguments[1] = y1;
		conflictMetaArguments[2] = x2;
		conflictMetaArguments[3] = y2;
		
		firstArg.addAttack(x1);
		x1.addAttack(y1);
		y1.addAttack(secondArg);
		secondArg.addAttack(x2);
		x2.addAttack(y2);
		y2.addAttack(firstArg);
		
		
	}

	@Override
	public Argument[] getInvolvedArguments() {
		Argument[] res = new Argument[2];
		res[0] = firstArg;
		res[1] = secondArg;
		return concat(res, conflictMetaArguments);
	}

	private Argument[] concat(Argument[] A, Argument[] B) {
		int aLen = A.length;
		int bLen = B.length;
		Argument[] C= new Argument[aLen+bLen];
		System.arraycopy(A, 0, C, 0, aLen);
		System.arraycopy(B, 0, C, aLen, bLen);
		return C;
	}
}
