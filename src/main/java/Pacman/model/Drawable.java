package Pacman.model;

/**
 * This interface represents objects that are drawable.
 * @author Tom Befieux
 *
 */
public interface Drawable {

    /**
     * This function returns the index of the sprite to take to draw the object in the sprite sheet.
     * @return the index
     */
    int getImageIndexInSpriteSheet();
}
