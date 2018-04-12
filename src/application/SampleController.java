package application;
import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

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
}