/**
 * The main class of the Pacman.
 * @author Tom
 *
 */
public class Pacman {
	public static void main(String[] args) {
		GameEngine engine = new GameEngine();
		Console console = new Console(engine);
		engine.addObserver(console);
		new Thread(engine).start();
	}
}
