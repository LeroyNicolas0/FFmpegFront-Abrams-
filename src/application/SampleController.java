package application;
import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class SampleController {
	@FXML
	//Bouton pour choisir le fichier vidéo
	private Button browse;
	@FXML
	private ListView<String> view;
	
	//Méthode pour choisir le fichier vidéo
	public String ButtonBrowseAction(ActionEvent event) {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().addAll(
					new ExtensionFilter("MP4", "*.mp4"),
					new ExtensionFilter("GP", "*.3gp"),
					new ExtensionFilter("G2", "*.3g2"),
					new ExtensionFilter("MKV", "*.mkv"),
					new ExtensionFilter("OGV", "*.ogv"),
					new ExtensionFilter("AVI", "*.avi"));
					
		File video = fc.showOpenDialog(null);
		if(video != null) {
			view.getItems().add(video.getAbsolutePath());
			return video.getAbsolutePath();
		}
		else {
			System.out.println("the file is not a video");
			return null;
		}		
	}
	
	public void openSubtitleWindow() {
		BorderPane secondaryLayout;
		try {
			secondaryLayout = FXMLLoader.load(getClass().getResource("CreateSubtitleWindow.fxml"));
			Scene secondScene = new Scene(secondaryLayout, 600, 300);
			
			Stage secondStage = new Stage();
			secondStage.setTitle("Testu titlu");
			secondStage.setScene(secondScene);
			
			secondStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}