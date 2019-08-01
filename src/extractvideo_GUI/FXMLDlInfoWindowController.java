/*
 * Copyright (C) 2019 Thali
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

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Thali
 */
public class FXMLDlInfoWindowController
{

     @FXML private TextArea consolewindow;
     @FXML private ProgressBar pbar;
//    @FXML Button closebutton;
    
    void updateProgress (String message, double progress)
    {
//	String currentText = consolewindow.getText();
//	currentText += "\n"+message;
	consolewindow.setText(message);
//	
	pbar.setProgress(progress);
    }
    
//    @FXML void closeApp(ActionEvent event)
//    {
//	((Stage) closebutton.getScene().getWindow()).close();
//    }    
    
}
