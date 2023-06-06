package mx.ipn.asdnr;

import java.util.List;
import java.util.Stack;

/**
 *
 * @author Xavier Arce
 */

public class Parser {
    private final List<Token> tokens;
    private String a;
    private final Token identificador = new Token(TipoToken.IDENTIFICADOR, "");
    //private final Token finCadena = new Token(TipoToken.EOF, "");
    private boolean hayErrores = false;
    private static Stack<String> pila = new Stack<>();
    private int i = 0;
    private Token preanalisis;

    private static final Tabla tabla;
    static {
        tabla = new Tabla();
        tabla.set("Q", "select", "select D from T");
        tabla.set("D", "id", "P");
        tabla.set("D", "*", "P");
        tabla.set("D", "distinct", "distinct P");
        tabla.set("P", "id", "A");
        tabla.set("P", "*", "*");
        tabla.set("A", "id", "A2 A1");
        tabla.set("A1", ",", ", A");
        tabla.set("A1", "from", "");
        tabla.set("A2", "id", "id A3");
        tabla.set("A3", ",", "");
        tabla.set("A3", ".", ". id");
        tabla.set("A3", "from", "");
        tabla.set("T", "id", "T2 T1");
        tabla.set("T1", ",", ", T");
        tabla.set("T1", "", "");
        tabla.set("T2", "id", "id T3");
        tabla.set("T3", "id", "id");
        tabla.set("T3", ",", "");
        tabla.set("T3", "", "");
    }

    public Parser(List<Token> tokens){
        this.tokens = tokens;
    }

    public void analizar(){
        pila.push("");
        pila.push("Q");
        String X, cuerpo;

        while((X=pila.peek()) != ""){ // la pila no está vacía
            preanalisis = tokens.get(i);
            if(preanalisis.equals(identificador))
                a = "id";
            else
                a = preanalisis.lexema;
            if(X.equals(a)){
                pila.pop(); i++;
            }
            else if(!X.matches("[A-Z](?:[1-3])?")){
                hayErrores = true; break;
            } 
            else if(tabla.get(X, a)==null){
                hayErrores = true; break;
            }                
            else if((cuerpo=tabla.get(X, a))!=null){
                //String cuerpoSinEspacios = cuerpo.replaceAll("\\s+", "");
                //System.out.println(X + " -> " + cuerpoSinEspacios);
                pila.pop();
                agregarCuerpoAPila(cuerpo);
            }
        }

        if(hayErrores){
            Principal.error("Error en la posición " + preanalisis.posicion 
            + ". No se esperaba el token " + preanalisis.tipo);
        }
        else //if(!hayErrores && preanalisis.equals(finCadena)){
            System.out.println("Consulta válida");
        //}
    }

    private static void agregarCuerpoAPila(String cuerpo){
        String[] substrings = cuerpo.split(" ");

        for(int i = substrings.length - 1; i >= 0; i--){
            if(!substrings[i].equals(""))
                pila.push(substrings[i]);
        }
    }
}