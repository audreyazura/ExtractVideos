/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extractvideos;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author audreyazura
 */
public class Video
{
    private final String m_videoID;
    private final URL m_link;
    private boolean m_exists;
    
    public Video (String p_authors, String p_anime, URL p_page) throws MalformedURLException, IOException
    {
	m_videoID = p_authors + " (" + p_anime + ")";
	m_exists = true;
	m_link = findLink(p_page);
    }
    
    private URL findLink (URL p_link) throws MalformedURLException, IOException
    {
	//by default, we initialize the video as the passed link
	URL videoLink = new URL(p_link.toString());
	Pattern videoLinkPatttern = Pattern.compile("https://sakugabooru.com/data/[a-z0-9]*.(m|w)(p|e)(4|bm)");
	
	//if the passed url is hosted on sakugabooru, then it is not a direct link to the video. So, we download the page and find the video link in it.
	if (p_link.getHost().equalsIgnoreCase("www.sakugabooru.com"))
	{
	    try
	    {
		BufferedInputStream booruPage = new BufferedInputStream(p_link.openStream());
	
		int readByte;
		boolean foundAddress = false;
		while (((readByte = booruPage.read()) != -1) && !(foundAddress))
		{
		    char readChar = (char) readByte;
		    String readLine = new String("");

		    while (readChar != '\n')
		    {
			readLine += readChar;
			readChar = (char) booruPage.read();
		    }

		    Matcher linkMatcher = videoLinkPatttern.matcher(readLine);
		    if (foundAddress = linkMatcher.find())
		    {
			videoLink = new URL(linkMatcher.group());
		    }
		}

		booruPage.close();
	    } catch (FileNotFoundException notFoundErr)
	    {
		System.err.println("Warning: no file found for the cut " + m_videoID + " at link: " + p_link);
		m_exists = false;
	    }
	}
	
	return videoLink;
    }
    
    public void downloadVideo (String p_Folder, String p_videoIndex) throws IOException
    {
	try
	{
	    BufferedInputStream videoStream = new BufferedInputStream(m_link.openStream());
	    Matcher fileTypeMatcher = Pattern.compile("(m|w)(p|e)(4|bm)").matcher(m_link.getPath());

	    if (fileTypeMatcher.find())
	    {
		BufferedOutputStream videoOut = new BufferedOutputStream(new FileOutputStream(p_Folder + "/" + p_videoIndex + " - " + m_videoID + "." + fileTypeMatcher.group()));
		int readByte;

		while((readByte = videoStream.read()) != -1)
		{
		    videoOut.write(readByte);
		}

		videoOut.flush();
		videoOut.close();
	    }
	    else
	    {
		throw new IOException("Video type cannot be found in link:" + m_link.toString());
	    }
	    
	    videoStream.close();
	    
	} catch (FileNotFoundException notFoundErr)
	{
	    if (!(m_link.getHost().equalsIgnoreCase("www.sakugabooru.com")))
	    {
		System.err.println("Warning: no file found for the cut " + m_videoID + " at link: " + m_link);
	    }
	}
    }
    
    public void makeSub (String p_Folder, String p_videoIndex) throws IOException
    {
	BufferedWriter subBuffer = new BufferedWriter(new FileWriter(p_Folder + "/" + p_videoIndex + " - " + m_videoID + ".srt"));
	
	subBuffer.write("1");
	subBuffer.newLine();
	subBuffer.write("00:00:00,0 --> 99:00:00,0");
	subBuffer.newLine();
	subBuffer.write(m_videoID);
	
	subBuffer.flush();
	subBuffer.close();

    }
    
    public boolean toDownload ()
    {
	return m_exists;
    }
}
