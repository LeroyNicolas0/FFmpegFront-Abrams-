package application;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import data.Source;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class SampleController implements Initializable{
	
	//Bouton pour choisir le fichier vidéo
	@FXML
	private Button browse_video;
	
	//Bouton pour choisir le(s) fichier(s) audio
	@FXML
	private Button browse_audio;
	
	//Bouton pour choisir le(s) sous-titre(s)
	@FXML
	private Button browse_subtitle;
	
	//Slider pour le temps de début (From)
	@FXML
    private Slider slider_from;
	@FXML
    private Label label_from;
	
	//Slider pour le temps de fin (To)
	@FXML
    private Slider slider_to;	
	@FXML
    private Label label_to;
		
	@FXML
	private ListView<String> view_video;
	
	@FXML
	private ListView<String> view_audio;
	
	@FXML
	private ListView<String> view_sub;
	
	private Source source;
	//private  Destination destination;
	
	//Méthode pour choisir le fichier vidéo
	public void ButtonBrowseVideoAction(ActionEvent event) {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().addAll(
					new ExtensionFilter("MP4, 3GP, 3G2, MKV, OGV, AVI", "*.mp4", "*.3gp", "*.3g2", "*.mkv", "*.ogv", "*.avi"));
					
		File video = fc.showOpenDialog(null);
		if(video != null) {
			view_video.getItems().add(video.getAbsolutePath());
			source = new Source(video.getAbsolutePath());
			float time=source.duration;
			label_to.setText(Main.timeToString(time));
			label_from.setText(Main.timeToString(0f));
		}
		else {
			System.out.println("the file is not a video");
		}		
	}
	
	public void openSubtitleWindow() {
		BorderPane secondaryLayout;
		try {
			secondaryLayout = FXMLLoader.load(getClass().getResource("CreateSubtitleWindow.fxml"));
			Scene secondScene = new Scene(secondaryLayout, 900, 350);
			
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
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//Méthode pour gérer le scroll du From
		slider_from.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				int valueSlider=(int)slider_from.getValue();
				if(source!=null) {
					float time=source.duration*valueSlider/100;
					if(valueSlider==100) {
						time--;
					}
					label_from.setText(Main.timeToString(time));
				}
				else {
					label_from.setText(String.valueOf(valueSlider) + "%");
				}
				slider_to.setMin(slider_from.getValue());
			}
		});
		
		//Méthode pour gérer le scroll du To
		slider_to.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				int valueSlider=(int)slider_to.getValue();
				if(source!=null) {
					float time=source.duration*valueSlider/100;
					if(valueSlider!=100) {
						time++;
					}
					label_to.setText(Main.timeToString(time));
				}
				else {
					label_to.setText(String.valueOf(valueSlider) + "%");
				}
				slider_from.setMax(slider_to.getValue());
			}
		});
	}

	

	
}