/*
 	Autor: Matheus DallRosa
	Email: matheusdallrosa94@gmail.com

	Está classe possui todos os metodos necessários para manipular
	variáveis( não seu conteúdo ).

	Um fato importante para entender o funcionamento da classe
	é que todos os métodos com new no inicio são responsaveis por verificar
	a existencia de uma variável de um certo tipo e verificar a validade do seu
	nome, esses métodos se utilizam dos métodos que possuem assig no inicio de seu nome,
	os quais atribuem valores as variáveis.

	Eu fiz essa separação pois quando for necessário fazer validações nos conteúdos a serem
	inseridos eles seram feitos diretamente nos metódos que possuem assig no início.
 */

package var;

import exp.Exp;
import java.util.*;
import java.lang.Integer;

public class VarManager{

	public static boolean newStr( Vector< Var > vars, String name, String v ){
		name = name.trim();
		v = v.trim();	

		if( nameVerify( name ) ) return false;

		if( exists( vars, name ) != null ){
			System.out.println("ERRO: A variável " + name + " já existe.");
			return false;	
		}

		Str s = new Str( name, "" );
		if( assigStr( s, v ) ){
			vars.add( s );

			return true;
		}

		return false;
	}

	public static boolean newReal( Vector< Var > vars, String name, String v ){
		name = name.trim();
		v = v.trim();

		if( nameVerify( name ) ) return false;

		if( exists( vars, name ) != null ) {
			System.out.println("ERRO: A variável " + name + " já existe.");
			return false;
		}

		Real r = new Real( name, 0 );

		if( assigReal( vars, r, v ) ){
			vars.add(r);

			return true;
		}

		return false;
	}

	public static boolean newInt( Vector< Var > vars, String name, String v ){
		name = name.trim();
		v = v.trim();

		if( nameVerify( name ) ) return false;

		if( exists( vars, name ) != null ){
			System.out.println("ERRO: A variável " + name + " já existe.");
			return false;
		}

		Int i = new Int( name, 0 );

		if( assigInt( vars, i, v ) ){
			vars.add(i);

			return true;
		}

		return false;
	}
	
	public static Var exists( Vector< Var > vars, String name ){

		for( int i = 0; i < vars.size(); i++ ){
			if( vars.elementAt(i).getName().compareTo( name ) == 0 ){
				return vars.elementAt(i);
			}
		}

		return null;
	}

	//restrições ao nome das variáveis devem ser adcionados aqui.
	public static boolean nameVerify( String v ){

		if( v.compareTo("Principal") == 0 || v.compareTo("real") == 0 ||
			v.compareTo("inteiro") == 0   || v.compareTo("str") == 0 ||
			v.compareTo("enquanto") == 0  || v.compareTo("se") == 0 ){

			System.out.println("ERRO: " + v + " é uma palavra reservada.");

			return true;	
		}

		if( v.contains(" ") || ( v.charAt(0) >= '0' && v.charAt(0) <= '9' ) || v.charAt(0) == '!' ||
		 	v.contains("+") || v.contains("-") || v.contains("/") || v.contains("*") ){
			System.out.println("ERRO: Existem caracteres inválidos no nome da variável " + v + ".");
			return true;
		}

		return false;
	}

	public static boolean assig( Vector< Var > var, String name, String v ){
		
		Var k = exists( var, name );

		if( k == null ){
			System.out.println("ERRO: A variável: " + name + " não existe.");

			return false;
		}

		else if( k instanceof Int ) return assigInt( var, (Int)k, v );

		else if( k instanceof Real ) return assigReal( var, (Real)k, v );
	
		else return assigStr( (Str)k, v );
	}

	private static boolean assigInt( Vector< Var > vars, Int var, String v ){

		Integer k = Exp.intArith( vars, v );		

		if( k != null ){
			var.setValue( k.intValue() );
		
			return true;
		}

		System.out.println("	Variavel: " + var.getName());

		return false;
	}

	private static boolean assigReal( Vector< Var > vars, Real var, String v ){

		Double k = Exp.realArith( vars, v );		

		if( k != null ){
			var.setValue( k.doubleValue() );
		
			return true;
		}

		System.out.println("	Variavel: " + var.getName());

		return false;
	}

	private static boolean assigStr( Str var, String v ){
		if( v.length() == 0 ) return true;
		
		if( v.charAt(0) != '"' || v.charAt(v.length()-1) != '"' ){
			System.out.println("ERRO: Uma String deve estar dentro de aspas duplas " + '"' + " ");
			return false;
		}

		var.setValue( v.substring( 1, v.length()-1 ) );

		return true;
	}
}
