package Pacman.gameObjects.entities;

import Pacman.Pacman;
import Pacman.Util.Config;
import Pacman.gameObjects.Direction;
import Pacman.gameObjects.Drawable;

import Pacman.gameObjects.objects.Wall;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

import physics.Side;
import physics.objects.PhysicEntity;
import physics.objects.PhysicObject;

import java.util.List;


/**
 * This function represents a game entity. It's a physics entity that we can display.
 * A game entity is also animated when he moved.
 * @author Tom Befieux
 *
 */
public abstract class GameEntity extends PhysicEntity implements Drawable {

    protected Image image;                          /** The sprites of the entity. */
    protected Direction currentDirection = null;    /** The current direction of the entity. */
    protected Direction wantToGoTo = null;          /** The direction where the entity want to go when it'll be possible. */

    private boolean isToFirstImage = true;          /** If it's to the first image to be displayed. */
    private float addedTime = 0;                    /** The added time for animation. */

    /**
     * Constructor.
     */
    public GameEntity() {
        loadImage();
    }

    /**
     * To load the image of the entity.
     */
    protected abstract void loadImage();

    // add the animation
    public void update(float delta) {
        super.update(delta);

        // change the image for animation
        this.addedTime += delta;
        if(this.addedTime >= Config.timeBetweenAnimation) {
            this.isToFirstImage = !this.isToFirstImage;
            this.addedTime -= Config.timeBetweenAnimation;
        }

        // if we want to go to another direction, see if we can
        if(this.wantToGoTo != null) {
            List<PhysicObject> objectsAround = Pacman.engine.getObjectsAround(this, 5);
            boolean weCanGo = false;
            final int acceptableMargin = 5;
            Point2D savedPosition = this.getPosition();

            switch (this.wantToGoTo) {
                case RIGHT:
                    for (PhysicObject object: objectsAround) {
                        if(
                            object instanceof Wall &&
                            this.getHitbox().getX() + this.getHitbox().getWidth() + 5 > object.getHitbox().getX() &&
                            this.getHitbox().getX() + this.getHitbox().getWidth() + 5 < object.getHitbox().getX() + object.getHitbox().getWidth() &&
                            this.getHitbox().getY() < object.getHitbox().getY() + object.getHitbox().getHeight() + acceptableMargin &&
                            this.getHitbox().getY() > object.getHitbox().getY() + object.getHitbox().getHeight() - acceptableMargin
                        ) {
                            this.setPosition(new Point2D(this.getPosition().getX(), object.getHitbox().getY() + object.getHitbox().getHeight()));
                            weCanGo = true;
                        }
                    }
                    break;

                case LEFT:
                    for (PhysicObject object: objectsAround) {
                        if(
                            object instanceof Wall &&
                            this.getHitbox().getX() - 5 > object.getHitbox().getX() &&
                            this.getHitbox().getX() - 5 < object.getHitbox().getX() + object.getHitbox().getWidth() &&
                            this.getHitbox().getY() < object.getHitbox().getY() + object.getHitbox().getHeight() + acceptableMargin &&
                            this.getHitbox().getY() > object.getHitbox().getY() + object.getHitbox().getHeight() - acceptableMargin
                        ) {
                            this.setPosition(new Point2D(this.getPosition().getX(), object.getHitbox().getY() + object.getHitbox().getHeight()));
                            weCanGo = true;
                        }
                    }
                    break;

                case TOP:
                    for (PhysicObject object: objectsAround) {
                        if(
                            object instanceof Wall &&
                            this.getHitbox().getY() - 5 > object.getHitbox().getY() &&
                            this.getHitbox().getY() - 5 < object.getHitbox().getY() + object.getHitbox().getHeight() &&
                            this.getHitbox().getX() < object.getHitbox().getX() + object.getHitbox().getWidth() + acceptableMargin &&
                            this.getHitbox().getX() > object.getHitbox().getX() + object.getHitbox().getWidth() - acceptableMargin
                        ) {
                            this.setPosition(new Point2D(object.getHitbox().getX() + object.getHitbox().getWidth(), this.getPosition().getY()));
                            weCanGo = true;
                        }
                    }
                    break;

                case BOTTOM:
                    for (PhysicObject object: objectsAround) {
                        if(
                            object instanceof Wall &&
                            this.getHitbox().getY() + this.getHitbox().getHeight() + 5 > object.getHitbox().getY() &&
                            this.getHitbox().getY() + this.getHitbox().getHeight() + 5 < object.getHitbox().getY() + object.getHitbox().getHeight() &&
                            this.getHitbox().getX() < object.getHitbox().getX() + object.getHitbox().getWidth() + acceptableMargin &&
                            this.getHitbox().getX() > object.getHitbox().getX() + object.getHitbox().getWidth() - acceptableMargin
                        ) {
                            this.setPosition(new Point2D(object.getHitbox().getX() + object.getHitbox().getWidth(), this.getPosition().getY()));
                            weCanGo = true;
                        }
                    }
                    break;
            }

            if(weCanGo) {
                Direction savedWantToGo = this.wantToGoTo;
                setDirection(this.wantToGoTo);
                if(this.currentDirection != savedWantToGo) // check that we are in the good direction
                    this.setPosition(savedPosition);
            }
        }
    }

