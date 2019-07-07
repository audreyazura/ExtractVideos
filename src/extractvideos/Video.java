/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extractvideos;

import static java.lang.Math.log10;
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
    
    public Video (int p_vidIndex, String p_authors, String p_anime, URL p_page) throws MalformedURLException
    {
	m_caption = p_authors + " (" + p_anime + ")";
	
	int log10Trunc = (int) log10(p_vidIndex);
	String prefixe = "";
	String suffixe = p_vidIndex + " - " + m_caption;
	switch(log10Trunc)
	{
	    case 0 : prefixe = "00"; break;
	    case 1 : prefixe = "0"; break;	    
	}
	m_fileName = prefixe + suffixe;
	
	m_link = findLink(p_page);
    }
    
    private URL findLink (URL p_link) throws MalformedURLException
    {
	URL videoLink = new URL("");
	
	//download page source
	//find video link in page
	//videoLink = pageLink;
	
	return videoLink;
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
