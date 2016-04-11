import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Nachrichtentransfer {
	HauptGUI hauptGUI;

	private Socket socket = new Socket();
	private DataOutputStream outToServer;

	public Nachrichtentransfer(HauptGUI hauptGUI) {
		this.hauptGUI = hauptGUI;
	}

	public boolean verbindungAufbauen(String ip, int port) {

		try {
			socket.setSoTimeout(5 * 1000);
			socket.connect(new InetSocketAddress(ip, port), 5 * 1000);
			outToServer = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	public boolean nachrichtSenden(String nutzereingabe) {
		try {
			outToServer.writeBytes(nutzereingabe + '\n');
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	public BufferedReader streamEmpfangen() {
		BufferedReader inFromServer;
		try {
			inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			return new BufferedReader(new StringReader(""));
		}
		return inFromServer;
	}

	public String nachrichtDecodieren(BufferedReader serverEmpfangen) {
		String inFromServer;
		try {
			inFromServer = serverEmpfangen.readLine();
		} catch (IOException e) {
			return new String();
		}
		return inFromServer;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public Socket getSocket() {
		return socket;
	}

}
