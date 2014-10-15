import java.util.*;

class AsdfInter {

    private String lines[];

	public AsdfInter(){}	

    public boolean interpret( String l[], int i, int j ) {

		char c;
		int subInit;
		Stack< Character > ifStack = new Stack();

        this.lines = l;

        for( ; i < this.lines.length; i++ ) {
            if( this.lines[i] != null ){

				subInit = 0;
				String line = lines[i].trim();

				System.out.println( line );
				for( ; j < line.length(); j++ ){
					c = line.charAt(j);

					if( c == '{' ){
										
						String command = line.substring( subInit, j ).trim();						

						if( command.compareTo( "Principal" ) != 0 ){

							System.out.println("Comando nao reconhecido na linha " + ( i + 1 ) + "." );
						
							return false;
						}

						subInit = j+1;
					}
					
					else if( c == '(' ){
						String command = line.substring( subInit, j ).trim();

						if( command.compareTo( "Se" ) == 0 ){
							System.out.println("Comando Se encontrado");
						}

						else if( command.compareTo( "Enquanto" ) == 0 ){
							System.out.println("Comando Enquanto encontrado");

						}

						for( ; j < line.length() && line.charAt(j) != ')'; j++ );

						if( j == line.length() ) {
							System.out.println("Esta faltando ')' na linha " + ( i + 1 ) + ".");

							return false;
						}

						subInit = j+1;
					}

					else{
					}
				}

				j = 0;
			}	
        }

		return true;
    }
}
