package Pacman.model.objects;

import Pacman.model.Drawable;
import physics.objects.PhysicObject;

/**
 * This function represents a game object. It's a physics object that we can display.
 * @author Tom Befieux
 *
 */
public abstract class GameObject extends PhysicObject implements Drawable {

    // because our object are not animated
    public int getImageIndexInSpriteSheet() {
        return 0;
    }

}
