package Pacman.model.entities;

import Pacman.Pacman;
import Pacman.Util.Config;
import Pacman.model.Direction;
import Pacman.model.Drawable;

import Pacman.model.objects.Wall;
import javafx.geometry.Point2D;

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

    protected Direction currentDirection = null;    /** The current direction of the entity. */
    protected Direction wantToGoTo = null;          /** The direction where the entity want to go when it'll be possible. */
    protected float velocity = 50;                  /** The velocity of the entity. */

    private boolean isToFirstImage = true;          /** If it's to the first image to be displayed. */
    private float addedTime = 0;                    /** The added time for animation. */

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

    // implement drawable function
    public int getImageIndexInSpriteSheet() {
        int result = 0;

        if(currentDirection == Direction.RIGHT)
            result += 2;
        else if(currentDirection == Direction.BOTTOM)
            result += 4;
        else if(currentDirection == Direction.LEFT)
            result += 6;

        if(!isToFirstImage && isMoving())
            result++;

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
    protected void setVelocityWithDirection(Direction direction) {
        switch (direction) {
            case LEFT:
                setVelocity(new Point2D(-1 * this.velocity, 0));
                break;

            case RIGHT:
                setVelocity(new Point2D(this.velocity, 0));
                break;

            case TOP:
                setVelocity(new Point2D(0, -1 * this.velocity));
                break;

            case BOTTOM:
                setVelocity(new Point2D(0, this.velocity));
                break;
        }
    }

    /**
     * Getter for the direction.
     * @return the direction
     */
    public Direction getCurrentDirection() {
        return currentDirection;
    }
}
