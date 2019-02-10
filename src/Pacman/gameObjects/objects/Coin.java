package Pacman.gameObjects.objects;

import Pacman.Util.Config;
import Pacman.gameObjects.entities.Player;
import javafx.scene.image.Image;
import physics.Side;
import physics.objects.PhysicObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * This class represents a coin.
 * @author Tom Befieux
 *
 */
public class Coin extends GameObject {

    protected boolean taken = false;        /** If the coin is taken or not. */

    /**
     * Constructor.
     */
    public Coin() {
        super();
        this.setName("Coin");
    }

    // load the image
    @Override
    protected void loadImage() {
        try {
            this.image = new Image(new FileInputStream(Config.imagePath + "coin.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // collision
    @Override
    public void collisionTriggeredOnSide(Side side, PhysicObject object) {
        if (!this.taken && object instanceof Player)
            this.taken = true;
    }

    /**
     * Getter to know if the coin is taken or not.
     * @return if the coin is taken or not.
     */
    public boolean isTaken() {
        return this.taken;
    }
}
