/** 
 *  testTanks. Used to actually play a game. 
 * 
 *
 **/
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class testTanks {
	
	static Tank hero;
	static Tank villain;
	static FileOutputStream fout;
	static ObjectOutputStream oos;
	
 /**
  * @param args
 * @throws IOException 
  */
 public static void main(String[] args) throws IOException {
  // TODO Auto-generated method stub
  hero=new Tank("Jim"); // Tank object #1 with the name Jim
  villain=new Tank("Baddie"); // Tank object #2 with the name Baddie
  game();
//  System.out.println("Let the battle begin: ");
//  System.out.println(hero.getHP()); // using the methods of each object created
//  System.out.println(villain.getHP()); // using the methods of each object created
//  hero.shoot(villain);
//  
//  System.out.println("---Results---");
//  System.out.println(hero.getHP());
//  System.out.println(hero.getAmmo());
//  System.out.println(villain.getHP());  
//  System.out.println(villain.getAmmo());
//  villain.shoot(hero); 

 }
 
 public static void game() throws IOException {
	 while((hero.getAmmo() > 0 && !hero.getDead()) || (!villain.getDead() || villain.getAmmo() > 0)) {
		  
		  System.out.println("Jim is shooting");
		  hero.shoot(villain);
		  System.out.println("Hero Ammo: "+hero.getAmmo());
		  System.out.println("Villain HP: "+villain.getHP());
		  System.out.println("------------------------------");
		  System.out.println("Baddie is shooting");
		  villain.shoot(hero);
		  System.out.println("Hero HP: "+hero.getHP());
		  System.out.println("Villain Ammo: "+villain.getAmmo());
		  System.out.println("------------------------------");
		  if(hero.getAmmo() <=0) {
			  break;
		  }
		  if(villain.getAmmo() <=0) { //redundant but easier to viualize
			  break;
		  }
		  
		  if(hero.getHP() <=0) {//redundant but easier to viualize
			  break;
		  }
		  if(villain.getHP() <=0) {//redundant but easier to viualize
			  break;
		  }
	  }
	  
	 
	 if(hero.getDead() && ! villain.getDead()) {
		  System.out.println("Villain wins");
	  }
	  
	  if(!hero.getDead() && villain.getDead()) {
		  System.out.println("Hero wins");
	  }
	  
	  if(hero.getDead() && villain.getDead()) {
		  System.out.println("TIE");
	  }
	 
	 System.out.println("We are now going to save your progress: ");
	  
	  fout = new FileOutputStream("save.ser");
	try {
		oos = new ObjectOutputStream(fout);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  oos.writeObject(hero);
	  oos.close(); 
 }
 
}