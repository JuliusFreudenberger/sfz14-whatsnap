import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ExtraGUI {

	public String[] verbindungsdetails() {
		String[] ipPort = new String[2];
		ipPort[0] = "";
		ipPort[1] = "";

		Frame frame = new Frame();
		frame.setTitle("Verbinungsdetails");
		// frame.setTitle("Verbindungsdetails");
		frame.setSize(300, 100);
		frame.setLayout(new FlowLayout());

		Button bestaetigungsKnopf = new Button("Ok");

		Label ipLabel = new Label("IP des Servers", Label.CENTER);
		TextField ipTextFeld = new TextField(15);

		Label portLabel = new Label("Port des Servers", Label.CENTER);
		TextField portTextFeld = new TextField(5);

		frame.add(ipLabel);
		frame.add(ipTextFeld);

		frame.add(portLabel);
		frame.add(portTextFeld);

		frame.add(bestaetigungsKnopf);

		KeyListener keyListener = new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					ipPort[0] = ipTextFeld.getText();
					ipPort[1] = portTextFeld.getText();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

		};

		frame.setAlwaysOnTop(true);
		frame.setVisible(true);

		ActionListener actionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ipPort[0] = ipTextFeld.getText();
				ipPort[1] = portTextFeld.getText();

			}
		};

		bestaetigungsKnopf.addActionListener(actionListener);
		ipTextFeld.addKeyListener(keyListener);
		portTextFeld.addKeyListener(keyListener);

		while (true) {
			// System.out.println(ipPort[0].length());
			if (ipPort[0].length() >= 1 && ipPort[1].length() >= 1) {
				frame.dispose();
				return ipPort;
			} else {
				System.out.print("");
			}
		}

	}

	public void verbindungsfehler(int i) {
		Frame frame = new Frame("Verbindung fehlgeschlagen!");

		Label label = new Label("Die Verbindung zum Server konnte nicht hergestellt werden!", Label.CENTER);
		Button knopf = new Button("Ok");
		knopf.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				return;
			}
		});

		frame.add(label);
		frame.add(knopf);
		frame.setLayout(new FlowLayout());
		frame.pack();
		frame.setVisible(true);

	}
}