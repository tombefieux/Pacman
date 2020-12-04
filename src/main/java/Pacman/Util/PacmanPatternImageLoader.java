package Pacman.Util;

import Pacman.model.entities.Ghost;
import Pacman.model.entities.Player;
import Pacman.model.objects.Coin;
import Pacman.model.objects.Gate;
import Pacman.model.objects.SpecialCoin;
import Pacman.model.objects.Wall;
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
        super(new int[]{18, 72, 100, 149, 199, 255});
    }

    // implement the function
    @Override
    protected PhysicObject getObjectFromColor(int color) {

        PhysicObject result = null;

        // a wall
        if(color == 255)
            result = new Wall();

        // player
        else if(color == 18)
            result = new Player();

        // coin
        else if (color == 72)
            result = new Coin();

        // special coin
        else if (color == 100)
            result = new SpecialCoin();

        // the special wall
        else if (color == 149)
            result = new Gate();

        // ghost
        else if (color == 199)
            result = new Ghost();

        return result;
    }
}
