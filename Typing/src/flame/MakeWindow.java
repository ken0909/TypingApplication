package flame;

import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import action.FileEnum;
import action.ReadDocument;
import action.WriteLogs;

/**
 * ウィンドウの設定
 *
 * @author shibayama
 *
 */
public class MakeWindow extends JFrame implements KeyListener, ActionListener {

	/* ヘッドライン用パネル */
	private JPanel head;

	/* ヘッドライン */
	private JLabel headLabel;

	/* スタートボタン */
	private Button startButton;

	/* 文章 */
	private String document;

	/* 文章格納コンポーネント */
	private JTextPane contents;

	/* 経過時間 */
	private JLabel time;

	/* ミス回数表示用ラベル */
	private JLabel missLabel;

	/* ミス回数 */
	private int miss;

	/* スタート時間 */
	private long start;

	/* 時間計測用オブジェクト */
	private MeasureTime measureTime;

	/* 現在のカーソルの位置 */
	private int cursor;

	/* キーイベント発動用フラグ */
	private boolean started;

	/* スタイル変更用オブジェクト */
	private DefaultStyledDocument doc;

	/* 結果表示用パネル */
	private JPanel result;

	/* 結果ラベル */
	private JLabel result1;

	/* 結果ラベル */
	private JLabel result2;

	/* 結果ラベル */
	private JLabel result3;

	/* 結果タイム用ラベル */
	private JLabel resultTime;

	/* 結果ミス回数用ラベル */
	private JLabel resultMiss;

	/* 経過時間 */
	private double elapsedTime;

	/* 時間の表示フォーマット */
	private DecimalFormat format;

	private JComboBox<String> gameMode;

	/**
	 * コンストラクタ ウィンドウの各種設定とコンポーネントの作成
	 */
	public MakeWindow() {
		super("タイピングアプリ");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 600);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setLayout(null);

		gameMode = new JComboBox<>(FileEnum.getPasses());
		gameMode.setBounds(100, 0, 600, 20);
		gameMode.addActionListener(this);
		this.add(gameMode);

		head = new JPanel();
		head.setBounds(50, 20, 300, 30);

		headLabel = new JLabel("Are You Ready?");
		headLabel.setFont(new Font("Arial", Font.ITALIC, 15));

		startButton = new Button("Go!");
		startButton.addActionListener(this);
		startButton.setFont(new Font("Arial", Font.ITALIC, 15));

		head.add(headLabel);
		head.add(startButton);
		this.add(head);

		contents = new JTextPane();
		contents.setEditable(false);
		contents.setFont(new Font("Arial", Font.BOLD, 30));
		contents.setBounds(0, 100, 800, 350);
		contents.addKeyListener(this);
		this.add(contents);

		time = new JLabel("-");
		time.setFont(new Font("Arial", Font.PLAIN, 18));
		time.setBounds(700, 500, 800, 20);
		this.add(time);

		miss = 0;
		missLabel = new JLabel("Miss:");
		missLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		missLabel.setForeground(Color.RED);
		missLabel.setBounds(100, 500, 100, 30);
		this.add(missLabel);

		result = new JPanel();
		result.setBounds(400, 20, 400, 60);

		result1 = new JLabel("Result   ");
		result1.setFont(new Font("Arial", Font.BOLD, 22));
		result1.setForeground(Color.BLUE);

		result2 = new JLabel("Time : ");
		result2.setFont(new Font("Arial", Font.BOLD, 22));
		result2.setForeground(Color.GREEN);

		resultTime = new JLabel();
		resultTime.setFont(new Font("Arial", Font.BOLD, 22));
		resultTime.setForeground(Color.GREEN);

		result3 = new JLabel("  Miss : ");
		result3.setFont(new Font("Arial", Font.BOLD, 22));
		result3.setForeground(Color.RED);

		resultMiss = new JLabel();
		resultMiss.setFont(new Font("Arial", Font.PLAIN, 22));
		resultMiss.setForeground(Color.RED);

		result.add(result1);
		result.add(result2);
		result.add(resultTime);
		result.add(result3);
		result.add(resultMiss);
		result.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.add(result);

		cursor = 0;
		started = false;
		format = new DecimalFormat("##0.00");

