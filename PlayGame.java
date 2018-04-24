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
 * @version (4/18/2018)
 */
public class PlayGame extends JPanel implements MouseListener, MouseMotionListener
{
    private static Image boardPic, blackBackground, TicketToRidePic;
    private Image blueDest, globeTrotter, orangeDest, trainCardBack;
    private boolean secondClick, choosingTrainCard;
    protected static int numberOfPlayers;
    // initializes the players
    private ArrayList<Player> players = new ArrayList<Player>();
    protected static Player currentPlayer;
    private static JFrame frame;
    private Deck deck;
    private Board board;
    private JLabel viewDestCards;
    private ImageIcon destCard;
    /**
     * Constructor for objects of class playGame
     */
    public PlayGame()
    {
        players = Driver.getPlayers();
        currentPlayer = players.get(0);
        addMouseListener(this);
        addMouseMotionListener(this);
        deck = new Deck();
        board = new Board();
        boardPic = new ImageIcon("Images" + File.separator + "Board.JPG").getImage();
        blackBackground = new ImageIcon("Images" + File.separator + "blackBackground.JPG").getImage();
        TicketToRidePic = new ImageIcon("Images" + File.separator + "HomeScreen.JPG").getImage();
        destCard =  new ImageIcon("Images" + File.separator + "BlueDest.JPG");
        blueDest = destCard.getImage();
        globeTrotter = new ImageIcon("Images" + File.separator + "Globetrotter.JPG").getImage();
        orangeDest = new ImageIcon("Images" + File.separator + "OrangeDest.JPG").getImage();
        trainCardBack = new ImageIcon("Images" + File.separator + "TrainCardBack.JPG").getImage();
        Dimension size = new Dimension(blackBackground.getWidth(null), boardPic.getHeight(null));
        //width is ... pixels
        //height is ...
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);
        dealDestinationCards();
        drawFirstFour();

        // JButton button = new JButton("Click Me");
        // button.setBounds(50,700,50,50);
        // button.addActionListener(new ActionListener() { 
        // public void actionPerformed(ActionEvent e) { 
        // Graphics.setColor(Color.BLUE);

