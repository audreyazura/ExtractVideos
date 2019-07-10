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
 * Class creating the Array List containing all the information about the videos from the file enetered by the user
 * @author audreyazura
 */
public class SakugaDAO
{
    private final List<Video> m_videoList = new ArrayList<>();
    
    /**
     * Construct the video Array List from a file entered by the user.
     * <p>
     * /!\ The column in the file must be separated by tabulation. /!\
     * @param p_fileBase the address of the sakuga database file
     * @throws FileNotFoundException
     * @throws IOException
     * @throws MalformedURLException
     */
    public SakugaDAO(File p_fileBase) throws FileNotFoundException, IOException, MalformedURLException
    {
	BufferedReader sakugaRead = new BufferedReader(new FileReader(p_fileBase));
	
	String line;
	while ((line = sakugaRead.readLine()) != null)
	{	    
	    String[] cutSplit = line.split("\t");
	    
	    //if the line is not empty, and if it is not the line with the title of the column
	    if (!(cutSplit.length == 1) && !(cutSplit[3].equals("Lien FTP")))
	    {
		URL link;
		//if there is a direct link to the cut, we take it. Otherwise, we take the booru link
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
    
    /**
     * Send the Array List constructed from the user selected file
     * @return the constructed Array List of Video
     */
    public List<Video> getVideoList()
    {
	return m_videoList;
    }
    
}
