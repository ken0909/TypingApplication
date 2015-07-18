package flame;

import java.awt.Button;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import action.ReadDocument;
import action.WriteLogs;

/**
 * ウィンドウの設定
 *
 * @author shibayama
 *
 */
public class MakeWindow extends JFrame implements ActionListener, KeyListener {

	/* ヘッドライン */
	private JLabel headLabel;

	/* スタートボタン */
	private Button startButton;

	/* 文章 */
	private JTextArea contents;

	/* 経過時間 */
	private JLabel time;

	/* スタート時間 */
	private long start;

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

		headLabel = new JLabel("Are You Ready?");
		headLabel.setFont(new Font("Arial", Font.ITALIC, 15));
		headLabel.setBounds(2, 0, 110, 30);
		this.add(headLabel);

		startButton = new Button("Go!");
		startButton.addActionListener(this);
		startButton.setFont(new Font("Arial", Font.ITALIC, 15));
		startButton.setBounds(110, 0, 50, 30);
		this.add(startButton);

		contents = new JTextArea(new ReadDocument().getSentence().toString());
		contents.setLineWrap(true);
		contents.setEditable(false);
		contents.setFont(new Font("Arial", Font.BOLD, 22));
		contents.setBounds(0, 70, 800, 400);
		this.add(contents);

		time = new JLabel("-");
		time.setFont(new Font("Arial", Font.PLAIN, 18));
		time.setBounds(700, 500, 800, 20);
		this.add(time);

		this.setVisible(true);

		WriteLogs.writeLog("アプリ起動");
	}

	/**
	 * ゲーム開始メソッド<br>
	 * スタートボタンを押すか、Enterキーを押すと呼び出される
	 */
	public void start() {
		headLabel.setText("Started");
		start = System.nanoTime();
		new Timer().schedule(new MeasureTime(), 0, 50);

		WriteLogs.writeLog("ゲーム開始");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		start();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		char key = e.getKeyChar();
		if (key == KeyEvent.VK_ESCAPE) {
			WriteLogs.writeLog("ゲームリセット");
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		char key = e.getKeyChar();
		if (key == KeyEvent.VK_ESCAPE) {
			WriteLogs.writeLog("ゲームリセット");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	/**
	 * 経過時間を測定しリアルタイムで表示する内部クラス
	 *
	 * @author shibayama
	 *
	 */
	class MeasureTime extends TimerTask {

		/* 時間の表示フォーマット */
		private DecimalFormat format = new DecimalFormat("##0.00");

		@Override
		public void run() {
			long now = System.nanoTime();
			double elapsedTime = (now - start) / 1000000000f;
			time.setText(format.format(elapsedTime));
		}
	}
}