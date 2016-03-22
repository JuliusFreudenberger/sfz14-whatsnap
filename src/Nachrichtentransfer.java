import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringBufferInputStream;
import java.io.StringReader;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.stream.Stream;

public class Nachrichtentransfer {

	
	 public BufferedReader nachrichtCodieren(String msg){
		 BufferedReader nutzereingabe = new BufferedReader(new StringReader(msg));
		 return nutzereingabe;
	 
	  }
	
	public boolean verbindungAufbauen(String ip, int port) {

		Socket socket = new Socket();
		
		try {
			socket.connect(new InetSocketAddress(ip, port), 5*1000);
		} catch (IOException e) {
			return false;
		}
		
		return true;
	}
	/*
	 * public boolean nachrichtSenden(BufferedReader){
	 * 
	 * }
	 * 
	 * public BufferedReader streamEmpfangen(){
	 * 
	 * }
	 * 
	 * public String nachrichtDecodieren(BufferedReader ){
	 * 
	 * }
	 */
}
