import java.util.List;

public class RobotControl
{
	private RobotHolder holder;
	private RobotInfo myRobot;
	private SwingArena arena;
	private RobotControllerState state;
	
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
	public boolean moveNorth() {
		// TODO Auto-generated method stub
		return state.moveNorth();
	}
	public boolean moveSouth() {
		// TODO Auto-generated method stub
		return state.moveSouth();
	}
	public boolean moveEast() {
		// TODO Auto-generated method stub
		return state.moveEast();
	}
	public boolean moveWest() {
		// TODO Auto-generated method stub
		return state.moveWest();
	}
	public boolean fire(int x, int y) {
		// TODO Auto-generated method stub
		return state.fire(x, y);
	}
	
	public void killRobot()
	{
		this.state = new DeadRobotState();
		arena.repaint();
	}
}


