/**
 * Exemplo de interpretador.
 *
 * Esse é o ponto de partida para o interpretador da linguagem 'Blah'.
 * O único objetivo desse programa é instanciar um objeto Blah, que é
 * o interpretador da linguagem, passando para ele o caminho do arquivo
 * a ser interpretador. Para mais informações, veja o arquivo Blah.java
 *
 * Para executar, rode o seguinte comando no terminal:
 * java Blah ./teste.blah
 * 
 * Por Fernando Bevilacqua <fernando.bevilacqua@uffs.edu.br>
 */

import java.io.*;
import java.util.Scanner;
import interpret.AsdfInter;

class AsdfReader {

    public static void main(String args[]) throws Exception {
		
        File f;

        Scanner s;

        AsdfInter b;

        String linhas[] = new String[2000]; // arquivo pode ter, no máximo, 2000 linhas.
        
        try {

            // Referencia o arquivo. args[0] conterá os dados passados pela linha de comando.
            f = new File(args[0]);

            // Mandamos o Scanner ler a partir do arquivo.

            s = new Scanner(f);
            
            // Lemos todas as linhas do arquivo para dentro do
            // vetor "linhas".
            int i = 0;

            while(s.hasNext()) {

                linhas[i] = s.nextLine();

                i++;

            }
            
			// Instanciamos o interpretador.
            b = new AsdfInter( linhas, i );

            // Inicializamos o interpretador com o vetor de linhas. A partir
            // desse ponto, o objeto "b" irá interpretar o código lido do arquivo.
            b.interpret( 0 );
            
        } catch (IOException e) {

            System.out.println("Nao consegui ler o arquivo: " + (args.length > 0 ? args[0] : "(desconhecido)"));

            System.out.println("Uso:");

            System.out.println("    java Blah /caminho/para/arquivo.blah");

        }
       
    }

}
