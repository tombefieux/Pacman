package Pacman.model.objects;

/**
 * This class represents the gate.
 * @author Tom Befieux
 *
 */
public class Gate extends Wall {

    private boolean isOpen = false;             /** If the gate is open or not. */

    /**
     * Constructor.
     */
    public Gate() {
        super();
        this.setName("Gate");
    }

    /**
     * Getter to know if the gate is open or not.
     * @return if the gate is open or not
     */
    public boolean isOpen() {
        return isOpen;
    }

    /**
     * Setter to open or close the gate.
     * @param open: the boolean value
     */
    public void setOpen(boolean open) {
        isOpen = open;
    }
}
