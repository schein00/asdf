import java.util.*;
import java.lang.Integer;

class VarManager{

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

		if( exists( vars, name ) != null ) {
			System.out.println("ERRO: A variável " + name + " já existe.");
			return false;
		}

		Real r = new Real( name, 0 );

		if( assigReal( r, v ) ){
			vars.add(r);

			return true;
		}

		return false;
	}

	public static boolean newInt( Vector< Var > vars, String name, String v ){
		name = name.trim();
		v = v.trim();

		if( exists( vars, name ) != null ){
			System.out.println("ERRO: A variável " + name + " já existe.");
			return false;
		}

		if( v.contains(".") ) {
			System.out.println("ERRO: Não pode colocar valor real em um número inteiro.");
			return false;
		}

		Int i = new Int( name, 0 );

		if( assigInt( i, v ) ){
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
	
	public static boolean nonNumeric( String name, String k ){
		for( int i = 0; i < k.length(); i++ ){
			if( ( k.charAt(i) > '9' || k.charAt(i) < '0' ) && k.charAt(i) != '.' && k.charAt(i) != '+'
				&& k.charAt(i) != '*' && k.charAt(i) != '/' && k.charAt(i) != '-' ){			
				System.out.println("ERRO: Existem caracteres inválidos apontados como valor da variável " + name + ".");
				return true;
			}
		}
		
		return false;
	}

	public static boolean nameVerify( String v ){

		if( v.compareTo("Principal") == 0 || v.compareTo("real") == 0 ||
			v.compareTo("inteiro") == 0   || v.compareTo("str") == 0 ||
			v.compareTo("enquanto") == 0  || v.compareTo("se") == 0 ){

			System.out.println("ERRO: " + v + " é uma palavra reservada.");

			return true;	
		}

		//restrições ao nome das variáveis devem ser adcionados aqui.
		if( v.contains(" ") || ( v.charAt(0) >= '0' && v.charAt(0) <= '9' ) || v.charAt(0) == '!' ){
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

		else if( k instanceof Int ) return assigInt( (Int)k, v );

		else if( k instanceof Real ) return assigReal( (Real)k, v );
	
		else return assigStr( (Str)k, v );
	}

	private static boolean assigInt( Int var, String v ){
		if( v.contains(".") ) {
			System.out.println("ERRO: Não pode colocar valor real em um número inteiro.");
			return false;
		}
		//fazer validações para número inteiro, e chamar método para resolver a expressão na string v		

		var.setValue(10);
		
		return true;
	}

	private static boolean assigReal( Real var, String v ){
		//fazer validações para número inteiro, e chamar método para resolver a expressão na string v

		var.setValue(12);

		return true;
	}

	private static boolean assigStr( Str var, String v ){
		
		if( v.charAt(0) != '"' || v.charAt(v.length()-1) != '"' ){
			System.out.println("ERRO: Uma String deve estar dentro de aspas duplas " + '"' + " ");
			return false;
		}

		var.setValue( v.substring( 1, v.length()-1 ) );

		return true;
	}
}
