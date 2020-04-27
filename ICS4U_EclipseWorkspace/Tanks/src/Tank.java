import java.io.Serializable;
import java.util.Random;

public class Tank implements Serializable {
	private int hp; // not so good
	private String name;
	private int ammo; // better
	private int damage;
	
	private boolean dead = false;

	// Constructor of the class
	// this is where default values are assigned
	public Tank(String givenName) {
		name = givenName;
		hp = 50;
		ammo = 10;

	}
	
	public boolean getDead() {
		return dead;
	}
	
	public void setDead(boolean _dead) {
		dead = _dead;
	}
	
	public void setHP(int _hp) {
		hp = _hp;
	}

	public int getHP() {
		if(hp <= 0)
			setDead(true);
		return hp;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public void setDamage() {
		Random r = new Random();
		damage = r.nextInt((15 - 1) + 1) + 1;
		
	}
	public void chHP(int change) {
		hp = hp + change;
	}

	public void shoot(Tank target) {

		this.ammo = this.ammo - 1;
		System.out.println(this.name + " has shot " + target.name); // usually bad practice
		
		setDamage();

		target.chHP(-getDamage());
		
		if(damage > target.hp || target.getHP() <=0) {
			target.setHP(0);
			
		}
		

	}

	public String getName() {
		return name;
	}

	public int getAmmo() {
		return ammo;
	}

	public String toString() {
		return "Name: " + name + " HP: " + hp;
	}

}