package Pacman.model.objects;

import Pacman.Pacman;
import Pacman.Util.Config;
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
        if (!this.taken && object instanceof Player) {
            this.taken = true;
            Pacman.engine.addScore(Config.pointsWhenCoinEaten);
        }
    }

    /**
     * Getter to know if the coin is taken or not.
     * @return if the coin is taken or not.
     */
    public boolean isTaken() {
        return this.taken;
    }

    /**
     * To set the coin taken or not.
     * @param taken: if it's taken or not
     */
    public void setTaken(boolean taken) {
        this.taken = taken;
    }
}
