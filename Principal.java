package mx.ipn.asdnr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 *
 * @author Xavier Arce
 */

public class Principal {
    private static int linea = 1;

    public static void main(String[] args) throws IOException {
        ejecutarPrompt();
    }

    private static void ejecutarPrompt() throws IOException{
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        while(true){
            System.out.print(">>> ");
            String inline = reader.readLine();
            if(inline == null 
            || inline.equalsIgnoreCase("exit"))
                break;
            ejecutar(inline);
            linea++;
        }
    }

    private static void ejecutar(String source){
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();

        Parser parser = new Parser(tokens);
        parser.analizar();
    }

    static void error(String mensaje){
        reportar(linea, "", mensaje);
    }

    private static void reportar(int linea, String donde, String mensaje){
        System.err.println(
                "[linea " + linea + "] Error" + donde + ": " + mensaje
        );
    }
}