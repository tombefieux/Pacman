package Pacman.gameObjects.objects;

import Pacman.gameObjects.Drawable;
import javafx.scene.image.Image;
import physics.objects.PhysicObject;

/**
 * This function represents a game object. It's a physics object that we can display.
 * @author Tom Befieux
 *
 */
public abstract class GameObject extends PhysicObject implements Drawable {

    protected Image image;         /** The image of the object. */

    /**
     * Constructor.
     */
    public GameObject() {
        loadImage();
    }

    /**
     * To load the image of the object.
     */
    protected abstract void loadImage();

    /**
     * Getter of the image to display.
     * @return the image to display
     */
    public Image getImage() {
        return image;
    }

    /**
     * Setter of the image to display.
     * @param image: the new image
     */
    public void setImage(Image image) {
        this.image = image;
    }
}
