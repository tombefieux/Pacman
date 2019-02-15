package Pacman;

import Pacman.Util.Config;
import Pacman.model.Direction;
import Pacman.model.Drawable;

import Pacman.model.entities.Ghost;
import Pacman.model.entities.Player;
import Pacman.model.objects.Coin;
import Pacman.model.objects.SpecialCoin;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import physics.objects.PhysicObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.Observer;

/**
 * This class represents the Pacman application.
 * It creates the window and handles the updates notifications of the game engine to
 * update the window.
 * @author Tom
 *
 */
public class Pacman extends Application implements Observer {

	public static GameEngine engine;							/** The game engine. */
	private GraphicsContext graphicsContext;					/** The graphic context. */

	private Image backgroundImage;								/** The background image. */
    private Image pacmanSpriteSheet;							/** The Pacman sprite sheet image. */
    private Image pacmanDeadSpriteSheet;						/** The Pacman sprite sheet image when it dead. */
    private Image ghostsSpriteSheet;						    /** The ghosts sprite sheet image. */
    private Image blueGhostsSpriteSheet;						/** The blue ghosts sprite sheet image. */
    private Image coinSpriteSheet;							    /** The coin sprite image. */
    private Image specialCoinSpriteSheet;					    /** The special coin sprite image. */

	/**
	 * The main function to start the application.
	 * @param args: args for the applications
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Start function of the application.
	 * @param theStage: stage
	 */
	public void start(Stage theStage) {
        // load images
        loadImages();

		// we start the window
		startTheWindow(theStage);

		// we start the game engine
		startGameEngine();
	}

	/**
	 * Function to load the useful images.
	 */
	private void loadImages() {
		try {
			this.backgroundImage = new Image(new FileInputStream(Config.imagePath + "background.png"));
            this.pacmanSpriteSheet = new Image(new FileInputStream(Config.imagePath + "pacman.png"));
            this.pacmanDeadSpriteSheet = new Image(new FileInputStream(Config.imagePath + "dead.png"));
            this.ghostsSpriteSheet = new Image(new FileInputStream(Config.imagePath + "ghosts.png"));
            this.blueGhostsSpriteSheet = new Image(new FileInputStream(Config.imagePath + "blueAndWhiteGhosts.png"));
            this.coinSpriteSheet = new Image(new FileInputStream(Config.imagePath + "coin.png"));
            this.specialCoinSpriteSheet = new Image(new FileInputStream(Config.imagePath + "specialCoin.png"));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This function creates and starts the game engine.
	 */
	private void startGameEngine() {
		engine = new GameEngine();
		engine.addObserver(this);
		new Thread(engine).start();
	}

	/**
	 * This function creates the window and shows it.
	 * @param theStage: stage
	 */
	private void startTheWindow(Stage theStage) {
		theStage.setTitle(Config.windowTitle);

		Group root = new Group();
		Scene theScene = new Scene(root);
		theStage.setScene(theScene);

		Canvas canvas = new Canvas(Config.windowWidth, Config.windowHeight);
		root.getChildren().add(canvas);

		this.graphicsContext = canvas.getGraphicsContext2D();

		// handle on close event to stop the game engine thread
		theStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent e) {
				Platform.exit();
				System.exit(0);
			}
		});

		// keyboard events
		theScene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
			if(key.getCode() == KeyCode.LEFT)
				engine.getPlayer().setDirection(Direction.LEFT);

			else if(key.getCode() == KeyCode.RIGHT)
				engine.getPlayer().setDirection(Direction.RIGHT);

			else if(key.getCode() == KeyCode.UP)
				engine.getPlayer().setDirection(Direction.TOP);

			else if(key.getCode() == KeyCode.DOWN)
				engine.getPlayer().setDirection(Direction.BOTTOM);
		});

		theStage.show();
	}

	/**
	 * This function renders the game in the window.
	 */
	private void render() {
		// draw the background
		this.graphicsContext.drawImage(this.backgroundImage, 0, 0);

		// draw all the objects
		for (Drawable object : engine.getObjectsToDraw()) {
			PhysicObject temp = (PhysicObject) object;

			// player
			if(object instanceof Player) {

                PixelReader reader = null;

                // if dead
                if(!((Player) object).isAlive())
                    reader = pacmanDeadSpriteSheet.getPixelReader();

                // not dead
			    else
                    reader = this.pacmanSpriteSheet.getPixelReader();

			    // draw image
                this.graphicsContext.drawImage(
                        new WritableImage(
                                reader,
                                object.getImageIndexInSpriteSheet() * Config.spriteSize,
                                0,
                                Config.spriteSize, Config.spriteSize
                        ),
                        temp.getPosition().getX(),
                        temp.getPosition().getY()
                );
            }

			// ghost
			else if(engine.getPlayer().isAlive() && object instanceof Ghost) {
			    // if blue
			    if(((Ghost) object).isBlue()) {
                    PixelReader reader = this.blueGhostsSpriteSheet.getPixelReader();
                    this.graphicsContext.drawImage(
                            new WritableImage(
                                    reader,
                                    object.getImageIndexInSpriteSheet() * Config.spriteSize,
                                    0,
                                    Config.spriteSize, Config.spriteSize
                            ),
                            temp.getPosition().getX(),
                            temp.getPosition().getY()
                    );
                }

			    // if normal
                else {
                    PixelReader reader = this.ghostsSpriteSheet.getPixelReader();
                    this.graphicsContext.drawImage(
                            new WritableImage(
                                    reader,
                                    object.getImageIndexInSpriteSheet() * Config.spriteSize,
                                    ((Ghost) temp).getGhostNumber() * Config.spriteSize,
                                    Config.spriteSize, Config.spriteSize
                            ),
                            temp.getPosition().getX(),
                            temp.getPosition().getY()
                    );
                }
            }

			// coin or special coin
            else if(object instanceof Coin && !((Coin) object).isTaken()) {
                if(object instanceof SpecialCoin)
                    this.graphicsContext.drawImage(this.specialCoinSpriteSheet, temp.getPosition().getX(), temp.getPosition().getY());
                else
                    this.graphicsContext.drawImage(this.coinSpriteSheet, temp.getPosition().getX(), temp.getPosition().getY());
            }
		}

		// draw the number of live
        PixelReader reader = this.pacmanSpriteSheet.getPixelReader();
        for (int i = 0; i < engine.getPlayer().getLiveNb(); i++) {
            this.graphicsContext.drawImage(
                    new WritableImage(
                            reader,
                            2 * Config.spriteSize,
                            0,
                            Config.spriteSize, Config.spriteSize
                    ),
                    335 + (i * (Config.spriteSize + 7)),
                    544
            );
        }
	}

	/**
	 * Implemented function of the observer interface.
	 * Here we handle only the game engine.
	 * @param o: the object updated
	 * @param arg: the argument
	 */
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof GameEngine) {
			if(((String)arg).equals("update"))
				render();
		}
	}
}