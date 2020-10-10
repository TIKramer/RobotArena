import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
//Used to store to robots - so they it is possisable to check each robot that exisit in the application
public class RobotHolder
{
	List<RobotInfo> robots;	
	private Object mutex = new Object();
	public RobotHolder()
	{
		robots = new ArrayList<RobotInfo>();
	}
	
	public void addRobot(RobotInfo newRobot)
	{
		synchronized(mutex)
		{
			robots.add(newRobot);
		}
	}
	
	public List<RobotInfo> getRobots()
	{
		return  Collections 
                .unmodifiableList(robots);
	}
	
}
