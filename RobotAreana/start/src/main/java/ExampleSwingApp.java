import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class ExampleSwingApp
{
private static boolean gameRunning = false;
private static JTextArea logger;
private static	RobotHolder holder;
    public static void main(String[] args)
    {
    	RobotInfo thomasRobot = new RobotInfo("CBot", 4,4, 100);
    	RobotInfo johnRobot = new RobotInfo("John", 5,5, 100);
    	    	RobotInfo jackRobot = new RobotInfo("AI_Bot", 3,3, 100);

      holder = new RobotHolder();
    	holder.addRobot(thomasRobot);
    	holder.addRobot(johnRobot);
    	    	holder.addRobot(jackRobot);

        SwingArena arena = new SwingArena(holder);
    	RobotControl thomasControl = new RobotControl(thomasRobot,arena,holder);
    	RobotControl johnController = new RobotControl(johnRobot,arena,holder);
    	RobotControl jackController = new RobotControl(jackRobot,arena,holder);

    	CRobot ai1 = new CRobot();
    	AI_Robot ai2 = new AI_Robot();
    	AI_Robot ai3 = new AI_Robot();

    	AIGroup.addAI(thomasRobot.getName(), ai1);
    	AIGroup.addAI(johnRobot.getName(), ai2);
    	AIGroup.addAI(jackRobot.getName(), ai3);


        // Note: SwingUtilities.invokeLater() is equivalent to JavaFX's Platform.runLater().
        SwingUtilities.invokeLater(() ->
        {
            JFrame window = new JFrame("Robot AI Test (Swing)");

            JToolBar toolbar = new JToolBar();
            JButton startBtn = new JButton("Start");
            JButton endBtn = new JButton("My Button 2");
            toolbar.add(startBtn);
            toolbar.add(endBtn);

            startBtn.addActionListener((event) ->
            {
              if(!gameRunning)
              {
                ai1.runAI(thomasControl);
                ai2.runAI(johnController);
                ai3.runAI(jackController);
                gameRunning = true;
              }
            });


            logger = new JTextArea();
            JScrollPane loggerArea = new JScrollPane(logger);
            loggerArea.setBorder(BorderFactory.createEtchedBorder());
            logger.append("Hello\n");
            logger.append("World\n");
            Logger myLogger = new Logger();
            myLogger.setLogger(logger);
            JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT, arena, logger);

            Container contentPane = window.getContentPane();
            contentPane.setLayout(new BorderLayout());
            contentPane.add(toolbar, BorderLayout.NORTH);
            contentPane.add(splitPane, BorderLayout.CENTER);

            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setPreferredSize(new Dimension(800, 800));
            window.pack();
            window.setVisible(true);

            splitPane.setDividerLocation(0.75);

            endBtn.addActionListener((event) ->
            {
              try
              {
              ai1.end();
              ai2.end();
              ai3.end();
              gameRunning = false;
            }
            catch(IllegalStateException e)
            {
              logger.append("Unable to correctly end- \n" + e.getMessage());

            }
            });
        });

        checkIfOver();

    }
    public static void logMessage(String message)
    {

      logger.append(message + "\n");
    }
public static void checkIfOver()
{
  final int INTERVAL=10000;
  int count = 0;
  java.util.List<RobotInfo> winners = new LinkedList<RobotInfo>();
  boolean gameOver = false;
    while(!gameOver)
    {
        int aliveCount = 0;
        String winnerName= "";
        for(RobotInfo robot : holder.getRobots())
        {
            if(robot.isAlive())
            {
              aliveCount ++;
              winnerName = robot.getName();
            }

        }
        if(aliveCount ==1)
        {
            Logger logger = new Logger();
            logger.logMessage(winnerName + " is the Winner!");
            gameOver = true;
            for(Interface_RobotAI robotAi : AIGroup.getAI())
            {
              robotAi.end();
            }

        }
        else if(aliveCount == 0)
        {
          Logger logger = new Logger();
          logger.logMessage("It's a tie!");
          gameOver = true;
        }


    }

}
}
