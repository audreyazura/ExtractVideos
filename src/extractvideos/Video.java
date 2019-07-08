/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extractvideos;

import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author audreyazura
 */
public class Video
{
    private final String m_fileName;
    private final String m_caption;
    private final URL m_link;
    
    public Video (String p_vidIndex, String p_authors, String p_anime, URL p_page) throws MalformedURLException
    {
	m_caption = p_authors + " (" + p_anime + ")";
	m_link = findLink(p_page);
	//to be modified: use the link to the video to find the video format
	m_fileName = p_vidIndex + " - " + m_caption;
    }
    
    private URL findLink (URL p_link) throws MalformedURLException
    {
	URL videoLink;
	
	//download page source
	//find video link in page
	//videoLink = pageLink;
	
	return p_link;
    }
    
    public void downloadVideo (String p_localFolder)
    {
	//download video from m_link in p_localFolder
    }
    
    public void makeSub (String p_localFolder)
    {
	//create subtitle file
    }
}
