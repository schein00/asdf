import java.util.*;

/*
 * 	Essa classe vai resolver igualdades, quando houver uma atribuição
 *
 *	a = c + b; por exemplo ela receberá a string c + b e resolverá a expressão.
 *
 *	O método ineq devolve o valor boleano de uma inequação
 *	i < j 			
 */
class Exp{

	private static char operand( String e ){
		if( e.contains("*") ) return '*';

		else if( e.contains("/") ) return '/';

		else if( e.contains("-") ) return '-';
	
		else if( e.contains("+") ) return '+';

		else if( e.contains("%") ) return '%';

		return ' ';
	}	

	public static boolean nonNumeric( String k ){
		for( int i = 0; i < k.length(); i++ ){
			if( ( k.charAt(i) > '9' || k.charAt(i) < '0' ) && k.charAt(i) != '.' && k.charAt(i) != '+'
				&& k.charAt(i) != '*' && k.charAt(i) != '/' && k.charAt(i) != '-' && k.charAt(i) != '%'){
				return true;
			}
		}
		
		return false;
	}

	private static Integer intFilter( Vector< Var > vars, String e ){
		if( nonNumeric( e ) ){
				
			Var k = VarManager.exists( vars, e );
				
			if( k == null || !( k instanceof Int ) ){
				System.out.println("ERRO: " + e + " nao e reconhecivel.");
				return null;
			}
				
			return new Integer( ((Int)k).getValue() );
		}
		else {
			if( e.contains(".") ){
				System.out.println("ERRO: Nao e permitido atribuir valor real a uma variavel inteira.");
				return null;
			}

			return new Integer( e );
		}
	}

	private static String[] oSplit( char o, String e ){
		
		if( o == '+' ) return e.split("\\+");
		
		if( o == '-' ) return e.split("-");

		if( o == '/' ) return e.split("/");

		if( o == '%' ) return e.split("%");
		
		return e.split("\\*");
	}

	public static Integer intArith( Vector< Var > vars, String e ){		
		char o = operand( e );
		
		if( o == ' ' ){

			return intFilter( vars, e );						
		} 
	
		String pair[] = oSplit( o, e );
		
		pair[0] = pair[0].trim();
		pair[1] = pair[1].trim();

		if( pair.length != 2 ) {
			System.out.println("ERRO: So e permitido dois operandos.");
			return null;
		}
			
		Integer a = intFilter( vars, pair[0] );
		Integer b = intFilter( vars, pair[1] );

		if( a == null || b == null )  return null;

		int k = 0;

		if( o == '*' ) k = a.intValue() * b.intValue();

		else if( o == '/' ) k = a.intValue() / b.intValue();
		
		else if( o == '+' ) k = a.intValue() + b.intValue();

		else if( o == '-' ) k = a.intValue() - b.intValue();
		
		else if( o == '%' ) k = a.intValue() % b.intValue();

		return new Integer( k );
	}

	private static Double realFilter( Vector< Var > vars, String e ){
		if( nonNumeric( e ) ){
				
			Var k = VarManager.exists( vars, e );
				
			if( k == null || !( k instanceof Int ) ){
				System.out.println("ERRO: " + e + " nao e reconhecivel.");
				return null;
			}
				
			return new Double( ((Real)k).getValue() );
		}
		else {

			return new Double( e );
		}
	}

	public static Double realArith( Vector< Var > vars, String e ){
		char o = operand( e );
		
		if( o == ' ' ){

			return realFilter( vars, e );	
		} 

		String pair[] = oSplit( o, e );

		pair[0] = pair[0].trim();
		pair[1] = pair[1].trim();

		if( pair.length != 2 ) {
			System.out.println("ERRO: So e permitido dois operandos.");
			return null;
		}		

		Double a = realFilter( vars, pair[0] );
		Double b = realFilter( vars, pair[1] );

		double k = 0;

		if( a == null || b == null ) return null;

		if( o == '*' ) k = a.doubleValue() * b.doubleValue();

		else if( o == '/' ) k = a.doubleValue() / b.doubleValue();
		
		else if( o == '+' ) k = a.doubleValue() + b.doubleValue();

		else if( o == '-' ) k = a.doubleValue() - b.doubleValue();

		return new Double( k );
	}

