#include <jni.h>
#include <stdio.h>
#include <string.h>
JNIEXPORT void JNICALL Java_CRobot_startCPP(JNIEnv*env,jobject this, jobject rc)
{
 	//Get controller class
	jclass controller=(*env)->GetObjectClass(env,rc);
	//Get controller methods
	jmethodID north=(*env)->GetMethodID(env,controller,"moveNorth","()Z");
	jmethodID east=(*env)->GetMethodID(env,controller,"moveEast","()Z");
	jmethodID south=(*env)->GetMethodID(env,controller,"moveSouth","()Z");
	jmethodID west=(*env)->GetMethodID(env,controller,"moveWest","()Z");
	jmethodID fire=(*env)->GetMethodID(env,controller,"fire","(II)Z");
	jmethodID killRobot=(*env)->GetMethodID(env,controller,"killRobot","()V");
	jmethodID getRobot=(*env)->GetMethodID(env,controller,"getRobot","()LRobotInfo;");
	jmethodID getRobots=(*env)->GetMethodID(env,controller,"getAllRobots","()Ljava/util/List;");

	//Get list of all robots
	jarray robotList = (*env)->CallObjectMethod(env,rc,getRobots);
	//Get the list class
	jclass robots = (*env)->GetObjectClass(env,robotList);
	//Get list methods
	jmethodID getElement = (*env)->GetMethodID(env, robots, "get", "(I)Ljava/lang/Object;");
	jmethodID getSize = (*env)->GetMethodID(env, robots, "size", "()I");
	jobject size = (*env)->CallIntMethod(env,robotList,getSize);
	jobject element = (*env)->CallObjectMethod(env,robotList,getElement, 1);
		
	//Get the robot used by this AI
	jobject myRobot = (*env)->CallObjectMethod(env,rc,getRobot);
	//Get the class of the Robot
	jclass robotInfo=(*env)->GetObjectClass(env,myRobot);
	//Get robot methods
	jmethodID getRobotName=(*env)->GetMethodID(env,robotInfo,"getName","()Ljava/lang/String;");
	jmethodID getRobotHealth=(*env)->GetMethodID(env,robotInfo,"getHealth","()D");
	jmethodID getRobotX=(*env)->GetMethodID(env,robotInfo,"getX","()I");
	jmethodID getRobotY=(*env)->GetMethodID(env,robotInfo,"getY","()I");
	jmethodID isAlive=(*env)->GetMethodID(env,robotInfo,"isAlive","()Z");
	
	jboolean result;

	int alive = 1;
	char direction = 'N';
	while(1)
	{
		if(alive ==1)
		{
			double health = (*env)->CallDoubleMethod(env,myRobot, getRobotHealth);
			if(health <= 0.0)
			{
				(*env)->CallObjectMethod(env,rc, killRobot);
				alive =0;
			}
			else
			{
				switch (direction)
				{
				case 'N':
				result = (*env)->CallBooleanMethod(env,rc,north);
					if(result == JNI_FALSE)
					{
						direction = 'E';
					}
						  break;

				case 'E':
				result = (*env)->CallBooleanMethod(env,rc,east);
					if(result == JNI_FALSE)
					{
						direction = 'S';
					}

				  break;
				case 'S':
				result = (*env)->CallBooleanMethod(env,rc,south);
					if(result == JNI_FALSE)
					{
						direction = 'W';
					}

				  break;
				case 'W':
					result = (*env)->CallBooleanMethod(env,rc,west);
					if(result == JNI_FALSE)
					{
						direction = 'N';
					}

				  break;



		   		 }
					 int i;
					//Get the robot name and location of the one controlled by this AI

					
					 jobject myRobotName = (*env)->CallObjectMethod(env,myRobot,getRobotName);
					jsize myRobotnChars=(*env)->GetStringLength(env,myRobotName);
					jsize myRobotnBytes=(*env)->GetStringUTFLength(env,myRobotName);

					char robotNameString[myRobotnBytes+1];
					memset(robotNameString,0,myRobotnBytes+1);
					(*env)->GetStringUTFRegion(env,myRobotName,0,myRobotnChars,robotNameString);

					 for(i = 0; i < size; i++)
					 {
						//Get the next robot from the list
						 jobject element = (*env)->CallObjectMethod(env,robotList,getElement, i);
						 jobject otherRobotName = (*env)->CallObjectMethod(env,element,getRobotName);


						jsize otherRobotnChars=(*env)->GetStringLength(env,otherRobotName);
						jsize otherRobotnBytes=(*env)->GetStringUTFLength(env,otherRobotName);

						char otherRobotNameString[otherRobotnBytes+1];
						memset(otherRobotNameString,0,otherRobotnBytes+1);


						(*env)->GetStringUTFRegion(env,otherRobotName,0,otherRobotnChars,otherRobotNameString);
						jboolean otherisAlive = (*env)->CallBooleanMethod(env,element,isAlive); 


					if(strcmp(robotNameString, otherRobotNameString)!=0 && otherisAlive == JNI_TRUE)
					 {
						 int myRobotX = (*env)->CallObjectMethod(env,myRobot,getRobotX);
						 int myRobotY = (*env)->CallObjectMethod(env,myRobot,getRobotY);
						 int otherRobotX = (*env)->CallIntMethod(env,element,getRobotX);
						 int otherRobotY = (*env)->CallIntMethod(env,element,getRobotY);
						 	
							if(abs(myRobotX - otherRobotX) <=2 &&abs(myRobotY - otherRobotY) <=2)
							{
							 jboolean shot = (*env)->CallBooleanMethod(env,rc,fire,otherRobotX,otherRobotY);
							}
						 

						 }


					 }
				 }
			 }
				//If exception happens just exit
				 if((*env)->ExceptionCheck(env))
				 {
					 return;
				 }


}


}
