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
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;

public class Verbindung extends Frame implements WindowListener {
	private static final long serialVersionUID = 1L;
	private Nachrichtentransfer nachrichtentransfer;
	private HauptGUI hauptGUI;
	private ReentrantLock lock = new ReentrantLock();

	private Socket socket;
	private final String[] ipPort = new String[2];
	private boolean verbindungsdetailsGeschlossen = false;
	private boolean verbunden = false;
	private Thread serverThread;

	public Verbindung(HauptGUI hauptGUI, Nachrichtentransfer nachrichtentransfer) {
		this.nachrichtentransfer = nachrichtentransfer;
		this.hauptGUI = hauptGUI;
	}

	public void initialisiereGUI() {
		serverThread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (verbunden == false) {
					aufVerbindungWarten();
				}
			}
		});
		serverThread.start();

		ipPort[0] = "";
		ipPort[1] = "";

		this.setTitle("Verbindungsdetails");
		this.setSize(300, 100);
		this.setLayout(new FlowLayout());

		Button bestaetigungsKnopf = new Button("Ok");

		Label ipLabel = new Label("IP des Servers", Label.CENTER);
		final TextField ipTextFeld = new TextField(15);

		Label portLabel = new Label("Port des Servers", Label.CENTER);
		final TextField portTextFeld = new TextField(Integer.toString(hauptGUI.getDefaultPort()), 5);

		this.add(ipLabel);
		this.add(ipTextFeld);

		this.add(portLabel);
		this.add(portTextFeld);

		this.add(bestaetigungsKnopf);

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

		this.addWindowListener(this);
		this.setAlwaysOnTop(true);
		this.setVisible(true);

		while (true) {
			if (ipPort[0].length() >= 1 && ipPort[1].length() >= 1) {
				serverThread.interrupt();
				break;
			} else {
				try {
					Thread.sleep(0);
				} catch (InterruptedException e) {
				}
			}
			if (verbunden == true) {
				break;
			}
		}
		frameSchlieﬂen();
	}

	// wartet auf Verbindung von anderen Clients
	public void aufVerbindungWarten() {
		lock.lock();
		ServerSocket serversocket;
		try {
			serversocket = new ServerSocket(hauptGUI.getDefaultPort());
			serversocket.setSoTimeout(2 * 1000);
			socket = serversocket.accept();
			serversocket.close();
			if (socket.isConnected()) {
				nachrichtentransfer.setSocket(socket);
				frameSchlieﬂen();
			}
		} catch (IOException e) {
		}
		lock.unlock();
	}

	public void frameSchlieﬂen() {
		this.dispose();
		serverThread.interrupt();
		verbunden = true;
		verbindungsdetailsGeschlossen = true;
	}

	public String[] getIpPort() {
		return ipPort;
	}

	public boolean isVerbindungsdetailsGeschlossen() {
		return verbindungsdetailsGeschlossen;
	}

	public Socket getSocket() {
		return socket;
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}
}