	public static boolean ineq( Vector< Var > vars, String e ){
		String pair[];
		Var a, b;

		if( e.contains(">") ){
			pair = e.split(">");
			pair[0] = pair[0].trim();
			pair[1] = pair[1].trim();
			
			a = VarManager.exists( vars, pair[0] );
			b = VarManager.exists( vars, pair[1] );

			if( a != null && b != null ) {
				if( a instanceof Int && b instanceof Int ){
					return ((Int)a).getValue() > ((Int)b).getValue();
				}

				if( a instanceof Real && b instanceof Real ){
					return ((Real)a).getValue() > ((Real)b).getValue();
				}

				if( a instanceof Str && b instanceof Str ){
					return ((Str)a).getValue().compareTo( ((Str)b).getValue() ) > 0;
				}
			}

			System.out.println("ERRO: Voce esta comparando variaveis de tipos diferentes!");
			System.exit(0);
		}

		else if( e.contains(">=") ){
			pair = e.split(">=");
			pair[0] = pair[0].trim();
			pair[1] = pair[1].trim();
			
			a = VarManager.exists( vars, pair[0] );
			b = VarManager.exists( vars, pair[1] );

			if( a != null && b != null ) {
				if( a instanceof Int && b instanceof Int ){
					return ((Int)a).getValue() >= ((Int)b).getValue();
				}

				if( a instanceof Real && b instanceof Real ){
					return ((Real)a).getValue() >= ((Real)b).getValue();
				}

				if( a instanceof Str && b instanceof Str ){
					return ((Str)a).getValue().compareTo( ((Str)b).getValue() ) >= 0;
				}
			}

			System.out.println("ERRO: Voce esta comparando variaveis de tipos diferentes!");
			System.exit(0);
		}

		else if( e.contains("<=") ){
			pair = e.split("<=");
			pair[0] = pair[0].trim();
			pair[1] = pair[1].trim();
			
			a = VarManager.exists( vars, pair[0] );
			b = VarManager.exists( vars, pair[1] );

			if( a != null && b != null ) {
				if( a instanceof Int && b instanceof Int ){
					return ((Int)a).getValue() <= ((Int)b).getValue();
				}

				if( a instanceof Real && b instanceof Real ){
					return ((Real)a).getValue() <= ((Real)b).getValue();
				}

				if( a instanceof Str && b instanceof Str ){
					return ((Str)a).getValue().compareTo( ((Str)b).getValue() ) <= 0;
				}
			}

			System.out.println("ERRO: Voce esta comparando variaveis de tipos diferentes!");
			System.exit(0);
		}

		else if( e.contains("<") ){
			pair = e.split("<");
			pair[0] = pair[0].trim();
			pair[1] = pair[1].trim();
			
			a = VarManager.exists( vars, pair[0] );
			b = VarManager.exists( vars, pair[1] );
			
			if( a != null && b != null ) {
				if( a instanceof Int && b instanceof Int ){
					return ((Int)a).getValue() < ((Int)b).getValue();
				}

				if( a instanceof Real && b instanceof Real ){
					return ((Real)a).getValue() < ((Real)b).getValue();
				}

				if( a instanceof Str && b instanceof Str ){
					return ((Str)a).getValue().compareTo( ((Str)b).getValue() ) < 0;
				}
			}

			System.out.println("ERRO: Voce esta comparando variaveis de tipos diferentes!");
			System.exit(0);
		}

		else if( e.contains("==") ){
			pair = e.split("==");
			pair[0] = pair[0].trim();
			pair[1] = pair[1].trim();
			
			a = VarManager.exists( vars, pair[0] );
			b = VarManager.exists( vars, pair[1] );

			if( a != null && b != null ) {
				if( a instanceof Int && b instanceof Int ){
					return ((Int)a).getValue() == ((Int)b).getValue();
				}

				if( a instanceof Real && b instanceof Real ){
					return ((Real)a).getValue() == ((Real)b).getValue();
				}

				if( a instanceof Str && b instanceof Str ){
					return ((Str)a).getValue().compareTo( ((Str)b).getValue() ) == 0;
				}
			}

			System.out.println("ERRO: Voce esta comparando variaveis de tipos diferentes!");
			System.exit(0);
		}

		else if( e.contains("!=") ){
			pair = e.split("!=");
			pair[0] = pair[0].trim();
			pair[1] = pair[1].trim();
			
			a = VarManager.exists( vars, pair[0] );
			b = VarManager.exists( vars, pair[1] );

			if( a != null && b != null ) {
				if( a instanceof Int && b instanceof Int ){
					return ((Int)a).getValue() != ((Int)b).getValue();
				}

				if( a instanceof Real && b instanceof Real ){
					return ((Real)a).getValue() != ((Real)b).getValue();
				}

				if( a instanceof Str && b instanceof Str ){
					return ((Str)a).getValue().compareTo( ((Str)b).getValue() ) != 0;
				}
			}

			System.out.println("ERRO: Voce esta comparando variaveis de tipos diferentes!");
			System.exit(0);
		}
		
		return true;
	}
}
