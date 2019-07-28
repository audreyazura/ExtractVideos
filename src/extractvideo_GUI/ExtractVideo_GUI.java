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

import extractvideos_dlappli.ExtractVideos;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author audreyazura
 */
public class ExtractVideo_GUI extends Application
{
    public static void startSakugaGUI(String[] args)
    {

//	String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
//	for (String font: fonts)
//	{
//	    System.out.println(font);
//	}
	
	launch(args);
    }
    
    @Override
    public void start(Stage stage)
    {
	stage.setTitle("Sakuga Extracter");
	stage.show();
	
        GridPane mainGrid = new GridPane();
	mainGrid.setAlignment(Pos.CENTER);
	mainGrid.setHgap(10);
	mainGrid.setVgap(20);
	mainGrid.setPadding(new Insets(25));
	
	Text instructions = new Text("Bienvenue dans l'extracteur de Sakuga ! Pour l'utiliser, veuillez suivre les étapes suivantes :\n1) Télécharger la base sakuga dans un fichiers de valeurs séparées par des tabulations (sur le Google Sheet, vous pouvez le faire en allant dans Fichier -> Télécharger au format -> Valeurs séparées par des tabulation (.tsv, feuille active). Le fichier résultant devrait être au format *.tsv\n2) Cliquer sur le bouton \"Parcourir\" ci-dessous et sélectionnez le fichier tsv que vous venez de créer.\n3) Cliquez sur le bouton \"Télécharger\". L'extracteur va créer un dossier \"Vidéo\" dans le même dossier que le fichier tsv, dans lequel se trouveront toutes les vidéos ainsi que leurs sous-titres.\n4) PROFIT!");
	instructions.setFont(Font.loadFont(ExtractVideo_GUI.class.getResource("URWPalladioL-Roma.ttf").toExternalForm(), 15));
	mainGrid.add(instructions, 0, 0, 2, 1);
	
	Label fieldName = new Label("Base sakuga:");
	fieldName.setFont(Font.loadFont(ExtractVideo_GUI.class.getResource("URWPalladioL-Roma.ttf").toExternalForm(), 15));
	mainGrid.add(fieldName, 0, 1);
	
	TextField addressField = new TextField();
	mainGrid.add(addressField, 1, 1);
	
	final Text buttonClicked = new Text();
	mainGrid.add(buttonClicked, 1, 3);
	
	Button browse = new Button("Parcourir");
	browse.setOnAction(new EventHandler<ActionEvent>()
	{
	    @Override
	    public void handle(ActionEvent ActionEvent)
	    {
		buttonClicked.setFill(Color.FIREBRICK);
		buttonClicked.setText("This will open a browsing window!");
	    }
	});
	HBox browseBox = new HBox(10);
	browseBox.getChildren().add(browse);
	mainGrid.add(browseBox, 2, 1);
	
	Button startDl = new Button("Télécharger");
	startDl.setOnAction(new EventHandler<ActionEvent>()
	{
	    @Override
	    public void handle(ActionEvent ActionEvent)
	    {
		buttonClicked.setFill(Color.FIREBRICK);
		buttonClicked.setText("This will start the download!");
//		ExtractVideos.extract("/home/audreyazura/Documents/Nijikai/BaseSakuga.tsv");
	    }
	});
	HBox dlBox = new HBox();
	dlBox.setAlignment(Pos.BOTTOM_RIGHT);
	dlBox.getChildren().add(startDl);
	mainGrid.add(dlBox, 3, 5);
	
        Scene scene = new Scene(mainGrid, 640, 480);
        stage.setScene(scene);
    }
}
