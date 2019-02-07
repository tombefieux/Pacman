import java.util.Observable;
import java.util.Observer;

/**
 * This class represents a console that observe the game engine to print it.
 * @author Tom
 *
 */
public class Console implements Observer {

	private GameEngine jeu;
	
	public Console(GameEngine jeu) {
		this.jeu = jeu;
	}
	
	/**
	 * This renders the game engine.
	 */
	public void render() {
		for (int i = 0; i < Config.mapHeight; i++) {
			for (int j = 0; j < Config.mapWidth; j++)
				System.out.print(" " + Config.map[i][j] + " ");

			System.out.print('\n');
		}

		System.out.println("\n");
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		render();
	}
}
