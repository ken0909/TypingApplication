package action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ログを書き出すクラス
 *
 * @author shibayama
 *
 */
public class WriteLogs {

	/* ログ書き出し先ファイル */
	private static File file = new File("resources/logs.log");

	/* ログ */
	private static StringBuilder log;

	/* ログ出力用日付 */
	private static String date;

	/**
	 * ログを書き出す
	 *
	 * @param log
	 *            ログメッセージ
	 */
	public static void writeLog(String log) {
		WriteLogs.log = new StringBuilder();
		date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
		WriteLogs.log.append("[").append(date).append("] ").append(log).append(System.lineSeparator());

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
			writer.write(WriteLogs.log.toString());
		} catch (IOException ignore) {
			// 無視
		}
	}
}
