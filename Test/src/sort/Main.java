package sort;

import java.util.Arrays;

/**
 * メインクラス
 *
 * @author shibayama
 *
 */
public class Main {

	/**
	 * コマンドライン引数を元にして昇順でソートして表示する
	 *
	 * @param argsコマンドライン引数
	 */
	public static void main(String[] args) {

		/* 引数は２つ以上 */
		if (args.length < 2) {
			System.out.println("引数を二つ以上指定してください");
			System.exit(1);
		}

		/* 半角数字以外の入力はNG */
		for (String str : args) {
			if (str.matches("[^0-9]")) {
				System.out.println("引数は半角数字のみ指定してください");
				System.exit(1);
			}
		}

		/* 入力時されたコマンドライン引数 */
		System.out.println("before : " + Arrays.asList(args));

		/* ソートした結果 */
		System.out.println("result : " + Arrays.asList(new Sort().sortArray(args)));
	}
}
