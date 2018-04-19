import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.util.*;
import java.lang.String;
/**
 * PlayGame class sets up the game and displays it to the screen to play.
 *
 * @author (Patrick, Alissa, Chris, Brooke, Hieu)
 * @version (4/17/2018)
 */
public class PlayGame extends JPanel implements MouseListener
{
    private Image traincol, traincol2, traincol3, traincol4, traincol5, traincol6;
    private Image traincol7, traincol8, traincol9;
    private static Image board, blackBackground, TicketToRidePic;
    private Image blueDest, globeTrotter, orangeDest, trainCardBack;
    private JButton button1, button2;
    private static boolean playerChosen;
    protected static int numberOfPlayers;
    private static JFrame frame;
    private Deck trainCards;
    /**
     * Constructor for objects of class playGame
     */
    public PlayGame()
    {
        addMouseListener(this);
        createDeck();
        //String dir = "Images/";
        traincol = new ImageIcon("Images" + File.separator + "BlackCard.JPG").getImage();
        traincol2 = new ImageIcon("Images" + File.separator + "BlueCard.JPG").getImage();
        traincol3 = new ImageIcon("Images" + File.separator + "GreenCard.JPG").getImage();
        traincol4= new ImageIcon("Images" + File.separator + "OrangeCard.JPG").getImage();
        traincol5 = new ImageIcon("Images" + File.separator + "PurpleCard.JPG").getImage();
        traincol6 = new ImageIcon("Images" + File.separator + "RainbowCard.JPG").getImage();
        traincol7 = new ImageIcon("Images" + File.separator + "RedCard.JPG").getImage();
        traincol8 = new ImageIcon("Images" + File.separator + "WhiteCard.JPG").getImage();
        traincol9 = new ImageIcon("Images" + File.separator + "YellowCard.JPG").getImage();
        board = new ImageIcon("Images" + File.separator + "Board.JPG").getImage();
        blackBackground = new ImageIcon("Images" + File.separator + "blackBackground.JPG").getImage();
        TicketToRidePic = new ImageIcon("Images" + File.separator + "HomeScreen.JPG").getImage();
        blueDest = new ImageIcon("Images" + File.separator + "BlueDest.JPG").getImage();
        globeTrotter = new ImageIcon("Images" + File.separator + "Globetrotter.JPG").getImage();
        orangeDest = new ImageIcon("Images" + File.separator + "OrangeDest.JPG").getImage();
        trainCardBack = new ImageIcon("Images" + File.separator + "TrainCardBack.JPG").getImage();

        Dimension size = new Dimension(blackBackground.getWidth(null), board.getHeight(null));
        //width is ... pixels
        //height is ...
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);

        button1 = new JButton("Select Short Card");
        button1.setBounds(1100,590,190,20);
        add(button1);
        button1.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e) 
                { 
                    shortButtonPressed();
                }         
            });

        button2 = new JButton("Select Long Card");
        button2.setBounds(1100,620,190,20);
        add(button2);
        button2.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                { 
                    longButtonPressed();
                }         
            });

    }

    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        g.drawImage(blackBackground,0,0,null);
        g.drawImage(board,270,0,null);
        drawTrainCards(g);
        drawDestinationCards(g);
    }

    public void homeScreen(Graphics g)
    {
        g.drawImage(TicketToRidePic,0,0,null);
    }

    public void drawTrainCards(Graphics g)
    {   
        g.drawImage(trainCardBack,930,650,null);
        g.drawImage(trainCards.drawTrainCard().getPicture(),930,600,null);     
    }

    public void drawDestinationCards(Graphics g)
    {
        g.drawImage(blueDest,1100,650,null);
        g.drawImage(orangeDest,1200,650,null);
    }

    /**
     * Gives players train cards, gives them destination cards to choose from
     * lets them choose
     * @param g The graphics object 
     */
    private void setUpGame(Graphics g) 
    {

    }

    public static void playersChosen()
    {
        //repaint();
    }

    public void shortButtonPressed()
    {
        //draw a short destination card
    }

    public void longButtonPressed()
    {
        //draw a long destination card
    }

    /**
     * Create all the TrainCarCards and destination cards
     */
    private void createDeck()
    {
        trainCards = new Deck();
    }

    /**
     * @param e an event that indicates a mouse action has occured.
     */
    public void mouseEntered( MouseEvent e ) { }

    /**
     * @param e an event that indicates a mouse action has occured.
     */
    public void mouseExited( MouseEvent e ) { }

    /**
     * @param e an event that indicates a mouse action has occured.
     */
    public void mousePressed( MouseEvent e ) { }

    /**
     * @param e an event that indicates a mouse action has occured.
     */
    public void mouseReleased( MouseEvent e ) { }

    /**
     * @param e an event that indicates a mouse action has occured.
     */
    public void mouseClicked( MouseEvent e ) {
        //clickX = e.getX();
        //clickY = e.getY();
        //click = true;
        //repaint();
        //e.consume();
    }

    /**
     * Adds the bonus points to each player
     */
    public void addBonusPoints() 
    {

    }

    /**
     * ends the game and shows the scores
     */
    private void endGame() 
    {

    }

    protected static void createAndShowGUI() {
        //Create and set up the window.
        frame = new JFrame("Ticket To Ride");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        PlayGame panel = new PlayGame();
        frame.getContentPane().add(panel);
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    createAndShowGUI();
                }
            });
    }
}
