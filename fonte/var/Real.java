package var;

public class Real extends Var{

	private double value;
	
	public Real( String name, double v ){
		super( name );
		this.value = v;
	}

	public double getValue(){
		return this.value;
	}

	public void setValue( double v ){
		this.value = v;
	}
}
