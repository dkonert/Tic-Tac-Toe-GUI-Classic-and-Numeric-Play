
package boardgame;
/**
 *
 */

        import javax.swing.JFrame;
        import javax.swing.JButton;
        import javax.swing.JPanel;
        import javax.swing.JLabel;
        import javax.swing.JOptionPane;
        import javax.swing.BoxLayout;
        import javax.swing.JMenuBar;
        import javax.swing.JMenu;
        import javax.swing.JMenuItem;
        import java.awt.BorderLayout;


/**
 *
 */
public class GUI extends JFrame {
    private JPanel gameContainer;
    private JLabel messageLabel;
    //private Player player1;
    //private Player player2;


    public GUI(String title){
        // call the superclass constructor
        super();
        // set the size, title and default close of the jframe
        this.setSize(WIDTH, HEIGHT);
        this.setTitle(title);
        gameContainer = new JPanel();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // make a new label to store messages

        add(gameContainer, BorderLayout.CENTER);
        add(makeButtonPanel(),BorderLayout.EAST);
        start();

    }
    private JPanel makeButtonPanel(){
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(makeTicTacToeButton());
        buttonPanel.add(makeNumericalTTTButton());
        return buttonPanel;
    }

    private JPanel startupMessage(){
        JPanel temp = new JPanel();
        temp.add(new JLabel("time to play some board games!"));
        return temp;

    }

    private JButton makeTicTacToeButton(){
        JButton button = new JButton("Play TicTacToe");
        button.addActionListener(e-> ticTacToe());
        return button;
    }

    private JButton makeNumericalTTTButton(){
        JButton button = new JButton("Play Numerical TicTacToe");
        button.addActionListener(e-> numericalTTT());
        return button;
    }




    public void start(){
        gameContainer.removeAll();
        gameContainer.add(startupMessage());
        getContentPane().repaint();
        getContentPane().revalidate();
        pack();
    }


    protected void ticTacToe(){
        gameContainer.removeAll();
        gameContainer.add(new TTTUI(this));
        getContentPane().repaint();
        getContentPane().revalidate();
        pack();
    }

    protected void numericalTTT(){
        gameContainer.removeAll();
        gameContainer.add(new NumericalGUI(this));
        getContentPane().repaint();
        getContentPane().revalidate();
        pack();

    }



    public static void main(String[] args){
        GUI example = new GUI("NxM Games");
        example.setVisible(true);


    }

}
