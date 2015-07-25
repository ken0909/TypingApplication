package action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;

/**
 * ファイルを読み込む
 *
 * @author shibayama
 *
 */
public class ReadDocument {

	/* 英単語csvのパス */
	private File englishFile = new File("resources/vocabulary.csv");

	private File programingFile = new File("resources/program.csv");

	/* ログ */
	private StringBuilder log;

	/**
	 * CSVファイルを読み込んでリストにして返す
	 *
	 * @param file
	 *            読み込むCSVファイル
	 * @return ファイルの中身
	 */
	public List<String> readCSVFile(File file) {
		List<String> vocabularies = new LinkedList<>();

		/* ファイル在しない場合その旨をログに書き出す */
		if (!file.exists()) {
			log = new StringBuilder();
			log.append("ファイルが存在しません [").append(file).append("]");
			WriteLogs.writeLog(log.toString());
		}

		/* ファイルを読み込み、空文字以外の文字列をリストに格納 */
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
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
		}
		return vocabularies;
	}

	/**
	 * 文章リストから文章を抜き出して文字列にして返す
	 *
	 * @return 文章
	 */
	public StringBuilder getSentence() {
		StringBuilder typingSentence = new StringBuilder();
		List<String> vocabularies = readCSVFile(programingFile);

		/* リストからランダムに単語を抽出し文章を生成 */
		for (int i = 0; i < vocabularies.size(); i++) {
			typingSentence.append(vocabularies.get(RandomUtils.nextInt(0, vocabularies.size())) + " ");
		}

		/* 文章を適度な長さで切る */
		if (typingSentence.length() > 400) {
			typingSentence.replace(0, typingSentence.length(), typingSentence.substring(0, 400));
		}

		WriteLogs.writeLog("Called Method [getSentence] class:ReadDocument");

		return typingSentence;
	}
}
