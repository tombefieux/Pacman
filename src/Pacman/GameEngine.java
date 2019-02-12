package Pacman;

import Pacman.Util.Config;
import Pacman.Util.PacmanPatternImageLoader;
import Pacman.model.Direction;
import Pacman.model.Drawable;
import Pacman.model.entities.GameEntity;
import Pacman.model.entities.Player;
import Pacman.model.objects.Coin;
import Pacman.model.objects.Wall;

import javafx.geometry.Point2D;
import physics.PhysicsEngine;
import physics.objects.PhysicEntity;
import physics.objects.PhysicObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * This class represents the game engine of the Pacman.
 * @author Tom
 */
public class GameEngine extends Observable implements Runnable {

	private PhysicsEngine physicsEngine;						/** The physics engine of the game. */

	/**
	 * Constructor of the class.
	 */
	public GameEngine() {
		super();
		buildPhysicsEngine();
	}

	/**
	 * This function builds the physics engine and load all the physical objects with the pattern image.
	 */
	private void buildPhysicsEngine() {
		try {
			BufferedImage patternImage = ImageIO.read(new File(Config.imagePath + "pattern.png"));

			PacmanPatternImageLoader engineLoader = new PacmanPatternImageLoader();
			this.physicsEngine = engineLoader.getEngineWithPatternImage(patternImage);

		} catch (IOException e) {
			System.out.println("Impossible to load the pattern image of the map.");
		}
	}

	/**
	 * See Runnable.run()
	 */
	@Override
	public void run() {
		while(true) {
			// ------------------- Game loop here (the update) -----------------
			// update the physics engine
			this.physicsEngine.update(1 / (float) Config.FPS);

            // teleport all the entities
            for (PhysicObject object : this.physicsEngine.getObjects()) {
                if(object instanceof GameEntity) {
                    GameEntity entity = (GameEntity) object;
                    // if we need to teleport us to the right
                    if(entity.getPosition().getX() < Config.leftTelportingValue &&
                            entity.getCurrentDirection() == Direction.LEFT
                    ) {
                        entity.setPosition(new Point2D(Config.rightTelportingValue - entity.getHitbox().getWidth(), entity.getHitbox().getY()));
                    }

                    // if we need to teleport us to the left
                    else if(entity.getPosition().getX() + entity.getHitbox().getWidth() > Config.rightTelportingValue
                            && entity.getCurrentDirection() == Direction.RIGHT
                    ) {
                        entity.setPosition(new Point2D(Config.leftTelportingValue, entity.getHitbox().getY()));
                    }
                }
            }

            // check if there are still coins
            if(!isStillCoins()) {
                // TODO: action when no coin
            }

			// notify that we are done
			this.setChanged();
			this.notifyObservers("update");

			try {
				Thread.sleep(1000 / (long) Config.FPS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * This function returns the objects to draw.
	 * @return the objects to draw
	 */
	public List<Drawable> getObjectsToDraw() {
		List<Drawable> result = new ArrayList<>();
		for (PhysicObject object : this.physicsEngine.getObjects())
			if(!(object instanceof Wall))
				result.add((Drawable) object);
		return result;
	}

	/**
	 * This function returns the player.
	 * @return the player
	 */
	public Player getPlayer() {
		return (Player) this.physicsEngine.getObjectsByName("Player").get(0);
	}

	/**
	 * This function returns if a collision will append for an entity in the next object.
	 * @param object: the object
	 * @return the object with which the collision will happen
	 */
	public PhysicObject willHitSomethingOnNextUpdate(PhysicEntity object) {
		return this.physicsEngine.collisionOnNextUpdate(object, 1 / (float) Config.FPS);
	}

	/**
	 * This function returns all the objects around the object in parameter with a perimeter.
	 * @param object: the object
	 * @param perimeter: the perimeter around the object
	 * @return the objects around
	 */
	public List<PhysicObject> getObjectsAround(PhysicObject object, int perimeter) {
		return this.physicsEngine.getObjectsAround(object, perimeter);
	}

	/**
	 * Remove a coin of the engine.
	 * @param coin: the coin to remove
	 */
	public void removeCoin(Coin coin) {
		this.physicsEngine.removeObject(coin);
	}

	/**
	 * This function returns if there are still coins in the engine.
	 * @return if there are still coins in the engine.
	 */
	public boolean isStillCoins() {
		boolean stillCoins = false;
		List<PhysicObject> coins = this.physicsEngine.getObjectsByName("Coin");
		for (int i = 0; !stillCoins && i < coins.size(); i++)
			if(!((Coin) coins.get(i)).isTaken())
				stillCoins = true;

		return stillCoins;
	}

	/**
	 * This function turns the ghost in blue.
	 */
	public void turnGhostInBlue() {
		// TODO: the function
		System.out.println("Ghost in blue!");
	}
}