		StyleContext style = new StyleContext();
		doc = new DefaultStyledDocument(style);
		contents.setDocument(doc);

		addKeyListener(this);
		this.setFocusable(true);
		this.setVisible(true);

		WriteLogs.writeLog("アプリ起動");
	}

	/**
	 * ゲーム開始メソッド<br>
	 * スタートボタンを押すか、Enterキーを押すと呼び出される
	 */
	public void start() {
		miss = 0;
		cursor = 0;

		headLabel.setText("Started");
		contents.setText(document);
		resultTime.setText("");
		resultMiss.setText("");
		start = System.nanoTime();
		measureTime = new MeasureTime();
		new Timer().schedule(measureTime, 0, 50);
		started = true;
		WriteLogs.writeLog("ゲーム開始");
	}

	/**
	 * ゲームリセットメソッド<br>
	 * escキーを押すとゲームを中断する
	 */
	public void reset() {
		miss = 0;
		cursor = 0;

		headLabel.setText("Are You Ready?");
		measureTime.cancel();
		time.setText("-");
		missLabel.setText("Miss:");
		contents.setText("");
		started = false;
		WriteLogs.writeLog("ゲームリセット");
	}

	/**
	 * 文章の文字を開始位置から指定した文字数分赤色に変える
	 *
	 * @param doc-対象となる文章
	 * @param start-開始位置
	 * @param length-文字数
	 */
	public void changeColor(DefaultStyledDocument doc, int start, int length) {
		SimpleAttributeSet sas = new SimpleAttributeSet();
		StyleConstants.setForeground(sas, Color.RED);
		doc.setCharacterAttributes(start, length, sas, false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		document = new ReadDocument().getSentence(gameMode.getSelectedIndex());
		if (e.getSource() == startButton) {
			start();
		}
		contents.requestFocusInWindow();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		char key = e.getKeyChar();

		/* 開始フラグがtrueで、押されたキーがEnterでない場合 */
		if (started && key != KeyEvent.VK_ENTER) {

			/* カーソル現在位置の文字と押されたキーが一致した場合カーソルを右に移動し、一文字分赤色にする */
			if (document.charAt(cursor) == key) {
				changeColor(doc, cursor++, 1);

				/* ミスの場合はミスのカウントをインクリメントし表示する */
			} else {
				miss++;
				missLabel.setText("Miss: " + String.valueOf(miss));
			}
			/* ミスが１００を超えたら強制終了 */
			if (miss >= 100) {
				reset();
				WriteLogs.writeLog("ゲームオーバー");
			}

			/*
			 * 文章の最後まで行ったらゲーム終了 結果タイムとミス回数を表示する
			 */
			if (document.length() <= cursor) {
				StringBuilder resultLog = new StringBuilder();
				StringBuilder result = new StringBuilder();
				String time = format.format(elapsedTime);
				String miss = String.valueOf(this.miss);
				resultTime.setText(time);
				resultMiss.setText(miss);
				reset();
				resultLog.append("Mode:").append(gameMode.getSelectedItem()).append(" Time:").append(time)
						.append(" Miss:").append(miss);
				WriteLogs.writeLog("ゲーム終了");
				WriteLogs.writeResult(resultLog.toString());
				result.append(new ReadDocument().getRank(Double.parseDouble(time), gameMode.getSelectedIndex()))
						.append("位にランクイン！");
				JOptionPane.showMessageDialog(this, new JLabel(result.toString()));
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		/* ゲームが開始されていない状態でEnterキーが押されたらゲームスタート */
		if (!started && key == KeyEvent.VK_ENTER) {
			document = new ReadDocument().getSentence(gameMode.getSelectedIndex());
			start();
		}

		/* ゲームが開始されている状態でescキーが押されたらゲーム終了 */
		if (started && key == KeyEvent.VK_ESCAPE) {
			reset();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	/**
	 * 時間計測に関する内部クラス
	 *
	 * @author shibayama
	 *
	 */
	class MeasureTime extends TimerTask {

		@Override
		public void run() {

			/* 経過時間を測定しリアルタイムで表示 */
			long now = System.nanoTime();
			elapsedTime = (now - start) / 1000000000f;
			time.setText(format.format(elapsedTime));
		}
	}
}