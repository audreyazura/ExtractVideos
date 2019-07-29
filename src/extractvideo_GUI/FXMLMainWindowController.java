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
import javafx.stage.Stage;

import extractvideos_dlappli.ExtractVideos_DlAppli;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    @FXML void browsingStart(ActionEvent event) throws IOException
    {
	Stage browseStage = new Stage();
	FileChooser browser = new FileChooser();
	
	browser.setTitle("Choisissez la base sakuga");
	browser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Tabulation Separated Value (*.tsv)", "*.tsv"), new FileChooser.ExtensionFilter("All files (*.*)", "*.*"));
	
	String fileAdress = "";
	try
	{
	    fileAdress = browser.showOpenDialog(browseStage).getAbsolutePath();
	}
	catch (NullPointerException ex)
	{
	    ExtractVideo_GUI.popupInfo("Entrez l'adresse du fichier.");
	}

	addressfield.setText(fileAdress);
    }
    
    @FXML void dlStart(ActionEvent event)
    {
	new Thread(() ->
	{
	    try
	    {
	        ExtractVideos_DlAppli.extract(addressfield.getText());
	    }
	    catch (NoSuchFileException ex)
	    {
		try
		{
		    ExtractVideo_GUI.popupInfo("Le fichier ne peut pas être trouvé.");
		} 
		catch (IOException ex1)
		{
		    Logger.getLogger(FXMLMainWindowController.class.getName()).log(Level.SEVERE, null, ex1);
		}
	    }
	}).start();
    }
}
