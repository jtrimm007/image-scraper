/**
 * -------------------------------------------------
 * File name: StripHtmlTags.java
 * Project name: Migration
 * -------------------------------------------------
 * Creator's name: Joshua Trimm
 * Email: joshua@trimwebdesign.com
 * Creation date: May 25, 2018
 * -------------------------------------------------
 */

/**
 * <b>[This class is used to strip all the html syntax and tags away from
 * a specific element.]</b>
 * <hr>
 * Date created: May 25, 2018
 * <hr>
 * @author Joshua Trimm
 */
public class StripHtmlTags
{
	String html = "";
	StringBuilder stringBuilder = new StringBuilder();
	/**
	 * 
	 * Description: Removes all the html from the stringbuilder
	 * @param html
	 * Date: May 25, 2018
	 */
	public StripHtmlTags(String html)
	{
		this.html = html;
		stringBuilder.append(html);
		
		int open;

		while((open = stringBuilder.indexOf("<")) != -1)
		{
			int close = stringBuilder.indexOf(">", open + 1);
			stringBuilder.delete(open, close + 1);

		}
	}

	/**
	 * 
	 * Description: Constructor
	 * Date: May 25, 2018
	 */
	public StripHtmlTags(String html, StringBuilder stringBuilder)
	{
		// TODO Auto-generated constructor stub
		this.html = html;
		this.stringBuilder = stringBuilder;
	}

	/**
	 * Description:
	 * Date: May 25, 2018
	 * @return the html
	 */
	public String getHtml()
	{
		return html;
	}

	/**
	 * Description:
	 * Date: May 25, 2018
	 * @param html the html to set
	 */
	public void setHtml(String html)
	{
		this.html = html;
	}

	/**
	 * Description:
	 * Date: May 25, 2018
	 * @return the stringBuilder
	 */
	public StringBuilder getStringBuilder()
	{
		return stringBuilder;
	}

	/**
	 * Description:
	 * Date: May 25, 2018
	 * @param stringBuilder the stringBuilder to set
	 */
	public void setStringBuilder(StringBuilder stringBuilder)
	{
		this.stringBuilder = stringBuilder;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return stringBuilder.toString().trim();
	}

}
