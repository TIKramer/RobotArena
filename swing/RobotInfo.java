
public class RobotInfo {
	
	


	private String name;
	private int x;
	private int y;
	private double health;
	private boolean alive;
	
	
	public RobotInfo(String name, int startX, int startY, double health)
	{
		this.name = name;
		x = startX;
		y = startY;
		this.health = health;
		this.alive = true;
	}
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}


	public double getHealth() {
		return health;
	}


	public void setHealth(double health) {
		this.health = health;
	}

	public void hit(String attacker) {
		// TODO Auto-generated method stub
		this.health -= 30;
		if(health <= 0)
		{
			alive = false;
		}
		
		
		
	}
	public boolean isAlive()
	{
		return alive;
	}

}
