package model;


/**
 * An exception that is thrown when the entered derivative order is higher than
 * the polynomial degree entered before.
 *
 * @author Arkadiusz Pilarski
 * @version 2.0
 */
public class DegreeToOrderRelationException extends Exception {

    /**
     * Calls the Exception constructor.
     */
    public DegreeToOrderRelationException() {
        super();
    }



}
