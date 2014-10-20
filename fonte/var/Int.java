/*
 	Autor: Matheus DallRosa
	Email: matheusdallrosa94@gmail.com

	Está classe representará inteiros no programa, ela foi criada
	para mudanças futuras dos metodos da classe Exp para está classe.
 */
package var;

public class Int extends Var{

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
