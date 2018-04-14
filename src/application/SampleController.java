package application;
import java.io.File;
import java.io.IOException;
import java.util.List;

import data.Source;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import jdk.nashorn.internal.runtime.options.Options;

public class SampleController {
	
	ObservableList<String> video_extension_list = FXCollections
				.observableArrayList("MP4", "GP", "G2", "MKV", "AVI");
	ObservableList<String> audio_codec_list = FXCollections
			.observableArrayList();
	ObservableList<String> video_codec_list = FXCollections
			.observableArrayList();
	
	//Bouton pour choisir le fichier vidéo
	@FXML
	private Button browse_video;
	
	//Bouton pour choisir le(s) fichier(s) audio
	@FXML
	private Button browse_audio;
	
	//Choix de l'extension;
	@FXML
	private ChoiceBox box_extension;
	
	//Choix du codec audio;
	@FXML
	private ChoiceBox box_audio;
		
	//Choix du codec vidéo;
	@FXML
	private ChoiceBox box_video;
	
	
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
	public void ButtonBrowseVideoAction(ActionEvent event) {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().addAll(
					new ExtensionFilter("MP4, 3GP, 3G2, MKV, OGV, AVI", "*.mp4", "*.3gp", "*.3g2", "*.mkv", "*.ogv", "*.avi"));
					
		File video = fc.showOpenDialog(null);
		if(video != null) {
			view_video.getItems().add(video.getAbsolutePath());
			Source source = new Source(video.getAbsolutePath());		
		}
		else {
			System.out.println("the file is not a video");
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
	public void ButtonBrowseAudioAction(ActionEvent event) {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().addAll(
					new ExtensionFilter("MP3, M4A, OGG, OGA, AAC", "*.mp3", "*.m4a", "*.ogg", "*.oga", "*.aac"));
							
		List<File> audio = fc.showOpenMultipleDialog(null);
		List<String> url = null;
		if(audio != null) {
			for(int i = 0; i < audio.size();i++) {
			view_sub.getItems().add(audio.get(i).getAbsolutePath());
			//url.add(view_sub.getItems().get(i));
			}
		}
		else {
			System.out.println("the file is not an audio");
		}	
	}
	
	//Méthode pour choisir le(s) fichier(s) de sous-titre(s)
	public void ButtonBrowseSubtitleAction(ActionEvent event) {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().addAll(
					new ExtensionFilter("SRT, MKS", "*.srt", "*.mks"));
							
		List<File> sub = fc.showOpenMultipleDialog(null);
		List<String> url = null;
		if(sub != null) {
			for(int i = 0; i < sub.size();i++) {
			view_sub.getItems().add(sub.get(i).getAbsolutePath());
			//url.add(view_sub.getItems().get(i));
			}
		}
		else {
			System.out.println("the file is not a subtitle");
		}		
	}
	
	public void initialize() 
	{
		box_extension.setValue("");
		box_video.setValue("");
		box_extension.setItems(video_extension_list);
		
		
		box_extension.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
		      @Override
		      public void changed(ObservableValue<? extends Number> observableValue, Number old_value, Number new_value) {
		    	String option;
		        System.out.println(box_extension.getItems().get((Integer) new_value));
		        option = (String) box_extension.getItems().get((Integer) new_value);
		        switch (option)
		        {
		        case "MP4":
		        	box_video.setItems(FXCollections.observableArrayList("LIBX264", "LIBX265", "LIBXVID"));
		        	box_audio.setItems(FXCollections.observableArrayList("AAC", "MP3LAME"));
		        	break;
		        case "GP":
		        	box_video.setItems(FXCollections.observableArrayList("LIBX264", "LIBXVID"));
		        	box_audio.setItems(FXCollections.observableArrayList("AAC"));
		        	break;
		        case "G2":
		        	box_video.setItems(FXCollections.observableArrayList("LIBX264", "LIBXVID"));
			        box_audio.setItems(FXCollections.observableArrayList("AAC"));
		        	break;
		        case "MKV":
		        	box_video.setItems(FXCollections.observableArrayList("LIBX264", "LIBX265", "LIBXVID"));
		        	box_audio.setItems(FXCollections.observableArrayList("VORBIS", "OPUS", "MP3LAME", "FLAC", "AAC"));
		        	break;
		        case "AVI":
		        	box_video.setItems(FXCollections.observableArrayList("LIBXVID"));
		        	box_audio.setItems(FXCollections.observableArrayList("MP3LAME"));
		        	break;
		        default:
		        	break;
		        }
		      }

		    });
	}
}