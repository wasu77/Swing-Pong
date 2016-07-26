// Tworzę klasę główną, która będzie służyła do obsługi gry Pong

// importuję biblioteki odpowiedzialne za wyświetlanie GUI
// menedżer ukłądu border layout
import java.awt.BorderLayout;
// biblioteka swing - JFrame odpowiedzialna za wyświetlenie okna z aplikacją
import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {

		// Tworzę nowy obiekt JFRame i definiuję jego ukłąd jako Border LAyout (menedżer zarządzający)
			JFrame frame = new JFrame("Pong");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLayout(new BorderLayout());

		// Tworzę odniesienie do PongPanel (który jest jednocześnie osobną klasą)

			PongPanel pongPanel = new PongPanel();
			frame.add(pongPanel, BorderLayout.CENTER);
			// Będzie umieszczony w centrum JFrame (co jest logiczne ze względu na sposób w jaki przedstawione będize pole gry)

			frame.setSize(500, 500);
			frame.setVisible(true);

	}
}