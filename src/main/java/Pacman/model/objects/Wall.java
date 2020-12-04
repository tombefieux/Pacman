package Pacman.model.objects;

import physics.Side;
import physics.objects.PhysicObject;

/**
 * This class represents a wall.
 * @author Tom Befieux
 *
 */
public class Wall extends PhysicObject {

    /**
     * Constructor.
     */
    public Wall() {
        super();
        this.setName("Wall");
    }

    @Override
    public void collisionTriggeredOnSide(Side side, PhysicObject object) {
        // nothing because it's a wall
    }
}
