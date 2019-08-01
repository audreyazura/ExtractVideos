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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author audreyazura
 */
public class FXMLPopupInfoController
{
    @FXML private Label popuptext;
    @FXML private Button okbutton;
    private String m_returnAddress;
    private boolean m_redirect;
    
    void setPopupInfo(String message, String p_returnAddress, boolean p_redirect)
    {
	popuptext.setText(message);
	m_returnAddress = p_returnAddress;
	m_redirect = p_redirect;
    }
    
    @FXML synchronized void closePopup(ActionEvent event)
    {
	if (m_redirect)
	{
	    ExtractVideos_GUI.backToMainWindow(m_returnAddress);
	}
	((Stage) okbutton.getScene().getWindow()).close();
    }
}
