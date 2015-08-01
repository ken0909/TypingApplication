package action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * ファイルを読み込む
 *
 * @author shibayama
 *
 */
public class ReadDocument {

	/* ログ */
	private StringBuilder log;

	/**
	 * CSVファイルを読み込んでリストにして返す
	 *
	 * @param file:読み込むCSVファイル
	 * @return ファイルの中身
	 */
	public List<String> readCSVFile(String file) {
		List<String> vocabularies = new LinkedList<>();
		URL filePath = this.getClass().getClassLoader().getResource(file);

		/* ファイル在しない場合その旨をログに書き出す */
		if (!new File(file).exists()) {
			log = new StringBuilder();
			log.append("ファイルが存在しません [").append(file).append("]");
			WriteLogs.writeErrorLog(log.toString());
		}

		/* ファイルを読み込み、空文字以外の文字列をリストに格納 */
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath.getFile()))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] words = line.split(",");
				for (String word : words) {
					if (!word.equals("")) {
						vocabularies.add(word);
					}
				}
			}
			WriteLogs.writeLog("Called Method [readCSVFile] class:ReadDocument");
		} catch (IOException e) {

			/* 読み込み失敗したことをログに書き出す */
			log = new StringBuilder();
			log.append("ファイルの読み込みに失敗しました [").append(file).append("]");
			WriteLogs.writeErrorLog(log.toString());
		}
		return vocabularies;
	}

	/**
	 * 文章リストから文章を抜き出して文字列にして返す
	 *
	 * @return 文章
	 */
	public String getSentence(int modeNumber) {
		StringBuilder typingSentence = new StringBuilder();
		List<String> vocabularies = readCSVFile(FileEnum.getPassByNumber(modeNumber));

		/* リストからランダムに単語を抽出し文章を生成 */
		for (int i = 0; i < vocabularies.size(); i++) {
			typingSentence.append(vocabularies.get(new Random().nextInt(vocabularies.size()))).append(" ");
		}

		/* 文章を適度な長さで切る */
		if (typingSentence.length() > 400) {
			typingSentence.replace(0, typingSentence.length(), typingSentence.substring(0, 400));
		}

		WriteLogs.writeLog("Called Method [getSentence] class:ReadDocument");

		return typingSentence.toString();
	}

	public int getRank(Double time, int gameMode) {
		int rank = 0;
		List<Double> results = new LinkedList<>();
		try (BufferedReader reader = new BufferedReader(
				new FileReader(this.getClass().getClassLoader().getResource("resources/result.log").getFile()))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] infos = line.split(" ");
				for (String info : infos) {
					if (info.contains("Mode")) {
						if (FileEnum.getNumberByMode(info.substring(5)) != gameMode) {
							break;
						}
					}
					if (info.contains("Time")) {
						results.add(Double.parseDouble(info.substring(5)));
					}
				}
			}
			Collections.sort(results);
			rank = results.indexOf(time) + 1;
		} catch (IOException e) {

			/* 読み込み失敗したことをログに書き出す */
			log = new StringBuilder();
			log.append("ファイルの読み込みに失敗しました [resources/result.log]");
			WriteLogs.writeErrorLog(log.toString());
		}
		return rank;
	}
}
