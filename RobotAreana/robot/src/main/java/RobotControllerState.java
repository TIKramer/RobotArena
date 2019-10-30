
public interface RobotControllerState {

	public boolean moveNorth() throws InterruptedException;
;
	public boolean moveEast() throws InterruptedException;
	public boolean moveWest() throws InterruptedException;


	public boolean fire(int x, int y) throws InterruptedException;

	public boolean moveSouth() throws InterruptedException;


}
