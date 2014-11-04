/*
 	Autor: Matheus DallRosa
	Email: matheusdallrosa94@gmail.com

	Está classe representará variáveis do tipo real no programa, 
	ela foi criada para mudanças futuras dos metodos da classe Exp
	para está classe.
 */
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
