import java.util.ArrayList;

import processing.core.*;

public class Particle extends GameObject {

	static PImage img;
	PFont pixelFont;

	int lastMilAni;
	int frameC;
	ArrayList<PImage> images;

	boolean floatUp;

	float floatSpeed;
	int animationSpeed = 50;

	String storedText;
	float textAlpha;

	final int MODE;

	final static int TEXT = 0;
	final static int ANIM = 1;

	int life = 2500;
	int birthMil;

	/**
	 * start a new particle
	 * 
	 * @param x_     x location
	 * @param y_     y location
	 * @param mode_  text or image mode
	 * @param input_ input
	 */
	Particle(float x_, float y_, int mode_, int size_, String input_) {
		super(x_, y_);
		MODE = mode_;

		if (MODE == TEXT) {
			storedText = input_;
			pixelFont = Main.scores.getFont();
		} else {
			images = new ArrayList<PImage>();
			for (int c = 0; c < size_; c++)
				images.add(Main.scores.pp.loadImage(input_ + Main.scores.pp.nf(c, 4) + ".png"));
			img = images.get(0);

		}

		floatSpeed = -1;
		birthMil = Main.scores.pp.millis();
	}

	void update() {
		if (floatUp) {

			vel.y = floatSpeed;

			simplePhysicsCal();
		}

		if (MODE == TEXT) {
			textAlpha = Main.scores.pp.map(Main.scores.pp.millis() - birthMil, 0, life, 255, 0);
			if (textAlpha <= 0) {
				Main.particles.remove(this);
			}
		}

		if (MODE == ANIM) {
			animate(0, images.size() - 1, animationSpeed, false, false);
		}

		drawImage();
	}

	void animate(int min, int max, int aniSpeed, boolean loop, boolean reverse) {

		if (Main.scores.pp.millis() - lastMilAni > aniSpeed) {
			if (reverse)
				frameC--;
			else
				frameC++;

			lastMilAni = Main.scores.pp.millis();
		}

		if (reverse) {
			if (frameC > max)
				frameC = max;
			else if (loop && frameC < min)
				frameC = max;
			else if (!loop && frameC < min)
				frameC = min;
		} else {
			if (frameC < min)
				frameC = min;
			else if (loop && frameC > max)
				frameC = min;
			else if (!loop && frameC > max) {
				frameC = max;

				// remove after animation is done
				Main.particles.remove(this);
				return;
			}
		}

		img = images.get(frameC);
	}

	void drawImage() {
		Main.scores.pp.pushMatrix();
		Main.scores.pp.translate(loc.x, loc.y);

		if (MODE == TEXT) {
			Main.scores.pp.textAlign(Main.scores.pp.CENTER);
			Main.scores.pp.fill(255, 255, 255, textAlpha);
			Main.scores.pp.textFont(pixelFont, 15);
			Main.scores.pp.text(storedText, 0, 0);
		} else {
			Main.scores.pp.image(img, 0, 0);
		}

		Main.scores.pp.popMatrix();
	}

}
