import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
 
public class TesterLoad{
 
   public static void main (String args[]) throws ClassNotFoundException, IOException {
 
	   
	   Tank hero = loadTank();
	   System.out.println("Your current hero status from last save is: ");
	   System.out.println(hero);
   }
 
   public static Tank loadTank() throws IOException, ClassNotFoundException{
 
	   Tank hero;
 
 
		   FileInputStream fin = new FileInputStream("save.ser");
		   ObjectInputStream ois = new ObjectInputStream(fin);
		   hero = (Tank) ois.readObject();
		   ois.close();
 
		   return hero;
 
   } 
}