import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.Random;

//This robot uses the notifcation system to Display chat messages based on the notifcation
	//It is an extention of the AI_Robot - moves untill hits wall then changes direction
	//Has 4 random message for each type of Notifcation.

public class AI_ChattyBot  implements Interface_RobotAI
{
	private BlockingQueue <HitNotification> queue=new ArrayBlockingQueue<HitNotification>(5);
	private Thread myThread;
	@Override
	public void runAI(RobotControl rc) {
		// TODO Auto-generated method stub
		Logger logger = new Logger();
		Runnable myTask = new Runnable()
				{
					@Override
					public void run()
					{
						char direction = 'N';
						boolean alive = true;
						try
						{
						while(true)
						{




							while(!queue.isEmpty())
							{
									


									HitNotification notification =queue.take();
									if(notification.getAttacker() != rc.getRobot().getName())
									{

										rc.logMessage(rc.getRobot().getName() + " says: " +getHitMessage());
										if(rc.getRobot().getHealth() <= 0 && alive)
										{
											rc.killRobot();
											alive = false;

										}
									}
									else
									{
										rc.logMessage(rc.getRobot().getName() + " says: " +getAttackMessage());

									}

							}
							if(alive)
							{
								if(rc.getRobot().getHealth() <= 0)
								{
									rc.killRobot();
									alive = false;
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

									if(!myRobot.getName().equals(robot.getName()) && myRobot.isAlive() && robot.isAlive())
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
						}
						catch (Exception e) {
							// TODO Auto-generated catch block
						System.out.println("Interuppted at message");
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
	public void end()
	{
		if(myThread == null)
		{
			throw new IllegalStateException();
		}
		myThread.interrupt();
		myThread =null;
	}

	private String getHitMessage()
	{

		int lineSelector = (int)(Math.random() * 5);
		String message = "";
		switch(lineSelector)
		{
		case 0:
			message = "Ouch!";
			break;
									
		 case 1:
			message = "You hit me!";
			break;
		 case 2:
			message = "I am dying, stop it!";
			break;
		 case 3:
			message = "don't shoot me I dont like it!";
			break;
		}
	return message;
	}

	private String getAttackMessage()
	{

		int lineSelector = (int)(Math.random() * 5);
		String message = "";
		switch(lineSelector)
		{
		case 0:
			message = "Hahaha, got you!";
			break;			
		 case 1:
			 message = "SCORE!";
			break;
		 case 2:
			 message = "die!die!die!";
		         break;
		 case 3:
			message = "I will kill you!";
			break;
		}
		return message;
	}


}
