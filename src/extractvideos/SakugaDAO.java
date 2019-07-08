/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extractvideos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Math.log10;
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
     * @param p_fileAddress the address of the sakuga database file
     */
    public SakugaDAO(int p_nVideos, String p_fileAddress) throws FileNotFoundException, IOException, MalformedURLException
    {
	BufferedReader sakugaRead = new BufferedReader(new FileReader(p_fileAddress));
	int log10nVideo = (int) log10(p_nVideos);
	
	String line;
	int index = 0;
	while ((line = sakugaRead.readLine()) != null)
	{	    
	    if (index-1 >= 0)
	    {
		String[] cutSplit = line.split("\t");
	    
		URL link;
		if (cutSplit[3].isEmpty())
		{
		    link = new URL("https://www.sakugabooru.com/post/show/" + cutSplit[2]);
		}
		else
		{
		    link = new URL(cutSplit[3]);
		}
		
		String zeroPrefix = new String();
		if (index > 1)
		{
		    int nZeros = log10nVideo - (int) log10(index-1);
		    for (int i = 0 ; i < nZeros ; i+=1)
		    {
			zeroPrefix += "0";
		    }
		}
		else if (index == 1)
		{
		    zeroPrefix = "00";
		}
		String cutIndex = zeroPrefix + (index-1);
		m_videoList.add(new Video(cutIndex, cutSplit[1], cutSplit[0], link));
	    }
	
	    index += 1;
	}
	
	sakugaRead.close();
    }
    
    public List<Video> getVideoList()
    {
	return m_videoList;
    }
    
}
