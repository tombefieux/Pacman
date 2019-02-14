package Pacman.model.entities;

import Pacman.Util.Config;

import Pacman.model.Direction;
import javafx.geometry.Point2D;
import physics.Side;
import physics.objects.PhysicObject;

/**
 * This class represents the player in the game.
 * @author Tom Befieux
 *
 */
public class Player extends GameEntity {

    private boolean alive = true;           /** If the player is alive or not. */
    public int liveNb = 3;				    /** How much lives do we have? */
    private int currentDeadFrame = 0;       /** The current frame of the death animation. */

    /**
     * Constructor.
     */
    public Player() {
        super();
        this.velocity = Config.PacmanVelocityValue;
        this.setName("Player");
        this.setDirection(Direction.LEFT);
    }

    // on collision
    @Override
    public void collisionTriggeredOnSide(Side side, PhysicObject object) {
        super.collisionTriggeredOnSide(side, object);

        if (object instanceof Ghost && !((Ghost) object).isBlue())
            kill();
    }

    // override for the death
    public int getImageIndexInSpriteSheet() {
        if(!this.alive)
            return currentDeadFrame;

        return super.getImageIndexInSpriteSheet();
    }

    /**
     * This function kill the player.
     */
    public void kill() {
        if(isAlive()) {
            this.alive = false;
            this.currentDeadFrame = 0;
            lunchDeadAnimation();
        }
    }

    /**
     * Lunch the dead animation.
     */
    private void lunchDeadAnimation() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 12; i++) {
                    currentDeadFrame = i;
                    try {
                        Thread.sleep((Config.deadAnimationTime * 1000) / 12);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if(liveNb > 1) {
                    // wait again 1 second before revive
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    setPosition(new Point2D(235, 394));
                    alive = true;
                    setDirection(Direction.LEFT);
                }
                liveNb--;
            }
        });
        t.start();
    }

    /**
     * Getter to know if the player is alive or not.
     * @return if the player is alive or not
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Getter for the live number of the player.
     * @return the live number of the player
     */
    public int getLiveNb() {
        return liveNb;
    }
}
