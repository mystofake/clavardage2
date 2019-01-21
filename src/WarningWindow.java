
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class WarningWindow {

	  public static void ShowWarn(String Message) {
		    final JPanel panel = new JPanel();

		    JOptionPane.showMessageDialog(panel, Message, "Warning",
		        JOptionPane.WARNING_MESSAGE);
		  }
}
