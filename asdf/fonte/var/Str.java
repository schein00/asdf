package var;

public class Str extends Var{

	private String value;
	
	public Str( String name, String v ){
		super( name );
		this.value = v;
	}

	public String getValue(){
		return this.value;
	}

	public void setValue( String v ){
		this.value = v;
	}
}