        // for (Route meh: board.routes) {
        // if ( meh.routeShape != null) {
        // g.drawPolygon(meh.routeShape);
        // }
        // }
        // } 
        // });
    }

    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        g.drawImage(blackBackground,0,0,null);
        g.drawImage(boardPic,270,0,null);
        paintTrainCards(g);
        paintDestinationCards(g);
        paintPlayerInfo(g);
        
        paintDestinationCardBack(g);
        g.setColor(Color.BLUE);

        for (Route meh: board.routes) {
            if ( meh.routeShape != null) {
                g.fillPolygon(meh.routeShape);
            }
        }
        paintPlayerRoutes(g);
    }

    public void paintDestinationCardBack(Graphics g)
    {
        g.drawImage(blueDest,50,700,null);
        Font font = new Font("Verdana", Font.BOLD, 20);
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString("View your", 50,670);
        g.drawString("destination cards", 50,690);
    }

    public void paintPlayerRoutes(Graphics g) {
        for (Player p: players) {
            g.setColor(p.color);
            for (Route r: p.controlledRoutes) {
                g.fillPolygon(r.routeShape);
            }
        }
    }

    public void homeScreen(Graphics g)
    {
        g.drawImage(TicketToRidePic,0,0,null);
    }

    public void paintTrainCards(Graphics g)
    {   
        int x1 = 905;
        int y1 = 620;
        //draw face down train card
        if (deck.trainCards.size() + deck.discardedTrainCards.size() > 0) {
            g.drawImage(trainCardBack,x1,y1, null);
        }
        y1 -=100;
        //draw the face up train cards
        for (int i=0; i<5; i++) {
            if (i < deck.faceUpTrainCards.size()) {
                //x1 y1 to begin is 905,520
                g.drawImage(deck.faceUpTrainCards.get(i).getPicture(),x1,y1,null);
                //decrements by 100 for the next card
                y1 -= 100;
            }
        }

        //draw the 9 card images on player side
        int x = 10;
        int y = 50;
        for (Image pic: deck.trainCardPics) {
            g.drawImage(pic,x,y,null);
            y += 55;
            x += 10;
        }

        //when deck is empty bad things happen
    }

    public void paintDestinationCards(Graphics g)
    {
        if (!deck.shortCards.isEmpty()) {
            g.drawImage(blueDest,905,720,null);
        }
        if (!deck.longCards.isEmpty()) {
            g.drawImage(orangeDest,905,820,null);
        }
    }

    public void paintPlayerInfo(Graphics g)
    {
        Font font = new Font("Verdana", Font.BOLD, 20);
        g.setFont(font);
        g.setColor(Color.WHITE);

        int x = 905;
        int y = 50;

        g.drawString("Player Scores", x,22);
        for(Player p : players) {
            g.setColor(p.color);
            g.drawString(p.name + ": "+ p.score, x,y);
            y+=25;
        }

        g.setColor(currentPlayer.color);
        g.drawString(currentPlayer.name + " has " + currentPlayer.carsRemaining + " train pieces", 10,22);

        ////to do: display meeple counts and option to see their destination cards

        //draw the 9 card images on player side
        int x2 = 73;
        int y2 = 77;
        for (int count: currentPlayer.trainCounts) {
            g.setColor(new Color(25, 25, 25));
            g.fillRect(x2, y2, 24, 24);
            g.setColor(Color.white);
            g.drawRect(x2, y2, 24, 24);
            g.drawString("" + count, x2+6, y2+20);
            y2 += 55;
            x2 += 10;
        }

    }

    public Player getCurrentPlayer()
    {
        return currentPlayer;   
    }

    /**
     * Fills all players' hands with
     * 4 train cards at the beginning of game. 
     */
    public void drawFirstFour()
    {
        // deals out the 4 cards from the draw deck
        for(Player p : players)
        {
            for(int i = 0; i < 4; i++)
            {
                //add a train card to the players hand
                p.addTrainCard(deck.drawTrainCard());
            }
        }   
    }

    protected void chooseDestinationCards(Player p, int keep){
        // if (deck.shortCards.size() + deck.longCards.size() <4) {
        // JOptionPane.showMessageDialog(null,"There are only " + "shortCards.size()";
        // return;
        // }

        /////////TO DO: add functionality for player to choose which combo of cards to take///////////

        JOptionPane.showMessageDialog(null,"Dealing " +
            p.name+" destination tickets!");
        // players options for their destination cards
        DestinationCard[] cards = new DestinationCard[4];
        // adds three destination cards
        for(int k = 0; k < 3; k++)
        {
            cards[k] = deck.drawShortCard();
        }
        // adds one long destination card
        cards[3] = deck.drawLongCard();
        // check boxes for the players options
        JCheckBox[] boxes = new JCheckBox[4];
        // creates a list of options in a JOption pain
        for(int i = 0; i < 4; i++)
        {
            boxes[i] = new JCheckBox(cards[i].toString() + 
                " for " + cards[i].getPoints() + " points");
        }   

        int count = 0;
        // makes sure at least 2 cards are chosen
        do 
        {
            count = 0;
            JOptionPane.showMessageDialog(null,boxes,"Pick your "
                +"destinations", JOptionPane.QUESTION_MESSAGE);
            for(int n = 0; n < 4; n++)
            {
                if(boxes[n].isSelected())
                {
                    count++;
                }
            }
        } while(count < keep);

        for(int j = 0; j < 4; j++)
        {
            if(boxes[j].isSelected())
            {
                p.addDestinationCard(cards[j]);
            }
            //what is this next line????
            //else if(!boxes[j].isSelected() && j != 3)
            else
            {
                if(cards[j].getPoints()<=11)
                {
                    deck.addShortCard(cards[j]);
                }
                else deck.addLongCard(cards[j]);
            }
        }

    }

    /**
     * Gives players destination cards to choose from 
     */
    public void dealDestinationCards() 
    {
        // for each player, display their destination card options and 
        // have them pick at least 2
        for(Player p : players)
        {
            chooseDestinationCards(p, 2);
        }       
    }

    protected void claimRoute (Route route) {
        ////////////////To do:///////////////
        //if route already taken then no
        //ask user which cards to use
        //if valid card combo: claim the route, dispose of the cards, and end player's turn
        //if invalid combo tell them no and let them try again
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
    public void mouseMoved( MouseEvent e ) { 
        ////////////////To do:///////////////
        //check if mouse location is within boundaries of a city
        //if so paint a little box for the city info like name and meeples
    }

    /**
     * @param e an event that indicates a mouse action has occured.
     */
    public void mouseDragged( MouseEvent e ) { }

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
        choosingTrainCard = false;
        ////////////////To do:///////////////
        //check if clicked on a route (see bottom of method)

        //check if clicked on a train card in deck
        if(e.getX() >=905 && e.getX() <= 1055 && e.getY() >= 620 && e.getY() <= 710) {
            if (deck.trainCards.size() + deck.discardedTrainCards.size() > 0) {
                currentPlayer.addTrainCard(deck.drawTrainCard());
                if(secondClick) {
                    nextPlayer();
                }
                secondClick = !secondClick;
                repaint();
            }
            return;
        }

        int cardIndex = 0;
        //x and y of the bottom most, face up train card
        if(e.getX() >=905 && e.getX() <= 1055 && e.getY() >= 520 && e.getY() <= 610 && deck.faceUpTrainCards.size() > 0) {
            cardIndex = 0;
            choosingTrainCard = true;
        }

        //x and y of the 2nd, face up train card
        if(e.getX() >=905 && e.getX() <= 1055 && e.getY() >= 420 && e.getY() <= 510 && deck.faceUpTrainCards.size() > 1)  {
            cardIndex = 1;
            choosingTrainCard = true;
        }

        if(e.getX() >=905 && e.getX() <= 1055 && e.getY() >= 320 && e.getY() <= 410 && deck.faceUpTrainCards.size() > 2)  {
            cardIndex = 2;
            choosingTrainCard = true;
        }

        if(e.getX() >=905 && e.getX() <= 1055 && e.getY() >= 220 && e.getY() <= 310 && deck.faceUpTrainCards.size() > 3)  {
            cardIndex = 3;
            choosingTrainCard = true;
        }

        if(e.getX() >=905 && e.getX() <= 1055 && e.getY() >=120 && e.getY() <= 210 && deck.faceUpTrainCards.size() > 4)  {
            cardIndex = 4;
            choosingTrainCard = true;
        }

        if (choosingTrainCard) {
            if (deck.faceUpTrainCards.get(cardIndex).isRainbow()) {
                if (secondClick) {
                    //reject trying to take rainbow card on second click
                    return;
                }
                else {
                    //can't choose a second card now so set secondclick to true
                    secondClick = true;
                }
            }

            currentPlayer.addTrainCard(deck.drawFaceupCard(cardIndex));

            if(secondClick) {
                nextPlayer();   
            }

            secondClick = !secondClick;
            repaint();
            return;
        }

        //check if clicked on destination card deck
        //in all these cases after stuff happens we move to next player in list
        //check if trying to see their own destination cards

        if (e.getX() >= 905 && e.getX() <=1055 && e.getY() >= 720 && e.getY() <= 910 ) {
            if(!secondClick) {
                chooseDestinationCards(currentPlayer, 1);
                nextPlayer();
                repaint();
                return;
            }
        }

        for (Route possibleR: board.routes) {
            if (possibleR.containsMouse(e.getX(), e.getY()) && currentPlayer.canTakeRoute(possibleR)) {
                ///////////// TO DO: add in all the stuff for choosing which cards to use/////////

                //deck.discardTrainCard(currentPlayer.removeTrainCard(the index)) for each card
                currentPlayer.addRoute(possibleR);
                //////////// TO DO: let player choose meeples
                nextPlayer();
                repaint();
                return;
            }
        }

        if(e.getX() >=50 && e.getX() <=200 && e.getY() >=700 && e.getY()<=790)
        {
            DestinationCardPanel.createAndShowGUI();
        }
        //nextPlayer();
        //repaint();
    }

    protected void nextPlayer() {
        int index = players.indexOf(currentPlayer);
        if (index == players.size()-1)
            currentPlayer = players.get(0);
        else 
            currentPlayer = players.get(index+1);
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
