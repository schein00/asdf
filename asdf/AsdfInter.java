import java.util.*;

class AsdfInter {

	private int hmLines;
    private String lines[], line;
	private Vector< Var > vars;

	public AsdfInter( String l[], int hml ){

		hmLines = hml;
		this.lines = l;
		vars = new < Var >Vector();
	}	

	private String getCond( String s, int i ){
		String pair[] = s.split("\\(");
					
		if( pair.length != 2 ){
			System.out.println("ERRO: Comando nao reconhecido");
			System.out.println("Linha: " + ( i + 1 ) );
			return null;
		}
					
		String pair2[] = pair[1].split("\\)");
		return pair2[0].trim();
	}

    public boolean interpret( int i ) {

		String s;
		Stack< String > check = new < String >Stack();

        for( ; i < hmLines; i++ ) {
            
			line = lines[i].trim();

			boolean isIF = line.contains("se");
			boolean isWhile = line.contains("enquanto");
	
			if( isIF || isWhile ){
				if(( s = readStep(i,0,'{')) == null ) return false;

				String cond = getCond( s, i );
		
				if( cond == null ) return false;

				cond = cond.trim();
					
				check.push( line );
				
				i++;
				if( isIF && Exp.ineq( vars, cond ) ){
					if( !interpret( i ) ) return false;
				}
				else if( isWhile ) {
					while( Exp.ineq( vars, cond ) ){												
						if( !interpret( i ) ) return false;
					}
				}

				int last = i-1;
				while( !check.empty() && i < lines.length ){

					if( lines[i].contains("se") || lines[i].contains("enquanto") ){
						last = i;
						check.push( lines[i] );
					}

					else if( lines[i].contains("}")  ){
						check.pop();
					}

					i++;
				}
	
				if( !check.empty() || i >= hmLines ){
					System.out.println("ERRRO: Esta faltando um '}' para o '{' na linha " + ( last + 1 ) );
					System.exit(0);
 				}
			}

			else if( line.contains("entrada") ) {
				if( ( s = readStep( i, 0, ';' ) ) == null ) {
					System.out.println("	Linha: " + (i+1) );
					System.exit(0);
				}

				IO.input( line, vars );
			}

			else if( line.contains("saida") ) {
				if( ( s = readStep( i, 0, ';' ) ) == null ) {
					System.out.println("	Linha: " + (i+1) );
					System.exit(0);
				}

				IO.output( line, vars );
			}

			else if( line.contains("}") ) return true;
				
			else execute( i );
		}	

		if( !check.empty() ){
			System.out.println("ERRRO: Esta faltando um '}'");
			System.exit(0);
		}

		return true;
    }

	public boolean execute( int i ){
		char c;
		int subInit = 0;

		for( int j = 0 ; j < line.length(); j++ ){
			c = line.charAt(j);

			if( c == '{' ){
										
				String command = line.substring( subInit, j ).trim();						

				if( command.compareTo( "Principal" ) != 0 || c == '(' ){

						System.out.println("ERRO: Comando " + "'" + c + "'" + " não reconhecido na linha " + ( i + 1 ) + "." );
						
						System.exit(0);
				}

				subInit = j+1;
			}

			else if( j+1 == line.length() && c != ';' && c != '}' ) {
				System.out.println("ERRO: Comando não reconhecido na linha " + ( i + 1 ) + "." );
						
				System.exit(0);
			}
			else if( c == ' ' ){

				String vars;
				String command = line.substring( subInit, j ).trim();
				
				if( command.compareTo("inteiro") == 0 ){
							
					if( ( vars = readStep( i,j+1, ';' ) ) == null ) {
						System.out.println("	Linha: " + (i+1) );
						System.exit(0);
					}
			
					if( !multDeclaration( vars, 0 ) )  {
						System.out.println("	Linha: " + (i+1) );
						System.exit(0);
					}
				}

				else if( command.compareTo("real") == 0 ){
					if( ( vars = readStep( i, j+1, ';' ) ) == null )  {
						System.out.println("	Linha: " + (i+1) );
						System.exit(0);
					}
			
					if( !multDeclaration( vars, 1 ) )   {
						System.out.println("	Linha: " + (i+1) );
						System.exit(0);
					}
				}
				else if( command.compareTo("str") == 0 ){
					if( ( vars = readStep( i, j+1, ';' ) ) == null )   {
						System.out.println("	Linha: " + (i+1) );
						System.exit(0);
					}
		
					if( !multDeclaration( vars, 2 ) )   {
						System.out.println("	Linha: " + (i+1) );
						System.exit(0);
					}
				}

				else if( line.contains("=") ) {

					if( ( vars = readStep( i, 0, ';' ) ) == null ) {
						
						System.out.println("	Linha: " + (i+1) );
						System.exit(0);
					}

					if( !multAssig( vars ) ){
						System.out.println("	Linha: " + (i+1) );
						System.exit(0);
					}
				}
				else {
					System.out.println("ERRO: Comando não reconhecido na linha " + ( i + 1 ) );
					System.exit(0);
				}
				
				break;
			}
		}

		return true;
	}

	private boolean multAssig( String line ){

		String mult[] = line.split(",");
		String pair[];		
		for( int i = 0; i < mult.length; i++ ){
			
			pair = mult[i].split("=");

			if( pair.length < 2 ){
				System.out.println("ERRO: Comando não reconhecido.");
				System.exit(0);
			}		

			String k = pair[ pair.length - 1 ].trim(), n;
			for( int j = 0; j < pair.length-1; j++ ){

				n = pair[j].trim();
				if( VarManager.nameVerify( n ) ){
					System.out.println("ERRO: Variável inválida.");
					System.exit(0);
				}

				if( !VarManager.assig( vars, n, k ) ) System.exit(0);
			}
		}

		return true;
	}

	private boolean multDeclaration( String dec, int t ){
		
		String k;
		String pair[];
		String mult[] = dec.split(",");

		for( int i = 0; i < mult.length; i++ ){
			k = mult[i].trim();
			char p = k.charAt(0);

			if( k.contains("=") ){
				pair = k.split("=");

				if( pair.length != 2 ){
					System.out.println("ERRO: Comando não reconhecido.");
					System.exit(0);
				}

				pair[0] = pair[0].trim();
				pair[1] = pair[1].trim();

				if( t == 0 ) {
					if( !VarManager.newInt( vars, pair[0], pair[1] ) ) System.exit(0);;
				}

				else if( t == 1 ) {
					if( !VarManager.newReal( vars, pair[0], pair[1] ) ) System.exit(0);;
				}
				else{
					if( !VarManager.newStr( vars, pair[0], pair[1] ) ) System.exit(0);;
				}
			}
			else{
				if( t == 0 ) {	
					if( !VarManager.newInt( vars, k, "0" ) ) System.exit(0);;
				}

				else if( t == 1 ) {
					if( !VarManager.newReal( vars, k, "0" ) ) System.exit(0);;
				}
				else{
					if( !VarManager.newStr( vars, k, " " ) ) System.exit(0);;
				}
			}
		}

		return true;
	}

	private String readStep( int i, int j, char c ){
		int k = j;

		for( ; j < line.length() && line.charAt(j) != c; j++ );

		if( j == line.length() ){

			System.out.println("ERRO: Esta faltando '" + c + "' na linha " + ( i + 1 ) + ".");			

			return null;
		}

		return line.substring( k, j ).trim();
	}
}
