package Pacman.model.objects;

import Pacman.Pacman;
import Pacman.model.entities.Player;
import physics.Side;
import physics.objects.PhysicObject;

/**
 * This class represents a special coin.
 * @author Tom Befieux
 *
 */
public class SpecialCoin extends Coin {

    // collision
    @Override
    public void collisionTriggeredOnSide(Side side, PhysicObject object) {
        if (!this.taken && object instanceof Player) {
            this.taken = true;
            Pacman.engine.turnGhostInBlue();
        }
    }
}
