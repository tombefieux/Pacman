package Pacman.gameObjects.entities;

import Pacman.Util.Config;

import Pacman.gameObjects.Direction;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import physics.Side;
import physics.objects.PhysicObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

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

    /**
     * This function loads the images of the player
     */
    protected void loadImage() {
        try {
            this.image = new Image(new FileInputStream(Config.imagePath + "pacman.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // on collision
    @Override
    public void collisionTriggeredOnSide(Side side, PhysicObject object) {
        super.collisionTriggeredOnSide(side, object);

        // TODO: more stuff here in the future
    }
}
