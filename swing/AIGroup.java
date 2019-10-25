import java.util.HashMap;

public class AIGroup 
{
	private static HashMap<String, Interface_RobotAI> aiRobots = new HashMap<String, Interface_RobotAI>();;
	
	

	
	public static void addAI(String robotName, Interface_RobotAI ai)
	{
		aiRobots.put(robotName, ai);
	}
	
	public static void notify(String robotName,HitNotification notifcation)
	{
		if(aiRobots.containsKey(robotName))
		{
			aiRobots.get(robotName).putNotification(notifcation);
		}
	}
}