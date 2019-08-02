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

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Font;

/**
 *
 * @author audreyazura
 */
public class ExtractVideos_GUI extends Application
{
    static Stage mainStage;
    static private FXMLDlInfoWindowController dlWindowController;
    
    static void startSakugaGUI(String[] args)
    {
	Font.loadFont(ExtractVideos_GUI.class.getResource("URWPalladioL-Roma.ttf").toExternalForm(), 10);
	launch(args);
    }
    
    @Override
    public void start(Stage stage)
    {
	mainStage = stage;
	loadMainWindow(null);
    }
    
    static void loadMainWindow(String address)
    {
	FXMLLoader loader = new FXMLLoader(ExtractVideos_GUI.class.getResource("FXMLMainWindow.fxml"));
	
	try
	{
	    Parent windowFxml = loader.load();
	    FXMLMainWindowController controller = loader.getController();
	    controller.setFileAddress(address);
	    mainStage.setScene(new Scene(windowFxml, 800, 600));
	    mainStage.show();
	}
	catch (IOException ex)
	{
	    Logger.getLogger(ExtractVideos_GUI.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
    
    public static void popupInfo(String infoMessage, String fileAddress, boolean redirect)
    {
	
	Stage infoStage = new Stage();
	FXMLLoader fxmlLoader = new FXMLLoader(ExtractVideos_GUI.class.getResource("FXMLPopupInfo.fxml"));
	
	try
	{
	    Parent infoFxml = fxmlLoader.load();
	    FXMLPopupInfoController popupController = fxmlLoader.getController();
	    
	    popupController.setPopupInfo(infoMessage, fileAddress, redirect);
	    
	    infoStage.initOwner(mainStage);
	    infoStage.setTitle("Warning!");
	    infoStage.setScene(new Scene(infoFxml, 400, 200));
	    infoStage.showAndWait();
	    infoStage.requestFocus();
	}
	catch (IOException ex)
	{
	    Logger.getLogger(ExtractVideos_GUI.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
    
    static void initiateDlScene()
    {
	FXMLLoader fxmlDlWindow = new FXMLLoader(ExtractVideos_GUI.class.getResource("FXMLDlInfoWindow.fxml"));
        
        try
	{
	    Parent dlFxml = fxmlDlWindow.load();
            dlWindowController = fxmlDlWindow.getController();
	    mainStage.setScene(new Scene(dlFxml, 800, 600));
	    mainStage.show();
	}
	catch (IOException ex)
	{
    	    Logger.getLogger(ExtractVideos_GUI.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
    
    static public void printProgress(String message, double progress)
    {
	dlWindowController.updateProgress(message, progress);
    }
}
