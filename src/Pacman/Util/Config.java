package Pacman.Util;

import Pacman.Pacman;

/**
 * A simple configuration class with constants.
 * @author Tom
 *
 */
public class Config {

	// it's a static class
	private Config() {}

	// FPS of the game
	public static final int FPS = 30;

	// sprite
	public static final int spriteSize = 30;

	// window
	public static final String windowTitle = "Pacman";
	public static final int windowWidth = 500;
	public static final int windowHeight = 580;

	// Pacman
	public static final int PacmanVelocityValue = 100;
	public static final int leftTelportingValue = 15;
	public static final int rightTelportingValue = 480;
	public static final int deadAnimationTime = 2;

	// ghosts
    public static final int ghostsVelocity = 105;
    public static final int addVelocityValue = 2;
    public static final int ghostsVelocityInBlue = 50;
    public static final int timeBeforeGoOut = 5;
    public static final int timeInBlue = 8;
    public static final int timeToWaitWhenDead = 1000;	// in milliseconds
	public static final int endAnimationTime = 2;

	// animations
	public static final float timeBetweenAnimation = 0.1f;

	// paths
	public static final String imagePath = "assets/images/";
}
