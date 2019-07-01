import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.Jsoup;

/**
 * -------------------------------------------------
 * File name: SaveImages.java
 * Project name: Migration
 * -------------------------------------------------
 * Creator's name: Joshua Trimm
 * Email: joshua@trimwebdesign.com
 * Creation date: May 25, 2018
 * -------------------------------------------------
 */

/**
 * <b>[This class is to gather the image address and then save it to the
 * desired location. ]</b>
 * <hr>
 * Date created: May 25, 2018
 * <hr>
 * @author Joshua Trimm
 */
public class SaveImages
{

	String url = "";
	String imageSrc = "";
	String directory = "";
	/**
	 * Description:
	 * Date: May 25, 2018
	 * @throws IOException 
	 */
	public SaveImages(String newDirectory, String src) throws IOException, ConnectException, StringIndexOutOfBoundsException
	{
		// TODO Auto-generated constructor stub
		this.url = url;
		String folder = null;

        //Exctract the name of the image from the src attribute
        int indexname = src.lastIndexOf("/");

        if (indexname == src.length()) {
            src = src.substring(1, indexname);
        }

        indexname = src.lastIndexOf("/");
        String name = src.substring(indexname, src.length());

        System.out.println("Saving image: " + name);
        System.out.println("Checking path: " + (newDirectory + name).trim());
        //Open a URL Stream
        URL url = new URL(src);
        InputStream in = url.openStream();

        OutputStream out = new BufferedOutputStream(new FileOutputStream((newDirectory + name).trim()));

        for (int b; (b = in.read()) != -1;) {
            out.write(b);
        }
        out.close();
        in.close();
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "SaveImages [url=" + url + ", imageSrc=" + directory + "\\" + imageSrc + ", directory=" + directory + "]";
	}

	/**
	 * Description:
	 * Date: May 25, 2018
	 * @return the imageSrc
	 */
	public String getImageSrc()
	{
		return imageSrc;
	}

	/**
	 * Description:
	 * Date: May 25, 2018
	 * @param imageSrc the imageSrc to set
	 */
	public void setImageSrc(String imageSrc)
	{
		this.imageSrc = imageSrc;
	}

}
