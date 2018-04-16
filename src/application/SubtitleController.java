package application;

import java.net.URL;
import java.util.ResourceBundle;

import com.sun.corba.se.pept.transport.EventHandler;

import data.SubEntry;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SubtitleController implements Initializable{
	
	//Donnees necessaires a une ligne de sous titre
	private int number=1;
	private int start=0;
	private int end=1;
	private String text="";
	
	@FXML
	private Slider slider_subtitle;
	@FXML
	private Label label_slider_subtitle;
	@FXML
	private TextField text_subtitle;
	@FXML
    private Spinner duration_spinner;
	@FXML
    private Label label_subtitle_duration;
	@FXML
    private Button button_remove;
	@FXML
    private Label label_remove;
	@FXML
    private Button button_done;
	
	//Tableau affichage
	@FXML
    private TableView<SubEntry> tableview_subtitle;
    @FXML
    private TableColumn<SubEntry, Integer> column_number;
    @FXML
    private TableColumn<SubEntry, String> column_start;
    @FXML
    private TableColumn<SubEntry, String> column_end;
    @FXML
    private TableColumn<SubEntry, String> column_text;
	 
	 public void initialize(URL arg0, ResourceBundle arg1) {
		 if(Main.subListObs==null) {
			 Main.subListObs= FXCollections.observableArrayList();
		 }
		 tableview_subtitle.setItems(Main.subListObs);
		 column_number.setCellValueFactory(new PropertyValueFactory<SubEntry, Integer>("Number"));
		 column_start.setCellValueFactory(new PropertyValueFactory<SubEntry, String>("Start"));
		 column_end.setCellValueFactory(new PropertyValueFactory<SubEntry, String>("End"));
		 column_text.setCellValueFactory(new PropertyValueFactory<SubEntry, String>("Text")); 
		 
		//Methode pour gerer le scroll du debut du sous titre
		slider_subtitle.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				int valueSlider=(int)slider_subtitle.getValue();
				if(Main.destination!=null) {
					float time=Main.destination.duration*valueSlider/100;
					//Ce if sert a assurer que le sous titre apparaisse au moins une seconde
					if(valueSlider==100) {
						time--;
					}
					label_slider_subtitle.setText(Main.timeToString(time));
					start=Math.round(time);
				}
				else {
					label_slider_subtitle.setText(String.valueOf(valueSlider) + "%");
				}
				end=(int)duration_spinner.getValue()+start;
				label_subtitle_duration.setText("Subtitle no " + (Main.subListObs.size()+1) + " duration");
				button_remove.setDisable(true);
				label_remove.setDisable(true);
			}
		});
		
		//Methode pour le textField du subtitle
		text_subtitle.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if(text_subtitle.getText()!=null) {
					text=text_subtitle.getText();
				}
				else {
					text="";
				}
				label_subtitle_duration.setText("Subtitle no " + (Main.subListObs.size()+1) + " duration");
				button_remove.setDisable(true);
				label_remove.setDisable(true);
			}
		});
		
		//Gestion du spinner pour la duree du sous titre
		int maxDuration;
		if(Main.destination!=null) {
			maxDuration=Math.round(Main.destination.duration-1f);
		}
		else {
			maxDuration=100;
		}
		SpinnerValueFactory<Integer> durationValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, maxDuration,1);
		duration_spinner.setValueFactory(durationValueFactory);
		//Methode pour gerer le scroll du debut du sous titre
		duration_spinner.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				if(Main.destination!=null) {
						end=(int)duration_spinner.getValue()+start;
				}
				button_remove.setDisable(true);
				label_remove.setDisable(true);
				label_subtitle_duration.setText("Subtitle no " + (Main.subListObs.size()+1) + " duration");
			}	
		});
	 }
	 
	 public void buttonAdd() { 
		SubEntry addedSE= new SubEntry (Main.subListObs.size()+1,start,end,text);
		for (int i=0;i<Main.subListObs.size();i++)
		{
			int tmp_number;
			if (Main.subListObs.get(i).seconds>addedSE.seconds)
			{
				tmp_number=Main.subListObs.get(i).number;
				Main.subListObs.get(i).number=addedSE.number;
				addedSE.number=tmp_number;
				SubEntry tmpSE =Main.subListObs.set(i, addedSE);
				addedSE=tmpSE;
			}	
		}	
		Main.subListObs.add(addedSE);
		label_subtitle_duration.setText("Subtitle no " + (Main.subListObs.size()+1) + " duration");
		button_remove.setDisable(true);
		label_remove.setDisable(true);
	 }
	 
	 public void buttonRemove() { 
		int id=number-1;
		Main.subListObs.remove(id);
		for (int i=id;i<Main.subListObs.size();i++)
		{
			Main.subListObs.get(i).setNumber(i+1);
		}
		label_subtitle_duration.setText("Subtitle no " + (Main.subListObs.size()+1) + " duration");
		button_remove.setDisable(true);
		label_remove.setDisable(true);
	 }
	 
	 public void selectRow(MouseEvent me) {
		 number=tableview_subtitle.getSelectionModel().getSelectedItem().getNumber();
		 //label_subtitle_duration.setText("Subtitle no " + (number) + " duration");
		 
		 start=tableview_subtitle.getSelectionModel().getSelectedItem().getSeconds();
		 end=tableview_subtitle.getSelectionModel().getSelectedItem().getEndTimeInt();
		 //duration_spinner.setAccessibleText(String.valueOf(end-start));
		 //label_slider_subtitle.setText(Main.timeToString((float)end));
		 //and set slider?
		 
		 text=tableview_subtitle.getSelectionModel().getSelectedItem().getText();
		 //text_subtitle.setText(text);
		 
		 button_remove.setDisable(false);
		 label_remove.setDisable(false);
		 label_remove.setText("Subtitle no " + number);
	 }
	 
	 public void buttonDone() {
		 Stage stage = (Stage) button_done.getScene().getWindow();
		 stage.close();
	}
}
