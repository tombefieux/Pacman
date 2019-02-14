package Pacman.model.entities;

import Pacman.Pacman;
import Pacman.Util.Config;
import Pacman.model.Direction;
import javafx.geometry.Point2D;
import physics.Side;
import physics.objects.PhysicObject;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class represents a ghost.
 * @author Tom Befieux
 *
 */
public class Ghost extends GameEntity{

    private static int currentGhostNb = 0;          /** The counter for ghost number */
    private int ghostNumber;                        /** The number of the ghost (for the sprite sheet). */
    private boolean isBlue = false;                 /** If the ghost is blue or not. */
    private List<Direction> directions = null;      /** The list of possible directions. */
    private boolean isOut = false;                  /** If the ghost is out or not. */
    private boolean isToWhiteImage = false;          /** To know if we are in blue if we need to use the white image. */

    /**
     * Constructor.
     */
    public Ghost() {
        super();
        this.velocity = Config.ghostsVelocity;
        this.setName("Ghost");
        this.currentDirection = null;
        this.ghostNumber = (currentGhostNb % 4);

        if(this.ghostNumber == 1) {
            isOut = true;
            this.setDirection(getDirection());
        }
        else
            waitToGoOut();

        currentGhostNb++;
    }

    @Override
    public void update(float delta) {

        // if we are stopped
        if(!this.isMoving()) {

            // if we are out
            if(this.isOut) {
                Direction direction;
                do {
                    direction = getDirection();
                } while (direction == this.currentDirection || areOpposite(direction, this.currentDirection));

                this.setDirection(direction);
            }
        }

        super.update(delta);

        // if we have the possibility to change of direction
        if(this.directions != null && isOut) {
            List<Direction> directions = getPossibleDirections();
            if (!this.directions.containsAll(directions)) {

                // do it really want to change of direction?
                // yes!
                if(ThreadLocalRandom.current().nextInt(0, 1 + 1) == 1) {
                    // get the other directions
                    for (Direction direction: this.directions)
                        directions.remove(direction);

                    // apply the new direction randomly if there are several
                    int index = ThreadLocalRandom.current().nextInt(0, directions.size());

                    // change the position of ghost if it's not the opposite of the current one
                    if (canGoInADirection(directions.get(index), true) && !areOpposite(this.currentDirection, directions.get(index)))
                        this.setDirection(directions.get(index));
                }
            }
        }
        this.directions = getPossibleDirections();
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

    /**
     * This function say the thing to do for the ghost to go out.
     */
    private void waitToGoOut() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                switch (ghostNumber) {

                    // the first to go out
                    case 2:
                        try {
                            Thread.sleep(Config.timeBeforeGoOut / 2 * 1000);
                            setVelocity(Config.ghostsVelocity);
                            Pacman.engine.getGate().setOpen(true);
                            setDirection(Direction.TOP);
                            Thread.sleep(200);
                            Pacman.engine.getGate().setOpen(false);
                            isOut = true;
                            if(isBlue)
                                setVelocity(Config.ghostsVelocityInBlue);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;

                    // the second to go out
                    case 0:
                        try {
                            Thread.sleep(Config.timeBeforeGoOut * 1000 * 2);
                            setVelocity(Config.ghostsVelocity);
                            setDirection(Direction.RIGHT);
                            Thread.sleep(300);
                            Pacman.engine.getGate().setOpen(true);
                            setDirection(Direction.TOP);
                            Thread.sleep(200);
                            Pacman.engine.getGate().setOpen(false);
                            isOut = true;
                            if(isBlue)
                                setVelocity(Config.ghostsVelocityInBlue);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;

                    // the second to go out
                    case 3:
                        try {
                            Thread.sleep(Config.timeBeforeGoOut * 1000 * 3);
                            setVelocity(Config.ghostsVelocity);
                            setDirection(Direction.LEFT);
                            Thread.sleep(300);
                            Pacman.engine.getGate().setOpen(true);
                            setDirection(Direction.TOP);
                            Thread.sleep(200);
                            Pacman.engine.getGate().setOpen(false);
                            isOut = true;
                            if(isBlue)
                                setVelocity(Config.ghostsVelocityInBlue);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        });
        t.start();
    }

    // get the good index if we are in blue or white
    public int getImageIndexInSpriteSheet() {
        if(isBlue) {
            int result = super.getImageIndexInSpriteSheet() % 2;
            if(isToWhiteImage) result += 2;
            return result;
        }

        return super.getImageIndexInSpriteSheet();
    }

    @Override
    public void collisionTriggeredOnSide(Side side, PhysicObject object) {
        super.collisionTriggeredOnSide(side, object);

        if(object instanceof Player && this.isBlue)
            killIt();
    }

    /**
     * This function turns the ghost in blue.
     */
    public void turnInBlue() {
        this.isBlue = true;
        this.isToWhiteImage = false;
        if(isOut)
            setVelocity(Config.ghostsVelocityInBlue);
    }

    /**
     * This function turns the ghost in normal.
     */
    public void turnInNormal() {
        this.isBlue = false;
        setVelocity(Config.ghostsVelocity);
    }

    /**
     * This function kill the ghost.
     */
    public void killIt() {
        Pacman.engine.ghostsKilledNb++;
        try {
            Thread.sleep(Config.timeToWaitWhenDead);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // go to origin and go top
        this.setPosition(new Point2D(235, 245));
        isOut = false;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    turnInNormal();
                    Pacman.engine.getGate().setOpen(true);
                    setDirection(Direction.TOP);
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    Pacman.engine.getGate().setOpen(false);
                    isOut = true;
                }
            }
        });
        t.start();
    }

    /**
     * This function start the animation of the end of the blue.
     */
    public void lunchEndBlueAnimation() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int numberOfBlinking = 10;
                    for (int i = 0; i < numberOfBlinking && Pacman.engine.ghostInBlueThreadSemaphore == 1; i++) {
                        isToWhiteImage = !isToWhiteImage;
                        Thread.sleep((Config.endAnimationTime * 1000) / numberOfBlinking);
                    }
                    isToWhiteImage = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    /**
     * This function returns the ghost number.
     * @return the ghost number
     */
    public int getGhostNumber() {
        return ghostNumber;
    }

    /**
     * Getter to know if the ghost is blue or not.
     * @return if the ghost is blue or not
     */
    public boolean isBlue() {
        return isBlue;
    }
}
