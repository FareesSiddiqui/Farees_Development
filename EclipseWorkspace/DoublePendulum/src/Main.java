import processing.core.*;

public class Main extends PApplet{
	
	int rod1_length = 200;
	
	int rod2_length = 200;
	
	int mass1 = 40;
	int mass2 = 40;
	
	float angle1 = PI/2;
	float angle2 = -PI/2;
	
	float angle1_vel = 0;
	float angle2_vel = 0;
	
	float angle1_accel = 0;
	
	float angle2_accel = 0;
	
	float x1, y1, x2, y2;
	
	float gravity = 1;
	
	float px = 0, py = 0;
	
	PGraphics bg;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("Main");
	}
	
	public void settings() {
		size(900, 900);
		
	}

	public void setup() {
		bg = createGraphics(900, 900);
		bg.beginDraw();
		bg.background(255);
		bg.endDraw();
	}
	
	public void draw() {
		
		float _num1 = -gravity*(2*mass1+mass2)*sin(angle1); // - num2
		float _num2 = mass2*gravity*sin(angle1-2*angle2); // - num3
		float _num3 = 2*sin(angle1-angle2)*mass2*(angle2_vel*angle2_vel*rod2_length+angle1_vel*angle1_vel*rod1_length*cos(angle1-angle2));
		float _den = rod1_length*(2*mass1+mass2-mass2*cos(2*angle1-2*angle2));
		float num1 = 2*sin(angle1 - angle2)*(angle1_vel*angle1_vel*rod1_length*(mass1+mass2)+gravity*(mass1+mass2)*cos(angle1)+angle2_vel*angle2_vel*rod2_length*mass2*cos(angle1-angle2));
		float den = rod2_length*(2*mass1+mass2-mass2*cos(2*angle1-2*angle2));
		angle1_accel = (_num1-_num2-_num3) / _den;
		angle2_accel = num1/den;
		
		angle1_vel *= 0.999;
		angle2_vel *= 0.999;
		
		image(bg, 0, 0);
		stroke(0);
		strokeWeight(2);
		translate(450, 50);
		
		px = x2; py = y2;
		
		x1 = rod1_length * sin(angle1);
		
		y1 = rod1_length * cos(angle1);
		
		x2 = x1 + rod2_length * sin(angle2);
		
		y2 = y1 + rod2_length * cos(angle2);
		
		line(0, 0, x1, y1);
		fill(0);
		ellipse(x1, y1, mass1, mass1);
		
		line(x1, y1, x2, y2);
		fill(0);
		ellipse(x2, y2, mass2, mass2);
		
		angle1_vel += angle1_accel;
		angle2_vel += angle2_accel;
		angle1 += angle1_vel;
		angle2 += angle2_vel;
		
		bg.beginDraw();
		bg.translate(width/2, 50);
		bg.line(px, py, x2, y2);
		bg.endDraw();
	}
	
	public void mousePressed() {
		angle1 = mouseX;
	}

}
