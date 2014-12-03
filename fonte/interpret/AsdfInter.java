/*
 	Autor: Matheus DallRosa
	Email: matheusdallrosa94@gmail.com

	Está classe é responsável por checar a sintaxe das linhas de código
	uma vez que a sintaxe está correta ela passa a responsabilidade de executar
	as ações para as outras classes, nenhuma outra classe no projeto se utiliza desta
	classe além da classe AsdfReader.
 */
package interpret;

import java.util.*;
import var.*;
import exp.Exp;
import io.IO;

public class AsdfInter {

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
				
		if( pair.length != 2 ){ 		//testa se so existe um ( para fazer testes logicos
			System.out.println("ERRO: Comando nao reconhecido");
			System.out.println("Linha: " + ( i + 1 ) );
			return null;
		}
				
		String pair2[] = pair[1].split("\\)");
		return pair2[0].trim();		//retorna apenas o teste logico dentro dos ()
	}

	public boolean interpret( int i ) {

		String s;
		Stack< String >	check = new < String >Stack();  //cria uma pilha de String
		for( ; i < hmLines; i++ ) {
		    
			line = lines[i].trim();

			boolean isIF = line.contains("se");
			boolean isWhile = line.contains("enquanto");

			if( isIF || isWhile ){
				if(( s = readStep(i,0,'{')) == null ) return false; // testa se abre o bloco de comandos do if/else

				String cond = getCond( s, i );
	
				if( cond == null ) return false;		//testa se existe algo para fazer testes

				cond = cond.trim();
				
				check.push( line );  	//empilha a linha que esta sendo interpretada
			
				i++;
				//Exp.ineq � o metodo usado para fazer os testes logicos
				if( isIF && Exp.ineq( vars, cond ) ){
					if( !interpret( i ) ) return false; //usando recurcao interpreta os comandos dentro do if
				}
				else if( isWhile ) {
					while( Exp.ineq( vars, cond ) ){		//faz teste logico do while, da mesma forma que no if										
						if( !interpret( i ) ) return false; 	//por recurcao faz a interpretacao dos comandos dentro do while
					}
				}

				int last = i-1;
				while( !check.empty() && i < hmLines ){	 	//testa se a pilha esta vazia, e se i � menor que numero de linhas passadas para o interoretador

					if( lines[i].contains("se") || lines[i].contains("enquanto") ){		//se encontrar if/while, como ja foi interpretado,
						last = i;																//os comandos sao empilhados ate o fim do bloco
						check.push( lines[i] );
					}

					else if( lines[i].contains("}")  ){
						check.pop();
					}

					i++;
				}

				if( lines[i].trim().length() != 0 ) i--;

				if( !check.empty() ){		//testa se o escopo do if/while foi fechado
					System.out.println("ERRRO: Esta faltando um '}' para o '{' na linha " + ( last + 1 ) );
					System.exit(0);
				}
			}

			else if( line.contains("entrada") ) {		//teste se tiver a palavra chave entrada, e entao � chamado o metodo para fazer a entrada de informacoes
				if( ( s = readStep( i, 0, ';' ) ) == null ) {
					System.out.println("	Linha: " + (i+1) );
					System.exit(0);
				}

				IO.input( line, vars );
			}

			else if( line.contains("saida") ) {			//comando de saida, chama metodo uasdo para fazer a saida de informacoes
				if( ( s = readStep( i, 0, ';' ) ) == null ) {
					System.out.println("	Linha: " + (i+1) );
					System.exit(0);
				}

				IO.output( line, vars );
			}

			else if( line.contains("}") ) {		//comando para fechar bloco de comando
				return true;
			}
			
			else execute( i );				// se nao encontrar nenhum dos comandos acimaele chama o metodo execute, que fara a parte de criacao de variavel, e operacoes. 
		}	

		if( !check.empty() ){
			System.out.println("ERRRO: Esta faltando um '}'");
			System.exit(0);
		}

		return true;
	}

	private boolean execute( int i ){				
		char c;
		int subInit = 0;

		for( int j = 0 ; j < line.length(); j++ ){
			c = line.charAt(j);

			if( c == '{' ){			
				return false;
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
					if( !VarManager.newInt( vars, pair[0], pair[1] ) ) System.exit(0);
				}

				else if( t == 1 ) {
					if( !VarManager.newReal( vars, pair[0], pair[1] ) ) System.exit(0);
				}
				else{
					if( !VarManager.newStr( vars, pair[0], pair[1] ) ) System.exit(0);
				}
			}
			else{
				if( t == 0 ) {	
					if( !VarManager.newInt( vars, k, "0" ) ) System.exit(0);
				}

				else if( t == 1 ) {
					if( !VarManager.newReal( vars, k, "0" ) ) System.exit(0);
				}
				else{
					if( !VarManager.newStr( vars, k, " " ) ) System.exit(0);
				}
			}
		}

		return true;
	}

	
	//funcao que testa se o comando e valido
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
