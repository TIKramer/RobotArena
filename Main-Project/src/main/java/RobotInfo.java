
public class RobotInfo {
	
	


	private String name;
	private int x;
	private int y;
	private double health;
	private boolean alive;
	//Starting health to calculate percentages. - if using a higher health
	private double startingHealth;
	//Mutexs to ensure that each thread accesses these at the same time.
		//Decided to make seperate ones - maybe one thread is able to access health while one checking its location.
	private Object aliveMutex = new Object();
	private Object xLocationMutex = new Object();
	private Object yLocationMutex = new Object();
	private Object healthMutex = new Object();
	public RobotInfo(String name, int startX, int startY, double health)
	{
		this.name = name;
		x = startX;
		y = startY;
		this.health = health;
		this.alive = true;
		this.startingHealth = health;
	}
	//Get name is unique so shouldnt be  thread sync issue - also starting health -only getter not setters
	public String getName() {
		
		return name;
	}
	public double getStartingHealth()
	{
		return startingHealth;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getX() {
		synchronized(xLocationMutex)
		{
		return x;
		}
	}


	public void setX(int x) {
		synchronized(xLocationMutex)
		{
		this.x = x;
		}
	}


	public int getY() {
		synchronized(yLocationMutex)
		{
		return y;
		}
	}


	public void setY(int y) {
		synchronized(yLocationMutex)
		{
		this.y = y;
		}
	}


	public double getHealth() {
		synchronized(healthMutex)
		{
		return health;
		}

	}


	public void setHealth(double health) {
		synchronized(healthMutex)
		{
		this.health = health;
		}
	}
//Called if hit - sets new health and checks if still alive.
	public void hit(String attacker) {
		double currentHealth = getHealth();
		double newHealth = currentHealth - 30;
		this.setHealth(newHealth);
		if(newHealth <= 0)
		{	
			synchronized(aliveMutex)
			{
				alive = false;
			}
		}
		
		
		
	}
	public boolean isAlive()
	{
		synchronized(aliveMutex)
		{
			return alive;
		}
	}

}
