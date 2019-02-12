package Pacman.model.entities;

import Pacman.Util.Config;

import Pacman.model.Direction;
import physics.Side;
import physics.objects.PhysicObject;

/**
 * This class represents the player in the game.
 * @author Tom Befieux
 *
 */
public class Player extends GameEntity {

    /**
     * Constructor.
     */
    public Player() {
        super();
        this.velocity = Config.PacmanVelocityValue;
        this.setName("Player");
        this.setDirection(Direction.LEFT);
    }

    // on collision
    @Override
    public void collisionTriggeredOnSide(Side side, PhysicObject object) {
        super.collisionTriggeredOnSide(side, object);

        // TODO: more stuff here in the future
    }
}
