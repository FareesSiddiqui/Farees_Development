
public class Cam2D extends GameObject{


	  /**
	   * Creates a camera where 0,0 is at the center of the screen
	   * Also starts smooth panning
	   */
	  Cam2D() {
	    super(new Main().width/2, new Main().height/2);

	    des = loc.copy();
	    smoothFactor = (float) .025;
	  }

	  /**
	   * Creates a camera where 0,0 is at x,y
	   * Also starts smooth panning
	   * @param x_ x locatoin for center
	   * @param y_ y locatoin for center
	   */
	  Cam2D(float x_, float y_) {
	    super(x_, y_);

	    des = loc.copy();
	    smoothFactor = (float) .1;
	  }

	  /**
	   * Overrides the default blank gameObject update method
	   * Should be ran every game tick
	   */
	  @Override
	  void update() {
	    smoothMove();

	    if (vel.x > 0)
	      vel.x = 0;

	    simplePhysicsCal();
	    Main.scores.pp.translate(loc.x, loc.y);
	  }
}
