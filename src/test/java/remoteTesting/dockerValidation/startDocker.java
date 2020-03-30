package remoteTesting.dockerValidation;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Calendar;


public class startDocker {


	public void startFile() throws IOException, InterruptedException
	{
		

//		Runtime runtime= Runtime.getRuntime();
//		runtime.exec("cmd /c start dockerUp.bat");

		boolean isGridStarted=false;
		Runtime runtime= Runtime.getRuntime();
		runtime.exec("cmd /c start dockerUp.bat");

		String DockerServerLoggerUponStart ="DockerServerLogs.txt";

		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.SECOND, 45);
		long stopnow=cal.getTimeInMillis();
		Thread.sleep(3000);

		while(System.currentTimeMillis()<stopnow)
		{
			if(isGridStarted)
			{
				break;
			}

			BufferedReader reader=new BufferedReader(new FileReader(DockerServerLoggerUponStart));
			String currentLine=reader.readLine();
			while(currentLine!=null && !isGridStarted)

			{

				if(currentLine.contains("registered to the hub and ready to use"))
				{
					System.out.println("The grid is started and ready for use");
					isGridStarted=true;
					break;
				}

				currentLine=reader.readLine();
			}
			reader.close();

		}

		Assert.assertTrue(isGridStarted);
		runtime.exec("cmd /c start scale.bat");
		Thread.sleep(15000);




	}
	
	
}
