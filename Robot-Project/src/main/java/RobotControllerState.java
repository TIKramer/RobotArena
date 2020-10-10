
//An interface so that the RobotController can have different states
//Only states: Alive and Dead

public interface RobotControllerState {

	public boolean moveNorth() throws InterruptedException;
	public boolean moveEast() throws InterruptedException;
	public boolean moveWest() throws InterruptedException;


	public boolean fire(int x, int y) throws InterruptedException;

	public boolean moveSouth() throws InterruptedException;


}
