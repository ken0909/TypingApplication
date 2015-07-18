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
	private File file = new File("resources/vocabulary.csv");

	/* ログ */
	private StringBuilder log;

	/**
	 * 英単語csvを読み込んで文章にして返す
	 *
	 * @return 英文
	 */
	public StringBuilder getSentence() {
		StringBuilder typingSentence = new StringBuilder();
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

			/* リストからランダムに単語を抽出し文章を生成 */
			for (int i = 0; i < vocabularies.size(); i++) {
				typingSentence.append(vocabularies.get(RandomUtils.nextInt(0, vocabularies.size())) + " ");
			}

			/* 文章を適度な長さで切る */
			if (typingSentence.length() > 1000) {
				typingSentence.replace(0, typingSentence.length(), typingSentence.substring(0, 1000));
			}
		} catch (IOException e) {

			/* 読み込み失敗したことをログに書き出す */
			log = new StringBuilder();
			log.append("ファイルの読み込みに失敗しました [").append(file).append("]");
		}
		return typingSentence;
	}
}
