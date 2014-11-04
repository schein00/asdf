/*
 	Autor: Matheus DallRosa
	Email: matheusdallrosa94@gmail.com

	Está classe será a base para todas as outras classes
	que representam um tipo de variável, metodos pertencentes
	a todos os tipos devem ser adcionados aqui.
 */
package var;

public abstract class Var{

	private String name;

	public Var( String name ){
		this.name = name;
	}	

	public String getName(){
		return this.name;
	}
}
