package Pacman.model.entities;

import Pacman.Util.Config;
import Pacman.model.Direction;

import java.util.concurrent.ThreadLocalRandom;

/**
 * This class represents a ghost.
 * @author Tom Befieux
 *
 */
public class Ghost extends GameEntity{

    private static int currentGhostNb = 0;          /** The counter for ghost number */
    private int ghostNumber;                        /** The number of the ghost (for the sprite sheet). */

    /**
     * Constructor.
     */
    public Ghost() {
        super();
        this.velocity = Config.ghostsVelocity;
        this.setName("Ghost");
        this.setDirection(getDirection());
        this.ghostNumber = (currentGhostNb % 4);

        currentGhostNb++;
    }

    @Override
    public void update(float delta) {
        // if we are stopped
        if(!this.isMoving()) {
            Direction direction;
            do {
                 direction = getDirection();
            } while(direction == this.currentDirection);

            this.setDirection(direction);
        }

        super.update(delta);
    }

    /**
     * This function returns a direction (here random).
     * @return a direction
     */
    private Direction getDirection() {
        Direction result = null;
        switch (ThreadLocalRandom.current().nextInt(0, 3 + 1)) {
            case 0:
                result = Direction.TOP;
                break;

            case 1:
                result = Direction.BOTTOM;
                break;

            case 2:
                result = Direction.LEFT;
                break;

            case 3:
                result = Direction.RIGHT;
                break;
        }

        return result;
    }

    /**
     * This function returns the ghost number.
     * @return the ghost number
     */
    public int getGhostNumber() {
        return ghostNumber;
    }
}
