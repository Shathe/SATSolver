package algoritmiapractica1;

/**
 *
 * @author http://www.keithschwarz.com/interesting/code/2sat/ 
 * and modified by Iñigo Alonso Ruiz y Alejandro Dieste Cortés
 */
import java.util.*; // For List, Set

public class TwoSat {
    
     ArrayList<Clause> formula;
     
     public TwoSat(ArrayList<Clause> formula){
         this.formula=formula;
     }
    /**
     * Given as input a list of clauses representing a 2-CNF formula, returns
     * whether that formula is satisfiable.
     *
     * @param formula The input 2-CNF formula.
     * @return Whether the formula has a satisfying assignment.
     */
    public  boolean isSatisfiable2SAT() {
        /* Begin by populating a set of all the variables in this formula. */
        Set<String> variables = new HashSet<String>();
        for (Clause clause: formula) {
            variables.add(clause.first().value());
            variables.add(clause.second().value());
        }
          
        /* Construct the directed graph of implications.  Begin by creating the
         * nodes.
         */
        DirectedGraph<Literal> implications = new DirectedGraph<Literal>();
        for (String variable: variables) {
            /* Add both the variable and its negation. */
            implications.addNode(new Literal(variable, true));
            implications.addNode(new Literal(variable, false));
        }

        /* From each clause (A or B), add two clauses - (~A -> B) and (~B -> A)
         * to the graph as edges.
         */
        for (Clause clause: formula) {
            implications.addEdge(clause.first().negation(), clause.second());
            implications.addEdge(clause.second().negation(), clause.first());
        }

        /* Compute the SCCs of this graph using Kosaraju's algorithm. */
        Map<Literal, Integer> scc = Kosaraju.stronglyConnectedComponents(implications);

        /* Finally, check whether any literal and its negation are in the same
         * strongly connected component.
         */
        for (String variable: variables)
            if (scc.get(new Literal(variable, true)).equals(scc.get(new Literal(variable, false))))
                return false;

        /* If not, the formula must be satisfiable. */
        return true;
    }
}