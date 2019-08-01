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
package extractvideos_dlappli;

import extractvideo_GUI.ExtractVideos_GUI;

import java.io.File;
import java.io.IOException;
import static java.lang.Math.log10;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.ConsoleHandler;
import java.util.logging.SimpleFormatter;
import java.util.zip.DataFormatException;
import javafx.application.Platform;

/**
 * Class coordinating the video extraction
 * @author audreyazura
 */
public class ExtractVideos_DlAppli
{

    static Logger cutInfoLogger = Logger.getLogger(ExtractVideos_DlAppli.class.getName());
    
    /**
     * Main function of the package, coordinating the video downloading from the
     * database passed.
     * @param p_fileSakuga the address of the sakuga base
     * @throws java.nio.file.NoSuchFileException
     * @throws java.nio.file.FileAlreadyExistsException
     * @throws java.util.zip.DataFormatException
     */
    public static void extract(String p_fileSakuga) throws IOException, DataFormatException
    {
	List<Video> videoList;
	SimpleFormatter defaultFormatter = new SimpleFormatter();
	
	try
	{
	    File sakugaCSV = new File(p_fileSakuga);
	    String sakugaFolder = sakugaCSV.getParent();
	    //we want the video folder to be in the same folder as the sakuga base
	    File dlFolder = new File(sakugaFolder + "/Videos");
	    
	    if (dlFolder.isDirectory())
	    {
		File renameDestination = new File(dlFolder.getAbsolutePath() + "_OLD");
		
		if (renameDestination.isDirectory())
		{
		    Platform.runLater(() ->
		    {
			ExtractVideos_GUI.popupInfo("Les dossiers \"Video\" et \"Video_OLD\" existent déjà dans le dossier de la base sakuga. Veillez les déplacer pour éviter une perte de données, puis relancer le téléchargement.", p_fileSakuga, true);
		    });
		}
		else
		{
		    Platform.runLater(() ->
		    {
			ExtractVideos_GUI.popupInfo("Un dossier \"Video\" a été trouvé dans le dossier de la base sakuga. Il a été renommé en Video_OLD.", "", false);
		    });
		    dlFolder.renameTo(renameDestination);
		}
	    }
	    
	    if (dlFolder.mkdir())
	    {
		Platform.runLater(new toUpdateGUI("Lecture de la base sakuga...\n", 0));
		videoList = new SakugaDAO(sakugaCSV).getVideoList();
	    
		System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tR %1td/%1$tm/%1$tY] %4$s : %5$s\n");
		FileHandler missingCutHandler = new FileHandler(sakugaFolder + "/CutsManquant.log");
		SimpleFormatter missingCutFormatter = new SimpleFormatter();
		missingCutHandler.setFormatter(missingCutFormatter);
		cutInfoLogger.addHandler(missingCutHandler);
		
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

			Platform.runLater(new toUpdateGUI("Téléchargement de "+vidIndex+" - "+currentVid.getVideoName()+"...", ((double) index-1)/((double) videoList.size()-1)));

			currentVid.downloadVideo(dlFolder.toString(), vidIndex);
			currentVid.makeSub(dlFolder.toString(), vidIndex);

			index += 1;

			Platform.runLater(new toUpdateGUI("Video "+vidIndex+" - "+currentVid.getVideoName()+" téléchargée.\n", ((double) index-1)/((double) videoList.size()-1)));
		    }
		}
	    }
	    
	}
	catch (SecurityException | IllegalArgumentException ex)
	{
	    Logger errLogger = Logger.getLogger(ExtractVideos_DlAppli.class.getName()+"stopExecLogger");
	    ConsoleHandler errHandler = new ConsoleHandler();
	    errHandler.setFormatter(defaultFormatter);
	    errLogger.addHandler(errHandler);
	    errLogger.log(Level.SEVERE, null, ex);
	}
    }
    
    static class toUpdateGUI implements Runnable
    {
	String m_message;
	Double m_progress;

	private toUpdateGUI (String p_message, double p_progress)
	{
	    m_message = p_message;
	    m_progress = p_progress;
	}
	
	@Override
	public void run()
	{
	    ExtractVideos_GUI.printProgress(m_message, m_progress);
	}
    }
    
}
