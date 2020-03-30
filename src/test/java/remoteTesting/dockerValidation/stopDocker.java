package remoteTesting.dockerValidation;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;


public class stopDocker {


	public void stopFile() throws IOException, InterruptedException
	{
		
	boolean isDockerServerDown=false;
		Runtime runtime= Runtime.getRuntime();
		runtime.exec("cmd /c start dockerDown.bat");
		
	String DockerServerLoggerForForcingDownTheIMGS ="DockerServerLogs.txt";
	
	Calendar cal=Calendar.getInstance();//2:44 15th second
	cal.add(Calendar.SECOND, 45);//2:44   45seconds
	long stopnow=cal.getTimeInMillis();
	Thread.sleep(3000);
	
	while(System.currentTimeMillis()<stopnow)
	{
		if(isDockerServerDown)
		{
			break;
		}
		
		BufferedReader reader=new BufferedReader(new FileReader(DockerServerLoggerForForcingDownTheIMGS));
		String currentLine=reader.readLine();
	while(currentLine!=null && !isDockerServerDown)
		
	{
		
		if(currentLine.contains("selenium-hub exited"))
		{
			System.out.println("The Docker server with Selenium Grid is down.");
			isDockerServerDown=true;//14th seconds
			break;
		}
		
		 currentLine=reader.readLine();
	}
	reader.close();
	
	}
	
Assert.assertTrue(isDockerServerDown);

}
	
	
}
