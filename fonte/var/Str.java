/*
 	Autor: Matheus DallRosa
	Email: matheusdallrosa94@gmail.com

	Está classe representará variáveis do tipo str.
 */

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
