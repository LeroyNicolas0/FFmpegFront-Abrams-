package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class SubtitleController implements Initializable{
	 @FXML
	 private Slider slider_subtitle;
	 @FXML
    private Label label_slider_subtitle;
	 
	 public void initialize(URL arg0, ResourceBundle arg1) {
		//Methode pour gerer le scroll du debut du sous titre
		slider_subtitle.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				int valueSlider=(int)slider_subtitle.getValue();
				if(Main.destination!=null) {
					float time=Main.destination.duration*valueSlider/100;
					//Ce if sert à assurer qu'il y ait au moins une seconde de video
					if(valueSlider==100) {
						time--;
					}
					label_slider_subtitle.setText(Main.timeToString(time));
					//On garde les donnees de la destination a jour
					Main.destination.start_cut=time;
					Main.destination.duration=Main.destination.end_cut-Main.destination.start_cut;
				}
				else {
					label_slider_subtitle.setText(String.valueOf(valueSlider) + "%");
				}
				slider_subtitle.setMin(slider_subtitle.getValue());
			}
		});
		
	 }
}
