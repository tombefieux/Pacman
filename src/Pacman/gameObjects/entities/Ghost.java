package Pacman.gameObjects.entities;

import Pacman.Util.Config;
import Pacman.gameObjects.Direction;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class represents a ghost.
 * @author Tom Befieux
 *
 */
public class Ghost extends GameEntity{

    private static int ghostNb = 0;

    /**
     * Constructor.
     */
    public Ghost() {
        super();
        this.setName("Ghost");
        this.setDirection(getDirection());

        PixelReader reader = this.image.getPixelReader();
        this.image = new WritableImage(reader, 0, ghostNb * Config.spriteSize, Config.spriteSize * 8, Config.spriteSize);
        ghostNb++;
    }

    @Override
    public void update(float delta) {
        // if we are stopped
        if(!this.isMoving()) {
            Direction direction;
            do {
                 direction = getDirection();
            } while(direction == this.currentDirection);

            this.setDirection(direction);
        }

        super.update(delta);
    }

    @Override
    protected void loadImage() {
        try {
            this.image = new Image(new FileInputStream(Config.imagePath + "ghosts.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void setVelocityWithDirection(Direction direction) {
        switch (direction) {
            case LEFT:
                setVelocity(new Point2D(-1 * Config.ghostsVelocity, 0));
                break;

            case RIGHT:
                setVelocity(new Point2D(Config.ghostsVelocity, 0));
                break;

            case TOP:
                setVelocity(new Point2D(0, -1 * Config.ghostsVelocity));
                break;

            case BOTTOM:
                setVelocity(new Point2D(0, Config.ghostsVelocity));
                break;
        }
    }

    /**
     * This function returns a direction (here random).
     * @return a direction
     */
    private Direction getDirection() {
        Direction result = null;
        switch (ThreadLocalRandom.current().nextInt(0, 3 + 1)) {
            case 0:
                result = Direction.TOP;
                break;

            case 1:
                result = Direction.BOTTOM;
                break;

            case 2:
                result = Direction.LEFT;
                break;

            case 3:
                result = Direction.RIGHT;
                break;
        }

        return result;
    }
}
