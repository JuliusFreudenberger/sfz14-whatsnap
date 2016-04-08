import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Verbindung extends Frame {
	private Socket socket;
	private final String[] ipPort = new String[2];
	private boolean verbindungsdetailsGeschlossen = false;
	private boolean verbunden = false;

	public void verbindungsdetails() {
		Thread serverThread = new Thread(new Runnable() {

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
		final TextField portTextFeld = new TextField(5);

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

		this.setAlwaysOnTop(true);
		this.setVisible(true);

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

		while (verbunden == false) {
			if (ipPort[0].length() >= 1 && ipPort[1].length() >= 1) {
				frameSchlieﬂen();
				serverThread.interrupt();
				return;
			} else {
				try {
					Thread.sleep(0);
				} catch (InterruptedException e1) {
				}
			}
		}
	}

	// wartet auf Verbindung von anderen Clients
	public void aufVerbindungWarten() {
		ServerSocket serversocket;
		try {
			//System.out.println("Warte");
			serversocket = new ServerSocket(13784);
			serversocket.setSoTimeout(2 * 1000);
			socket = serversocket.accept();
			serversocket.close();
		if(socket.isConnected()) {
			System.out.println("Verbunden!");
			frameSchlieﬂen();
		}
		} catch (IOException e) {
		}
	}
	
	public void frameSchlieﬂen() {
		this.dispose();
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
}
