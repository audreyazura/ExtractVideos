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

import java.io.File;
import java.io.IOException;
import static java.lang.Math.log10;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileAlreadyExistsException;
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

    static final Logger CUTINFOLOGGER = Logger.getLogger(ExtractVideos_DlAppli.class.getName());
    
    /**
     * Main function of the package, coordinating the video downloading from the
     * database passed.
     * @param p_fileSakuga the address of the sakuga base
     * @throws java.nio.file.NoSuchFileException
     * @throws java.nio.file.FileAlreadyExistsException
     * @throws java.util.zip.DataFormatException
     */
    public void extract(String p_fileSakuga, GUICallBack p_callBack) throws IOException, DataFormatException, FileAlreadyExistsException
    {
	List<Video> videoList;
	SimpleFormatter defaultFormatter = new SimpleFormatter();
	
	
	
	try
	{
	    File sakugaCSV = new File(p_fileSakuga);
	    String sakugaFolder = sakugaCSV.getParent();
	    //we want the video folder to be in the same folder as the sakuga base
	    File dlFolder = new File(sakugaFolder + "/Videos");
	    
	    File logFile = new File(sakugaFolder + "/CutsManquant.log");
	    if (logFile.isFile())
	    {
		if (!logFile.delete())
                {
                     throw new AccessDeniedException(logFile.getAbsolutePath());
                }
	    }
	    
	    System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tR %1td/%1$tm/%1$tY] %4$s : %5$s\n");
	    FileHandler missingCutHandler = new FileHandler(sakugaFolder + "/CutsManquant.log");
	    SimpleFormatter missingCutFormatter = new SimpleFormatter();
	    missingCutHandler.setFormatter(missingCutFormatter);
	    CUTINFOLOGGER.addHandler(missingCutHandler);
	    
	    if (dlFolder.isDirectory())
	    {
		File renameDestination = new File(dlFolder.getAbsolutePath() + "_OLD");
		
		if (renameDestination.isDirectory())
		{
                    throw new FileAlreadyExistsException("Les dossiers \"Video\" et \"Video_OLD\" existent déjà dans le dossier de la base sakuga. Veuillez les déplacer pour éviter une perte de données, puis relancer le téléchargement.");
//		    Platform.runLater(() ->
//		    {
//			ExtractVideos_GUI.popupInfo("Les dossiers \"Video\" et \"Video_OLD\" existent déjà dans le dossier de la base sakuga. Veuillez les déplacer pour éviter une perte de données, puis relancer le téléchargement.", p_fileSakuga, true);
//		    });
		}
		else
		{
		    Platform.runLater(() ->
		    {
			p_callBack.popupInfo("Un dossier \"Video\" a été trouvé dans le dossier de la base sakuga. Il a été renommé en Video_OLD.", "", false);
		    });
		    dlFolder.renameTo(renameDestination);
		}
	    }
	    
	    if (dlFolder.mkdir())
	    {
		Platform.runLater(new toUpdateGUI("Lecture de la base sakuga...\n", 0, p_callBack));
                videoList = new SakugaDAO(sakugaCSV).getVideoList();
	    
		int listSize0 = videoList.size();
		int log10ListSize = (int) log10(listSize0);
		int index = 1;
		
		for (Video currentVid: videoList)
		{
		    int lIndex = videoList.indexOf(currentVid);
                     
		    Platform.runLater(new toUpdateGUI("Téléchargement de "+currentVid.getVideoName()+"...", ((double) lIndex)/((double) listSize0), p_callBack));
		   
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

			if (lIndex <= listSize0-1)
			{
			    Platform.runLater(new toUpdateGUI("Video "+vidIndex+" - "+currentVid.getVideoName()+" téléchargée.\n", ((double) lIndex+1)/((double) listSize0), p_callBack));
			}
			else
			{
			    Platform.runLater(new toUpdateGUI("Video "+vidIndex+" - "+currentVid.getVideoName()+" téléchargée.\n", ((double) lIndex)/((double) listSize0), p_callBack));
			}
		    }
		    else
		    {
			if (lIndex <= listSize0-1)
			{
			    Platform.runLater(new toUpdateGUI("Problème avec le téléchargement de la vidéo "+currentVid.getVideoName()+". L'évènement a été entré dans le log.\n", ((double) lIndex+1)/((double) listSize0), p_callBack));
			}
			else
			{
			    Platform.runLater(new toUpdateGUI("Problème avec le téléchargement de la vidéo "+currentVid.getVideoName()+". L'évènement a été entré dans le log.\n", ((double) lIndex)/((double) listSize0), p_callBack));
			}
		    }
		}
		
		Platform.runLater(() ->
		{
		    p_callBack.popupInfo("Le téléchargement des vidéos est terminé! Vous allez être redirigé vers la fenêtre d'acceuil.", "", true);
		});
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
    
    private class toUpdateGUI implements Runnable
    {
	String m_message;
	Double m_progress;
        GUICallBack m_windowCallBack;

	private toUpdateGUI (String p_message, double p_progress, GUICallBack p_callBack)
	{
	    m_message = p_message;
	    m_progress = p_progress;
            m_windowCallBack = p_callBack;
	}
	
	@Override
	public void run()
	{
	    m_windowCallBack.printProgress(m_message, m_progress);
	}
    }
    
}
