import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * -------------------------------------------------
 * File name: TestGettingImages.java
 * Project name: Migration
 * -------------------------------------------------
 * Creator's name: Joe Programmer
 * Email: jprogrammer@northeaststate.edu
 * Course and section: CISP 1020 A01
 * Creation date: May 24, 2018
 * -------------------------------------------------
 */

/**
 * <b>[Just for testing]</b>
 * <hr>
 * Date created: May 24, 2018
 * <hr>
 * @author Joshua Trimm
 */
public class TestGettingImages
{

	static ArrayList<String> images = new ArrayList<String> ();
	/**
	 * Method description: 
	 * Date: May 24, 2018
	 * @param args
	 * @return void
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException
	{
		String directory = "C:/Users/Joshua/Dropbox (Personal)/white-performance/scrap";
		// TODO Auto-generated method stub
		
		String url = "https://shop.whiteperformance.com";

		String pageTitle = getPageTitle(url);
		
		//System.out.println(images);
		
		CreatDirectory newDirectory = new CreatDirectory(directory, pageTitle);
		
		Boolean check = newDirectory.checkDirectory(directory, pageTitle);
		System.out.println(newDirectory.checkDirectory(directory, pageTitle));
		int check2 = newDirectory.fileContentExist(directory, pageTitle);
		System.out.println(check2);
		if(check2 == 0)
		{

			getsImages(url, newDirectory.toString());
		//getsImages(url, newDirectory.toString());
		}


		

	}


	public static String getPageTitle(String url) throws IOException
	{
		
		
		//connects to the url
		Document doc = Jsoup.connect(url).get();

		//gets the elements with the image tag
		Elements pageTitle = doc.getElementsByTag("title");
		
		String titleToString = pageTitle.toString();
		
		//String titleToString = pageTitle.attr("abs:title");
		
		StripHtmlTags stripTitle = new StripHtmlTags(titleToString);

		System.out.println("Page title found!");
		
		System.out.println(stripTitle.toString());
		
		return stripTitle.toString();
	
	}
	public static void getsImages(String url, String newDirectory) throws IOException
	{
		Document doc = Jsoup.connect(url).get();

		Elements imgs = doc.getElementsByTag("img");
		System.out.println("Images Found!");

		String test = imgs.toString();
		
		writeToFile(newDirectory.toString(), url);


		for(Element img : imgs)
		{
			String imgAddress = img.absUrl("src");


			String makeImgString = img.toString();
			if(!makeImgString.contains("NEW_WS_HEADER-2(2)") && !makeImgString.contains("customerlobby") && 
					!makeImgString.contains("facebook") && !makeImgString.contains("paypal") &&
					!makeImgString.contains("visa") && !makeImgString.contains("master") &&
					!makeImgString.contains("discover") && !makeImgString.contains("am_ex.gif") &&
					!makeImgString.contains("qsc_badge"))
			{
				SaveImages testing = new SaveImages(newDirectory.toString(), imgAddress);
				//System.out.println(testing);
				
			}

			//return imgAddress;

		}

		TimerTask timerTask = new MyTimerTask();
		//running timer task as daemon thread
		Timer timer = new Timer(true);
		timer.scheduleAtFixedRate(timerTask, 0, 2*1000);
		System.out.println("TimerTask started");
		//cancel after sometime
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		timer.cancel();
		System.out.println("TimerTask cancelled");
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
	public static void writeToFile(String directory, String url) throws IOException
	{

		PrintWriter savingPoint = new PrintWriter(directory.trim() + "\\pageTitle.txt");

		String pageTitle = getPageTitle(url).toString();

		String writingInfromtion = "[ " + pageTitle + ", " + url + "]";

		System.out.println("Writing to file: " + writingInfromtion);
		
		savingPoint.write(writingInfromtion);

		savingPoint.flush();

		savingPoint.close();

	}

}
