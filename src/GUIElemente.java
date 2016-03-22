import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class GUIElemente extends Frame implements WindowListener {

	public static void main(String[] args) {
		GUIElemente guiElemente = new GUIElemente();
		ExtraGUI extraGUI = new ExtraGUI();
		Nachrichtentransfer nachrichtentransfer = new Nachrichtentransfer();
		guiElemente.initialisiereGUI();

		String[] ipPort = extraGUI.verbindungsdetails();

		int i = 0;
		while (nachrichtentransfer.verbindungAufbauen(ipPort[0], Integer.parseInt(ipPort[1])) == false && i <= 3) {
			System.out.println("[Client] [WARNUNG] Verbindung zum " + (i+1) + ". Mal fehlgeschlagen. Versuche erneut...");
			if (i >= 3) {
				extraGUI.verbindungsfehler(i);
			}
			i++;
		}
		System.out.println("[Client] Connection established");
		// nachrichtentransfer.nachrichtCodieren();

		while (true) {
			// nachrichtAnzeigen(nachrichtDecodieren(streamEmpfangen()));
			break;
		}

	}

	public void initialisiereGUI() {
		addWindowListener(this);
		setTitle("WhatSnap - Hauptfenster");
		setSize(800, 800);

		TextField nachrichtenTextfeld = new TextField();
		nachrichtenTextfeld.setBounds(180, 700, 500, 50);
		nachrichtenTextfeld.setEditable(true);

		add(nachrichtenTextfeld);

		Button sendenKnopf = new Button("Senden");

		add(sendenKnopf);
		sendenKnopf.setBounds(700, 700, 80, 50);
		setLayout(null);
		setVisible(true);

	}

	/*
	 * public void onKnopfDruck() { nachrichtSenden(nachrichtCodieren(String));
	 * // TODO TextFeld leeren }
	 */

	public void nachrichtAnzeigen() {

	}

	

	@Override
	public void windowClosing(WindowEvent e) {
		e.getWindow().dispose();
		System.exit(0);
	}

	@Override
	public void windowActivated(WindowEvent arg0) {}

	@Override
	public void windowClosed(WindowEvent arg0) {}

	@Override
	public void windowDeactivated(WindowEvent arg0) {}

	@Override
	public void windowDeiconified(WindowEvent arg0) {}

	@Override
	public void windowIconified(WindowEvent arg0) {}

	@Override
	public void windowOpened(WindowEvent arg0) {}

}
