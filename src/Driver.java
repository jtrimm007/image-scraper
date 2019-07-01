import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * -------------------------------------------------
 * File name: Driver.java
 * Project name: Migration
 * -------------------------------------------------
 * Creator's name: Joshua Trimm
 * Email: joshua@trimwebdesign.com
 * Creation date: May 24, 2018
 * -------------------------------------------------
 */

/**
 * <b>[This program is designed to scrap images from a specific website.
 * Then, it saves them to the stated address on a local drive.]</b>
 * <hr>
 * Date created: May 24, 2018
 * <hr>
 * @author Joshua Trimm
 */
public class Driver
{
	public static String checkingForExistingDirectory;
	public static String directory = "C:/Users/Joshua/Dropbox (Personal)/white-performance/scrap";
	public static Queue<String> listOfPageLinks = new LinkedList<String> ();
	public static ArrayList<String> listOfVisitedPages = new ArrayList<String> ();
	/**
	 * Method description: 
	 * Date: May 24, 2018
	 * @param args
	 * @return void
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException
	{

		String startingPointUrl = "https://shop.whiteperformance.com/Home_c1.htm?page=all";


		// TODO Auto-generated method stub
		System.out.println(checkForCorrectDiv(startingPointUrl));
		System.out.println();

		addPageLinksToQueue(startingPointUrl);

		for(String print : listOfPageLinks)
		{
			System.out.println(print);
		}



		//iterate over the Queue string of listOfPageLinks. It will throw and exception if it doesn't have ".hasNext()"
		while(listOfPageLinks.iterator().hasNext())
			runThroughTheQueue(listOfPageLinks);




	}

	/**
	 * 
	 * Method description: This method gets the page title and strips all
	 * the html and white space away.
	 * Date: May 25, 2018
	 * @param url
	 * @throws IOException
	 * @return String
	 */
	public static String getPageTitle(String url) throws IOException
	{
		//connects to the url
		Document doc = Jsoup.connect(url).get();

		//gets the elements with the image tag
		Elements pageTitle = doc.getElementsByTag("title");

		//Turn the page title into a string
		String titleToString = pageTitle.toString();

		//Start a new StripHtmlTags object
		StripHtmlTags stripTitle = new StripHtmlTags(titleToString);

		System.out.println("Page title found!");

		System.out.println(stripTitle.toString().trim());

		//return the page title as a string so it can be use for the CreatDirectory object
		return stripTitle.toString();
	}
	/**
	 * 
	 * Method description: Might need to delete this method
	 * Date: May 26, 2018
	 * @param directory
	 * @param pageTitle
	 * @return
	 * @return Boolean
	 */
	public static Boolean checkForDirectory(String directory, String pageTitle)
	{
		File newDirectory = new File(directory + pageTitle);

		boolean exists = newDirectory.exists();

		if(exists)
		{
			return true;
		}

		return false;
	}
	/**
	 * 
	 * Method description: Finds the correct images on the page. Then,
	 * passes them to the SaveImages object to be saved.
	 * Date: May 25, 2018
	 * @param url
	 * @throws IOException
	 * @return void
	 */
	public static void getsImages(String url, String newDirectory) throws IOException
	{
		//connects to the url
		Document doc = Jsoup.connect(url).get();

		//gets the elements with the image tag
		Elements imgs = doc.getElementsByTag("img");

		//Saves the page title and url to a text document
		writeToFile(newDirectory.toString(), url);

		System.out.println("Images Found!");

		//Start looping through the images
		for(Element img : imgs)
		{
			//This strips away all the html stuff
			String imgAddress = img.absUrl("src");

			//Turn the img Element into a string for the if statement
			String makeImgString = img.toString();

			//if statement to filter unwanted images
			if(!makeImgString.contains("NEW_WS_HEADER-2(2)") && !makeImgString.contains("customerlobby") && 
					!makeImgString.contains("facebook") && !makeImgString.contains("paypal") &&
					!makeImgString.contains("visa") && !makeImgString.contains("master") &&
					!makeImgString.contains("discover") && !makeImgString.contains("am_ex.gif") &&
					!makeImgString.contains("qsc_badge") && !makeImgString.contains("priority") && 
					!makeImgString.contains("ups") && !makeImgString.contains("bucket") && 
					!makeImgString.contains("proheader") && !makeImgString.contains("data:image/png;base64"))
			{

				try
				{

					//Instaniate the SaveImages object to save the images that passed through the if statement
					SaveImages savingTheImages = new SaveImages(newDirectory.toString(), imgAddress);

				}
				catch(FileNotFoundException error)
				{
					System.out.println(error);
					continue;
				}
				catch(UnknownHostException error)
				{
					System.out.println(error);
					continue;
				}
				catch(IOException error)
				{
					System.out.println(error);
					continue;
				}

			}

		}

		//Instantiate a new Timer object.
		TimerTask timerTask = new MyTimerTask();

		//running timer task as daemon thread
		Timer timer = new Timer(true);

		//Start the schedule
		timer.scheduleAtFixedRate(timerTask, 0, 5*1000);

		System.out.println("TimerTask started");
		//cancel after sometime
		try {
			Thread.sleep(1200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		timer.cancel();
		System.out.println("TimerTask cancelled");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * Method description: This method is used for writing to a file. It records
	 * the page title and url infromation. And will be used for locating the
	 * product in the database.
	 * Date: May 30, 2018
	 * @param directory
	 * @param url
	 * @throws IOException
	 * @return void
	 */
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
	/**
	 * 
	 * Method description: This method is not in use. 
	 * Date: May 30, 2018
	 * @return
	 * @throws IOException
	 * @return String
	 */
	public static String readFileCheck() throws IOException
	{
		String line = null;

		String page = listOfVisitedPages.toString();


		FileReader fileReader = new FileReader("Save-Point.txt");

		BufferedReader bufferedReader = new BufferedReader(fileReader);

		while((line = bufferedReader.readLine()) != null)
		{
			String[] splitting = line.split(",");
			for(int a = 0; a < (splitting.length - 1); a++)
			{
				System.out.println(splitting[a]);

				//a++;

				return splitting[a];
			}
		}


		bufferedReader.close();
		return "Visited Pages: " + page.toString();
	}
	/**
	 * 
	 * Method description: This method starts running through the queue. It
	 * checks each item to see if it has been checked for the correct div and 
	 * then starts adding the images if everything checks out. It also removes
	 * the checked items from the queue.
	 * Date: May 24, 2018
	 * @param list
	 * @throws IOException
	 * @return void
	 */
	public static void runThroughTheQueue(Queue<String> list) throws IOException
	{
		//change arraylist to string for the if statement
		String page = listOfVisitedPages.toString();


		System.out.println("Checking the peek...");

		try
		{
			//Start checking the links on the next page in queue
			addPageLinksToQueue(list.peek().toString());


			//Check to see if page has been checked before
			if(!page.contains(list.peek()))
			{
				//get the head of the queue
				String peekItem = list.peek();

				System.out.println("Adding " + peekItem);

				//add the head to the arraylist listOfVisitedPages
				listOfVisitedPages.add(peekItem);


				//Remove the head of the queue
				String removedPollItem = list.remove();

				System.out.println(removedPollItem.toString() + " removed from queue.");

				//Check to see if the page has the correct div to pull images
				if(checkForCorrectDiv(removedPollItem) == true)
				{
					//put the getPageTitle into a string for the CreatDiriectory object
					String pageTitle = getPageTitle(removedPollItem);

					//Instantiate new CreatDirectory object.
					CreatDirectory newDirectory = new CreatDirectory(directory, pageTitle);

					//Boolean checkForDirectory = newDirectory.checkDirectory(directory, pageTitle);

					//write the page title to a file in the products directory

					int checkingForFiles = newDirectory.fileContentExist(directory, pageTitle);
					//System.out.println("Does file directory exist? " + checkForDirectory);

					if(checkingForFiles == 0)
					{
						

						System.out.println("Start collecting images");

						//Pass the head of the queue into the getsImages to return all the image src's along with the new directory
						getsImages(removedPollItem, newDirectory.toString());
					}



				}
				else 
				{
					System.out.println("Item is false. Check next item");
				}
			}
			else //Item is in the arraylist wich means it has been checked. Still must remove the item from queue because if it is in the arraylist it needs to be removed from the queue
			{
				//remove the item that is in the arraylist from the queue
				String removedPollItem = list.remove();

				//System.out.println(list.peek() + " was not added.");

				System.out.println("dequeue " + removedPollItem);

			}
			//}
			//readSavePoint.close();

		}
		catch(SocketTimeoutException error)
		{
			System.out.println(error);

		}
	}
	/**
	 * 
	 * Method description: Get all of the links on the page that stay on shop.whiteperformance.com. Also 
	 * set statement that if there are two of the same link don't add it to the queue.
	 * Date: May 24, 2018
	 * @param url
	 * @throws IOException
	 * @return void
	 */
	public static void addPageLinksToQueue(String url) throws IOException
	{
		//set a new url
		URL urlToParse = new URL(url);

		//Need to parse the time to get the links
		Document docToParse = Jsoup.parse(urlToParse, 3*1000);

		//Selet ahrefs to parse
		Elements links = docToParse.select("a[href]");

		//Java string for if statement
		String checkForOfPageLinks = "java";

		System.out.println("List of Visited Pages " + listOfVisitedPages.toString());

		for(Element link : links)
		{
			String stripHtml = link.attr("abs:href");
			if(!listOfVisitedPages.contains(stripHtml))
			{
				System.out.println("Checking Link: " + stripHtml);
				System.out.println();

				String castLink = stripHtml.toString();
				if(!listOfPageLinks.contains(stripHtml) && !stripHtml.contains(checkForOfPageLinks) 
						&& stripHtml.contains("https://shop.whiteperformance.com"))
				{

					listOfPageLinks.add(stripHtml);

					System.out.println(castLink + " added");
					System.out.println();
				}
			}

		}
	}
	/**
	 * 
	 * Method description: Checks the page for the correct div class, "product-description ", and returns a boolean value based upon that information. 
	 * Date: May 24, 2018
	 * @param url
	 * @return
	 * @throws IOException
	 * @return boolean
	 */
	public static boolean checkForCorrectDiv(String url) throws IOException
	{

		Document doc = Jsoup.connect(url).get();

		Elements correctDiv = doc.getElementsByClass("product-description ");

		System.out.println("Checking for Correct Div...");

		for(Element div : correctDiv)
		{
			//System.out.println(div);
			//System.out.println("done");

			if(!div.equals(null))
			{
				return true;
			}
		}
		return false;
	}
}
