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
import java.lang.ModuleLayer.Controller;
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
public class ExtractVideo_GUI extends Application
{
    public static void startSakugaGUI(String[] args)
    {
	Font.loadFont(ExtractVideo_GUI.class.getResource("URWPalladioL-Roma.ttf").toExternalForm(), 10);
	launch(args);
    }
    
    @Override
    public void start(Stage stage) throws IOException
    {
	Parent windowFxml = FXMLLoader.load(ExtractVideo_GUI.class.getResource("FXMLMainWindow.fxml"));
	stage.setTitle("Sakuga Extracter");
	stage.setScene(new Scene(windowFxml, 800, 600));
	stage.show();
    }
    
    static void popupInfo(String infoMessage) throws IOException
    {
	Stage infoStage = new Stage();
	FXMLLoader fxmlLoader = new FXMLLoader(ExtractVideo_GUI.class.getResource("FXMLPopupInfo.fxml"));
	Parent infoFxml = fxmlLoader.load();
	FXMLPopupInfoController popupController = fxmlLoader.getController();
	
	popupController.setPopupInfo(infoMessage);
	
	infoStage.requestFocus();
	infoStage.setScene(new Scene(infoFxml, 200, 100));
	infoStage.show();
    }
}
