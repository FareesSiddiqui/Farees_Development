import processing.core.*;
import java.util.ArrayList;

public class Scores {
	int score;
	int shot;
	int shotHit;

	private int lastKillScore;
	private int lastShotUsed;

	private int streak;

	PFont pixelFont;
	PImage hud;
	protected final PApplet pp;

	Scores(PApplet app) {
		pp = app;
		pixelFont = pp.loadFont("PressStart2P-48.vlw");
		hud = pp.loadImage("Sprites/HUD.png");
	}

	void showScores() {

	    pp.image(hud,pp.width/2,pp.height-hud.height/2);

	    pp.textAlign(pp.CENTER);
	    pp.fill(255, 255, 255);
	    pp.textFont(pixelFont, 15);
	    pp.text("SCORE:"+pp.nf(score,10), pp.width/2, pp.height-35);

	    pp.fill(76, 0, 0);
	    pp.rect(pp.width/2-400+200/2,pp.height-32,200,25);
	    if (Main.player.health > 0) {
	      float mappedValue = pp.map(Main.player.health,0,100,0,200);
	      pp.fill(237, 37, 37);
	      pp.rect(pp.width/2-400+mappedValue/2,pp.height-32,mappedValue,25);
	    }
	    pp.fill(255, 255, 255);
	    pp.text("Health",pp.width/2-465,pp.height-25);


	    if (Main.boss != null && Main.boss.active) {
	      pp.fill(76, 0, 0);
	      pp.rect(pp.width/2+400-200/2,pp.height-32,200,25);
	      float mappedValue = pp.map(Main.boss.health,0,50,0,200);
	      pp.fill(237, 37, 37);
	      pp.rect(pp.width/2+400-mappedValue/2,pp.height-32,mappedValue,25);
	      pp.fill(255,255,255);
	      pp.text("Boss",pp.width/2+465,pp.height-25);
	    }

	    streakCalc();
	    pp.fill(255,255,255);
	    if (shotHit == 0)
	      pp.text("HIT/SHOT:NA", pp.width/2, pp.height-10);
	    else
	      pp.text("HIT/SHOT:"+(int)(((shotHit*1.0)/(shot*1.0))*100)+"%", pp.width/2, pp.height-10);

	    if (!Main.gameOver && streak > 2) {
	      pp.textFont(Main.scores.getFont(), pp.sin((float) (pp.millis()*.005))*3+50);
	      pp.textAlign(pp.CENTER);
	      pp.fill(242, 220, 184);

	      pp.text("STREAK OF "+streak,pp.width/2,pp.height-50);

	    }
	  }

	PFont getFont() {
		return pixelFont;
	}

	void streakCalc() {
		if (lastKillScore < score)
			streak++;
		else
			streak = 0;
		lastKillScore = score;
		lastShotUsed = shotHit;
	}

	int getStreak() {
		return streak;
	}
}
