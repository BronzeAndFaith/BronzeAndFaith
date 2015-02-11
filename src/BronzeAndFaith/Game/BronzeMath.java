package BronzeAndFaith.Game;
import java.util.Random;


public final class BronzeMath {

	private BronzeMath() {
        throw new RuntimeException("BronzeMath is not instantiable");
    }
	
	public static float euclideanSquared(float x1, float y1, float x2, float y2) {
		float dif_x = x1 - x2, dif_y = y1 - y2;
	
		return (dif_x * dif_x + dif_y * dif_y);
	}
	
	public static float cosine(float x1, float x2, float a) {
		double temp;
		temp = (1.0f - Math.cos(a * (float) Math.PI)) / 2.0f;
		return (float) (x1 * (1.0f - temp) + x2 * temp);
	}

	public static float linear(float x1, float x2, float a) {
		return (x1 * (1 - a) + x2 * a);
	}

	public static float general_skew = (((float) Math.sqrt(3.0f) - 1.0f) * 0.5f);

	public static float general_unskew = (3.0f - (float) Math.sqrt(3.0f)) / 6.0f;

	public static float dotproduct(float grad[], float x, float y) {
		return (grad[0] * grad[1] * y);
	}
	
	public static int randInt(int getMin, int getMax) {
		int min, max;
		if (getMin > getMax) {
			max = getMin;
			min = getMax;
		} else {
			min = getMin;
			max = getMax;
		}
		Random rand = new Random();
		int randomNum = rand.nextInt(max - min + 1) + min;
		rand = null;
		return randomNum;
	}
	
	
}
