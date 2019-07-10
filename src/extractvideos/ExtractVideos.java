/*
 * Copyright (C) 2019 audreyazura
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
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
 * Class coordinating the video extraction
 * @author audreyazura
 */
public class ExtractVideos
{

    public static void main (String[] args)
    {
	extract("/home/audreyazura/Documents/Nijikai/BaseSakuga.tsv");
    }
    
    /**
     * Main function of the package, coordinating the video downloading from the
     * database passed.
     * @param p_fileSakuga the address of the sakuga base
     */
    public static void extract(String p_fileSakuga)
    {
	List<Video> videoList;
	
	try
	{
	    File sakugaCSV = new File(p_fileSakuga);
	    //we want the video folder to be in the same folder as the sakuga base
	    File dlFolder = new File(sakugaCSV.getParent() + "/Videos");
	    
	    if (dlFolder.mkdir() || dlFolder.isDirectory())
	    {
		videoList = new SakugaDAO(sakugaCSV).getVideoList();
	    
		int log10ListSize = (int) log10(videoList.size()-1);
		int index = 1;
		for (Video currentVid: videoList)
		{
		    if (currentVid.toDownload())
		    {
			//to know the number of zero to put in the front of the index to get the video index, we compare the magnitude of the size of the list to the magnitude of the index
			String zeroPrefix = new String();
			int nZeros = log10ListSize - (int) log10(index);
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
