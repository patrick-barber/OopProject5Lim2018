import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*; //Question: needed?
import java.util.*;
import java.lang.String;
/**
 * PlayGame class sets up the game and displays it to the screen to play.
 *
 * @author Alissa Ronca, Patrick Baraber, Brooke Hossley,
 * Hieu Le, Chris Adams
 * @version Spring 2018
 */
public class PlayGame extends JPanel implements MouseListener, 
MouseMotionListener, ActionListener
{
    // initializes the players
    protected int numberOfPlayers;
    protected ArrayList<Player> players = new ArrayList<Player>();
    protected Player currentPlayer;
    //Images and JFrame 
    private static Image boardPic, blackBackground, TicketToRidePic;
    private static JFrame frame;
    private Image shortDest, globeTrotter, longDest, trainCardBack;
    private Image yellowMeeple, greenMeeple, blueMeeple, whiteMeeple;
    private Image blackMeeple, redMeeple, meeplePics[];
    //Game components 
    private boolean secondClick, choosingTrainCard, finalTurn;
    private Deck deck;
    private Board board;
    private JLabel viewDestCards;
    private ImageIcon destCard;
    private JButton helpButton;

    private City hoverCity;
    private int hoverX, hoverY;
    /**
     * Default constructor for the PlayGame class
     */
    public PlayGame()
    {
        //Getting the number of players for the game
        players = Driver.getPlayers();
        numberOfPlayers = players.size();
        currentPlayer = players.get(0);
        //Creating the deck and board used for the game
        deck = new Deck();
        board = new Board();

        //Load in images
        boardPic = new ImageIcon("Images" + File.separator 
            + "Board.JPG").getImage();
        blackBackground = new ImageIcon("Images" + File.separator 
            + "blackBackground.JPG").getImage();
        TicketToRidePic = new ImageIcon("Images" + File.separator 
            + "HomeScreen.JPG").getImage();
        destCard =  new ImageIcon("Images" + File.separator 
            + "BlueDest.JPG");
        shortDest = destCard.getImage();
        globeTrotter = new ImageIcon("Images" + File.separator 
            + "Globetrotter.JPG").getImage();
        longDest = new ImageIcon("Images" + File.separator 
            + "OrangeDest.JPG").getImage();
        trainCardBack = new ImageIcon("Images" + File.separator 
            + "TrainCardBack.JPG").getImage();

        blackMeeple = new ImageIcon("Images" + File.separator 
            + "blackMeeple.png").getImage();
        redMeeple = new ImageIcon("Images" + File.separator 
            + "redMeeple.png").getImage();
        greenMeeple = new ImageIcon("Images" + File.separator 
            + "greenMeeple.png").getImage();
        whiteMeeple = new ImageIcon("Images" + File.separator 
            + "whiteMeeple.png").getImage();
        blueMeeple = new ImageIcon("Images" + File.separator 
            + "blueMeeple.png").getImage();
        yellowMeeple = new ImageIcon("Images" + File.separator 
            + "yellowMeeple.png").getImage();
        Image[] meeplePics = {redMeeple, blackMeeple, greenMeeple, 
                yellowMeeple, blueMeeple, whiteMeeple};
        this.meeplePics = meeplePics;

        //Setting up window size
        Dimension size = new Dimension(blackBackground.getWidth(null), 
                boardPic.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);
        //Game set up
        dealDestinationCards();
        drawFirstFour();
        //Adding the help button to the board
        helpButton = new JButton("Help");
        helpButton.setFont(new Font("Arial", Font.BOLD, 20));
        helpButton.setBackground(new Color(51, 205, 200));
        helpButton.setBorder(BorderFactory.createLineBorder(Color.black));
        helpButton.setBounds(87, 835, 100, 50);
        add(helpButton);
        helpButton.addActionListener(this);
        helpButton.setActionCommand("Help");
        //Enabeling the mouse
        addMouseListener(this);
        addMouseMotionListener(this);

    }

    /**
     * Called when actionListeners are triggered
     * 
     * @param e The action calling the event
     */
    public void actionPerformed(ActionEvent e)
    {
        String action = e.getActionCommand();
        if(action.equals("Help"))
        {
            Rules.createAndShowGUI();
        }   
    }

    /**
     * Panel's paint method to manage the most of the graphics
     * 
     * @param g The Graphics reference
     */
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        g.drawImage(blackBackground, 0, 0, null);
        g.drawImage(boardPic, 270, 0, null);
        paintTrainCards(g);
        paintDestinationCardsFront(g);
        paintPlayerInfo(g);
        paintDestinationCardBack(g);
        paintPlayerRoutes(g);
        paintHoverCity(g);
        paintMeepleCount(g);
    }

    /**
     * Panel's paint method to paint player's meeple info
     * 
     * Question: Merge the paint classes?
     * 
     * @param g The Graphics reference 
     */
    public void paintMeepleCount(Graphics g)
    {
        int a = 12;
        int b = 620;
        for (Image pic: meeplePics) {
            g.drawImage(pic, a, b, null);
            a += 41;
        }

        a = 22;
        b += 20;
        Font font = new Font("Arial", Font.BOLD, 15);
        g.setFont(font);
        for(int i = 0 ; i < 6; i++)
        {
            int num = currentPlayer.meeples[i];
            g.setColor(new Color(25, 25, 25));
            g.fillRect(a, b, 20, 18);
            g.setColor(Color.white);
            g.drawRect(a, b, 20, 18);
            if (num > 9)
                g.drawString("" + num, a + 2, b + 15);
            else 
                g.drawString("" + num, a + 7, b + 15);
            a+=41;
        }
    }

    /**
     * Panel's paint method to paint the info box 
     * when hovering over a city
     * 
     * Question: Merge the paint classes?
     * 
     * @param g The Graphics reference 
     */
    protected void paintHoverCity(Graphics g)
    {
        if (hoverCity == null) return;
        int x1 = hoverX + 10;
        int y1 = hoverY - 20;
        boolean hasMeeples = false;

        Font font1 = new Font("Verdana", Font.BOLD, 15);
        g.setFont(font1);
        g.setColor(new Color(170, 190, 240));
        g.fillRect(x1, y1, 120, 50);
        g.setColor(Color.BLACK);
        g.drawRect(x1, y1, 120, 50);
        x1 += 5;

        g.drawString(hoverCity.name, x1, y1 + 20);
        y1 += 42;

        Font font2 = new Font("Verdana", Font.BOLD, 12);
        g.setFont(font2);
        g.drawString("Meeples: ", x1, y1);
        x1 += 58;

        for (int index = 0; index < 6; index++ ) 
        {
            int count = hoverCity.meeples[index]; 
            if (count > 0) 
            {
                if (index == 0) g.setColor(Color.red);
                else if (index == 1) g.setColor(Color.black);
                else if (index == 2) g.setColor (new Color(0, 150, 20));
                else if (index == 3) g.setColor (Color.yellow);
                else if (index == 4) g.setColor (new Color(0, 20, 220));
                else g.setColor (Color.white);
                g.drawString("" + count, x1, y1);
                x1 += 10;
                hasMeeples = true;
            }
        }
        if (!hasMeeples) {
            g.drawString(" none", x1, y1);
        }
    }

    /**
     * Panel's paint method to paint the back of the destination 
     * cards
     * 
     * Question: Merge the paint classes?
     * 
     * @param g The Graphics reference 
     */
    protected void paintDestinationCardBack(Graphics g)
    {
        g.drawImage(shortDest, 60, 720, null);
        Font font = new Font("Verdana", Font.BOLD, 20);
        g.setFont(font);
        g.setColor(Color.white);
        g.drawString("View my cards", 50, 710);
    }

    /**
     * Panel's paint method for the front of the destination cards
     * 
     * @param g The Graphics reference
     */
    protected void paintDestinationCardsFront(Graphics g)
    {
        if (!deck.shortCards.isEmpty()) {
            g.drawImage(shortDest,905,720,null);
        }
        if (!deck.longCards.isEmpty()) {
            g.drawImage(longDest,905,820,null);
        }
    }

    /**
     * Panel's paint method to paint each players claimed route
     * 
     * @param g The Graphics reference 
     */
    protected void paintPlayerRoutes(Graphics g) 
    {
        for (Player p : players) 
        {
            g.setColor(p.color);
            for (Route r : p.controlledRoutes) 
            {
                g.fillPolygon(r.routeShape);
            }
        }
    }

    /**
     * Panel's paint method to paint the train cards
     * 
     * @param g The Graphics reference 
     */
    protected void paintTrainCards(Graphics g)
    {   
        int x = 905;
        int y = 620;
        //Draw face down train card
        if (deck.trainCards.size() + deck.discardedTrainCards.size() > 0) 
        {
            g.drawImage(trainCardBack, x, y, null);
        }
        y -= 100;
        //Draw the face up train cards
        for (int i = 0; i < 5; i++) 
        {
            if (i < deck.faceUpTrainCards.size()) 
            {
                g.drawImage(deck.faceUpTrainCards.get(i).getPicture(), 
                    x, y, null);
                y -= 100;
            }
        }
        //Draw the train cards
        x = 15;
        y = 50;
        for (Image pic : deck.trainCardPics) 
        {
            g.drawImage(pic, x, y, null);
            y += 55;
            x += 10;
        }
    }

    /**
     * Panel's paint method for the Player information
     * 
     * @param g The Graphics reference
     */
    protected void paintPlayerInfo(Graphics g)
    {
        Font font = new Font("Verdana", Font.BOLD, 20);
        g.setFont(font);
        g.setColor(Color.WHITE);

        int x = 905;
        int y = 52;

        g.drawString("Player Scores", x, 24);
        for(Player p : players) 
        {
            g.setColor(p.color);
            g.drawString(p.name + ": "+ p.score, x, y);
            y += 25;

        }

        //Draw the train card counts the player has
        x = 75;
        y = 77;
        for (int count: currentPlayer.trainCounts) 
        {
            g.setColor(new Color(25, 25, 25));
            g.fillRect(x, y, 32, 24);
            g.setColor(Color.white);
            g.drawRect(x, y, 32, 24);
            if (count > 9)
                g.drawString("" + count, x + 2, y + 20);
            else 
                g.drawString("" + count, x + 10, y + 20);
            y += 55;
            x += 10;
        }

        g.setFont(new Font("Verdana", Font.BOLD, 23));
        g.setColor(currentPlayer.color);
        g.drawString(currentPlayer.name + ": " + 
            currentPlayer.carsRemaining + " train pieces", 10, 32);
    }

    /**
     * Method to return the current player
     * 
     * @return The current player
     */
    protected Player getCurrentPlayer()
    {
        return currentPlayer;   
    }

    /**
     * Method that fills all players' hands with
     * 4 train cards at the beginning of game
     */
    protected void drawFirstFour()
    {
        //Deals out the 4 cards from the draw deck
        for(Player p : players)
        {
            for(int i = 0; i < 4; i++)
            {
                //Add a train card to the players hand
                p.addTrainCard(deck.drawTrainCard());
            }
        }   
    }

    /**
     * Method to choose destination cards
     * 
     * @param p The player choosing the destination card
     * @param keep How many destination tickets the player is keeping
     */
    protected boolean chooseDestinationCards(Player p, int keep){
        /*////////TO DO: add functionality for player 
        to choose which combo of cards to take//////////*/
        //Question: Progress?
        boolean validCombo = false;
        int numShort = deck.shortCards.size();
        int numLong = deck.longCards.size();
        int otherColorIndex = 0;
        boolean takeAll = numShort + numLong <= 4;
        int maxShort = Math.min(4, numShort);
        int maxLong = Math.min(4, numLong);

        while (!takeAll && !validCombo) {
            SpinnerNumberModel shortModel = 
                new SpinnerNumberModel(maxShort, 0, maxShort, 1);
            JSpinner shortSpinner = new JSpinner(shortModel);

            SpinnerNumberModel longModel = 
                new SpinnerNumberModel(0, 0, maxLong, 1);
            JSpinner longSpinner = new JSpinner(longModel);

            Object[] message = {
                    "Number of short cards:", shortSpinner,
                    "Number of long cards:", longSpinner};

            int option = JOptionPane.showOptionDialog(null, message, 
                    "Choose 4 cards to claim ", JOptionPane.OK_CANCEL_OPTION, 
                    JOptionPane.QUESTION_MESSAGE, null, null, null);

            if (option == JOptionPane.CANCEL_OPTION)
            {
                return false;
            }

            numShort = (int) shortSpinner.getValue();
            numLong = (int) longSpinner.getValue();

            validCombo = numShort + numLong == 4;
        }        

        JOptionPane.showMessageDialog(null, "Dealing " +
            p.name + "'s destination tickets!");
        //Players options for their destination cards (3 short and 1 long)
        DestinationCard[] cards = new DestinationCard[4];
        int k = 0;
        //Adds short destination cards
        for(k = 0; k < numShort; k++) 
        {
            cards[k] = deck.drawShortCard();
        }
        //Adds long destination cards
        for(int j = 0; j < numLong; j++) 
        {
            cards[j + k] = deck.drawLongCard();
        } 

        //Check boxes for the players options
        JCheckBox[] boxes = new JCheckBox[4];
        //Creates a list of options in a JOption pain
        for(int i = 0; i < 4; i++) 
        {
            boxes[i] = new JCheckBox(cards[i].toString() + 
                " for " + cards[i].getPoints() + " points");
        }   

        //Count for numbers of check boxes clicked
        int count = 0;
        //Makes sure at least "keep" parameter cards are chosen
        do {
            count = 0;
            JOptionPane.showMessageDialog(null, boxes, "Pick your "
                + "destinations", JOptionPane.QUESTION_MESSAGE);
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
            else 
            {
                if(cards[j].getPoints() <= 11)
                {
                    deck.addShortCard(cards[j]);
                }
                else deck.addLongCard(cards[j]);
            }
        }
        return true;
    }

    /**
     * Gives players destination cards to choose from 
     */
    protected void dealDestinationCards() 
    {
        /*For each player, display their destination card options and 
        have them pick at least 2 */
        for(Player p : players)
        {
            chooseDestinationCards(p, 2);
        }       
    }

    /**
     * Method for taking meeples during route claiming
     * 
     * @param route The route being claimed
     */
    protected void claimMeepleDialogue(Route route) 
    {
        ////////////////To do://///////////// Question: ?
        int numRainbowsToRemove = 0;
        int numOtherToRemove = 0;
        int otherColorIndex = 0;
        for (City city: route.twoCities) 
        {
            String[] meepleOptions = city.getMeepleColors();
            if (meepleOptions.length > 1) 
            {
                JComboBox cardColor = new JComboBox(meepleOptions);

                Object[] message = {"Meeple Color:", cardColor};

                int option = JOptionPane.showOptionDialog(null, message,
                        "Choose meeple to claim from " + city.name, 
                        JOptionPane.DEFAULT_OPTION, 
                        JOptionPane.QUESTION_MESSAGE, null, null, null);

                String meepleColor = (String) cardColor.getSelectedItem();
                currentPlayer.meeples[city.discardMeeple(meepleColor)]++;
            }
            else if (meepleOptions.length == 1) 
            {
                String meepleColor = meepleOptions[0];
                currentPlayer.meeples[city.discardMeeple(meepleColor)]++;
            }
        }
    }

    /**
     * Method for claiming routes
     * 
     * @param route The route being claimed
     */
    protected boolean claimRouteDialogue(Route route) 
    {
        ////////////////To do://///////////// Question: ?
        ArrayList<Integer> cardsToRemove = new ArrayList();
        boolean validCombo = false;
        int numRainbowsToRemove = 0;
        int numOtherToRemove = 0;
        int otherColorIndex = 0;
        while (!validCombo) {
            SpinnerNumberModel numberSpinnerModel = 
                new SpinnerNumberModel(0, 0, currentPlayer.trainCounts[8], 1);
            JSpinner numberSpinner = new JSpinner(numberSpinnerModel);

            String[] colorOptions = route.getCardColors();
            JComboBox cardColor = new JComboBox(colorOptions);

            Object[] message = {
                    "Number of Locomotives:", numberSpinner,
                    "Other Card Color:", cardColor};

            int option = JOptionPane.showOptionDialog(null, message, 
                    "Choose Cards to claim "+ route.toString(), 
                    JOptionPane.OK_CANCEL_OPTION, 
                    JOptionPane.QUESTION_MESSAGE, null, null, null);

            if (option == JOptionPane.CANCEL_OPTION)
            {
                return false;
            }

            numRainbowsToRemove = (int) numberSpinner.getValue();
            String colorRemove = (String) cardColor.getSelectedItem();
            numOtherToRemove = route.length - numRainbowsToRemove;
            otherColorIndex = getColorIndex(colorRemove);
            validCombo = numOtherToRemove >= 0 && numOtherToRemove 
            <= currentPlayer.trainCounts[otherColorIndex];
        }
        for (int i = 0; i < numRainbowsToRemove; i++) {
            cardsToRemove.add(8);
        }
        for (int i = 0; i < numOtherToRemove; i++) {
            cardsToRemove.add(otherColorIndex);
        }

        //if valid card combo: dispose of the cards
        for (int index: cardsToRemove) {
            deck.discardTrainCard(currentPlayer.removeTrainCard(index));
        }
        return true;
    }

    protected int getColorIndex(String strColor) {
        switch (strColor) {
            case "Yellow": return 0;
            case "Blue": return 1;
            case "Green": return 2;
            case "Pink": return 3;
            case "Red": return 4;
            case "Black": return 5;
            case "Orange": return 6;
            case "White": return 7;
        }
        return 0;
    }

    public void mouseEntered(MouseEvent e) { }

    public void mouseExited(MouseEvent e) { }

    public void mouseDragged( MouseEvent e ) { }

    public void mousePressed( MouseEvent e ) { }

    public void mouseReleased( MouseEvent e ) { }

    /**
     * Method checking for mouse movement to create a hovering box
     * over cities
     * 
     * @param e an event that indicates a mouse action has occured.
     */
    public void mouseMoved(MouseEvent e) 
    { 
        ////////////////To do:///////////////
        //check if mouse location is within boundaries of a city
        //if so paint a little box for the city info like name and meeples
        hoverCity = null;
        for (City c : board.allCities.values()) 
        {
            if (c.cityShape != null && 
            c.cityShape.contains(e.getX(), e.getY())) 
            {
                hoverCity = c;
                hoverX = e.getX();
                hoverY = e.getY();
                break;
            }
        }
        repaint();
    }

    /**
     * Method that deals with mouse clicking 
     * 
     * @param e an event that indicates a mouse action has occured.
     */
    public void mouseClicked(MouseEvent e) 
    {
        choosingTrainCard = false;
        //Check if clicked on a train card in the deck
        if(e.getX() >= 905 && e.getX() <= 1055 && e.getY() >= 620 && 
        e.getY() <= 710) 
        {
            if (deck.trainCards.size() + 
            deck.discardedTrainCards.size() > 0) 
            {
                TrainCard cardDrawn = deck.drawTrainCard();
                currentPlayer.addTrainCard(cardDrawn);
                JOptionPane.showMessageDialog(null, "You drew " + 
                    cardDrawn.toString() + " card.");
                if(secondClick) 
                {
                    repaint();
                    nextPlayer();
                }
                secondClick =! secondClick;
                repaint();
            }
            return;
        }

        int cardIndex = 0;
        //x and y of the bottom most, face up train card
        if(e.getX() >= 905 && e.getX() <= 1055 && e.getY() >= 520 && 
        e.getY() <= 610 && deck.faceUpTrainCards.size() > 0) 
        {
            cardIndex = 0;
            choosingTrainCard = true;
        }
        //x and y of the 2nd, face up train card
        if(e.getX() >= 905 && e.getX() <= 1055 && e.getY() >= 420 && 
        e.getY() <= 510 && deck.faceUpTrainCards.size() > 1)  
        {
            cardIndex = 1;
            choosingTrainCard = true;
        }
        //x and y of the 3rd, face up train card
        if(e.getX() >= 905 && e.getX() <= 1055 && e.getY() >= 320 && 
        e.getY() <= 410 && deck.faceUpTrainCards.size() > 2)  
        {
            cardIndex = 2;
            choosingTrainCard = true;
        }
        //x and y of the 4th, face up train card
        if(e.getX() >= 905 && e.getX() <= 1055 && e.getY() >= 220 && 
        e.getY() <= 310 && deck.faceUpTrainCards.size() > 3)  
        {
            cardIndex = 3;
            choosingTrainCard = true;
        }
        //x and y of the 5th, face up train card
        if(e.getX() >= 905 && e.getX() <= 1055 && e.getY() >=120 && 
        e.getY() <= 210 && deck.faceUpTrainCards.size() > 4)  
        {
            cardIndex = 4;
            choosingTrainCard = true;
        }

        if (choosingTrainCard) 
        {
            if (deck.onlyRainbows(cardIndex))
            {
                if (!secondClick) 
                {
                    //Can't choose a second card now so set secondclick to true
                    secondClick = true;
                }
            }
            else if(deck.faceUpTrainCards.get(cardIndex).isRainbow()) 
            {
                if (secondClick) 
                {
                    //Reject trying to take rainbow card on second click
                    return;
                }
                else 
                {
                    //Can't choose a second card now so set secondclick to true
                    secondClick = true;
                }
            }
            currentPlayer.addTrainCard(deck.drawFaceupCard(cardIndex));
            if(secondClick) 
            {
                nextPlayer();   
            }
            secondClick =! secondClick;
            repaint();
            return;
        }

        //Check if clicked on destination card deck
        /*/in all these cases after stuff happens we move 
        to next player in list Question: how to fix??*/
        //Check if trying to see their own destination cards
        if(!secondClick) 
        {
            if(e.getX() >= 905 && e.getX() <= 1055 && 
            e.getY() >= 720 && e.getY() <= 910 ) 
            {
                if (deck.shortCards.size() + deck.longCards.size() > 0) 
                {
                    if (!chooseDestinationCards(currentPlayer, 1)) return;
                    nextPlayer();
                    repaint();
                    return;
                }
            }
            for (Route possibleR : board.routes) 
            {
                if(possibleR.containsMouse(e.getX(), e.getY()) && 
                currentPlayer.canTakeRoute(possibleR)) 
                {
                    if (!claimRouteDialogue(possibleR)) return;
                    currentPlayer.addRoute(possibleR);
                    claimMeepleDialogue(possibleR);
                    nextPlayer();
                    repaint();
                    return;
                }
            }
        }

        if(e.getX() >=60 && e.getX() <= 210 && 
        e.getY() >= 720 && e.getY() <= 810)
        {
            DestinationCardPanel.createAndShowGUI(currentPlayer);
        }
    }

    /**
     * Method that causes the move to the next player if 
     * the game isn't over, or calls endGame if it is
     */
    protected void nextPlayer() 
    {
        if(currentPlayer.isLastTurn) 
        {
            endGame();
            return;
        }
        else if(currentPlayer.carsRemaining <= 3) 
        {
            currentPlayer.isLastTurn = true;
            finalTurn = true;
        }
        int index = players.indexOf(currentPlayer);
        if (index == players.size()-1)
            currentPlayer = players.get(0);
        else 
            currentPlayer = players.get(index+1);

        if (finalTurn) 
        {
            JOptionPane.showMessageDialog(null, "FINAL ROUND: It is now " +
                currentPlayer.name + "'s FINAL turn.");
        }
        else {
            JOptionPane.showMessageDialog(null, "It is now " +
                currentPlayer.name + "'s turn.");
        }
    }

    /**
     * Adds the bonus points to each player
     */
    protected void addBonusPoints() 
    {
        //To determin the glober trotter bonus
        int[] playerTotal = new int[players.size()];
        int index = 0;
        for(Player p : players)
        {
            playerTotal[index] += p.numCompletedDest;
            index++;
        }
        int max;
        if(playerTotal.length == 2)
        {
            max = 0;
            index = 0;
            for(int x = 0; x < playerTotal.length; x++)
            {
                if(playerTotal[x] > max)
                {
                    max = playerTotal[x]; 
                    index = x;
                }
                else if(playerTotal[x] == max && playerTotal[x] != 0)
                { 
                    players.get(0).score += 15;
                    players.get(1).score += 15;
                }
                else if(playerTotal[x] != max && x == 1)
                {
                    players.get(index).score += 15;
                }
            }
        }
        else
        {
            max = 0;
            index = 0;
            for(int x = 0; x < playerTotal.length; x++)
            {
                if(playerTotal[x] > max)
                {
                    max = playerTotal[x]; 
                    index = x;
                }
                else if(playerTotal[0] == playerTotal[1])
                { 
                    players.get(0).score += 15;
                    players.get(1).score += 15;
                }
                else if(playerTotal[0] == playerTotal[2])
                { 
                    players.get(0).score += 15;
                    players.get(2).score += 15;
                }
                else if(playerTotal[1] == playerTotal[2])
                { 
                    players.get(1).score += 15;
                    players.get(2).score += 15;
                }
                else if(playerTotal[x] != max && x == 2)
                {
                    players.get(index).score += 15;
                }
            }
        }
        //To determin the Meeple bonus
        if(playerTotal.length == 2)
        {
            for(int y = 0; y < 6; y++)
            {
                if(players.get(0).meeples[y] == 0 && 
                players.get(1).meeples[y] != 0)
                {
                    players.get(1).score += 20;
                }
                else if(players.get(0).meeples[y] != 0 && 
                players.get(1).meeples[y] == 0)
                {
                    players.get(0).score += 20;
                }
                else if(players.get(0).meeples[y] > 
                players.get(1).meeples[y])
                {
                    players.get(0).score += 20;
                    players.get(1).score += 10;
                }
                else if(players.get(0).meeples[y] == 
                players.get(1).meeples[y])
                {
                    players.get(0).score += 20;
                    players.get(1).score += 20;
                }
                else if(players.get(0).meeples[y] < 
                players.get(1).meeples[y])
                {
                    players.get(0).score += 10;
                    players.get(1).score += 20;
                }
            }
        }
        else
        {
            for(int z = 0; z < 6; z++)
            {
                if(players.get(0).meeples[z] == 0 &&
                players.get(2).meeples[z] == 0)
                {
                    players.get(1).score += 20;
                }
                else if(players.get(0).meeples[z] == 0 &&
                players.get(1).meeples[z] == 0)
                {
                    players.get(2).score += 20;
                }
                else if(players.get(1).meeples[z] == 0 &&
                players.get(2).meeples[z] == 0)
                {
                    players.get(0).score += 20;
                }
                else if(players.get(0).meeples[z] == 0)
                {
                    if(players.get(1).meeples[z] > players.get(2).meeples[z])
                    {
                        players.get(1).score += 20;
                        players.get(2).score += 10;
                    }
                    else if(players.get(1).meeples[z] == 
                    players.get(2).meeples[z])
                    {
                        players.get(1).score += 20;
                        players.get(2).score += 20;
                    }
                    else if(players.get(1).meeples[z] < 
                    players.get(2).meeples[z])
                    {
                        players.get(1).score += 10;
                        players.get(2).score += 20;
                    }
                }
                else if(players.get(1).meeples[z] == 0)
                {
                    if(players.get(0).meeples[z] > players.get(2).meeples[z])
                    {
                        players.get(0).score += 20;
                        players.get(2).score += 10;
                    }
                    else if(players.get(0).meeples[z] == 
                    players.get(2).meeples[z])
                    {
                        players.get(0).score += 20;
                        players.get(2).score += 20;
                    }
                    else if(players.get(0).meeples[z] < 
                    players.get(2).meeples[z])
                    {
                        players.get(0).score += 10;
                        players.get(2).score += 20;
                    }
                }
                else if(players.get(2).meeples[z] == 0)
                {
                    if(players.get(0).meeples[z] > players.get(1).meeples[z])
                    {
                        players.get(0).score += 20;
                        players.get(1).score += 10;
                    }
                    else if(players.get(0).meeples[z] == 
                    players.get(1).meeples[z])
                    {
                        players.get(0).score += 20;
                        players.get(1).score += 20;
                    }
                    else if(players.get(0).meeples[z] < 
                    players.get(1).meeples[z])
                    {
                        players.get(0).score += 10;
                        players.get(1).score += 20;
                    }
                }
                else if(players.get(0).meeples[z] > players.get(1).meeples[z] &&
                players.get(0).meeples[z] > players.get(2).meeples[z])
                {
                    players.get(0).score += 20;
                    if(players.get(1).meeples[z] > players.get(2).meeples[z])
                    {
                        players.get(1).score += 10;
                    }
                    else if(players.get(1).meeples[z] == 
                    players.get(2).meeples[z])
                    {
                        players.get(1).score += 10;
                        players.get(2).score += 10;
                    }
                    else if(players.get(1).meeples[z] < 
                    players.get(2).meeples[z])
                    {
                        players.get(2).score += 10;
                    }
                }
                else if(players.get(0).meeples[z] < players.get(1).meeples[z] &&
                players.get(1).meeples[z] > players.get(2).meeples[z])
                {
                    players.get(1).score += 20;
                    if(players.get(0).meeples[z] > players.get(2).meeples[z])
                    {
                        players.get(0).score += 10;
                    }
                    else if(players.get(0).meeples[z] == 
                    players.get(2).meeples[z])
                    {
                        players.get(0).score += 10;
                        players.get(2).score += 10;
                    }
                    else if(players.get(0).meeples[z] < 
                    players.get(2).meeples[z])
                    {
                        players.get(2).score += 10;
                    }
                }
                else if(players.get(0).meeples[z] < players.get(2).meeples[z] &&
                players.get(1).meeples[z] < players.get(2).meeples[z])
                {
                    players.get(2).score += 20;
                    if(players.get(0).meeples[z] > 
                    players.get(1).meeples[z])
                    {
                        players.get(0).score += 10;
                    }
                    else if(players.get(0).meeples[z] == 
                    players.get(1).meeples[z])
                    {
                        players.get(0).score += 10;
                        players.get(1).score += 10;
                    }
                    else if(players.get(0).meeples[z] < 
                    players.get(1).meeples[z])
                    {
                        players.get(1).score += 10;
                    }
                }
                else if(players.get(0).meeples[z] == 
                players.get(1).meeples[z] &&
                players.get(0).meeples[z] > 
                players.get(2).meeples[z])
                {
                    players.get(0).score += 20;
                    players.get(1).score += 20;
                }
                else if(players.get(0).meeples[z] == 
                players.get(2).meeples[z] &&
                players.get(0).meeples[z] > 
                players.get(1).meeples[z])
                {
                    players.get(0).score += 20;
                    players.get(2).score += 20;
                }
                else if(players.get(1).meeples[z] == 
                players.get(2).meeples[z] &&
                players.get(1).meeples[z] > 
                players.get(0).meeples[z])
                {
                    players.get(1).score += 20;
                    players.get(2).score += 20;
                }
                else if(players.get(0).meeples[z] == 
                players.get(1).meeples[z] &&
                players.get(0).meeples[z] < 
                players.get(2).meeples[z])
                {
                    players.get(0).score += 10;
                    players.get(1).score += 10;
                    players.get(2).score += 20;
                }
                else if(players.get(0).meeples[z] == 
                players.get(2).meeples[z] &&
                players.get(0).meeples[z] < 
                players.get(1).meeples[z])
                {
                    players.get(0).score += 10;
                    players.get(1).score += 20;
                    players.get(2).score += 10;
                }
                else if(players.get(1).meeples[z] == 
                players.get(2).meeples[z] &&
                players.get(1).meeples[z] < 
                players.get(0).meeples[z])
                {
                    players.get(0).score += 20;
                    players.get(1).score += 10;
                    players.get(2).score += 10;
                }
            }
        }
    }

    /**
     * Method to end the game and show the scores
     */
    protected void endGame() 
    {
        for (Player p : players) 
        {
            board.traverseDestinations(p);
            int add = p.getPosDestScore();
            int sub = p.getNegDestScore();
            p.score += add;
            p.score -= sub;
        }
        addBonusPoints();
        EndGameWin.createAndShowGUI(players);
        frame.dispose();
    }

    /**
     * Creates the JFrame for the PlayGame window
     */
    protected static void createAndShowGUI() 
    {
        //Create and set up the window.
        frame = new JFrame("Ticket To Ride");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        PlayGame panel = new PlayGame();
        frame.getContentPane().add(panel);
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}
