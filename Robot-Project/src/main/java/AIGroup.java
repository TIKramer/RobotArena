import java.util.*;
/* Group of all AI in the application - used to easily find an AI by using the unique robotName to send that ai a notification */
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

	public static List<Interface_RobotAI> getAI()
	{
		List<Interface_RobotAI> list = new ArrayList<Interface_RobotAI>(aiRobots.values());
		return list;
	}
}
