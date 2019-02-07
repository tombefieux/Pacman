import java.util.Observable;

/**
 * This class represents the game engine of the Pacman.
 * @author Tom
 */
public class GameEngine extends Observable implements Runnable {
	
	/**
	 * See Runnable.run()
	 */
	@Override
	public void run() {
		while(true) {
			
			// ------------------- Game loop here (the update) -----------------



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
