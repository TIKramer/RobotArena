
public class AliveRobotState implements RobotControllerState
{
	private RobotControl robotControl;
	private Object lock = new Object();

	public AliveRobotState(RobotControl robotControl)
	{
		this.robotControl = robotControl;
	}

	public boolean move(int x, int y) throws InterruptedException
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
					throw new InterruptedException();

				}
			}
		}
		return validMove;
	}
	public boolean moveNorth() throws InterruptedException
	{
		boolean validMove = false;

		try
		{
		 validMove = move(0,-1);
	  }
		catch(InterruptedException e)
		{
			throw new InterruptedException();
		}
		return validMove;
	}
	public boolean moveEast() throws InterruptedException
	{
			boolean validMove = false;
		try
		{
		 validMove = move(1,0);
	  }
		catch(InterruptedException e)
		{
			throw new InterruptedException();
		}
		return validMove;

	}
	public boolean moveWest() throws InterruptedException
	{
		boolean validMove = false;

		try
		{
		 validMove = move(-1,0);
   	}
		catch(InterruptedException e)
		{
			throw new InterruptedException();
		}
		return validMove;

	}
	public boolean moveSouth() throws InterruptedException
	{
		boolean validMove = false;

		try
		{
		 validMove = move(0,1);
	  }
		catch(InterruptedException e)
		{
			throw new InterruptedException();
		}
		return validMove;

	}

	public boolean fire(int x, int y) throws InterruptedException
	{
		boolean valid = false;
		RobotInfo myRobot = robotControl.getRobot();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		throw new InterruptedException();
		}
		for(RobotInfo robot : robotControl.getAllRobots())
		{
			if((!(myRobot.getName().equals(robot.getName()) ))&&(robot.isAlive()))
			{
			if((robot.getX() == x) && (robot.getY() == y))
			{
				valid = true;
			//	robot.hit();

				robotControl.getArena().fireLazer(myRobot.getX(), myRobot.getY(), robot.getX(), robot.getY());

				HitNotification notification = new HitNotification(myRobot.getName(), robot.getName());
				AIGroup.notify(robot.getName(), notification);
				AIGroup.notify(myRobot.getName(), notification);
				robot.hit( myRobot.getName());
				robotControl.logMessage(myRobot.getName() + " has hit " + robot.getName() + "!");


			}
			}
		}
		return valid;
	}



}
