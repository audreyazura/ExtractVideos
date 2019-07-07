/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extractvideos;

import java.util.List;

/**
 *
 * @author audreyazura
 */
public class ExtractVideos
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
	String dlFolder = "";
	
	List<Video> videoList = new SakugaDAO(/*addresse*/).getVideoList();
	
	for (Video currentVid: videoList)
	{
	    currentVid.downloadVideo(dlFolder);
	    currentVid.makeSub(dlFolder);
	}
    }
    
}
