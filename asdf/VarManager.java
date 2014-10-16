import java.util.*;
import java.lang.Integer;

class VarManager{

	public static boolean newStr( Vector< Var > vars, String name, String v ){
		name = name.trim();
		v = v.trim();	

		if( v.charAt(0) != '"' || v.charAt(v.length()-1) != '"' ){
			System.out.println("ERRO: Uma String deve estar dentro de aspas duplas " + '"' + " ");
			return false;
		}

		if( nameVerify( name ) ) return false;

		if( exists( vars, name ) ) return false;	

		vars.add( new Str( name, v.substring( 1, v.length()-1) ) );

		return true;
	}

	public static boolean newReal( Vector< Var > vars, String name, String v ){
		name = name.trim();
		v = v.trim();

		if( nameVerify( name ) ) return false;

		if( nonNumeric( name, v ) ) return false;

		if( exists( vars, name ) ) return false;
	
		if( v.contains("+") || v.contains("*") || v.contains("-") || v.contains("/") ){
			//implementar quando o valor de atribuição é uma operação aritmética.
		}
		else vars.add( new Real( name, Double.parseDouble( v ) ) );

		return true;
	}

	public static boolean newInt( Vector< Var > vars, String name, String v ){
		name = name.trim();
		v = v.trim();

		if( nameVerify( name ) ) return false;

		if( nonNumeric( name, v ) ) return false;

		if( exists( vars, name ) ) return false;

		if( v.contains(".") ) {
			System.out.println("ERRO: Não pode colocar valor real em um número inteiro.");
			return false;
		}

		if( v.contains("+") || v.contains("*") || v.contains("-") || v.contains("/") ){
			//implementar quando o valor de atribuição é uma operação aritmética.
		}
	
		else vars.add( new Int( name, Integer.parseInt( v ) ) );

		return true;
	}
	
	public static boolean exists( Vector< Var > vars, String name ){

		for( int i = 0; i < vars.size(); i++ ){
			if( vars.elementAt(i).getName().compareTo( name ) == 0 ){
				System.out.println("ERRO: A variável " + name + " já existe.");
				return true;
			}
		}

		return false;
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
			v.compareTo("inteiro") == 0 || v.compareTo("str") == 0 ||
			v.compareTo("enquanto") == 0 || v.compareTo("se") == 0 ){
			System.out.println("ERRO: " + v + " é uma palavra reservada.");
			return true;	
		}

		//restrições ao nome das variáveis devem ser adcionados aqui.
		if( v.contains(" ") || ( v.charAt(0) >= '0' && v.charAt(0) <= '9' ) || v.charAt(0) == '1' ){
			System.out.println("ERRO: Existem caracteres inválidos no nome da variável " + v + ".");
			return true;
		}

		return false;
	}
}