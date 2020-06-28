import processing.core.*;

public class Main extends PApplet {

	float[] values;

	int i = 0;
	int j = 0;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("Main");
	}

	public void setup() {
		values = new float[width];

		for (int i = 0; i < values.length; i++) {
			values[i] = noise(i/100.0f)*height;
		}

	}

	public void settings() {
		size(800, 500);
		fullScreen();

	}

	public void draw() {
		background(0);

//		float a = values[j];
//		float b = values[j + 1];
//
//		if (a > b) {
//			swap(values, j, j + 1);
//		}

		if (i < values.length) {
			for (int j = 0; j < values.length - i - 1; j++) {
				float a = values[j];
				float b = values[j + 1];

				if (a > b) {
					swap(values, j, j + 1);
				}
			}
		}

		else {
			println("finished");
			noLoop();
		}
		i++;

		for (int i = 0; i < values.length; i++) {
			stroke(255);
			line(i, height, i, height - values[i]);
		}
	}

	public void swap(float[] arr, int a, int b) {
		float temp = arr[a];
		arr[a] = arr[b];
		arr[b] = temp;
	}

}
