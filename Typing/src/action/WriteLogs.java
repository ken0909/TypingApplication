package action;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
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
	private static URL logFile = WriteLogs.class.getClassLoader().getResource(("resources/logs.log"));

	/* 結果ログ出力先ファイル */
	private static URL resultFile = WriteLogs.class.getClassLoader().getResource(("resources/result.log"));

	/* ログ */
	private static StringBuilder log = new StringBuilder();

	/* ログ出力用日付フォーマット */
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	/**
	 * ログを書き出す
	 *
	 * @param log:ログメッセージ
	 */
	public static void writeLog(String log) {
		String date = dateFormat.format(new Date());
		WriteLogs.log.delete(0, WriteLogs.log.length());
		WriteLogs.log.append(date).append(" [INFO] ").append(log).append(System.lineSeparator());

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile.getPath(), true))) {
			writer.write(WriteLogs.log.toString());
		} catch (IOException ignore) {
			// 無視
		}
	}

	/**
	 * エラーログを書き出す
	 *
	 * @param log:エラーメッセージ
	 */
	public static void writeErrorLog(String log) {
		String date = dateFormat.format(new Date());
		WriteLogs.log.delete(0, WriteLogs.log.length());
		WriteLogs.log.append(date).append(" [ERROR] ").append(log).append(System.lineSeparator());

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile.getPath(), true))) {
			writer.write(WriteLogs.log.toString());
		} catch (IOException ignore) {
			// 無視
		}
	}

	/**
	 * 結果を書き出す
	 *
	 * @param result:結果
	 */
	public static void writeResult(String result) {
		String date = dateFormat.format(new Date());
		WriteLogs.log.delete(0, WriteLogs.log.length());
		WriteLogs.log.append(date).append(" [RESULT] ").append(result).append(System.lineSeparator());

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(resultFile.getPath(), true))) {
			writer.write(WriteLogs.log.toString());
		} catch (IOException ignore) {
			// 無視
		}
	}
}
