package application;
import java.io.File;
import java.io.IOException;
import java.util.List;

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
	
	//Bouton pour choisir le fichier vidéo
	@FXML
	private Button browse_video;
	
	//Bouton pour choisir le(s) fichier(s) audio
	@FXML
	private Button browse_audio;
	
	//Bouton pour choisir le(s) sous-titre(s)
	@FXML
	private Button browse_subtitle;
		
	@FXML
	private ListView<String> view_video;
	
	@FXML
	private ListView<String> view_audio;
	
	@FXML
	private ListView<String> view_sub;
	
	
	
	//Méthode pour choisir le fichier vidéo
	public String ButtonBrowseVideoAction(ActionEvent event) {
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
			view_video.getItems().add(video.getAbsolutePath());
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
	
	//Méthode pour choisir le(s) fichier(s) audio
	public List<String> ButtonBrowseAudioAction(ActionEvent event) {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().addAll(
					new ExtensionFilter("MP3", "*.mp3"),
					new ExtensionFilter("M4A", "*.m4a"),
					new ExtensionFilter("OGG", "*.ogg"),
					new ExtensionFilter("OGA", "*.oga"),
					new ExtensionFilter("AAC", "*.aac"));
							
		List<File> audio = fc.showOpenMultipleDialog(null);
		List<String> url = null;
		if(audio != null) {
			for(int i = 0; i < audio.size();i++) {
			view_sub.getItems().add(audio.get(i).getAbsolutePath());
			//url.add(view_sub.getItems().get(i));
			}
			return url;
		}
		else {
			System.out.println("the file is not an audio");
			return null;
		}	
	}
	
	//Méthode pour choisir le(s) fichier(s) de sous-titre(s)
	public List<String> ButtonBrowseSubtitleAction(ActionEvent event) {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().addAll(
					new ExtensionFilter("SRT", "*.srt"),
					new ExtensionFilter("MKS", "*.mks"));
							
		List<File> sub = fc.showOpenMultipleDialog(null);
		List<String> url = null;
		if(sub != null) {
			for(int i = 0; i < sub.size();i++) {
			view_sub.getItems().add(sub.get(i).getAbsolutePath());
			//url.add(view_sub.getItems().get(i));
			}
			return url;
		}
		else {
			System.out.println("the file is not a subtitle");
			return null;
		}		
	}
}