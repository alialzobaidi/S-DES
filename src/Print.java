public class Print {
	/** Prints array to console **/
	public static void array(int[] arr, int len) {
		System.out.print(" - ");
		for (int i = 0; i < len; i++) {
			System.out.print(arr[i] + " ");
		}
	}

	 public static void msg(String msg) {
		System.out.print(msg);
	}
}