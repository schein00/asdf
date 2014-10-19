import java.util.*;

class IO{

	//line = entrada( var_1, ... , var_n );
	private static String[] getParam( String str ){
		return str.split("\\(")[1].split("\\)")[0].split(",");
	}
	
	public static void input( String line, Vector< Var > vars ){

		Var v;
		String[] param = getParam(line);

		Scanner scan = new Scanner( System.in );

		for( int i = 0; i < param.length; i++ ){
			
			v = VarManager.exists( vars, param[i].trim() );

			if( v instanceof Str ){
				((Str)v).setValue( scan.next() );
			}

			else if( v instanceof Real ){
				double k = scan.nextDouble();

				((Real)v).setValue( k );
			}			

			else if( v instanceof Int ){
				((Int)v).setValue( scan.nextInt() );
			}

			else{
				System.out.println("ERRO: " + param[i] + " nao reconhecido " );
	
				System.exit(0);
			}
		}

		scan.close();
	}

	public static void output( String line, Vector< Var > vars ){
		
		Var v;
		String[] param = getParam(line.trim());

		for( int i = 0; i < param.length; i++ ){
			if( param[i] == null ) break;

			String p = param[i].trim();
			
			v = VarManager.exists( vars, p );

			if( v != null ){
				if( v instanceof Int ) System.out.print( ((Int)v).getValue() );
		
				else if( v instanceof Str ) System.out.print( ((Str)v).getValue() );

				else if( v instanceof Real ) System.out.print( ((Real)v).getValue() );

				else{
					System.out.println( "ERRO: " + param[i] + " nao reconhecido"  );

					System.out.println(0);
				}
			}
			else{
				System.out.print( param[i] );
			}
		}	

		System.out.println();
	}
}
