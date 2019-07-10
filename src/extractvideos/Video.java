/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extractvideos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
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
    
    public Video (String p_authors, String p_anime, URL p_page) throws MalformedURLException, IOException
    {
	m_videoID = p_authors + " (" + p_anime + ")";
	m_link = findLink(p_page);
    }
    
    private URL findLink (URL p_link) throws MalformedURLException, IOException
    {
	URL videoLink = null;
	Pattern videoLinkPatttern = Pattern.compile("https://sakugabooru.com/data/[a-z0-9]*.(m|w)(p|e)(4|bm)");
	
	//if the passed url is hosted on sakugabooru, then it is not a direct link to the video. So, we download the page and find the video link in it.
	if (p_link.getHost().equalsIgnoreCase("www.sakugabooru.com"))
	{
	    try
	    {
		BufferedReader booruPage = new BufferedReader(new InputStreamReader(p_link.openStream()));
	
		String dataLine;
		boolean foundAddress = false;
		while (((dataLine = booruPage.readLine()) != null) && !(foundAddress))
		{
		    Matcher linkMatcher = videoLinkPatttern.matcher(dataLine);
		    if (foundAddress = linkMatcher.find())
		    {
			videoLink = new URL(linkMatcher.group());
		    }
		}

		booruPage.close();
		
	    } 
	    catch (FileNotFoundException notFoundErr)
	    {
		System.err.println("Warning: no file found for the cut " + m_videoID + " at link: " + p_link);
	    }
	} 
	else //the video link is passed as a ftp link
	{
	    videoLink = p_link;
	}
	
	return videoLink;
    }
    
    public void downloadVideo (String p_Folder, String p_videoIndex) throws IOException
    {
	
	Matcher fileTypeMatcher = Pattern.compile("(m|w)(p|e)(4|bm)").matcher(m_link.getPath());

	if (fileTypeMatcher.find())
	{
	    String fileLink = p_Folder + "/" + p_videoIndex + " - " + m_videoID + "." + fileTypeMatcher.group();

	    System.out.println("Downloading " + fileLink);
	    
	    try
	    {
		InputStream videoStream = m_link.openStream();
		Files.copy(videoStream, new File(fileLink).toPath());
		videoStream.close();
	    } 
	    catch (FileNotFoundException notFoundErr)
	    {
		if (!(m_link.getHost().equalsIgnoreCase("www.sakugabooru.com")))
		{
		    System.err.println("Warning: no file found for the cut " + m_videoID + " at link: " + m_link);
		}
	    }	
	}
	else
	{
	    throw new IOException("Video type cannot be found in link:" + m_link.toString());
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
	return (m_link != null);
    }
}
