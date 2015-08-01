package action;

import java.util.Vector;

public enum FileEnum {
	ENGLISH_WORD(0, "resources/vocabulary.csv", "英単語モード"), PROGRAMING_WORD(1, "resources/program.csv", "プログラミング単語モード");

	private int number;

	private String pass;

	private String mode;

	private FileEnum(int number, String pass, String mode) {
		this.number = number;
		this.pass = pass;
		this.mode = mode;
	}

	public int getNumber() {
		return number;
	}

	public String getPass() {
		return pass;
	}

	public String getMode() {
		return mode;
	}

	public static Vector<String> getPasses() {
		Vector<String> passes = new Vector<>();
		for (FileEnum enums : values()) {
			passes.add(enums.getMode());
		}
		return passes;
	}

	public static String getPassByNumber(int number) {
		String pass = null;
		for (FileEnum enums : values()) {
			if (number == enums.getNumber()) {
				pass = enums.getPass();
			}
		}
		return pass;
	}

	public static int getNumberByMode(String mode) {
		int number = 0;
		for (FileEnum enums : values()) {
			if (mode.equals(enums.getMode())) {
				number = enums.getNumber();
			}
		}
		return number;
	}
}
