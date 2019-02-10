package Pacman.gameObjects.objects;

import Pacman.Pacman;
import Pacman.Util.Config;
import Pacman.gameObjects.entities.Player;
import javafx.scene.image.Image;
import physics.Side;
import physics.objects.PhysicObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * This class represents a special coin.
 * @author Tom Befieux
 *
 */
public class SpecialCoin extends Coin {

    // load the image
    @Override
    protected void loadImage() {
        try {
            this.image = new Image(new FileInputStream(Config.imagePath + "specialCoin.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // collision
    @Override
    public void collisionTriggeredOnSide(Side side, PhysicObject object) {
        if (!this.taken && object instanceof Player) {
            this.taken = true;
            Pacman.engine.turnGhostInBlue();
        }
    }
}
