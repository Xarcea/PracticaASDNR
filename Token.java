package mx.ipn.asdnr;

/**
 *
 * @author Xavier Arce
 */

public class Token {
    final TipoToken tipo;
    final String lexema;
    final int posicion;

    public Token(TipoToken tipo, String lexema, int posicion) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.posicion = posicion;
    }

    public Token(TipoToken tipo, String lexema) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.posicion = 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Token)) {
            return false;
        }

        if(this.tipo == ((Token)obj).tipo){
            return true;
        }

        return false;
    }

    public String toString(){
        return tipo + " " + lexema + " ";
    }
}