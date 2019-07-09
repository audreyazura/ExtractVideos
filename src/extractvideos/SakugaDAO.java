/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extractvideos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author audreyazura
 */
public class SakugaDAO
{
    private final List<Video> m_videoList = new ArrayList<>();
    
    /**
     * @param p_nVideos Approximate number of video entered in the base
     * @param p_fileBase the address of the sakuga database file
     */
    public SakugaDAO(File p_fileBase) throws FileNotFoundException, IOException, MalformedURLException
    {
	BufferedReader sakugaRead = new BufferedReader(new FileReader(p_fileBase));
	
	String line;
	while ((line = sakugaRead.readLine()) != null)
	{	    
	    String[] cutSplit = line.split("\t");
	    
	    if (!(cutSplit[3].equals("Lien FTP")))
	    {
		URL link;
		if (cutSplit[3].isEmpty())
		{
		    link = new URL("https://www.sakugabooru.com/post/show/" + cutSplit[2]);
		}
		else
		{
		    link = new URL(cutSplit[3]);
		}

		m_videoList.add(new Video(cutSplit[1], cutSplit[0], link));
	    }
	}
	
	sakugaRead.close();
    }
    
    public List<Video> getVideoList()
    {
	return m_videoList;
    }
    
}
