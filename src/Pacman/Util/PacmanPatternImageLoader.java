package Pacman.Util;

import Pacman.gameObjects.Direction;
import Pacman.gameObjects.entities.Player;
import Pacman.gameObjects.objects.Wall;
import physics.Util.ObjectsImageLoader;
import physics.objects.PhysicObject;

/**
 * This class allows to get a physics engine with a pattern image.
 * @author Tom Befieux
 *
 */
public class PacmanPatternImageLoader extends ObjectsImageLoader {

    /**
     * Constructor of the class.
     */
    public PacmanPatternImageLoader() {
        super(new int[]{18, 255});
    }

    // implement the function
    @Override
    protected PhysicObject getObjectFromColor(int color) {

        PhysicObject result = null;

        // a wall
        if(color == 255) {
            result = new Wall();
            result.setName("Wall");
        }

        else if(color == 18) {
            result = new Player();
            ((Player) result).setDirection(Direction.LEFT);
            result.setName("Player");
        }

        return result;
    }
}
