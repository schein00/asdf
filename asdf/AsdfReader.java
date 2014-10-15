import java.io.*;
import java.util.Scanner;

class AsdfReader {

    public static void main(String args[]) throws Exception {

        File f;

        Scanner s;

        AsdfInter b;

        String linhas[] = new String[2000]; // arquivo pode ter, no maximo, 2000 linhas.
        
        try {

            // Referencia o arquivo. args[0] contera os dados passados pela linha de comando.
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
            // desse ponto, o objeto "b" ira interpretar o codigo lido do arquivo.
            b.interpret( linhas, 0, 0 );
            
        } catch (IOException e) {

            System.out.println("Nao consegui ler o arquivo: " + (args.length > 0 ? args[0] : "(desconhecido)"));

            System.out.println("Uso:");

            System.out.println("    java Blah /caminho/para/arquivo.blah");

        }
       
    }

}
