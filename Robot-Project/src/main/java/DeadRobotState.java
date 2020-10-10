//If a robot health goes to 0 switch to this state which always returns false
public class DeadRobotState implements RobotControllerState{

	@Override
	public boolean moveNorth() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean moveEast() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean moveWest() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fire(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean moveSouth() {
		// TODO Auto-generated method stub
		return false;
	}

}