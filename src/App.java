import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import ui.ManageBooksFrame;

public class App {
    public static void main(String[] args) {
        // Set the native Windows system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Launch the GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new ManageBooksFrame().setVisible(true);
        });
    }
}