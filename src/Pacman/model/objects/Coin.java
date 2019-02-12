package Pacman.model.objects;

import Pacman.model.entities.Player;
import physics.Side;
import physics.objects.PhysicObject;

/**
 * This class represents a coin.
 * @author Tom Befieux
 *
 */
public class Coin extends GameObject {

    protected boolean taken = false;        /** If the coin is taken or not. */

    /**
     * Constructor.
     */
    public Coin() {
        super();
        this.setName("Coin");
    }

    // collision
    @Override
    public void collisionTriggeredOnSide(Side side, PhysicObject object) {
        if (!this.taken && object instanceof Player)
            this.taken = true;
    }

    /**
     * Getter to know if the coin is taken or not.
     * @return if the coin is taken or not.
     */
    public boolean isTaken() {
        return this.taken;
    }
}
