package mx.ipn.asdnr;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Xavier Arce
 */

public class Tabla {
    private final Map<String, Map<String, String>> tabla;

    public Tabla() {
        tabla = new HashMap<>();
    }

    public void set(String fila, String columna, String dato){
        if(!tabla.containsKey(fila)){
            tabla.put(fila, new HashMap<>());
        }
        
        Map<String, String> filaActual = tabla.get(fila);
        filaActual.put(columna, dato);
    }

    public String get(String fila, String columna){
        if(tabla.containsKey(fila)){
            Map<String, String> filaActual = tabla.get(fila);
            if (filaActual.containsKey(columna)) {
                return filaActual.get(columna);
            }
        }
        return null;
    }
}