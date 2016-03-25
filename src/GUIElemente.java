import java.awt.*;
import java.awt.event.*;

public class GUIElemente extends Frame implements WindowListener {

	private TextArea nachrichtenTextArea;
	
	public static void main(String[] args) {
		GUIElemente guiElemente = new GUIElemente();
		ExtraGUI extraGUI = new ExtraGUI();
		Nachrichtentransfer nachrichtentransfer = new Nachrichtentransfer();
		guiElemente.initialisiereGUI(nachrichtentransfer);

		String[] ipPort = extraGUI.verbindungsdetails();

		int i = 0;
		while (nachrichtentransfer.verbindungAufbauen(ipPort[0], Integer.parseInt(ipPort[1])) == false && i <= 3) {
			System.out.println(
					"[Client] [WARNUNG] Verbindung zum " + (i + 1) + ". Mal fehlgeschlagen. Versuche erneut...");
			if (i >= 3) {
				extraGUI.verbindungsfehler(i);
			}
			i++;
		}
		System.out.println("[Client] Connection established");

		while (true) {
			guiElemente
					.nachrichtAnzeigen(nachrichtentransfer.nachrichtDecodieren(nachrichtentransfer.streamEmpfangen()));

			try {
				Thread.sleep(5 * 1000);
			} catch (InterruptedException e) {
			}
		}
	}

	public void initialisiereGUI(Nachrichtentransfer nachrichtentransfer) {
		addWindowListener(this);
		setTitle("WhatSnap - Hauptfenster");
		setSize(800, 800);

		nachrichtenTextArea = new TextArea();
		nachrichtenTextArea.setFocusable(false);
		nachrichtenTextArea.setEditable(false);
		nachrichtenTextArea.setBounds(180, 50, 500, 630);
		add(nachrichtenTextArea);

		TextField nachrichtenTextfeld = new TextField();
		nachrichtenTextfeld.setBounds(180, 700, 500, 50);
		nachrichtenTextfeld.setEditable(true);
		nachrichtenTextfeld.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					nachrichtentransfer.nachrichtSenden(nachrichtenTextfeld.getText());
					nachrichtenTextfeld.setText("");
				}
			}
		});

		add(nachrichtenTextfeld);

		Button sendenKnopf = new Button("Senden");

		sendenKnopf.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				nachrichtentransfer.nachrichtSenden(nachrichtenTextfeld.getText());
				nachrichtenTextfeld.setText("");
			}
		});

		add(sendenKnopf);
		sendenKnopf.setBounds(700, 700, 80, 50);
		setLayout(null);
		setVisible(true);

	}

	public void nachrichtAnzeigen(String nachricht) {
		System.out.println(nachricht);
		nachrichtenTextArea.setText(nachrichtenTextArea.getText() + nachricht + '\n');
	}

	@Override
	public void windowClosing(WindowEvent e) {
		e.getWindow().dispose();
		System.exit(0);
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
	}

}
