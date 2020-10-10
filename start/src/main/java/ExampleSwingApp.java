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
	//Create each RobotInfo object
    	RobotInfo thomasRobot = new RobotInfo("CBot", 2,2, 100);
    	RobotInfo chatRobot = new RobotInfo("ChatRobot", 8,7, 100);
    	RobotInfo trackerBot = new RobotInfo("Tracker_Bot", 6,6, 100);
    	RobotInfo defaultBot = new RobotInfo("Default_Bot", 4,3, 100);
	//Create the holder which holds each robot to be used in the run
		//Add each robot to this holder
        holder = new RobotHolder();
    	holder.addRobot(thomasRobot);
    	holder.addRobot(chatRobot);
    	holder.addRobot(trackerBot);
    	holder.addRobot(defaultBot);
	//Create the arena passing holder so it can get a live update of robot info
        SwingArena arena = new SwingArena(holder);

	//Create a controller for each robot
    	RobotControl thomasControl = new RobotControl(thomasRobot,arena,holder);
    	RobotControl chatController = new RobotControl(chatRobot,arena,holder);
    	RobotControl trackerController = new RobotControl(trackerBot,arena,holder);
	RobotControl defaultController = new RobotControl(defaultBot,arena,holder);
//Create each AI
	//- CRobot -programmed in C
	//- AI_Robot - my first AI -just basic walks till hits edge then changes direction
	//- TrackClosestBot - Tracks the clostest bot to it and targets that bot - updates target when a different bot becomes the closest
	//- ChattyBot - Makes a comment based on the notification recived - has 4 random messages for each notification type
    	CRobot ai1 = new CRobot();
    	AI_ChattyBot ai2 = new AI_ChattyBot();
    	AI_TrackClosestBot ai3 = new AI_TrackClosestBot();
	AI_Robot ai4 = new AI_Robot();	

    	AIGroup.addAI(thomasRobot.getName(), ai1);
    	AIGroup.addAI(chatRobot.getName(), ai2);
    	AIGroup.addAI(trackerBot.getName(), ai3);
	AIGroup.addAI(defaultBot.getName(), ai4);


        // Note: SwingUtilities.invokeLater() is equivalent to JavaFX's Platform.runLater().
        SwingUtilities.invokeLater(() ->
        {
            JFrame window = new JFrame("Robot AI Test (Swing)");

            JToolBar toolbar = new JToolBar();
            JButton startBtn = new JButton("Start");
            JButton endBtn = new JButton("End");
            toolbar.add(startBtn);
            toolbar.add(endBtn);

            startBtn.addActionListener((event) ->
            {
		//Check if game is not already running 
              if(!gameRunning)
              {
                ai1.runAI(thomasControl);
                ai2.runAI(chatController);
                ai3.runAI(trackerController);
		ai4.runAI(defaultController);
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
//Checks also if AI fails to end - happen if user presses end when game is already ended
            endBtn.addActionListener((event) ->
            {
              try
              {
              ai1.end();
              ai2.end();
              ai3.end();
	      ai4.end();
              gameRunning = false;
            }
            catch(IllegalStateException e)
            {
              logger.append("Unable to correctly end- Game may not be started yet \n");

            }
            });
        });

        checkIfOver();

    }
    public static void logMessage(String message)
    {

      logger.append(message + "\n");
    }

//constantly checks cases where the game is over.
	//Ends each AI and logs winner messages if over
public static void checkIfOver()
{

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
