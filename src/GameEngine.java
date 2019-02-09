import physics.PhysicsEngine;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
	 * This function builds the physics engine and load all physics objects.
	 */
	private void buildPhysicsEngine() {
		// load the pattern img
		try {
			BufferedImage patternImage = ImageIO.read(new File(Config.imagePath + "pattern.png"));

			// TODO: get the engine with the image
			this.physicsEngine = new PhysicsEngine();

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

			// notify that we are done
			this.setChanged();
			this.notifyObservers();

			try {
				Thread.sleep(1000 / (long) Config.FPS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
