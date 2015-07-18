package sort;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;

/**
 * ソート実行クラス
 *
 * @author shibayama
 *
 */
public class Sort implements Comparator<String> {

	/**
	 * 引数で受け取った配列を昇順でソートする<br>
	 * ただし半角数値のみソート可能
	 *
	 * @param array半角数値のみの配列
	 * @return ソートされた配列
	 */
	public String[] sortArray(String[] array) {
		Arrays.sort(array, new Sort());
		return array;
	}

	@Override
	public int compare(String prev, String next) {
		BigDecimal prevNumber = new BigDecimal(prev);
		BigDecimal nextNumber = new BigDecimal(next);
		return prevNumber.compareTo(nextNumber);
	}
}
