package test;

public class Test2 {

	public static void main(String[] args) {

		long start = System.nanoTime();
		long end = System.nanoTime();
		String result = String.valueOf((start - end) / 1000000);
		System.out.println(result);
	}
}
