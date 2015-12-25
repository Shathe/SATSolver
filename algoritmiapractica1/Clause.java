package algoritmiapractica1;

/**
 *
 * @author http://www.keithschwarz.com/interesting/code/2sat/ 
 * and modified by Iñigo Alonso Ruiz y Alejandro Dieste Cortés
 */

public  class Clause {
    private final Literal primero; 
    private final Literal segundo;

    /**
     * Constructs a new clause out of two literals.
     *
     * @param one The first literal
     * @param two The second literal
     */
    public Clause(Literal one, Literal two) {
        primero = one;
        segundo = two;
    }
        public Clause(Literal one) {
        primero = one;
        segundo = one;
    }

    /**
     * Returns the first literal in this clause.
     *
     * @return The first literal in this clause.
     */
    public Literal first() {
        return primero;
    }

    /**
     * Return the second literal in this clause.
     *
     * @return The second literal in this clause.
     */
    public Literal second() {
        return segundo;
    }

    /**
     * Returns a string representation of this clause.
     *
     * @return A string representation of this clause
     */
    @Override
    public String toString() {
        return "(" + first() + " or " + second() + ")";
    }
}
