import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Bag {
	public static ArrayList<Item> items;
	private int maxWeight;
	Random r;
	Bag(int _maxWeight){
		maxWeight = _maxWeight;
		items = new ArrayList<Item>();
	}
	
	public void addArray(int cap) {
		for (int i = 0; i < cap; i++) {
			int weight = ThreadLocalRandom.current().nextInt(1, maxWeight + 1);
			items.add(new Item(weight, "Item"+i));
		}
	}
	
	
	
	public void handleArray() {
		for (int x = items.size()-1; x >= 0; x--) {
			bagTester.newSum += items.get(x).getWeight();
			if(bagTester.newSum > getMaxweight()) {
				System.out.println("Item"+x+" Was Removed with a weight of: "+items.get(x).getWeight());
				items.remove(x);
			}
			
		}
	}

	
	public void checkWeight(ArrayList<Item> check) {
		
	}
	
	public int getMaxweight() {
		return maxWeight;
	}
	
	public void setMaxWeight(int w) {
		
	}
}
