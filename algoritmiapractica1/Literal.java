package algoritmiapractica1;

/**
 *
 * @author http://www.keithschwarz.com/interesting/code/2sat/ 
 * and modified by Iñigo Alonso Ruiz y Alejandro Dieste Cortés
 */
public class Literal{
    final String nombre; // The variable in question
    final boolean valor; // Whether this is X (true) or ~X (false)

    /**
     * Constructs a new literal from the specified value and sign.  Null
     * literals are not supported.
     *
     * @param value The value representing the literal.
     * @param isPositive Whether the value is positive or negative.
     */
    public Literal(String value, boolean isPositive) {
        nombre = value;
        valor = isPositive;
    }

    /**
     * Returns the negation of this literal.
     *
     * @return A Literal holding the negation of this literal.
     */
    public Literal negation() {
        return new Literal(value(), !isPositive());
    }

    /**
     * Returns the object used to represent the literal in this clause.
     *
     * @return The object used to represent the literal in this clause.
     */
    public String value() {
        return nombre;
    }

    /**
     * Returns whether this literal is positive or negative.
     *
     * @return Whether this literal is positive or negative.
     */
    public boolean isPositive() {
        return valor;
    }

    /**
     * Returns a string representation of this literal.
     *
     * @return A string representation of this literal.
     */
    @Override
    public String toString() {
        return (isPositive() ? "" : "~") + value();
    }

    /**
     * Returns whether this literal is equal to some other object.
     *
     * @param obj An object to which this literal should be compared.
     * @return Whether this object is equal to that object.
     */
    @Override
    public boolean equals(Object obj) {
        /* Confirm that the other object has the proper type. */
        if (!(obj instanceof Literal))
            return false;

        /* Downcast, then do a field-by-field comparison. */
        Literal realObj = (Literal) obj;

        return realObj.isPositive() == isPositive() && realObj.value().equals(value());
    }

    /**
     * Returns a semi-random value computed from the state of this object.
     *
     * @return The object's hash code.
     */
    @Override
    public int hashCode() {
        return (isPositive() ? 1 : 31) * value().hashCode();
    }
}