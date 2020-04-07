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

import java.io.File;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileAlreadyExistsException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.nio.file.NoSuchFileException;
import java.util.zip.DataFormatException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import extractvideos_dlappli.ExtractVideos_DlAppli;
import extractvideos_dlappli.GUICallBack;

/**
 * FXML Controller class
 *
 * @author audreyazura
 */
public class FXMLMainWindowController
{
    @FXML private TextField addressfield;
    private WindowsCall m_mainApp;
    private GUICallBack m_GUIApp;
    
    @FXML private void browsingStart(ActionEvent event)
    {
	FileChooser browser = new FileChooser();
	
	browser.setTitle("Choisissez la base sakuga");
	browser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Tabulation Separated Value (*.tsv)", "*.tsv"), new FileChooser.ExtensionFilter("All files (*.*)", "*.*"));
	
	try
        {
            String fieldText = addressfield.getText();
            browser.setInitialDirectory(new File((new File(fieldText)).getParent()));
        }
        catch (NullPointerException ex)
        {
            browser.setInitialDirectory(new File(System.getProperty("user.home")));
        }
        
        try
	{
            String fileAdress = browser.showOpenDialog(m_mainApp.getMainStage()).getAbsolutePath();
            addressfield.setText(fileAdress);
	}
	catch (NullPointerException ex)
	{
	   m_mainApp.popupInfo("Entrez l'adresse du fichier.", "", false);
	}
    }
    
    @FXML private void dlStart(ActionEvent event)
    {
	String enteredAddress = addressfield.getText();
	
	if (enteredAddress == null || enteredAddress.equals(""))
	{
    	   m_mainApp.popupInfo("Entrez l'adresse du fichier.", "", false);
	}
	else
	{
	    m_mainApp.initiateDlScene();
	    //to refactor with an uncaughtExceptionHandler for popupCritical?
            //If each Video is downloaded in its own thread, it may not be necessary to run ExtractVideos_dlAppli in its own thread
	    new Thread(() ->
	    {
		try
		{
		    ExtractVideos_DlAppli dlApp = new ExtractVideos_DlAppli();
                    dlApp.extract(enteredAddress, m_GUIApp);
		}
		catch (NoSuchFileException | NullPointerException exNoFile)
		{
		    Platform.runLater(() ->
		    {
			m_mainApp.popupInfo("Le fichier entré ne peut pas être trouvé.", enteredAddress, true);
		    });
		}
		catch (DataFormatException exData)
		{
		    Platform.runLater(() ->
		    {
			m_mainApp.popupInfo("Le fichier de base sakuga ne peut pas être lu correctement.", enteredAddress, true);
		    });
		}
                catch (AccessDeniedException exAccess)
                {
                    Platform.runLater(() ->
		    {
			m_mainApp.popupInfo("Erreur lors de l'accès au fichier " + exAccess.getFile(), enteredAddress, true);
		    });
                }
                catch (FileAlreadyExistsException exFile)
                {
                    Platform.runLater(() ->
                    {
                        m_mainApp.popupInfo(exFile.getFile(), enteredAddress, true);
                    });
                            
                }
		catch (IOException otherEx)
		{
		    Logger.getLogger(FXMLMainWindowController.class.getName()).log(Level.SEVERE, null, otherEx);
		}
	    }).start();
	}
    }
    
    void setMainApp (ExtractVideos_GUI p_App)
    {
        m_mainApp = p_App;
        m_GUIApp = p_App;
    }
    
    void setFileAddress (String address)
    {
	addressfield.setText(address);
    }
}
