import java.util.Scanner;
import java.util.ArrayList;
public class Main {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		int T = in.nextInt();
		
		in.nextLine();
		
		String chord = "";
		
		ArrayList<String> chords = new ArrayList<String>(T);
		
		for (int i = 1; i <= T; i++) {
			chord = in.nextLine();
			chords.add(chord);
			
			
//			System.out.println(i);
		}
		System.out.println(chords);
		
	}

}
