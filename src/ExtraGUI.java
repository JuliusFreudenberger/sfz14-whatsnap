import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class ExtraGUI {

	private boolean verbindungsfehlerGeschlossen;

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
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
			}

			@Override
			public void windowClosed(WindowEvent e) {
				verbindungsfehlerGeschlossen = true;
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}
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