import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * Write a description of class Board here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Board extends JPanel
{
    //get rid of these later
    private static Image board, blackBackground;
    
    
    // instance variables - replace the example below with your own
    protected ArrayList<Route> routes;
    protected HashMap<String, City> allCities;

    /**
     * Constructor for objects of class Board
     */
    public Board()
    {
        ArrayList<Integer> meepleIndexes = new ArrayList<Integer>(60);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 10; j++) {
                meepleIndexes.add(i);
            }
        }
        Collections.shuffle(meepleIndexes);
        
        allCities = new HashMap<String, City>();
        allCities.put("Augsburg", new City(meepleIndexes, "Augsburg"));
        allCities.put("Berlin", new City(meepleIndexes, "Berlin"));
        allCities.put("Bremen", new City(meepleIndexes, "Bremen"));
        allCities.put("Bremerhaven", new City(meepleIndexes, "Bremerhaven"));
        allCities.put("Chemnitz", new City(meepleIndexes, "Chemnitz"));
        allCities.put("Danemark", new City(meepleIndexes, "Danemark"));
        allCities.put("Dortmund", new City(meepleIndexes, "Dortmund"));
        allCities.put("Dresden", new City(meepleIndexes, "Dresden"));
        allCities.put("Dusseldorf", new City(meepleIndexes, "Dusseldorf"));
        allCities.put("Emden", new City(meepleIndexes, "Emden"));
        allCities.put("Erfurt", new City(meepleIndexes, "Erfurt"));
        allCities.put("Frankfurt", new City(meepleIndexes, "Frankfurt"));
        allCities.put("Frankreich", new City(meepleIndexes, "Frankreich"));
        allCities.put("Freiburg", new City(meepleIndexes, "Freiburg"));
        allCities.put("Hamburg", new City(meepleIndexes, "Hamburg"));
        allCities.put("Hannover", new City(meepleIndexes, "Hannover"));
        allCities.put("Karlsruhe", new City(meepleIndexes, "Karlsruhe"));
        allCities.put("Kassel", new City(meepleIndexes, "Kassel"));
        allCities.put("Kiel", new City(meepleIndexes, "Kiel"));
        allCities.put("Koblenz", new City(meepleIndexes, "Koblenz"));
        allCities.put("Koln", new City(meepleIndexes, "Koln"));
        allCities.put("Konstanz", new City(meepleIndexes, "Konstanz"));
        allCities.put("Leipzig", new City(meepleIndexes, "Leipzig"));
        allCities.put("Lindau", new City(meepleIndexes, "Lindau"));
        allCities.put("Magdeburg", new City(meepleIndexes, "Magdeburg"));
        allCities.put("Mainz", new City(meepleIndexes, "Mainz"));
        allCities.put("Mannheim", new City(meepleIndexes, "Mannheim"));
        allCities.put("Munchen", new City(meepleIndexes, "Munchen"));
        allCities.put("Munster", new City(meepleIndexes, "Munster"));
        allCities.put("Niederlande", new City(meepleIndexes, "Niederlande"));
        allCities.put("Nurnberg", new City(meepleIndexes, "Nurnberg"));
        allCities.put("Osterreich", new City(meepleIndexes, "Osterreich"));
        allCities.put("Regensburg", new City(meepleIndexes, "Regensburg"));
        allCities.put("Rostock", new City(meepleIndexes, "Rostock"));
        allCities.put("Saarbrucken", new City(meepleIndexes, "Saarbrucken"));
        allCities.put("Schweiz", new City(meepleIndexes, "Schweiz"));
        allCities.put("Schwerin", new City(meepleIndexes, "Schwerin"));
        allCities.put("Stuttgart", new City(meepleIndexes, "Stuttgart"));
        allCities.put("Ulm", new City(meepleIndexes, "Ulm"));
        allCities.put("Wurzburg", new City(meepleIndexes, "Wurzburg"));
        
        routes = new ArrayList<Route>();
        routes.add(new Route("Danemark","Kiel", allCities));
        routes.add(new Route("Danemark","Bremerhaven", allCities));
        routes.add(new Route("Kiel","Rostock", allCities));
        routes.add(new Route("Kiel","Schwerin", allCities));
        routes.add(new Route("Kiel","Hamburg", allCities));
        routes.add(new Route("Kiel","Bremerhaven", allCities));
        routes.add(new Route("Rostock","Schwerin", allCities));
        routes.add(new Route("Rostock","Berlin", allCities));
        routes.add(new Route("Schwerin","Hamburg", allCities));
        routes.add(new Route("Schwerin","Berlin", allCities));
        routes.add(new Route("Hamburg","Berlin", allCities));
        routes.add(new Route("Hamburg","Hannover", allCities));
        routes.add(new Route("Hamburg","Bremen", allCities));
        routes.add(new Route("Hamburg","Bremerhaven", allCities));
        routes.add(new Route("Bremerhaven","Bremen", allCities));
        routes.add(new Route("Bremerhaven","Emden", allCities));
        routes.add(new Route("Emden","Bremen", allCities));
        routes.add(new Route("Emden","Munster", allCities));
        routes.add(new Route("Emden","Niederlande", allCities));
        routes.add(new Route("Bremen","Munster", allCities));
        routes.add(new Route("Bremen","Hannover", allCities));
        routes.add(new Route("Niederlande","Munster", allCities));
        routes.add(new Route("Niederlande","Dusseldorf", allCities));
        routes.add(new Route("Munster","Hannover", allCities));
        routes.add(new Route("Munster","Dortmund", allCities));
        routes.add(new Route("Hannover","Kassel", allCities));
        routes.add(new Route("Hannover","Erfurt", allCities));
        routes.add(new Route("Hannover","Magdeburg", allCities));
        routes.add(new Route("Hannover","Berlin", allCities));
        routes.add(new Route("Berlin","Magdeburg", allCities));
        routes.add(new Route("Berlin","Leipzig", allCities));
        routes.add(new Route("Berlin","Dresden", allCities));
        routes.add(new Route("Magdeburg","Leipzig", allCities));
        routes.add(new Route("Dusseldorf","Dortmund", allCities));
        routes.add(new Route("Dusseldorf","Koln", allCities));
        routes.add(new Route("Kassel","Dortmund", allCities));
        routes.add(new Route("Kassel","Frankfurt", allCities));
        routes.add(new Route("Kassel","Erfurt", allCities));
        routes.add(new Route("Leipzig","Erfurt", allCities));
        routes.add(new Route("Leipzig","Dresden", allCities));
        routes.add(new Route("Leipzig","Chemnitz", allCities));
        routes.add(new Route("Dresden","Chemnitz", allCities));
        routes.add(new Route("Dresden","Regensburg", allCities));
        
        routes.add(new Route("Chemnitz","Leipzig", allCities));
        routes.add(new Route("Chemnitz","Erfurt", allCities));
        routes.add(new Route("Chemnitz","Regensburg", allCities));
        routes.add(new Route("Erfurt","Nurnberg", allCities));
        routes.add(new Route("Erfurt","Regensburg", allCities));
        routes.add(new Route("Koln","Dusseldorf", allCities));
        routes.add(new Route("Koln","Frankfurt", allCities));
        routes.add(new Route("Koln","Koblenz", allCities));
        routes.add(new Route("Frankfurt","Wurzburg", allCities));
        routes.add(new Route("Frankfurt","Mannheim", allCities));
        routes.add(new Route("Frankfurt","Mainz", allCities));
        routes.add(new Route("Koblenz","Mainz", allCities));
        routes.add(new Route("Koblenz","Saarbrucken", allCities));
        routes.add(new Route("Mainz","Mannheim", allCities));
        routes.add(new Route("Mainz","Saarbrucken", allCities));
        routes.add(new Route("Wurzburg","Nurnberg", allCities));
        routes.add(new Route("Nurnberg","Augsburg", allCities));
        routes.add(new Route("Nurnberg","Munchen", allCities));
        routes.add(new Route("Nurnberg","Regensburg", allCities));
        routes.add(new Route("Regensburg","Munchen", allCities));
        routes.add(new Route("Regensburg","Osterreich", allCities));
        routes.add(new Route("Mannheim","Stuttgart", allCities));
        routes.add(new Route("Mannheim","Karlsruhe", allCities));
        routes.add(new Route("Mannheim","Saarbrucken", allCities));
        routes.add(new Route("Saarbrucken","Frankreich", allCities));
        routes.add(new Route("Saarbrucken","Karlsruhe", allCities));
        routes.add(new Route("Karlsruhe","Frankreich", allCities));
        routes.add(new Route("Karlsruhe","Freiburg", allCities));
        routes.add(new Route("Karlsruhe","Stuttgart", allCities));
        routes.add(new Route("Stuttgart","Freiburg", allCities));
        routes.add(new Route("Stuttgart","Konstanz", allCities));
        routes.add(new Route("Stuttgart","Ulm", allCities));
        routes.add(new Route("Frankreich","Freiburg", allCities));
        routes.add(new Route("Ulm","Augsburg", allCities));
        routes.add(new Route("Ulm","Lindau", allCities));
        routes.add(new Route("Augsburg","Munchen", allCities));
        routes.add(new Route("Munchen","Lindau", allCities));
        routes.add(new Route("Munchen","Osterreich", allCities));
        routes.add(new Route("Munchen","Regensburg", allCities));
        routes.add(new Route("Freiburg","Frankreich", allCities));
        routes.add(new Route("Freiburg","Konstanz", allCities));
        routes.add(new Route("Freiburg","Schweiz", allCities));
        routes.add(new Route("Konstanz","Schweiz", allCities));
        routes.add(new Route("Konstanz","Lindau", allCities));
        routes.add(new Route("Lindau","Schweiz", allCities));
        routes.add(new Route("Lindau","Osterreich", allCities));
        
        for (Route r: routes) {
            //r.addToCities();
            for (City c: r.twoCities) {
                //System.out.println(r);
                c.connectedRoutes.add(r); 
            }
        }
        
        
        
    }

    // public static void main (String args[]) {
        // Board b = new Board();
        // int count = 0;
        // for (Route r: b.routes) {
            // System.out.println(r);
            // count ++;
            // if (count >15) break;
        // }
        // System.out.println("");
        // City c1 = b.allCities.get("Berlin");
        // for (Route r1: c1.connectedRoutes) {
            // System.out.println(r1);
        // }
    // }
    
    public Board(int i)
    {
        String dir = "Images//";
        board = new ImageIcon(dir + "Board.JPG").getImage();
        blackBackground = new ImageIcon(dir + "blackBackground.JPG").getImage();
        Dimension size = new Dimension(blackBackground.getWidth(null), board.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);
    }

    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Ticket To Ride");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Board panel = new Board(1);
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

    @Override
    public void paintComponent(Graphics g){
        // As we learned in the last lesson,
        // the origin (0,0) is at the upper left corner.
        // x increases to the right, and y increases downward.
        // add 270 to be on board, board ends at 890
        super.paintComponent(g);

        g.drawImage(blackBackground,0,0,null);
        g.drawImage(board,270,0,null);

        //used to find coordinates
        g.setColor(Color.BLUE);
        
        Board b = new Board();
        for (City c: b.allCities.values()) {
            if (c.cityShape != null) {
                //show a box with city name and meeple counts 
                //the city's meeple counts will be in c.meeples which is an int[]
                g.fillPolygon(c.cityShape);
            }
        }
        
    }
}
