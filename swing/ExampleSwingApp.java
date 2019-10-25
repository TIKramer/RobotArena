import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ExampleSwingApp 
{
    public static void main(String[] args) 
    {
    	RobotInfo thomasRobot = new RobotInfo("Thomas", 2,5, 100);
    	RobotInfo johnRobot = new RobotInfo("John", 2,2, 100);
    	    	RobotInfo jackRobot = new RobotInfo("John", 7,0, 100);
    	
    	RobotHolder holder = new RobotHolder();
    	holder.addRobot(thomasRobot);
    	holder.addRobot(johnRobot);
    	    	holder.addRobot(jackRobot);
    	

        SwingArena arena = new SwingArena(holder);
    	RobotControl thomasControl = new RobotControl(thomasRobot,arena,holder);
    	RobotControl johnController = new RobotControl(johnRobot,arena,holder);
    	RobotControl jackController = new RobotControl(jackRobot,arena,holder);

    	AI_Robot ai1 = new AI_Robot();
    	AI_Robot ai2 = new AI_Robot();
    	    	AI_Robot ai3 = new AI_Robot();
    	
    	AIGroup.addAI(thomasRobot.getName(), ai1);
    	AIGroup.addAI(johnRobot.getName(), ai2);
    	AIGroup.addAI(jackRobot.getName(), ai3);

    	ai1.runAI(thomasControl);
    	ai2.runAI(johnController);
    	ai3.runAI(jackController);


        // Note: SwingUtilities.invokeLater() is equivalent to JavaFX's Platform.runLater().
        SwingUtilities.invokeLater(() ->
        {
            JFrame window = new JFrame("Robot AI Test (Swing)");
            
            JToolBar toolbar = new JToolBar();
            JButton btn1 = new JButton("My Button 1");
            JButton btn2 = new JButton("My Button 2");
            toolbar.add(btn1);
            toolbar.add(btn2);
            
            btn1.addActionListener((event) ->
            {
                System.out.println("Button 1 pressed");
            });
            
            JTextArea logger = new JTextArea();
            JScrollPane loggerArea = new JScrollPane(logger);
            loggerArea.setBorder(BorderFactory.createEtchedBorder());
            logger.append("Hello\n");
            logger.append("World\n");
            
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
        });
    }    
}
