
public class AliveRobotState implements RobotControllerState 
{
	private RobotControl robotControl;
	private Object lock = new Object();
	
	public AliveRobotState(RobotControl robotControl)
	{
		this.robotControl = robotControl;
	}
	
	public boolean move(int x, int y)
	{
boolean validMove = true;
		RobotInfo myRobot = robotControl.getRobot();
		for(RobotInfo robot : robotControl.getAllRobots())
		{
			if((myRobot.getX()+x == robot.getX()) && (myRobot.getY()+y ==robot.getY()))
			{
				validMove = false;
			}
		}
		if(validMove)
		{
			validMove = robotControl.getArena().setRobotPosition(myRobot.getX()+x, myRobot.getY()+y);
			if(validMove)
			{
				myRobot.setX(myRobot.getX()+x);

				myRobot.setY(myRobot.getY()+y);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return validMove;
	}
	public boolean moveNorth()
	{
		return move(0,1);
	}
	public boolean moveEast()
	{
		return move(1,0);

	}
	public boolean moveWest()
	{
		return move(-1,0);

		
	
	}
	
	public boolean fire(int x, int y)
	{
		boolean valid = false;
		RobotInfo myRobot = robotControl.getRobot();

		for(RobotInfo robot : robotControl.getAllRobots())
		{
			if((!(myRobot.getName().equals(robot.getName()) )&&(robot.isAlive())))
			{
			if((robot.getX() == x) && (robot.getY() == y))
			{
				valid = true;
			//	robot.hit();
				
				robotControl.getArena().fireLazer(myRobot.getX(), myRobot.getY(), robot.getX(), robot.getY());
				
				HitNotification notification = new HitNotification(myRobot.getName(), robot.getName()); 
				AIGroup.notify(robot.getName(), notification);
				AIGroup.notify(myRobot.getName(), notification);
				myRobot.hit( myRobot.getName());
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			}
		}
		return valid;
	}
	public boolean moveSouth() {
		// TODO Auto-generated method stub
		
		return move(0,-1);

	}
	

}
