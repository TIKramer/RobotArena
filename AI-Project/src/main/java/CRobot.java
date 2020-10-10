import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
//To handle creating C code thread. 
	//Notifications are not used here.
public class CRobot  implements Interface_RobotAI
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
						startCPP(rc);

					}
				};
				myThread = new Thread(myTask,"Thomas");
				myThread.start();


	}
public native void startCPP(RobotControl rc);

static{System.loadLibrary("mynativelibrary");}

	public void putNotification(HitNotification notifcation)
	{
		//queue.add(notifcation);
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
