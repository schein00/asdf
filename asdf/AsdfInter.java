import java.util.*;

class AsdfInter {

	private int a;
    private String lines[], line;
	private Vector< Var > vars;
	private Stack< Character > ifStack;

	public AsdfInter(){
		this.vars = new Vector();
		this.ifStack = new Stack();
	}	

    public boolean interpret( String l[], int i, int j ) {

		char c;
		int subInit;

        this.lines = l;

        for( ; i < this.lines.length; i++ ) {
            if( this.lines[i] != null ){

				subInit = 0;
				line = lines[i].trim();

				for( ; j < line.length(); j++ ){
					c = line.charAt(j);

					if( c == '{' || c == '(' || ( j+1 == line.length() && c != ';' && c != '}' )  ){
										
						String command = line.substring( subInit, j ).trim();						

						if( command.compareTo( "Principal" ) != 0 ){

							System.out.println("ERRO: Comando não reconhecido na linha " + ( i + 1 ) + "." );
						
							return false;
						}

						subInit = j+1;
					}
					
					else if( c == '(' ){
						String command = line.substring( subInit, j ).trim();

						if( command.compareTo( "Se" ) == 0 ){
							System.out.println("ERRO: Comando Se encontrado");
						}

						else if( command.compareTo( "Enquanto" ) == 0 ){
							System.out.println("ERRO: Comando Enquanto encontrado");

						}

						for( ; j < line.length() && line.charAt(j) != ')'; j++ );

						if( j == line.length() ) {
							System.out.println("ERRO: Esta faltando ')' na linha " + ( i + 1 ) + ".");

							return false;
						}

						subInit = j+1;
					}

					else if( c == ' ' ){

						String vars;
						String command = line.substring( subInit, j ).trim();
				
						if( command.compareTo("inteiro") == 0 ){
							
							if( ( vars = readVariables( i,j ) ) == null ) {
								System.out.println("	Linha: " + (i+1) );
								return false;
							}
			
							if( !multDeclaration( vars, 0 ) )  {
								System.out.println("	Linha: " + (i+1) );
								return false;
							}
						}

						else if( command.compareTo("real") == 0 ){
							if( ( vars = readVariables( i,j ) ) == null )  {
								System.out.println("	Linha: " + (i+1) );
								return false;
							}
			
							if( !multDeclaration( vars, 1 ) )   {
								System.out.println("	Linha: " + (i+1) );
								return false;
							}
						}

						else if( command.compareTo("str") == 0 ){
							if( ( vars = readVariables( i,j ) ) == null )   {
								System.out.println("	Linha: " + (i+1) );
								return false;
							}
			
							if( !multDeclaration( vars, 2 ) )   {
								System.out.println("	Linha: " + (i+1) );
								return false;
							}
						}

						else{
							/*
								procurar sinal de igual pois o unico comando possivel
								apartir daqui é uma atribuição.			
							*/
						}
					}
				}

				j = 0;
			}	
        }

	
		for( int z = 0; z < vars.size(); z++ ){
			Int k1;
			Real k2;	
			Str k3;		

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

	private boolean multDeclaration( String dec, int t ){
		
		String k;
		String pair[];
		String mult[] = dec.split(",");

		for( int i = 0; i < mult.length; i++ ){
			k = mult[i].trim();
			char p = k.charAt(0);

			if( k.contains("=") ){
				pair = k.split("=");

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

	private String readVariables( int i, int j ){
		int k = j+1;

		for( ; j < line.length() && line.charAt(j) != ';'; j++ );


		if( j == line.length() ){
			System.out.println("ERRO: Esta faltando ';' na linha " + ( i + 1 ) + ".");			

			return null;
		}

		this.a = j;

		return line.substring( k, j ).trim();
	}
}