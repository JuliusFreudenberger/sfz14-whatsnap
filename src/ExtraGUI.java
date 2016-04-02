import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class ExtraGUI {
	
	private boolean verbindungsfehlerGeschlossen;

	public String[] verbindungsdetails() {
		final String[] ipPort = new String[2];
		ipPort[0] = "";
		ipPort[1] = "";

		Frame frame = new Frame();
		frame.setTitle("Verbindungsdetails");
		frame.setSize(300, 100);
		frame.setLayout(new FlowLayout());

		Button bestaetigungsKnopf = new Button("Ok");

		Label ipLabel = new Label("IP des Servers", Label.CENTER);
		final TextField ipTextFeld = new TextField(15);

		Label portLabel = new Label("Port des Servers", Label.CENTER);
		final TextField portTextFeld = new TextField(5);

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
			if (ipPort[0].length() >= 1 && ipPort[1].length() >= 1) {
				frame.dispose();
				return ipPort;
			} else {
				try {
					Thread.sleep(0);
				} catch (InterruptedException e1) {
				}
			}
		}

	}

	public void verbindungsfehler() {
		verbindungsfehlerGeschlossen = false;
		final Frame frame = new Frame("Verbindung fehlgeschlagen!");
		frame.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {}

			@Override
			public void windowDeactivated(WindowEvent e) {}

			@Override
			public void windowClosing(WindowEvent e) {}

			@Override
			public void windowClosed(WindowEvent e) {
				verbindungsfehlerGeschlossen=true;
			}

			@Override
			public void windowActivated(WindowEvent e) {}
		});
		frame.setBounds(50, 50, 150, 80);
		frame.setAlwaysOnTop(true);

		Label label1 = new Label("Die Verbindung zum Server konnte nicht hergestellt werden!", Label.CENTER);
		Label label2 = new Label("Soll es erneut versucht werden? ('Nein' beendet das Programm!)", Label.CENTER);
		Button knopf1 = new Button("Ja");
		knopf1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});

		Button knopf2 = new Button("Nein");
		knopf2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				System.exit(0);
			}
		});
		frame.setLayout(new FlowLayout());
		frame.add(label1);
		frame.add(label2);
		frame.add(knopf1);
		frame.add(knopf2);

		frame.pack();
		frame.setVisible(true);

	}

	public boolean isVerbindungsfehlerGeschlossen() {
		return verbindungsfehlerGeschlossen;
	}

	public void setVerbindungsfehlerGeschlossen(boolean verbindungsfehlerGeschlossen) {
		this.verbindungsfehlerGeschlossen = verbindungsfehlerGeschlossen;
	}
}