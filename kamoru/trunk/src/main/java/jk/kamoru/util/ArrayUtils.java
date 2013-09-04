package jk.kamoru.util;

/**
 * commons.lang3.ArrayUtils 상속받아 필요한 기능 추가
 * @author kamoru
 *
 */
public class ArrayUtils extends org.apache.commons.lang3.ArrayUtils {

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
