package Pacman.gameObjects;

import Pacman.Pacman;
import javafx.scene.image.Image;

/**
 * This interface represents objects that are drawable.
 * @author Tom Befieux
 *
 */
public interface Drawable {

    /**
     * This function return the image to draw for the object.
     * @return the image to draw
     */
    Image getImage();
}
