import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
/* This is AI for tracking the closest bot
The clostest bot is set as the target and will move to its location and only attack this target
	target will change if a new bot becomes the closest 


*/
public class AI_TrackClosestBot  implements Interface_RobotAI
{
	private BlockingQueue <HitNotification> queue=new ArrayBlockingQueue<HitNotification>(5);
	private Thread myThread;
	private RobotControl rc;
	@Override
	public void runAI(RobotControl rc) {
		this.rc = rc;
		Runnable myTask = new Runnable()
				{
					@Override
					public void run()
					{
						char direction = 'N';
						boolean alive = true;
						RobotInfo target;
						RobotInfo myRobot = rc.getRobot();
						try
						{
						while(true)
						{	
							 target = getNextTarget();





							while(!queue.isEmpty())
							{


									HitNotification notification =queue.take();
									if(notification.getAttacker() != rc.getRobot().getName())
									{

										if(rc.getRobot().getHealth() <= 0 && alive)
										{
											rc.killRobot();
											alive = false;

										}
									}
									else
									{


									}

							}


							if(alive)
							{
								if(rc.getRobot().getHealth() <= 0)
								{
									rc.killRobot();
									alive = false;
								}
								//Getting the difference between this robot location and the target
								int targetDiffX = target.getX() - myRobot.getX();
								int targetDiffY = target.getY() - myRobot.getY();
								//move horizontal somewhere if horizontal distance shorter than vertical
								if(Math.abs(targetDiffX) < Math.abs(targetDiffY) && targetDiffX !=0)
								{
									if(targetDiffX < 0)
									{
										rc.moveWest();
									}
									else
									{
										rc.moveEast();
									}
								}
								//move vertical if horizontal is shortest
								else
								{
									if(targetDiffX > 0)
									{
										rc.moveSouth();
									}
									else
									{
										rc.moveNorth();
									}
								}

								
							}

								
							//Check if in firing range
							if(Math.abs(myRobot.getX() - target.getX()) <=2)
							{
								if(Math.abs(myRobot.getY() - target.getY()) <=2)
								{
									rc.fire(target.getX(), target.getY());



								}
							}
						}
						
						}
						catch (Exception e) {
							// TODO Auto-generated catch block

						}
					}



				};
				myThread = new Thread(myTask,"Thomas");
				myThread.start();

	}
//Get The next closest alive target
	public RobotInfo getNextTarget()
	{
		RobotInfo newTarget = null;
		
		for(RobotInfo robot: rc.getAllRobots())
		{
			RobotInfo myRobot = rc.getRobot();
			
			if(!myRobot.getName().equals(robot.getName()) && myRobot.isAlive() && robot.isAlive())
			{
				if(newTarget == null)
				{
					newTarget = robot;
				}
				else
				{
					

					int targetDiffX = Math.abs(newTarget.getX() - myRobot.getX());
					int targetDiffY = Math.abs(newTarget.getY() - myRobot.getY());
					int totalTargetDiff = targetDiffX + targetDiffY;
					int nextRobotDiffX = Math.abs(robot.getX() - myRobot.getX());
					int nextRobotDiffY = Math.abs(robot.getY() - myRobot.getY());
					int totalNextRobotDiff = nextRobotDiffX + nextRobotDiffY;
					{
						if(totalNextRobotDiff<totalTargetDiff )
						{
							newTarget = robot;
						}
					}
				}
			}
		}
		return newTarget;
	}
//Main app puts notifications using this method
	public void putNotification(HitNotification notifcation)
	{
		queue.add(notifcation);
	}
	public void end()
	{
		if(myThread == null)
		{
			throw new IllegalStateException();
		}
		myThread.interrupt();
		myThread =null;
	}


}
