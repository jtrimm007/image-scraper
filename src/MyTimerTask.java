import java.util.Date;
import java.util.TimerTask;

/**
 * -------------------------------------------------
 * File name: MyTimerTask.java
 * Project name: Migration
 * -------------------------------------------------
 * Creator's name: Joshua Trimm
 * Email: joshua@trimwebdesign.com
 * Creation date: May 24, 2018
 * -------------------------------------------------
 */

/**
 * <b>[Thread object to space out the time of pinging the website]</b>
 * <hr>
 * Date created: May 24, 2018
 * <hr>
 * @author Joshua Trimm
 */
public class MyTimerTask extends TimerTask
{
	@Override
    public void run() {
        System.out.println("Timer task started at:"+new Date());
        completeTask();
        System.out.println("Timer task finished at:"+new Date());
    }

    private void completeTask() {
        try {
            //assuming it takes 20 secs to complete the task
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
