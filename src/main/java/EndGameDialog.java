import util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndGameDialog extends JWindow {
    private JPanel panelMain;
    private JButton restartButton;
    private JLabel mainText;

    JFrame frameMain;

    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public EndGameDialog(ActionListener newGameAction, JFrame frameMain) {
        this.frameMain = frameMain;
        this.setContentPane(panelMain);
        this.pack();

        restartButton.addActionListener(actionEvent -> {
            try {
                // Вызов newGame() из FrameMain
                newGameAction.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "newGame"));
                this.setVisible(false);
                frameMain.setEnabled(true);
            } catch (Exception e) {
                SwingUtils.showErrorMessageBox(e);
            }
        });
    }

    public void showDialog(String dialogText, JFrame main) {
        mainText.setText(dialogText);
        this.setLocation(main.getLocation().x + main.getWidth() / 2 - this.getWidth() / 2, main.getLocation().y + main.getHeight() / 2 - this.getHeight() / 2);
        this.setVisible(true);
        this.setAlwaysOnTop(true);
    }

}
