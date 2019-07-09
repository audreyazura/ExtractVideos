/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extractvideos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import static java.lang.Math.log10;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author audreyazura
 */
public class ExtractVideos
{

    public static void main (String[] args)
    {
	extract("/home/audreyazura/Documents/Nijikai/BaseSakuga.tsv");
    }
    
    /**
     * @param p_fileSakuga the address of the sakuga base
     */
    public static void extract(String p_fileSakuga)
    {
	List<Video> videoList;
	
	try
	{
	    File sakugaCSV = new File(p_fileSakuga);
	    File dlFolder = new File(sakugaCSV.getParent() + "/Videos");
	    
	    if (dlFolder.mkdir() || dlFolder.isDirectory())
	    {
		videoList = new SakugaDAO(sakugaCSV).getVideoList();
	    
		int log10nVideo = (int) log10(videoList.size()-1);
		int index = 0;
		for (Video currentVid: videoList)
		{
		    String zeroPrefix = new String();
		    int nZeros = log10nVideo - (int) log10(index > 0 ? index : 1);
		    for (int i = 0 ; i < nZeros ; i+=1)
		    {
			zeroPrefix += "0";

		    }
		    String vidIndex = zeroPrefix + index;

		    currentVid.downloadVideo(dlFolder.toString(), vidIndex);
		    currentVid.makeSub(dlFolder.toString(), vidIndex);

		    index += 1;
		}
	    }
	    
	}
	catch (FileNotFoundException ex)
	{
	    Logger.getLogger(ExtractVideos.class.getName()).log(Level.SEVERE, null, ex);

	}
	catch (IOException ex)
	{
	    Logger.getLogger(ExtractVideos.class.getName()).log(Level.SEVERE, null, ex);
	}	
    }
    
}
