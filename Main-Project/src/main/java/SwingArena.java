import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;
import java.util.TimerTask;
import java.util.Timer;
import javax.swing.*;

/**
 * A Swing GUI element that displays a grid on which you can draw images, text and lines.
 */
public class SwingArena extends JPanel
{
    // Represents the image to draw. You can modify this to introduce multiple images.
    private static final String IMAGE_FILE = "1554047213.png";
    private ImageIcon robot1;

    // The following values are arbitrary, and you may need to modify them according to the 
    // requirements of your application.
    private int gridWidth = 10;
    private int gridHeight = 10;
    private double robotX = 1;
    private double robotY = 3;
    private RobotHolder robots;
    private LinkedList<LazerLine> lazers;
    private Object lazerMonitor = new Object();
    private double gridSquareSize; // Auto-calculated
    ArrayList<LazerLine> forRemoval = new ArrayList<LazerLine>();

  
//Class for holder data needed to create a lazerline
	//If lazerline active value false it means it should no longer appear on screen
		//Active false values are removed from the lazerline array
  private class LazerLine
    {
    	int x1;
    	private boolean active;
    	public int getX1() {
			return x1;
		}
		public int getY1() {
			return y1;
		}
		public int getX2() {
			return x2;
		}
		public int getY2() {
			return y2;
		}
		public boolean isActive()
		{
			if(timer == null)
			{
				timer = new Timer();
		        timer.schedule( new  RemindTask(), 250);
			}
			return active;
		}
		int y1;
    	int x2;
    	int y2;
    	private Timer timer;
    	public LazerLine(int x1,int y1, int x2, int y2)
    	{
    	this.x1 = x1;
    	this.y1 = y1;
    	this.x2 = x2;
    	this.y2 = y2;
    	this.active = true;
    	
    }
    
    	 class RemindTask extends TimerTask {
    	        public void run() {
    	        	active = false;
    	    		timer.cancel();
    	        }
    	    }

    	
    
    }
    

    /**
     * Creates a new arena object, loading the robot image.
     */
    public SwingArena(RobotHolder holder)
    {
    	lazers = new LinkedList<LazerLine>();
    	robots = holder;
        // Here's how you get an Image object from an image file (which you provide in the 
        // 'resources/' directory.
   
        robot1 = new ImageIcon(getClass().getClassLoader().getResource(IMAGE_FILE));
        // You will get an exception here if the specified image file cannot be found.
    }
    
    
    /**
     * Moves a robot image to a new grid position. This method is a *demonstration* of how you
     * can do such things, and you may want or need to modify it substantially.
     */
    public boolean setRobotPosition(double x, double y)
    {
    	boolean move = false;
    	if( (x>= 0 &&x < gridWidth) && (y>=0 && y< gridHeight))
    	{
	        robotX = x;
	        robotY = y;
	        repaint();
	        move = true;
    	}
		return move;
    }
    
    
    /**
     * This method is called in order to redraw the screen, either because the user is manipulating 
     * the window, OR because you've called 'repaint()'.
     *
     * You will need to modify the last part of this method; specifically the sequence of calls to
     * the other 'draw...()' methods. You shouldn't need to modify anything else about it.
     */
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D gfx = (Graphics2D) g;
        gfx.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
                             RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        
        // First, calculate how big each grid cell should be, in pixels. (We do need to do this
        // every time we repaint the arena, because the size can change.)
        gridSquareSize = Math.min(
            (double) getWidth() / (double) gridWidth,
            (double) getHeight() / (double) gridHeight);
            
        int arenaPixelWidth = (int) ((double) gridWidth * gridSquareSize);
        int arenaPixelHeight = (int) ((double) gridHeight * gridSquareSize);
            
            
        // Draw the arena grid lines. This may help for debugging purposes, and just generally
        // to see what's going on.
        gfx.setColor(Color.GRAY);
        gfx.drawRect(0, 0, arenaPixelWidth - 1, arenaPixelHeight - 1); // Outer edge

        for(int gridX = 1; gridX < gridWidth; gridX++) // Internal vertical grid lines
        {
            int x = (int) ((double) gridX * gridSquareSize);
            gfx.drawLine(x, 0, x, arenaPixelHeight);
        }
        
        for(int gridY = 1; gridY < gridHeight; gridY++) // Internal horizontal grid lines
        {
            int y = (int) ((double) gridY * gridSquareSize);
            gfx.drawLine(0, y, arenaPixelWidth, y);
        }

        
        // Invoke helper methods to draw things at the current location.
        // ** You will need to adapt this to the requirements of your application. **
        for(RobotInfo robot : robots.getRobots())
        {
	        drawImage(gfx, robot1, robot.getX(), robot.getY());
	        drawLabel(gfx, robot.getName() + " " + (robot.getHealth()/robot.getStartingHealth()*100) + "%", robot.getX(), robot.getY());
        }
    
