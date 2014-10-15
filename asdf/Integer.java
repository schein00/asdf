class Integer extends Var{

	private int value;	

	public Integer( String name, int v ){
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