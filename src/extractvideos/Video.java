/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extractvideos;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author audreyazura
 */
public class Video
{
    private final String m_videoID;
    private final URL m_link;
    
    public Video (String p_authors, String p_anime, URL p_page) throws MalformedURLException
    {
	m_videoID = p_authors + " (" + p_anime + ")";
	m_link = findLink(p_page);
    }
    
    private URL findLink (URL p_link) throws MalformedURLException
    {
	URL videoLink;
	
	//download page source
	//find video link in page
	//videoLink = pageLink;
	
	return p_link;
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
