
public class bagTester {
	static Bag bag;
	static int sum = 0;
	static int newSum;
	static int x;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		bag = new Bag(50);
		
		bag.addArray(25);
//		
		for (int i = 0; i < bag.items.size(); i++) {
			sum += bag.items.get(i).getWeight();
			System.out.println("Item"+i+" Weight: "+bag.items.get(i).getWeight());
		}
		bag.handleArray();
		
		for (int i = 0; i < bag.items.size(); i++) {
			x += bag.items.get(i).getWeight();
			System.out.println("Item"+i+" Weight: "+bag.items.get(i).getWeight());
			
		}
		
		
		
		
		System.out.println("Total Weight: "+ bag.items.size());
		System.out.println("UnderWeight: "+x);
	}

}