    /**
     * Getter of the image to display to draw the entity.
     * @return the image to display
     */
    public Image getImage() {
        Image result = null;
        PixelReader reader = this.image.getPixelReader();

        // set the good image according to the direction
        switch (currentDirection) {
            case TOP:
                if (isToFirstImage || !isMoving())
                    result = new WritableImage(reader, 0, 0, Config.spriteSize, Config.spriteSize);
                else
                    result = new WritableImage(reader, Config.spriteSize, 0, Config.spriteSize, Config.spriteSize);
                break;

            case RIGHT:
                if (isToFirstImage || !isMoving())
                    result = new WritableImage(reader, Config.spriteSize * 2, 0, Config.spriteSize, Config.spriteSize);
                else
                    result = new WritableImage(reader, Config.spriteSize * 3, 0, Config.spriteSize, Config.spriteSize);
                break;

            case BOTTOM:
                if (isToFirstImage || !isMoving())
                    result = new WritableImage(reader, Config.spriteSize * 4, 0, Config.spriteSize, Config.spriteSize);
                else
                    result = new WritableImage(reader, Config.spriteSize * 5, 0, Config.spriteSize, Config.spriteSize);
                break;

            case LEFT:
                if (isToFirstImage || !isMoving())
                    result = new WritableImage(reader, Config.spriteSize * 6, 0, Config.spriteSize, Config.spriteSize);
                else
                    result = new WritableImage(reader, Config.spriteSize * 7, 0, Config.spriteSize, Config.spriteSize);
                break;
        }

        return result;
    }

    // on collision
    @Override
    public void collisionTriggeredOnSide(Side side, PhysicObject object) {

        // if we have hit a wall we stop the velocity
        if(object instanceof Wall) {
            if(side == Side.TOP || side == Side.BOTTOM) {
                this.setVelocity(new Point2D(this.getVelocity().getX(), 0));

                if(side == Side.TOP)
                    this.setPosition(new Point2D(this.getPosition().getX(), object.getPosition().getY() + object.getHitbox().getHeight()));
                else
                    this.setPosition(new Point2D(this.getPosition().getX(), object.getPosition().getY() - this.getHitbox().getHeight()));
            }
            else {
                this.setVelocity(new Point2D(0, this.getVelocity().getY()));

                if(side == Side.LEFT)
                    this.setPosition(new Point2D(object.getHitbox().getX() + object.getHitbox().getWidth(), this.getPosition().getY()));
                else
                    this.setPosition(new Point2D(object.getHitbox().getX() - this.getHitbox().getWidth(), this.getPosition().getY()));
            }
        }
    }

    /**
     * Set a new direction for the entity.
     * @param direction: the new direction
     */
    public void setDirection(Direction direction) {

        // see if we'll hit something with this direction
        setVelocityWithDirection(direction);

        // it's ok
        if(Pacman.engine == null || !(Pacman.engine.willHitSomethingOnNextUpdate(this) instanceof Wall)) {
            this.currentDirection = direction;
            this.wantToGoTo = null;
        }

        // not ok -> we'll do it when it'll be possible
        else {
            setVelocityWithDirection(this.currentDirection);
            this.wantToGoTo = direction;
        }
    }

    /**
     * Set the good velocity according to the direction.
     * @param direction: the direction
     */
    protected abstract void setVelocityWithDirection(Direction direction);

    /**
     * Setter of the image.
     * @param image: the new image
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * Getter for the direction.
     * @return the direction
     */
    public Direction getCurrentDirection() {
        return currentDirection;
    }
}
