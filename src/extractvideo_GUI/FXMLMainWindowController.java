/*
 * Copyright (C) 2019 audreyazura
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
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
package extractvideo_GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import extractvideos_dlappli.ExtractVideos_DlAppli;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.NoSuchFileException;
import java.util.zip.DataFormatException;
import javafx.application.Platform;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author audreyazura
 */
public class FXMLMainWindowController
{
    @FXML private TextField addressfield;
    
    @FXML void browsingStart(ActionEvent event)
    {
	FileChooser browser = new FileChooser();
	
	browser.setTitle("Choisissez la base sakuga");
	browser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Tabulation Separated Value (*.tsv)", "*.tsv"), new FileChooser.ExtensionFilter("All files (*.*)", "*.*"));
	
	String fileAdress = "";
	try
	{
	    fileAdress = browser.showOpenDialog(ExtractVideos_GUI.mainStage).getAbsolutePath();
	}
	catch (NullPointerException ex)
	{
	   ExtractVideos_GUI.popupInfo("Entrez l'adresse du fichier.");
	}

	addressfield.setText(fileAdress);
    }
    
    @FXML void dlStart(ActionEvent event)
    {
//	Thread.UncaughtExceptionHandler h = (Thread th, Throwable ex) -> 
//	{
//	    System.out.println("Uncaught exception: " + ex.getClass());
//	};
//	
//	Thread dlThread = new Thread(() ->
//	{
//	    ExtractVideos_DlAppli.extract(addressfield.getText());
//	});

	Thread dlThread = new Thread(() ->
	{
	    try
	    {
	        ExtractVideos_DlAppli.extract(addressfield.getText());
	    }
	    catch (NoSuchFileException exNoFile)
	    {
		Platform.runLater(() ->
		{
		    ExtractVideos_GUI.popupInfo("Le fichier ne peut pas être trouvé.");
		});
	    }
	    catch (FileAlreadyExistsException exExist)
	    {
		Platform.runLater(() ->
		{
		    ExtractVideos_GUI.popupInfo("Un dossier \"Video\" a été trouvé dans le dossier de la base sakuga. Il va être renommé en Video_OLD.");
		});
	    }
	    catch (DataFormatException exData)
	    {
		Platform.runLater(() ->
		{
		    ExtractVideos_GUI.popupInfo("Le fichier de base sakuga ne peut pas être lu correctement.");
		});
	    }
	});
	
//	dlThread.setUncaughtExceptionHandler(h);
	dlThread.start();
    }
}
