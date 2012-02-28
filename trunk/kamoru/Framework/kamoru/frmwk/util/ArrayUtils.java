package kamoru.frmwk.util;

public class ArrayUtils {

	/**
	 * return non-null number of array elements
	 * @param array
	 * @return
	 */
	public static int getRealLength(Object[] array) {
		if(array == null) {
			return 0;
		}
		int length = 0;
		for(int i=0; i<array.length; i++) {
			if(array[i] != null) {
				length++;
			}
		}
		return length;
	}
}
