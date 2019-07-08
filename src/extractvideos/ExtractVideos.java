/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extractvideos;

import java.io.FileNotFoundException;
import java.io.IOException;
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
	extract(652, "/home/audreyazura/Dev/Source/NetbeansProject/BaseSakuga.tsv");
    }
    
    /**
     * @param p_nCuts the maximum number of video in the base
     * @param p_sakugaCSV the address of the sakuga base
     */
    public static void extract(int p_nCuts, String p_sakugaCSV)
    {
	String dlFolder = "test";
	
	 List<Video> videoList;
	try
	{
	    videoList = new SakugaDAO(p_nCuts, p_sakugaCSV).getVideoList();
	    
	    for (Video currentVid: videoList)
	    {
		currentVid.downloadVideo(dlFolder);
		currentVid.makeSub(dlFolder);
	    }
	    
	} catch (FileNotFoundException ex)
	{
	    Logger.getLogger(ExtractVideos.class.getName()).log(Level.SEVERE, null, ex);

	}
	catch (IOException ex)
	{
	    Logger.getLogger(ExtractVideos.class.getName()).log(Level.SEVERE, null, ex);
	}	
    }
    
}
