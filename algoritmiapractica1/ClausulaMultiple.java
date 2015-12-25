package algoritmiapractica1;

import java.util.ArrayList;

/**
 *
 * @author Iñigo Alonso Ruiz y Alejandro Dieste Cortés
 */
/*
Lista de literales, representa una combinación de operación lógica OR
entre todas ellas
*/
public class ClausulaMultiple {
    ArrayList<Literal> variables;
    public ClausulaMultiple(ArrayList<Literal> variables){
        this.variables=variables;
    }
}
