import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.applet.Applet;

public class GUIElemente extends Frame implements WindowListener{

	public static void main(String[] args) {
		GUIElemente guiElemente = new GUIElemente();
		guiElemente.initialisiereGUI();
		// verbindungsdetailsAnfragen();
		// verbindungAufbauen(String , int);
		while (true) {
			// nachrichtAnzeigen(nachrichtDecodieren(streamEmpfangen()));
		}

	}

	public void initialisiereGUI() {
		addWindowListener(this);
		TextField textlefeldle = new TextField();
		textlefeldle.setBounds(180, 700, 500, 50);
		textlefeldle.setEditable(true);
		
		add(textlefeldle);

		setSize(800, 800);
		Button knoepfle = new Button("Senden");

		add(knoepfle);
		knoepfle.setBounds(700, 700, 80, 50);
		setLayout(null);
		setVisible(true);

	}

	public String[] verbindungsdetailsAnfragen() {

	}

	public void knoepfleDrueckt() {
		nachrichtSenden(nachrichtCodieren(String));
		// Textlefeldle läre
	}

	public void nachrichtAnzeigen() {

	}
	@Override
	public void windowClosing(WindowEvent arg0)
    {
      System.exit(0);                            
    }

	
	
}
