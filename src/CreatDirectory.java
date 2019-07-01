import java.io.File;

/**
 * -------------------------------------------------
 * File name: CreatDirectory.java
 * Project name: Migration
 * -------------------------------------------------
 * Creator's name: Joshua Trimm
 * Email: joshua@trimwebdesign.com
 * Creation date: May 25, 2018
 * -------------------------------------------------
 */

/**
 * <b>[This class is for creating an object that can create a local directory]</b>
 * <hr>
 * Date created: May 25, 2018
 * <hr>
 * @author Joshua Trimm
 */
public class CreatDirectory 
{
	String directory = "";
	String pageTitle = "";

	/**
	 * Description: Overloaded Constructor that will get the title and 
	 * replace any characters that can not be save. It will also create
	 * create a new directory for each new product found.
	 * Date: May 25, 2018
	 */
	public CreatDirectory(String directory, String pageTitle)
	{

		
		this.directory = directory;
		this.pageTitle = pageTitle;
		
		//replace all the unwanted characters with the correct ones. 
		String titleForDirectorSave = pageTitle.replace(" ", "-").replace("*", "")
				.replace("\\", "-").replace("/", "-").replace(",", "")
				.replace("\"", "").replace("|", "").replace("#", "HASHTAG").replace(":", "");
		
		//This will create a new directory for each product
		File createNewFileToSaveImages = new File(directory + "/" + titleForDirectorSave);
		
		//Check to see if the directory has been created.
		if(createNewFileToSaveImages.mkdir() == true)
		{
			System.out.println("Created directory: " + createNewFileToSaveImages);
		}
		else
		{
			System.out.println("This directory exisists: " + createNewFileToSaveImages);
		}
	}
	public boolean checkDirectory(String directory, String pageTitle)
	{
		this.directory = directory;
		this.pageTitle = pageTitle;
		
		//replace all the unwanted characters with the correct ones. 
		String titleForDirectorSave = pageTitle.replace(" ", "-").replace("*", "")
				.replace("\\", "-").replace("/", "-").replace(",", "")
				.replace("\"", "").replace("|", "").replace("#", "HASHTAG").replace(":", "");
		
		//This will create a new directory for each product
		File createNewFileToSaveImages = new File(directory + "/" + titleForDirectorSave);
		
		//Check to see if the directory has been created.
		if(createNewFileToSaveImages.exists())
		{
			return true;
		}
		return false;
	}
	public int fileContentExist(String directory, String pageTitle)
	{
		this.directory = directory;
		this.pageTitle = pageTitle;
		
		//replace all the unwanted characters with the correct ones. 
		String titleForDirectorSave = pageTitle.replace(" ", "-").replace("*", "")
				.replace("\\", "-").replace("/", "-").replace(",", "")
				.replace("\"", "").replace("|", "").replace("#", "HASHTAG").replace(":", "");
		
		//This will create a new directory for each product
		File createNewFileToSaveImages = new File(directory + "/" + titleForDirectorSave);
		
		int checking = createNewFileToSaveImages.list().length;
		return checking;
	}
	/**
	 * Description:
	 * Date: May 25, 2018
	 * @return the directory
	 */
	public String getDirectory()
	{
		return directory;
	}

	/**
	 * Description:
	 * Date: May 25, 2018
	 * @param directory the directory to set
	 */
	public void setDirectory(String directory)
	{
		this.directory = directory;
	}

	/**
	 * Description:
	 * Date: May 25, 2018
	 * @return the pageTitle
	 */
	public String getPageTitle()
	{
		return pageTitle;
	}

	/**
	 * Description:
	 * Date: May 25, 2018
	 * @param pageTitle the pageTitle to set
	 */
	public void setPageTitle(String pageTitle)
	{
		this.pageTitle = pageTitle;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return " " + directory + "/" + pageTitle.replace(" ", "-").replace("*", "")
				.replace("\\", "-").replace("/", "-").replace(",", "")
				.replace("\"", "").replace("|", "").replace("#", "HASHTAG").replace(":", "");
	}

}
