package application;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Enums.ACodec;
import Enums.Extension;
import Enums.VCodec;
import data.AudioTrack;
import data.Destination;
import data.Resolution;
import data.Source;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class SampleController implements Initializable{
	
	ObservableList<String> video_extension_list = FXCollections
				.observableArrayList(Extension.MP4.name(), Extension.GP.name(), Extension.G2.name(), Extension.MKV.name(), Extension.AVI.name() );
	ObservableList<String> audio_codec_list = FXCollections
			.observableArrayList();
	ObservableList<String> video_codec_list = FXCollections
			.observableArrayList();
	ObservableList<String> audio_extension_list = FXCollections.observableArrayList(Extension.MP3.name(),Extension.M4A.name(),Extension.MKA.name(),Extension.OGG.name(),Extension.OGA.name(),Extension.AAC.name());
	
	//Bouton pour choisir le fichier video
	@FXML
	private Button browse_video;
	
	//Bouton pour choisir le(s) fichier(s) audio
	@FXML
	private Button browse_audio;
	@FXML
    private Label label_audio;
	
	//Bouton pour choisir le dossier de destination
	@FXML
	private Button browse_directory;
	
	//Bouton pour lancer
	@FXML
	private Button launch;
	
	//Choix de l'extension;
	@FXML
	private ChoiceBox<String> box_extension;
	
	//Choix du codec audio;
	@FXML
	private ChoiceBox<String> box_audio;
		
	//Choix du codec video;
	@FXML
	private ChoiceBox<String> box_video;
	
	//gridpane pour les slider from/to
	@FXML
    private GridPane grid_from;
	@FXML
    private GridPane grid_to;
	
	//Bouton pour choisir le(s) sous-titre(s)
	@FXML
	private Button browse_subtitle;
	
	//choix de cut vidï¿½o
	@FXML
    private CheckBox checkbox_cut_video;
	
	//crf
	@FXML
    private CheckBox checkbox_crf;
    @FXML
    private Slider slider_crf;
    @FXML
    private TextField text_crf;
    
    //bitrate
    @FXML
    private TextField text_bitrate;
    @FXML
    private Slider slider_bitrate;
    @FXML
    private CheckBox checkbox_bitrate;
    
    //Nom fichier sortie
    @FXML TextField text_name;
    
  //Nom dossier sortie
    @FXML TextField text_directory;


	//Slider pour le temps de debut (From)
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
	private TextField text_video;
	
	@FXML
	private ListView<String> view_audio;
	
	@FXML
	private ListView<String> view_sub;
	
	@FXML
	private Button subtitle_window_button;
	
	@FXML
	private Slider abitrate_slider;
	
	@FXML
	private TextField abitrate_field;
	@FXML 
	private ChoiceBox<Resolution> res_list;
	@FXML
	private TextField resolution_w;
	@FXML
	private TextField resolution_h;
	@FXML
	private CheckBox no_video;
	@FXML 
	private CheckBox no_audio;
	
	//Methode pour choisir le fichier video
	public void ButtonBrowseVideoAction(ActionEvent event) {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().addAll(
					new ExtensionFilter("MP4, 3GP, 3G2, MKV, OGV, AVI", "*.mp4", "*.3gp", "*.3g2", "*.mkv", "*.ogv", "*.avi"));
					
		File video = fc.showOpenDialog(null);
		if(video != null) {
			text_video.setText(video.getAbsolutePath());
			System.out.println(video.getAbsolutePath());
			Main.source = new Source(video.getAbsolutePath());
			//on met a jour les labels des sliders From/To
			float time=Main.source.duration;
			label_to.setText(Main.timeToString(time));
			label_from.setText(Main.timeToString(0f));
			//on met a jour la destination
			Main.destination=new Destination(Main.source);
			Main.destination.end_cut=Main.source.duration;
			//on met a jour le bitrate proposï¿½
			slider_bitrate.setValue(Main.destination.resolution.width*Main.destination.resolution.height*60/10000);
			text_bitrate.setText(String.valueOf(Main.destination.resolution.width*Main.destination.resolution.height*60/10000));
			checkbox_cut_video.setDisable(false);
			subtitle_window_button.setDisable(false);

			res_list.setConverter(new StringConverter<Resolution>(){
				@Override
				public String toString(Resolution r) {
					if (r.width==0) {
						return ("Custom");
					}
					else return r.print();
				}	
				@Override
				public Resolution fromString(String string) {
					return new Resolution(string);
				}
			});
			if (!Main.source.resolution.is_16_9()) {
				res_list.getItems().addAll(Main.source.resolution.get_360p_ratio(),Main.source.resolution.get_480p_ratio(),Main.source.resolution.get_720p_ratio(),Main.source.resolution.get_1080p_ratio(),Resolution.get_custom());
			} else {
				res_list.getItems().addAll(Resolution.get_360p(),Resolution.get_480p(),Resolution.get_720p(),Resolution.get_1080p(),Resolution.get_custom());			
			}
		}
		else {
			System.out.println("the file is not a video");
			checkbox_cut_video.setDisable(true);
			subtitle_window_button.setDisable(true);
		}		
	}
	
	//Mï¿½thode pour lancer
	public void ButtonLaunch(ActionEvent event) {
		Main.buildFile(Main.destination.Generate_command(Main.source)); // crï¿½e le fichier command.bat		
	}
	
	//Ouverture de la fenetre ajout de sous-titres
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
	
	// Ouverture de la fenetre de barre de progression 
	  public void openProgressBarWindow() { 
	    BorderPane secondaryLayout; 
	    try { 
	      secondaryLayout = FXMLLoader.load(getClass().getResource("ProgressBarWindow.fxml")); 
	      Scene thirdScene = new Scene(secondaryLayout, 250, 50); 
	 
	      Stage thirdStage = new Stage(); 
	      thirdStage.setTitle("Current progress"); 
	      thirdStage.setScene(thirdScene); 
	 
	      thirdStage.show(); 
	    } catch (IOException e) { 
	      // TODO Auto-generated catch block 
	      e.printStackTrace(); 
	    }
	  }
	
	//Methode pour choisir le(s) fichier(s) audio
	public void ButtonBrowseAudioAction(ActionEvent event) {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().addAll(
					new ExtensionFilter("MP3, M4A, OGG, OGA, AAC", "*.mp3", "*.m4a", "*.ogg", "*.oga", "*.aac"));
							
		List<File> audio = fc.showOpenMultipleDialog(null);
		if(audio != null) {
			for(int i = 0; i < audio.size();i++) {
			view_sub.getItems().add(audio.get(i).getAbsolutePath());
			Main.destination.pistes_audio.add(new AudioTrack(audio.get(i).getAbsolutePath()));
			}
		}
		else {
			System.out.println("the file is not an audio");
		}
	}
	
	//Methode pour choisir le(s) fichier(s) de sous-titre(s)
	public void ButtonBrowseSubtitleAction(ActionEvent event) {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().addAll(
					new ExtensionFilter("SRT, MKS", "*.srt", "*.mks"));
							
		List<File> sub = fc.showOpenMultipleDialog(null);
		if(sub != null) {
			for(int i = 0; i < sub.size();i++) {
			view_sub.getItems().add(sub.get(i).getAbsolutePath());
			}
		}
		else {
			System.out.println("the file is not a subtitle");
		}		
	}
	
	//Methode pour choisir le dossier de destination
		public void ButtonBrowserDestination(ActionEvent event) {
			DirectoryChooser dc = new DirectoryChooser();								
			File dir = dc.showDialog(null);
			if(dir != null) {
					text_directory.setText(dir.getAbsolutePath());
					System.out.println(text_directory.getText());
					Main.destination.file_path = text_directory.getText() + "\\" + Main.destination.name;
			}
			else {
				System.out.println("choose a directory");
			}		
		}
	
	//Methode pour la checkbox cut_the_video
	public void checkCheckboxCutVideo() {
		if(checkbox_cut_video.isSelected()) {
			grid_from.setVisible(true);
			grid_to.setVisible(true);
		}
		else {
			grid_from.setVisible(false);
			grid_to.setVisible(false);
			//on reset les changements
			if(Main.destination!=null) {
				Main.destination.start_cut=0f;
				Main.destination.end_cut=Main.source.duration;
				Main.destination.duration=Main.source.duration;
			}
			slider_from.setValue(0);
			slider_to.setValue(100);
		}
	}
	

	//Methode pour la checkbox crf
	public void checkCheckboxCRF() {
		if(checkbox_crf.isSelected()) {
			slider_crf.setDisable(false);
			text_crf.setDisable(false);
			slider_crf.setValue(30);
			text_crf.setText(String.valueOf(30));
			
			checkbox_bitrate.setSelected(false);
			slider_bitrate.setDisable(true);
			text_bitrate.setDisable(true);
			text_bitrate.setText("");
			if(Main.destination!=null) {
				Main.destination.use_crf=true;
				Main.destination.crf=30f;
			}
		}
		else {
			checkbox_bitrate.setSelected(true);
			slider_bitrate.setDisable(false);
			text_bitrate.setDisable(false);
			if(Main.destination!=null) {
				slider_bitrate.setValue(Main.destination.resolution.width*Main.destination.resolution.height*60/10000);
				text_bitrate.setText(String.valueOf(Main.destination.resolution.width*Main.destination.resolution.height*60/10000));
				Main.destination.use_crf=false;
			}
			else {
				slider_bitrate.setValue(100);
				text_bitrate.setText(String.valueOf(100));
			}
			
			slider_crf.setDisable(true);
			text_crf.setDisable(true);
			text_crf.setText("");
		}
	}
	
	//Methode pour la checkbox bitrate
	public void checkCheckboxBitrate() {
		if(checkbox_bitrate.isSelected()) {
			if(Main.destination!=null) {
				slider_bitrate.setValue(Main.destination.resolution.width*Main.destination.resolution.height*60/10000);
				text_bitrate.setText(String.valueOf(Main.destination.resolution.width*Main.destination.resolution.height*60/10000));
				Main.destination.vbitrate=Main.destination.resolution.width*Main.destination.resolution.height*60/10000;
				Main.destination.use_crf=false;
			}
			else {
				slider_bitrate.setValue(100);
				text_bitrate.setText(String.valueOf(100)+" kb/s");
			}
			slider_bitrate.setDisable(false);
			text_bitrate.setDisable(false);
			
			checkbox_crf.setSelected(false);
			slider_crf.setDisable(true);
			text_crf.setDisable(true);
			text_crf.setText("");
		}
		else {
			if(Main.destination!=null) {
				Main.destination.use_crf=true;
			}
			checkbox_crf.setSelected(true);
			slider_crf.setDisable(false);
			text_crf.setDisable(false);
			slider_crf.setValue(30);
			text_crf.setText(String.valueOf(30));
			
			slider_bitrate.setDisable(true);
			text_bitrate.setDisable(true);
			text_bitrate.setText("");
		}
	}
	// Methode pour la checkbox no_audio
	public void checkCheckBoxno_audio() {
		if (no_audio.isSelected()) {
			no_video.setDisable(true);
			browse_audio.setDisable(true);
			box_audio.setDisable(true);
			abitrate_slider.setDisable(true);
			abitrate_field.setDisable(true);
			System.out.println("No Audio");
			box_audio.setValue(null);
			//box_audio.setItems(null);
			if (Main.destination!=null) {
				Main.destination.acodec=ACodec.NONE;
			}
		}else {
			no_video.setDisable(false);
			browse_audio.setDisable(false);
			box_audio.setDisable(false);
			abitrate_slider.setDisable(false);
			abitrate_field.setDisable(false);
			if (Main.destination!=null) {
				Main.destination.acodec=null;
			}
		}
	}
	
	public void checkCheckBoxno_video() {
		System.out.println("Hello!");
		if (no_video.isSelected()) {
			System.out.println("Hello2!");
			no_audio.setDisable(true);
			System.out.println(no_audio.isDisable());
			box_video.setDisable(true);
			slider_crf.setVisible(false);
			slider_bitrate.setVisible(false);
			text_crf.setVisible(false);
			text_bitrate.setVisible(false);
			checkbox_crf.setVisible(false);
			checkbox_bitrate.setVisible(false);
			box_extension.setValue(null);
			box_extension.setItems(audio_extension_list);
			box_video.setValue(null);
			if (Main.destination!=null) {
				Main.destination.vcodec=VCodec.NONE;
			}
			}
			else {
				no_audio.setDisable(false);
				box_video.setDisable(false);
				slider_crf.setVisible(true);
				slider_bitrate.setVisible(true);
				text_crf.setVisible(true);
				text_bitrate.setVisible(true);
				checkbox_crf.setVisible(true);
				checkbox_bitrate.setVisible(true);
				if (Main.destination!=null) {
					Main.destination.vcodec=null;
				}
				box_extension.setValue(null);
				box_extension.setItems(video_extension_list);
			}
		}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//Methode pour gerer le scroll du From
		slider_from.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				int valueSlider=(int)slider_from.getValue();
				if(Main.source!=null) {
					float time=Main.source.duration*valueSlider/100;
					//Ce if sert ï¿½ assurer qu'il y ait au moins une seconde de video
					if(valueSlider==100) {
						time--;
					}
					label_from.setText(Main.timeToString(time));
					//On garde les donnees de la destination a jour
					Main.destination.start_cut=time;
					Main.destination.duration=Main.destination.end_cut-Main.destination.start_cut;
				}
				else {
					label_from.setText(String.valueOf(valueSlider) + "%");
				}
				slider_to.setMin(slider_from.getValue());
			}
		});
		
		//Methode pour gerer le scroll du To
		slider_to.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				int valueSlider=(int)slider_to.getValue();
				if(Main.source!=null) {
					float time=Main.source.duration*valueSlider/100;
					//Ce if sert ï¿½ assurer qu'il y ait au moins une seconde de video
					if(valueSlider!=100) {
						time++;
					}
					label_to.setText(Main.timeToString(time));
					Main.destination.end_cut=time;
					Main.destination.duration=Main.destination.end_cut-Main.destination.start_cut;
				}
				else {
					label_to.setText(String.valueOf(valueSlider) + "%");
				}
				slider_from.setMax(slider_to.getValue());
			}
		});
		
		//Methode pour gerer le scroll du CRF
		slider_crf.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				int valueSlider=(int)slider_crf.getValue();
				if(Main.destination!=null) {
					Main.destination.crf=valueSlider;
				}
				text_crf.setText(String.valueOf(valueSlider));
			}
		});
		
		//Methode pour gerer le scroll du bitrate
		slider_bitrate.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				int valueSlider=(int)slider_bitrate.getValue();
				if(Main.destination!=null) {
					Main.destination.vbitrate=valueSlider;
				}
				text_bitrate.setText(String.valueOf(valueSlider)+ "kb/s");
			}
		});
		
		//Methode pour gérer le scorll du bitrate audio
		abitrate_slider.valueProperty().addListener(new ChangeListener<Number>(){
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				int valueSlider=(int)abitrate_slider.getValue();
				if(Main.destination!=null) {
					Main.destination.abitrate=valueSlider;
				}
				abitrate_field.setText(String.valueOf(valueSlider)+" kb/s");
			}
		});
		
		//Methode pour le textField crf
		ChangeListener<? super Boolean> crf_listener=new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				if(text_crf.getText()!=null ) {
					Pattern pattern = Pattern.compile("\\d+");
					Matcher matcher = pattern.matcher(text_crf.getText());
					if (matcher.find()) {
						int value=Integer.parseInt(matcher.group(0));
						System.out.println(value);
						if (value>51) {
							value=51;
						}
						else if (value<0) {
							value=0;
						}
						slider_crf.setValue(value);
						text_crf.setText(Integer.toString((int)slider_crf.getValue()));
							if(Main.destination!=null) {
								Main.destination.vbitrate=value;
							}
						}
					}
				}		
			};
			text_crf.focusedProperty().addListener(crf_listener);
		//Methode pour le textField bitrate
			ChangeListener<? super Boolean> bitrate_listener=new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				if(text_bitrate.getText()!=null ) {
					Pattern pattern = Pattern.compile("\\d+");
					Matcher matcher = pattern.matcher(text_bitrate.getText());
					if (matcher.find()) {
						int value=Integer.parseInt(matcher.group(0));
						System.out.println(value);
						if (value>200000) {
							value=200000;
						}
						else if (value<100) {
							value=100;
						}
						slider_bitrate.setValue(value);
						text_bitrate.setText(Integer.toString(value)+ " kb/s");
							if(Main.destination!=null) {
								Main.destination.vbitrate=value;
							}
						}
					}
				}		
			};
			text_bitrate.focusedProperty().addListener(bitrate_listener);
			
			//Methode pour le textField abitrate
			ChangeListener<? super Boolean> abitrate_listener=new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				if(abitrate_field.getText()!=null ) {
					Pattern pattern = Pattern.compile("\\d+");
					Matcher matcher = pattern.matcher(abitrate_field.getText());
					if (matcher.find()) {
						int value=Integer.parseInt(matcher.group(0));
						System.out.println(value);
						if (value>640) {
							value=640;
						}
						else if (value<1) {
							value=1;
						}
						abitrate_slider.setValue(value);
						abitrate_field.setText(Integer.toString(value)+ " kb/s");
							if(Main.destination!=null) {
								Main.destination.abitrate=value;
							}
						}
					}
				}		
			};
			abitrate_field.focusedProperty().addListener(abitrate_listener);
			
			//Methode pour le textfield resolution_w
			
			ChangeListener<? super Boolean> w_listener=new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
					if(resolution_w.getText()!=null ) {
						Pattern pattern = Pattern.compile("\\d+");
						Matcher matcher = pattern.matcher(resolution_w.getText());
						if (matcher.find()) {
							int value_w=Integer.parseInt(matcher.group(0));
							if (value_w<64) {
								value_w=64;
							}
							if (value_w%2!=0) {
								value_w++;
							}
							resolution_w.setText(Integer.toString(value_w));
							Matcher matcher2 = pattern.matcher(resolution_h.getText());
							if (matcher.find() && Main.destination!=null) {
								int value_h=Integer.parseInt(matcher.group(0));
								Main.destination.resolution=new Resolution(value_w,value_h);	
								}		
							}
						}
					}		
				};
				resolution_w.focusedProperty().addListener(w_listener);
				
				//Methode pour le textfield resolution_h
				
				ChangeListener<? super Boolean> h_listener=new ChangeListener<Boolean>() {
					@Override
					public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
						if(resolution_h.getText()!=null ) {
							Pattern pattern = Pattern.compile("\\d+");
							Matcher matcher = pattern.matcher(resolution_h.getText());
							if (matcher.find()) {
								int value_h=Integer.parseInt(matcher.group(0));
								if (value_h<64) {
									value_h=64;
								}
								if (value_h%2!=2) {
									value_h++;
								}
								resolution_h.setText(Integer.toString(value_h));
								Matcher matcher2 = pattern.matcher(resolution_w.getText());
								if (matcher.find() && Main.destination!=null) {
									int value_w=Integer.parseInt(matcher.group(0));
									Main.destination.resolution=new Resolution(value_w,value_h);	
									}		
								}
							}
						}		
					};
					resolution_h.focusedProperty().addListener(h_listener);
			
		text_name.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
						Main.destination.name = text_name.getText() + "." + Main.destination.extension.get_ext();
						System.out.println(Main.destination.name);
				}		
		});
		
		box_extension.setValue("");
		box_video.setValue("");
		box_extension.setItems(video_extension_list);
		
		//Mï¿½thode pour choix extension vidï¿½o
		box_extension.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
	      @Override
	      public void changed(ObservableValue<? extends Number> observableValue, Number old_value, Number new_value) {
	    	String option;
	    	if (box_extension.getValue()!=null) {
	        System.out.println(box_extension.getItems().get((Integer) new_value));
	        option = (String) box_extension.getItems().get((Integer) new_value);

	        switch (option)
	        {
	        case "MP4":
	        	box_video.setDisable(false);
	        	box_audio.setDisable(false);
	        	box_video.setItems(FXCollections.observableArrayList(VCodec.LIBX264.name(), VCodec.LIBX265.name(), VCodec.LIBXVID.name()));
	        	if (!no_audio.isSelected()) {
	        	box_audio.setItems(FXCollections.observableArrayList(ACodec.AAC.name(), ACodec.MP3LAME.name()));
		        Main.destination.acodec=null;
	        	}
	        	Main.destination.extension = Extension.MP4;
	        	subtitle_window_button.setDisable(false);
	        	browse_subtitle.setDisable(false);
		        Main.destination.vcodec=null;

	        	break;
	        case "GP":
	        	box_video.setDisable(false);
	        	box_video.setItems(FXCollections.observableArrayList(VCodec.LIBX264.name(), VCodec.LIBX265.name()));
	        	if (!no_audio.isSelected()) {
	        	box_audio.setItems(FXCollections.observableArrayList(ACodec.AAC.name()));
	        	box_audio.setValue(ACodec.AAC.name());
	        	Main.destination.acodec=ACodec.AAC;
	        	box_audio.setDisable(true);
	        	}
	        	Main.destination.extension = Extension.GP;
	        	subtitle_window_button.setDisable(false);
	        	browse_subtitle.setDisable(false);
		        Main.destination.vcodec=null;
	        	break;
	        case "G2":
	        	box_video.setDisable(false);
	        	box_video.setItems(FXCollections.observableArrayList(VCodec.LIBX264.name(), VCodec.LIBX265.name()));
	        	if (!no_audio.isSelected()) {
	        	box_audio.setItems(FXCollections.observableArrayList(ACodec.AAC.name()));
	        	box_audio.setValue(ACodec.AAC.name());
	        	Main.destination.acodec=ACodec.AAC;
	        	box_audio.setDisable(true);
	        	}
	        	Main.destination.extension = Extension.G2;
	        	subtitle_window_button.setDisable(false);
	        	browse_subtitle.setDisable(false);
		        Main.destination.vcodec=null;
	        	break;
	        case "MKV":
	        	box_video.setDisable(false);
	        	box_audio.setDisable(false);
	        	box_video.setItems(FXCollections.observableArrayList(VCodec.LIBX264.name(), VCodec.LIBX265.name(), VCodec.LIBXVID.name()));
	        	if (!no_audio.isSelected()) {
	        	box_audio.setItems(FXCollections.observableArrayList(ACodec.VORBIS.name(), ACodec.OPUS.name(), ACodec.MP3LAME.name(), ACodec.FLAC.name(), ACodec.AAC.name()));
		        Main.destination.acodec=null;
	        	}
	        	Main.destination.extension = Extension.MKV;
	        	subtitle_window_button.setDisable(false);
	        	browse_subtitle.setDisable(false);
		        Main.destination.vcodec=null;
	        	break;
	        case "AVI":
	        	box_video.setItems(FXCollections.observableArrayList(VCodec.LIBXVID.name()));
	        	if (!no_audio.isSelected()) {
	        	box_audio.setItems(FXCollections.observableArrayList(ACodec.MP3LAME.name()));
	        	box_audio.setValue(ACodec.MP3LAME.name());
	        	box_audio.setDisable(true);
	        	Main.destination.acodec=ACodec.MP3LAME;
	        	}
	        	box_video.setValue(VCodec.LIBXVID.name());
	        	box_video.setDisable(true);
	        	Main.destination.vcodec=VCodec.LIBXVID;
	        	subtitle_window_button.setDisable(true);
	        	browse_subtitle.setDisable(true);
	        	Main.destination.extension = Extension.AVI;
	        	break;
	        //Audio Options
	        case "MP3":
	        	box_audio.setItems(FXCollections.observableArrayList(ACodec.MP3LAME.name()));
	        	box_audio.setValue(ACodec.MP3LAME.name());
	        	box_audio.setDisable(true);
	        	subtitle_window_button.setDisable(true);
	        	browse_subtitle.setDisable(true);
	        	Main.destination.extension= Extension.MP3;
	        	Main.destination.acodec=ACodec.MP3LAME;
	        	break;
	        case "M4A":
	        	box_audio.setDisable(false);
	        	box_audio.setItems(FXCollections.observableArrayList(ACodec.AAC.name(),ACodec.MP3LAME.name()));
	        	subtitle_window_button.setDisable(true);
	        	browse_subtitle.setDisable(true);
	        	Main.destination.extension= Extension.M4A;
		        Main.destination.acodec=null;
	        	break;
	        case "MKA":
	        	box_audio.setDisable(false);
	        	box_audio.setItems(FXCollections.observableArrayList(ACodec.AAC.name(),ACodec.MP3LAME.name(),ACodec.VORBIS.name(),ACodec.OPUS.name(),ACodec.FLAC.name()));
	        	subtitle_window_button.setDisable(true);
	        	browse_subtitle.setDisable(true);
	        	Main.destination.extension= Extension.MKA;
		        Main.destination.acodec=null;
	        	break;
	        case "OGG":
	        	box_audio.setItems(FXCollections.observableArrayList(ACodec.VORBIS.name()));
	        	subtitle_window_button.setDisable(true);
	        	browse_subtitle.setDisable(true);
	        	box_audio.setValue(ACodec.VORBIS.name());
	        	box_audio.setDisable(true);
	        	Main.destination.acodec=ACodec.VORBIS;
	        	Main.destination.extension= Extension.OGG;
	        	break;
	        case "OGA":
	        	box_audio.setDisable(false);
	        	box_audio.setItems(FXCollections.observableArrayList(ACodec.OPUS.name(),ACodec.FLAC.name()));
	        	subtitle_window_button.setDisable(true);
	        	browse_subtitle.setDisable(true);
	        	Main.destination.extension= Extension.OGA;
		        Main.destination.acodec=null;
	        case "AAC":
	        	box_audio.setItems(FXCollections.observableArrayList(ACodec.AAC.name()));
	        	box_audio.setValue(ACodec.AAC.name());
	        	box_audio.setDisable(true);
	        	subtitle_window_button.setDisable(true);
	        	browse_subtitle.setDisable(true);
	        	Main.destination.extension= Extension.AAC;
	        	Main.destination.acodec=ACodec.AAC;
	        	break;
	        default:
	        	break;
	        }
	      }
	      }
	    });
		
		//Mï¿½thode pour choix codec audio
		box_audio.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
		      @Override
		      public void changed(ObservableValue<? extends Number> observableValue, Number old_value, Number new_value) {
		    	  if (!no_audio.isSelected()) {
		    	  String aud = (String) box_audio.getItems().get((Integer) new_value);
		    	  System.out.println(box_audio.getItems().get((Integer) new_value));
		    	  for(ACodec audio : ACodec.values()) {
		    		if(aud==audio.name())
		    	  		Main.destination.acodec = audio ;
		    	  }
		    	}		      
		      }
		    });
		
		//Méthode pour choix résolution
		res_list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Resolution>() {
			@Override
			public void changed(ObservableValue<? extends Resolution> observableValue, Resolution old_value, Resolution new_value) {
				if (new_value.width!=0) {
				resolution_w.setText(Integer.toString(new_value.width));
				resolution_h.setText(Integer.toString(new_value.height));
				Main.destination.resolution=new_value;
				resolution_w.setDisable(true);
				resolution_h.setDisable(true);
				}else {
				resolution_w.setDisable(false);
				resolution_h.setDisable(false);
				}
			}
		});

		//Mï¿½thode pour choix codec video
		box_video.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
		      public void changed(ObservableValue<? extends Number> observableValue, Number old_value, Number new_value) {
				if (box_video.getValue()!=null) {
		    	  String vid = (String) box_video.getItems().get((Integer) new_value);
		    	  System.out.println(box_video.getItems().get((Integer) new_value));
		    	  for(VCodec video : VCodec.values()) {
		    		if(vid==video.name())
		    			Main.destination.vcodec = video;
		    	  }
		      }
			}
		    });
	}
}