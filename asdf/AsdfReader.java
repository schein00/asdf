/**
 * Exemplo de interpretador.
 *
 * Esse � o ponto de partida para o interpretador da linguagem 'Blah'.
 * O �nico objetivo desse programa � instanciar um objeto Blah, que �
 * o interpretador da linguagem, passando para ele o caminho do arquivo
 * a ser interpretador. Para mais informa��es, veja o arquivo Blah.java
 *
 * Para executar, rode o seguinte comando no terminal:
 * java Blah ./teste.blah
 * 
 * Por Fernando Bevilacqua <fernando.bevilacqua@uffs.edu.br>
 */

import java.io.*;
import java.util.Scanner;

class AsdfReader {

    public static void main(String args[]) throws Exception {

        File f;

        Scanner s;

        AsdfInter b;

        String linhas[] = new String[2000]; // arquivo pode ter, no m�ximo, 2000 linhas.
        
        try {

            // Referencia o arquivo. args[0] conter� os dados passados pela linha de comando.
            f = new File(args[0]);

            // Mandamos o Scanner ler a partir do arquivo.

            s = new Scanner(f);

            // Instanciamos o interpretador.
            b = new AsdfInter();
            
            // Lemos todas as linhas do arquivo para dentro do
            // vetor "linhas".
            int i = 0;

            while(s.hasNext()) {

                linhas[i] = s.nextLine();

                i++;

            }
            
            // Inicializamos o interpretador com o vetor de linhas. A partir
            // desse ponto, o objeto "b" ir� interpretar o c�digo lido do arquivo.
            b.interpret( linhas, 0, 0 );
            
        } catch (IOException e) {

            System.out.println("Nao consegui ler o arquivo: " + (args.length > 0 ? args[0] : "(desconhecido)"));

            System.out.println("Uso:");

            System.out.println("    java Blah /caminho/para/arquivo.blah");

        }
       
    }

}