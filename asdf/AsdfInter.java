import java.util.*;

class AsdfInter {

    private String lines[], line;
	private Vector< Var > vars;
	private Stack< String > step, loop;

	public AsdfInter(){
		this.vars = new < Var >Vector();
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

    public boolean interpret( String l[] ) {

		String s;
        this.lines = l;
		step = new < String >Stack();
		loop = new < String >Stack();

        for( int i = 0; i < this.lines.length; i++ ) {
            if( this.lines[i] != null ){
				line = lines[i].trim();

				if( line.contains("se") || line.contains("enquanto") ){
					if(( s = readStep(i,0,'{')) == null ) return false;

					String cond = getCond( s, i );
		
					if( cond == null ) return false;

					boolean flag = Exp.ineq( vars, cond.trim() );
		
					i++;	
					step.push( line );
					while( i < lines.length ){
						if( lines[i] == null ) {
							i++;
							continue;
						}

						line = lines[i].trim();

						step.push( line );					
		
						if( line.contains("se") || line.contains("enquanto") ){
							if( flag ) {
								if(( s = readStep(i,0,'{')) == null ) return false;

								cond = getCond( s, i );

								if( cond == null ) return false;

								flag = Exp.ineq( vars, cond.trim() );
							}
						}

						else if( line.contains("}") ){
							int key = 0;	

							while( !step.empty() ){
								loop.push( step.pop() );

								if( loop.peek().contains("}") ) key++;

								else if( loop.peek().contains("se") || loop.peek().contains("enquanto") || 
										 loop.peek().contains("Principal") ) {
									key--;
									if( key == 0 ) break;
								}
							}

							if( step.empty() && key > 0 ){
								System.out.println("ERRO: Comando nao reconhecido linha: " + ( i + 1 ));
								return false;
							}

							if( loop.peek().contains("enquanto") ){
								//simular loop.
								//faltando.
							}

							else if( loop.peek().contains("se") ){
								//apenas colocar devolta.
								while( !loop.empty() ) step.push( loop.pop());
							}

							else if( !loop.peek().contains("Principal") ){
								System.out.println("ERRO: Comando nao reconhecido linha: " + ( i + 1 ));
								return false;
							}
						}

						else if( flag ) execute( i );
			
						i++;
					}
				}

				else {
					execute( i );
				}
			}	
        }

		if( !step.empty() ){
			System.out.println("ERRO: Esta faltando algum '{'.");
			return false;
		}

		//debug.
		Int k1; Real k2; Str k3;
		for( int z = 0; z < vars.size(); z++ ){		

			if( vars.elementAt(z) instanceof Int ){
				k1 = ( Int ) vars.elementAt(z);
				System.out.println( vars.elementAt(z).getName() + " : " + k1.getValue() );
			}
				
			if( vars.elementAt(z) instanceof Real ){
				k2 = ( Real ) vars.elementAt(z);
				System.out.println( vars.elementAt(z).getName() + " : " + k2.getValue() );
			}	
		
			if( vars.elementAt(z) instanceof Str ){
				k3 = ( Str ) vars.elementAt(z);
				System.out.println( vars.elementAt(z).getName() + " : " + k3.getValue() );
			}	
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
						
						return false;
				}
				step.push( line );
				subInit = j+1;
			}

			else if( j+1 == line.length() && c != ';' && c != '}' ) {
				System.out.println("ERRO: Comando não reconhecido na linha " + ( i + 1 ) + "." );
						
				return false;
			}
			else if( c == ' ' ){

				String vars;
				String command = line.substring( subInit, j ).trim();
				
				if( command.compareTo("inteiro") == 0 ){
							
					if( ( vars = readStep( i,j+1, ';' ) ) == null ) {
						System.out.println("	Linha: " + (i+1) );
						return false;
					}
			
					if( !multDeclaration( vars, 0 ) )  {
						System.out.println("	Linha: " + (i+1) );
						return false;
					}
				}

				else if( command.compareTo("real") == 0 ){
					if( ( vars = readStep( i, j+1, ';' ) ) == null )  {
						System.out.println("	Linha: " + (i+1) );
						return false;
					}
			
					if( !multDeclaration( vars, 1 ) )   {
						System.out.println("	Linha: " + (i+1) );
						return false;
					}
				}
				else if( command.compareTo("str") == 0 ){
					if( ( vars = readStep( i, j+1, ';' ) ) == null )   {
						System.out.println("	Linha: " + (i+1) );
						return false;
					}
		
					if( !multDeclaration( vars, 2 ) )   {
						System.out.println("	Linha: " + (i+1) );
						return false;
					}
				}

				else if( line.contains("=") ){
					if( ( vars = readStep( i,j-1, ';' ) ) == null ) {
						System.out.println("	Linha: " + (i+1) );
						return false;
					}
		
					if( !multAssig( vars ) ){
						System.out.println("	Linha: " + (i+1) );
						return false;
					}
				}
				else {
					System.out.println("ERRO: Comando não reconhecido na linha " + ( i + 1 ) );
					return false;
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
				return false;
			}		

			String k = pair[ pair.length - 1 ].trim(), n;
			for( int j = 0; j < pair.length-1; j++ ){
				//System.out.println( pair[j].trim() + " = " + k );

				n = pair[j].trim();
				if( VarManager.nameVerify( n ) ){
					System.out.println("ERRO: Variável inválida.");
					return false;
				}

				if( !VarManager.assig( vars, n, k ) ) return false;
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
					return false;
				}

				pair[0] = pair[0].trim();
				pair[1] = pair[1].trim();

				if( t == 0 ) {
					if( !VarManager.newInt( vars, pair[0], pair[1] ) ) return false;
				}

				else if( t == 1 ) {
					if( !VarManager.newReal( vars, pair[0], pair[1] ) ) return false;
				}
				else{
					if( !VarManager.newStr( vars, pair[0], pair[1] ) ) return false;
				}
			}
			else{
				if( t == 0 ) {	
					if( !VarManager.newInt( vars, k, "0" ) ) return false;
				}

				else if( t == 1 ) {
					if( !VarManager.newReal( vars, k, "0" ) ) return false;
				}
				else{
					if( !VarManager.newStr( vars, k, " " ) ) return false;
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
