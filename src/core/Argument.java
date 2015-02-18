package core;
import java.util.ArrayList;


public class Argument {

	private static final String DEFAULT_SHAPE = "record";
	private ArrayList<Argument> attacked = new ArrayList<Argument>();
	private double initialValue;
	private String name;
	private final String shape;
	private ArrayList<Argument> supported = new ArrayList<Argument>();
	private double value;
	private boolean accepted;

	public Argument(String name, double d)
	{
		this(DEFAULT_SHAPE,name, d);
	}
	
	protected Argument(String shape, String name, double d)
	{
		this.setName(name);
		this.setValue(d);
		this.initialValue = d;
		this.shape = shape;
		this.accepted = false;
	}

	public void addAttack(Argument a)
	{
		this.attacked.add(a);
	}
	
	public void addSupport(Argument a)
	{
		this.supported.add(a);
	}

	public ArrayList<Argument> getAttacked()
	{
		return this.attacked;
	}
	
	public double getInitialValue() {
		return initialValue;
	}
	
	public String getName() {
		return name;
	}
	
	public String getShape() {
		return shape;
	}

	public ArrayList<Argument> getSupported()
	{
		return this.supported;
	}

	public double getValue() {
		return value;
	}

	private void setName(String name) {
		this.name = name;
	}

	public void setValue(double d) {
		this.value = d;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.name + " (");
		sb.append(this.value + ")");
//		sb.append(" - {A}:");
//		for (Argument a: this.getAttacked())
//		{
//			sb.append(a.getName());
//		}
//		sb.append(" - {S}:");
//		for (Argument a: this.getSupported())
//		{
//			sb.append(a.getName());
//		}
		return sb.toString();
	}

	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}
}
