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

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author Thali
 */
public class FXMLDlInfoWindowController
{

     @FXML private TextArea consolewindow;
     @FXML private ProgressBar pbar;
    
    void updateProgress (String message, double progress)
    {
	String currentText = message+"\n";
	consolewindow.appendText(currentText);
	
	pbar.setProgress(progress);
    }
    
    @FXML void closeApp(ActionEvent event)
    {
	System.exit(0);
    }    
    
}
