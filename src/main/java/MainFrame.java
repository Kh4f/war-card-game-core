import util.*;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;

public class MainFrame extends JFrame {

    private JPanel panelMain;

    private JTable cardDeck1Table;
    private JTable cardDeck2Table;

    private JTable faceUpCards1Table;
    private JTable faceUpCards2Table;

    private JButton playCardButton;
    private JLabel player1Label;
    private JLabel player2Label;
    private JLabel currentMove;

    private Game game = new Game();
    private final EndGameDialog endGameDialog;

    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();


    public MainFrame() {
        this.setTitle("War");
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setJMenuBar(createMenuBar());
        this.pack();
        this.setLocation((screenSize.width - panelMain.getWidth()) / 2, (screenSize.height - panelMain.getHeight()) / 2);

        endGameDialog = new EndGameDialog(e -> newGame(), this);

        JTableUtils.initJTableForArray(cardDeck1Table, 60, false, false, false, false);
        JTableUtils.initJTableForArray(cardDeck2Table, 60, false, false, false, false);
        JTableUtils.initJTableForArray(faceUpCards1Table, 80, false, false, false, false);
        JTableUtils.initJTableForArray(faceUpCards2Table, 80, false, false, false, false);

        updateViewForGameOnMyQueue();

        JFileChooser fileChooserOpen = new JFileChooser();
        JFileChooser fileChooserSave = new JFileChooser();
        fileChooserOpen.setCurrentDirectory(new File("./src/Tests"));
        fileChooserSave.setCurrentDirectory(new File("."));
        FileFilter filter = new FileNameExtensionFilter("Text files", "txt");
        fileChooserOpen.addChoosableFileFilter(filter);
        fileChooserSave.addChoosableFileFilter(filter);
        fileChooserSave.setAcceptAllFileFilterUsed(false);
        fileChooserSave.setDialogType(JFileChooser.SAVE_DIALOG);
        fileChooserSave.setApproveButtonText("Save");

        playCardButton.addActionListener(actionEvent -> {
            try {
                makeMove();
                updateViewForGameOnMyQueue();
            } catch (Exception e) {
                SwingUtils.showErrorMessageBox(e);
            }
        });
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new MainFrame().setVisible(true));
    }

    private void makeMove() {
        game.makeMove();
    }

    private void updateViewForGameOnMyQueue() {
        currentMove.setText("Move: " + game.getCurrentMove());

        writeStringCardsArrToJTable(cardDeck1Table, game.fromCardMyQueueToStringArray(game.getCardDeck1()));
        writeStringCardsArrToJTable(cardDeck2Table, game.fromCardMyQueueToStringArray(game.getCardDeck2()));
        writeStringCardsArrToJTable(faceUpCards1Table, game.fromCardListToStringArray(game.getFaceUpCards1()));
        writeStringCardsArrToJTable(faceUpCards2Table, game.fromCardListToStringArray(game.getFaceUpCards2()));

        int moveWinner = game.getMoveWinner();
        if (moveWinner == 1) {
            player1Label.setText("<html>⠀⠀⠀✔<br>PLAYER 1</html>");
            player2Label.setText("<html>⠀⠀⠀✖<br>PLAYER 2</html>");
        } else if (moveWinner == 2) {
            player1Label.setText("<html>⠀⠀⠀✖<br>PLAYER 1</html>");
            player2Label.setText("<html>⠀⠀⠀✔<br>PLAYER 2</html>");
        } else if (game.isDispute()) {
            player1Label.setText("<html>⠀⠀⠀⚔️<br>PLAYER 1</html>");
            player2Label.setText("<html>⠀⠀⠀⚔️<br>PLAYER 2</html>");
        } else {
            player1Label.setText("<html>⠀⠀⠀—<br>PLAYER 1</html>");
            player2Label.setText("<html>⠀⠀⠀—<br>PLAYER 2</html>");
        }

        if (game.isDraw() || (game.getGameWinner() == 1) || (game.getGameWinner() == 2)) {
            String dialogText = "";
            if (game.isDraw()) {
                dialogText = "DRAW";
            } else if (game.getGameWinner() == 1) {
                dialogText = "Winner: PLAYER 1";
            } else if (game.getGameWinner() == 2) {
                dialogText = "Winner: PLAYER 2";
            }
            endGameDialog.showDialog(dialogText, this);
            this.setEnabled(false);
        }
    }

    private JMenuItem createMenuItem(String text, String shortcut, ActionListener listener) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.addActionListener(listener);
        if (shortcut != null) {
            menuItem.setAccelerator(KeyStroke.getKeyStroke(shortcut.replace('+', ' ')));
        }
        return menuItem;
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBarMain = new JMenuBar();
        JMenu menuGame = new JMenu("Game options");
        menuBarMain.add(menuGame);
        menuGame.add(createMenuItem("New game", "ctrl+N", e -> {
            newGame();
        }));
        menuGame.addSeparator();
        menuGame.add(createMenuItem("Exit", "ctrl+X", e -> {
            System.exit(0);
        }));
        JMenu menuHelp = new JMenu("Info");
        menuBarMain.add(menuHelp);
        menuHelp.add(createMenuItem("Rules", "ctrl+R", e -> {
            JOptionPane.showMessageDialog(this, "Колода раздаётся поровну всем игрокам. Каждый ход игроки снимают верхнюю карту \nиз своей стопки и кладут её в центр стола в открытом виде. Тот игрок, чья карта оказалась \nстарше всех остальных, снимает свою и «битые» карты и кладёт их в низ своей стопки.\nИгрок, потерявший все свои карты, выбывает из игры. Победителем считается игрок, \nв стопке у которого окажется вся колода.\n\nЕсли у двух и более игроков окажутся одинаковые карты, то каждый из этих игроков \nкладет сверху ещё по одной карте, и тот, чья карта оказалась старше всех остальных, \nснимает карты.", "Rules", JOptionPane.QUESTION_MESSAGE);
        }));
        menuHelp.add(createMenuItem("About this program", "ctrl+A", e -> {
            SwingUtils.showInfoMessageBox(
                    """
                            Название игры: Пьяница (War)
                            Описание: карточная игра на Java Swing
                            Автор: Гуров А.О. (ВГУ, 2 курс ФКН, группа 10.1 )
                            E-mail: khafg050@gmail.com
                            """,
                    "О программе"
            );
        }));

        return menuBarMain;
    }

    private void newGame() {
        game = new Game();
        updateViewForGameOnMyQueue();
    }

    private void writeStringCardsArrToJTable(JTable table, String[] arr) {
        String[][] tempArr = new String[arr.length][1];
        int i = 0;
        for (String string : arr) {
            tempArr[i++][0] = string;
        }
        JTableUtils.writeArrayToJTable(table, tempArr);
    }
}

