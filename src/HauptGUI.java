import java.awt.Button;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HauptGUI extends Frame implements WindowListener {
	private static final long serialVersionUID = 1L;

	// Der Standard verwendete Port
	private final int defaultPort = 13784;

	private TextArea nachrichtenTextArea;
	private TextField nachrichtenTextfeld;
	private boolean sendeNachricht = false;

	// Zu sendende Nachricht-Puffer
	private String nachricht;

	// Empfangene Nachricht-Puffer
	private String empfangeneNachricht = "";

	public static void main(String[] args) {
		HauptGUI hauptGUI = new HauptGUI();
		ExtraGUI extraGUI = new ExtraGUI();
		Nachrichtentransfer nachrichtentransfer = new Nachrichtentransfer(hauptGUI);
		Verbindung verbindung = new Verbindung(hauptGUI, nachrichtentransfer);

		String[] ipPort;

		verbindung.initialisiereGUI();

		boolean erfolgreich = true;
		do {
			// warte auf geschlossenes Verbindungsdetails-Frame
			while (verbindung.isVerbindungsdetailsGeschlossen() == false) {
				try {
					Thread.sleep(0);
				} catch (InterruptedException e) {
				}
			}
			// Verbinden, wenn nicht schon verbunden
			if (nachrichtentransfer.getSocket().isConnected() == false) {
				ipPort = verbindung.getIpPort();
				erfolgreich = hauptGUI.initialisiereVerbindungsaufbau(nachrichtentransfer, extraGUI, ipPort);
			}
		} while (erfolgreich == false);

		verbindung.frameSchließen();

		hauptGUI.initialisiereGUI();
		nachrichtentransfer.outputStreamInitialisieren();

		Runnable empfangenRunnable = new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(0);
					} catch (InterruptedException e) {
					}
					hauptGUI.setEmpfangeneNachricht(
							nachrichtentransfer.nachrichtDecodieren(nachrichtentransfer.streamEmpfangen()));
				}
			}
		};

		Runnable sendenRunnable = new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(0);
					} catch (InterruptedException e) {
					}
					if (hauptGUI.isSendeNachricht()) {
						hauptGUI.nachrichtAnzeigen();
						nachrichtentransfer.nachrichtSenden(hauptGUI.getNachricht());
						hauptGUI.setSendeNachricht(false);
					}
				}
			}
		};

		Runnable empfangeneNachrichtAnzeigenRunnable = new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(0);
					} catch (InterruptedException e) {
					}

					if (hauptGUI.getEmpfangeneNachricht().length() > 1) {
						hauptGUI.nachrichtAnzeigen(hauptGUI.getEmpfangeneNachricht());
						hauptGUI.setEmpfangeneNachricht("");
					}
				}
			}
		};

		Thread empfangenThread = new Thread(empfangenRunnable, "Empfangen");
		Thread sendenThread = new Thread(sendenRunnable, "Senden");
		Thread empfangeneNachrichtAnzeigenThread = new Thread(empfangeneNachrichtAnzeigenRunnable,
				"Empfangene Nachricht Anzeigen");

		empfangenThread.start();
		sendenThread.start();
		empfangeneNachrichtAnzeigenThread.start();
	}

	public boolean initialisiereVerbindungsaufbau(Nachrichtentransfer nachrichtentransfer, ExtraGUI extraGUI,
			String[] ipPort) {
		int i = 0;
		// Versuche 3-mal zu verbinden
		while (nachrichtentransfer.verbindungAufbauen(ipPort[0], Integer.parseInt(ipPort[1])) == false && i <= 3) {
			if (i == 3) {
				extraGUI.verbindungsfehler();
				while (extraGUI.isVerbindungsfehlerGeschlossen() == false) {
					try {
						Thread.sleep(0);
					} catch (InterruptedException e) {
					}
				}
				return false;
			}
			i++;
		}
		return true;
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
					nachricht = nachrichtenTextfeld.getText();
					nachrichtenTextfeld.setText("");
				}
			}
		});

		add(nachrichtenTextfeld);

		Button sendenKnopf = new Button("Senden");

		sendenKnopf.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sendeNachricht = true;
				nachricht = nachrichtenTextfeld.getText();
				nachrichtenTextfeld.setText("");
			}
		});

		add(sendenKnopf);
		sendenKnopf.setBounds(700, 700, 80, 50);
		setLayout(null);
		setVisible(true);

	}

	public void nachrichtAnzeigen() {
		nachrichtenTextArea
				.setText(nachrichtenTextArea.getText() + "(DU) " + getFormattedTime() + ": " + nachricht + '\n');
	}

	public void nachrichtAnzeigen(String empfangeneNachricht) {
		nachrichtenTextArea
				.setText(nachrichtenTextArea.getText() + getFormattedTime() + ": " + empfangeneNachricht + '\n');
	}

	public String getFormattedTime() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(calendar.getTime());
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

	public String getNachricht() {
		return nachricht;
	}

	public int getDefaultPort() {
		return defaultPort;
	}

	public String getEmpfangeneNachricht() {
		return empfangeneNachricht;
	}

	public void setEmpfangeneNachricht(String empfangeneNachricht) {
		this.empfangeneNachricht = empfangeneNachricht;
	}
}
