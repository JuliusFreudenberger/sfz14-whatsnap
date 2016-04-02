import java.awt.*;
import java.awt.event.*;

public class HauptGUI extends Frame implements WindowListener {

	private TextArea nachrichtenTextArea;
	private TextField nachrichtenTextfeld;
	private boolean sendeNachricht = false;

	public static void main(String[] args) {
		HauptGUI hauptGUI = new HauptGUI();
		ExtraGUI extraGUI = new ExtraGUI();
		Nachrichtentransfer nachrichtentransfer = new Nachrichtentransfer();

		String[] ipPort;
		hauptGUI.initialisiereGUI();

		while(true) {
			ipPort = extraGUI.verbindungsdetails();

			int i = 0;
			while (nachrichtentransfer.verbindungAufbauen(ipPort[0], Integer.parseInt(ipPort[1])) == false && i <= 3) {
				System.out.println(
						"[Client] [WARNUNG] Verbindung zum " + (i + 1) + ". Mal fehlgeschlagen. Versuche erneut...");
				if (i >= 3) {
					extraGUI.verbindungsfehler();
				}
				i++;
			}
			if(i== 0) {
				break;
			}
			while(extraGUI.isVerbindungsfehlerGeschlossen() == false) {
				try {
					Thread.sleep(0);
				} catch (InterruptedException e) {}
			}
		}

			System.out.println("[Client] Connection established");

			while (true) {
				String empfangeneNachricht = nachrichtentransfer
						.nachrichtDecodieren(nachrichtentransfer.streamEmpfangen());
				/** Prueft, ob die empfangene Nachricht nicht leer ist. */
				
				if (empfangeneNachricht != null) {
					hauptGUI.nachrichtAnzeigen(empfangeneNachricht);
				}

				if (hauptGUI.isSendeNachricht()) {
					nachrichtentransfer.nachrichtSenden(hauptGUI.getNachrichtenTextfeld().getText());
					hauptGUI.getNachrichtenTextfeld().setText("");
					hauptGUI.setSendeNachricht(false);
				}
			}
		}

	public void initialisiereGUI() {
		addWindowListener(this);
		setTitle("WhatSnap - Hauptfenster");
		setSize(800, 800);

		nachrichtenTextArea = new TextArea();
		nachrichtenTextArea.setFocusable(false);
		nachrichtenTextArea.setEditable(false);
		nachrichtenTextArea.setBounds(180, 50, 500, 630);
		add(nachrichtenTextArea);

		nachrichtenTextfeld = new TextField();
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
					sendeNachricht = true;
				}
			}
		});

		add(nachrichtenTextfeld);

		Button sendenKnopf = new Button("Senden");

		sendenKnopf.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sendeNachricht = true;
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

	public void setSendeNachricht(boolean sendeNachricht) {
		this.sendeNachricht = sendeNachricht;
	}

	public TextField getNachrichtenTextfeld() {
		return nachrichtenTextfeld;
	}

	public boolean isSendeNachricht() {
		return sendeNachricht;
	}

}
