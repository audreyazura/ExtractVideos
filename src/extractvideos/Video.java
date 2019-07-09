/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extractvideos;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
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
    
    public Video (String p_authors, String p_anime, URL p_page) throws MalformedURLException, IOException
    {
	m_videoID = p_authors + " (" + p_anime + ")";
	m_link = findLink(p_page);
    }
    
    private URL findLink (URL p_link) throws MalformedURLException, IOException
    {
	URL videoLink = new URL(p_link.toString());
	Pattern videoLinkPatttern = Pattern.compile("https://sakugabooru[.]com/data/[a-z0-9]*[.](m|w)(p|e)(4|bm)");
	
	if (Pattern.matches(p_link.getHost(), "www.sakugabooru.com"))
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
	}
	
	return videoLink;
    }
    
    public void downloadVideo (String p_Folder, String p_videoIndex)
    {
	//find extension from m_link (should be of the form [address].webm or [address].mp4)
	//fileName = p_Folder + p_videoIndex + " - " + m_videoID + "." + extension
	//download video from m_link in fileName
	
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

    }
}
