package Pacman.gameObjects.entities;

import Pacman.Util.Config;

import Pacman.gameObjects.Direction;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * This class represents the player in the game.
 * @author Tom Befieux
 *
 */
public class Player extends GameEntity {

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

    // set the velocity with the direction
    protected void setVelocityWithDirection(Direction direction) {
        switch (direction) {
            case LEFT:
                setVelocity(new Point2D(-1 * Config.PacmanVelocityValue, 0));
                break;

            case RIGHT:
                setVelocity(new Point2D(Config.PacmanVelocityValue, 0));
                break;

            case TOP:
                setVelocity(new Point2D(0, -1 * Config.PacmanVelocityValue));
                break;

            case BOTTOM:
                setVelocity(new Point2D(0, Config.PacmanVelocityValue));
                break;
        }
    }
}
