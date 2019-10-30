#include <jni.h>
#include <stdio.h>
#include <string.h>
JNIEXPORT void JNICALL Java_CRobot_startCPP(JNIEnv*env,jobject this, jobject rc)
{
 //To do:
 	//Get current LRobotInfo
			//copare with rest
	jclass controller=(*env)->GetObjectClass(env,rc);
	jmethodID north=(*env)->GetMethodID(env,controller,"moveNorth","()Z");
	jmethodID east=(*env)->GetMethodID(env,controller,"moveEast","()Z");
	jmethodID south=(*env)->GetMethodID(env,controller,"moveSouth","()Z");
		jmethodID west=(*env)->GetMethodID(env,controller,"moveWest","()Z");
		jmethodID fire=(*env)->GetMethodID(env,controller,"fire","(II)Z");
		jmethodID killRobot=(*env)->GetMethodID(env,controller,"killRobot","()V");

	jmethodID getRobot=(*env)->GetMethodID(env,controller,"getRobot","()LRobotInfo;");

		jmethodID getRobots=(*env)->GetMethodID(env,controller,"getAllRobots","()Ljava/util/List;");

		jarray robotList = (*env)->CallObjectMethod(env,rc,getRobots);
	//	printf("Robot list: %s", robotList);
	jclass robots = (*env)->GetObjectClass(env,robotList);
	jmethodID getElement = (*env)->GetMethodID(env, robots, "get", "(I)Ljava/lang/Object;");

		jmethodID getSize = (*env)->GetMethodID(env, robots, "size", "()I");

		jboolean result;

		jobject size = (*env)->CallIntMethod(env,robotList,getSize);
		jobject element = (*env)->CallObjectMethod(env,robotList,getElement, 1);
		jobject myRobot = (*env)->CallObjectMethod(env,rc,getRobot);
		jclass robotInfo=(*env)->GetObjectClass(env,myRobot);
		jmethodID getRobotName=(*env)->GetMethodID(env,robotInfo,"getName","()Ljava/lang/String;");
		jmethodID getRobotHealth=(*env)->GetMethodID(env,robotInfo,"getHealth","()D");
		jmethodID getRobotX=(*env)->GetMethodID(env,robotInfo,"getX","()I");
		jmethodID getRobotY=(*env)->GetMethodID(env,robotInfo,"getY","()I");



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
					 jobject myRobotName = (*env)->CallObjectMethod(env,myRobot,getRobotName);
					 int myRobotX = (*env)->CallObjectMethod(env,myRobot,getRobotX);
					 int myRobotY = (*env)->CallObjectMethod(env,myRobot,getRobotY);


					 for(i = 0; i < size; i++)
					 {
						 jobject element = (*env)->CallObjectMethod(env,robotList,getElement, i);
						 int otherRobotName = (*env)->CallIntMethod(env,element,getRobotName);
					if(strcmp("myRobotName", "otherRobotName")!=0)
					 {
						 int otherRobotX = (*env)->CallIntMethod(env,element,getRobotX);
						 int otherRobotY = (*env)->CallIntMethod(env,element,getRobotY);
						 	if(abs(myRobotX - otherRobotX) <=2)
							{
								if(abs(myRobotY - otherRobotY) <=2)
								{
							 		jboolean shot = (*env)->CallBooleanMethod(env,rc,fire,otherRobotX,otherRobotY);
								}
						 }

						 }


					 }
				 }
			 }
				 if((*env)->ExceptionCheck(env))
				 {
					 return;
				 }


}


}
