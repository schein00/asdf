class Int extends Var{

	private int value;	

	public Int( String name, int v ){
		super( name );
		this.value = v;
	}

	public void setValue( int v ){
		this.value = v;
	}

	public int getValue(){
		return this.value;
	}
}
