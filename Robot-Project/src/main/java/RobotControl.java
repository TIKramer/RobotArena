import java.util.List;

public class RobotControl
{
	private RobotHolder holder;
	private RobotInfo myRobot;
	private SwingArena arena;
	private RobotControllerState state;
	private Object mutex = new Object();

	public RobotControllerState getState() {
		return state;
	}
	public SwingArena getArena() {
		return arena;
	}
	public RobotControl(RobotInfo newRobot ,SwingArena arena, RobotHolder holder)
	{
		myRobot = newRobot;
		this.arena = arena;
		this.holder = holder;
		this.state = new AliveRobotState(this);
	}
	public RobotInfo getRobot()
	{
		return myRobot;

	}

	public List<RobotInfo> getAllRobots()
	{
		return holder.getRobots();
	}
	public boolean moveNorth() throws InterruptedException{
		boolean validMove = false;
		try{
			validMove = state.moveNorth();
		}
		catch(InterruptedException e)
		{
			throw new InterruptedException();
		}
		return validMove;
	}
	public boolean moveSouth() throws InterruptedException{
		// TODO Auto-generated method stub
		boolean validMove = false;
		try{
			validMove = state.moveSouth();
		}
		catch(InterruptedException e)
		{
			throw new InterruptedException();
		}
		return validMove;
	}

	public boolean moveEast() throws InterruptedException {
		// TODO Auto-generated method stub
		boolean validMove = false;
		try{
			validMove = state.moveEast();
		}
		catch(InterruptedException e)
		{
			throw new InterruptedException();
		}
		return validMove;
	}

	public boolean moveWest() throws InterruptedException {
		// TODO Auto-generated method stub
		boolean validMove = false;
		try{
			validMove = state.moveWest();
		}
		catch(InterruptedException e)
		{
			throw new InterruptedException();
		}
		return validMove;

	}
	public boolean fire(int x, int y) throws InterruptedException{
		// TODO Auto-generated method stub
		boolean validMove = false;
		try{
			validMove = state.fire(x, y);
		}
		catch(InterruptedException e)
		{
			throw new InterruptedException();
		}
		 return validMove;
	}
	//This is called to change the state of robot to dead.
	public void killRobot()
	{
		this.state = new DeadRobotState();
		
	logMessage(myRobot.getName() + " has died!");
	}


	public void logMessage(String message)
	{
		synchronized(mutex)
 		 {
		Logger logger = new Logger();
		logger.logMessage(message);
		}

	}
}
