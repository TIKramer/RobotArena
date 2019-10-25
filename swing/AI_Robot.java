import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class AI_Robot  implements Interface_RobotAI 
{
	private BlockingQueue <HitNotification> queue=new ArrayBlockingQueue<HitNotification>(5);
	private Thread myThread;
	@Override
	public void runAI(RobotControl rc) {
		// TODO Auto-generated method stub

		Runnable myTask = new Runnable()
				{
					@Override
					public void run()
					{
						char direction = 'N';
						while(true)
						{
							while(!queue.isEmpty())
							{
								try {
									HitNotification notification =queue.take();
									if(notification.getAttacker() != rc.getRobot().getName())
									{
										System.out.println(rc.getRobot().getName() + ": " + "Ouch!");
										if(rc.getRobot().getHealth() <= 0)
										{
											rc.killRobot();
										}
									}
									else
									{
										System.out.println(rc.getRobot().getName() + ": " + "Got u!");

									}
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							switch(direction) 
							{
								case 'N':
									if(!rc.moveNorth())
										direction = 'E';
									break;
								case 'E':
									if(!rc.moveEast())
										direction = 'S';
									break;
								case 'S':
									if(!rc.moveSouth())
										direction = 'W';
									break;
								case 'W':
									if(!rc.moveWest())
										direction = 'N';
									break;
									
							  default:
							    // code b
						}

							for(RobotInfo robot: rc.getAllRobots())
							{
								RobotInfo myRobot = rc.getRobot();

								if(!myRobot.getName().equals(robot.getName()))
								{
									if(Math.abs(myRobot.getX() - robot.getX()) <=2)
									{
										if(Math.abs(myRobot.getY() - robot.getY()) <=2)
										{
											rc.fire(robot.getX(), robot.getY());
											
												
											
										}
									}
								}
							}
						}
					}
				};
				myThread = new Thread(myTask,"Thomas");
				myThread.start();
		
	}

	public void putNotification(HitNotification notifcation)
	{
		queue.add(notifcation);
	}
	

}