       //Draw active lines and remove any unactive lines from array.
        if(lazers != null)
        {
        for (LazerLine lazer : lazers) {
        	  if (lazer != null ){
	        	if(lazer.isActive())
	        	{
	            drawLine(gfx, lazer.getX1(), lazer.getY1(), lazer.getX2(), lazer.getY2());
	        	}
	        	else
	        	{
	        		forRemoval.add(lazer);
	        	}
	        }
        	}
        
      
        
        }
        
 
    }
    //Used by main application to draw a lazer on the screen

    public void fireLazer(int x1, int y1, int x2, int y2)
    {

       LazerLine line = new LazerLine(x1,y1,x2,y2);
      
       for(LazerLine lazer : forRemoval)
       {
       	lazers.remove(lazer);
       }
       lazers.add(line);
        repaint();
        
       
    }
    
    
    /** 
     * Draw an image in a specific grid location. *Only* call this from within paintComponent(). 
     *
     * Note that the grid location can be fractional, so that (for instance), you can draw an image 
     * at location (3.5,4), and it will appear on the boundary between grid cells (3,4) and (4,4).
     *     
     * You shouldn't need to modify this method.
     */
    private void drawImage(Graphics2D gfx, ImageIcon icon, double gridX, double gridY)
    {
        // Get the pixel coordinates representing the centre of where the image is to be drawn. 
        double x = (gridX + 0.5) * gridSquareSize;
        double y = (gridY + 0.5) * gridSquareSize;
        
        // We also need to know how "big" to make the image. The image file has a natural width 
        // and height, but that's not necessarily the size we want to draw it on the screen. We 
        // do, however, want to preserve its aspect ratio.
        double fullSizePixelWidth = (double) robot1.getIconWidth();
        double fullSizePixelHeight = (double) robot1.getIconHeight();
        
        double displayedPixelWidth, displayedPixelHeight;
        if(fullSizePixelWidth > fullSizePixelHeight)
        {
            // Here, the image is wider than it is high, so we'll display it such that it's as 
            // wide as a full grid cell, and the height will be set to preserve the aspect 
            // ratio.
            displayedPixelWidth = gridSquareSize;
            displayedPixelHeight = gridSquareSize * fullSizePixelHeight / fullSizePixelWidth;
        }
        else
        {
            // Otherwise, it's the other way around -- full height, and width is set to 
            // preserve the aspect ratio.
            displayedPixelHeight = gridSquareSize;
            displayedPixelWidth = gridSquareSize * fullSizePixelWidth / fullSizePixelHeight;
        }

        // Actually put the image on the screen.
        gfx.drawImage(icon.getImage(), 
            (int) (x - displayedPixelWidth / 2.0),  // Top-left pixel coordinates.
            (int) (y - displayedPixelHeight / 2.0), 
            (int) displayedPixelWidth,              // Size of displayed image.
            (int) displayedPixelHeight, 
            null);
    }
    
    
    /**
     * Displays a string of text underneath a specific grid location. *Only* call this from within 
     * paintComponent(). 
     *
     * You shouldn't need to modify this method.
     */
    private void drawLabel(Graphics2D gfx, String label, double gridX, double gridY)
    {
        gfx.setColor(Color.BLUE);
        FontMetrics fm = gfx.getFontMetrics();
        gfx.drawString(label, 
            (int) ((gridX + 0.5) * gridSquareSize - (double) fm.stringWidth(label) / 2.0), 
            (int) ((gridY + 1.0) * gridSquareSize) + fm.getHeight());
    }
    
    /** 
     * Draws a (slightly clipped) line between two grid coordinates. 
     *
     * You shouldn't need to modify this method.
     */
    private void drawLine(Graphics2D gfx, double gridX1, double gridY1, 
                                          double gridX2, double gridY2)
    {
        gfx.setColor(Color.RED);
        
        // Recalculate the starting coordinate to be one unit closer to the destination, so that it
        // doesn't overlap with any image appearing in the starting grid cell.
        final double radius = 0.5;
        double angle = Math.atan2(gridY2 - gridY1, gridX2 - gridX1);
        double clippedGridX1 = gridX1 + Math.cos(angle) * radius;
        double clippedGridY1 = gridY1 + Math.sin(angle) * radius;
        
        gfx.drawLine((int) ((clippedGridX1 + 0.5) * gridSquareSize), 
                     (int) ((clippedGridY1 + 0.5) * gridSquareSize), 
                     (int) ((gridX2 + 0.5) * gridSquareSize), 
                     (int) ((gridY2 + 0.5) * gridSquareSize));
    }
    
    public void rePaint()
    {
        repaint();

    }
}
