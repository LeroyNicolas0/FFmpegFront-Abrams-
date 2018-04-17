package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

public class ProgressBarController implements Initializable{
	 @FXML
	 private ProgressBar progressBar;
	 @FXML
	 private Button button_next;
	 @FXML
	 private Label value;
	 private Thread thread;
	 private Process proc;
	 private Task<Void> task;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		progressBar.setProgress(0.0);
		
		 if(!Main.isEncoding) {
			 //Main.isEncoding=true;
			 value = new Label();
			 value.setText("0");
			 task = new Task<Void>(){
				 @Override
				 protected Void call() throws Exception {
					try {
						float time = 0;
						ProcessBuilder procBuilder = new ProcessBuilder(Main.pathTempDirectory + Main.fileCommand);
						procBuilder.redirectErrorStream(true);
						proc = procBuilder.start();
						BufferedReader infoBuff = new BufferedReader(new InputStreamReader(proc.getInputStream()));
						String lineToParse = null;
						while ((lineToParse = infoBuff.readLine()) != null) {
							if (lineToParse.contains("time=")) {
								String extractedLine = lineToParse.substring(lineToParse.indexOf("time=")+5,
								lineToParse.indexOf("time=")+17);				
								time=Main.stringToTime(extractedLine);
								updateProgress((time/Main.destination.duration)*100f,100);
								System.out.println(time/Main.destination.duration*100);
							}
						}
						System.out.println("testitestu");
						 
						if(Thread.currentThread().isInterrupted()) {
							System.out.println("interr");
				            return null;
				        }
					}
					catch (IOException e) {
						System.out.println("bug");
						e.printStackTrace();
					}
					Platform.runLater(
					    new Runnable() {
					        public void run() {
					        	button_next.setDisable(false);
					        }
					    }
					);
					return null;
				 }
		 	};
		 	
		 	thread = new Thread(task);
		 	thread.setDaemon(true);
		 	thread.start();
	 		progressBar.progressProperty().bind(task.progressProperty());
	 		System.out.println(progressBar.progressProperty().getValue());
	 		value.setText(progressBar.progressProperty().getValue().toString());
		}
		else
			System.out.println("Already encoding");
		 
		 progressBar.sceneProperty().addListener((obs, oldScene, newScene) -> {
	        Platform.runLater(() -> {
	            Stage stage = (Stage) newScene.getWindow();
	            stage.setOnCloseRequest(e -> {
	            	
	            	//On kill le process ffmpeg si on ferme la fenetre
	            	final String getTasklist = "cmd /c tasklist";
	        		final String killTask = "cmd /c taskkill /F /IM ";
	        		Process process;

	        		try {
	        			//We get the list of the tasks runnning
	        			process = Runtime.getRuntime().exec(getTasklist);
	        			BufferedReader bufferReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	        			String line;

	        			//We look for ffmpeg.exe process and we ask the system to terminate it
	        			while ((line = bufferReader.readLine()) != null){
	        				if(line.contains("ffmpeg.exe")){
	        					String command = killTask+"ffmpeg.exe";
	        					Runtime.getRuntime().exec(command);
	        					System.out.println("ffmpeg process killed without error.");
	        				}
	        			}
	        			bufferReader.close();
	        		} catch (IOException err) {
	        			System.out.println("Error while killing ffmpeg process :");
	        			System.out.println(err.getMessage());
	        			err.printStackTrace();
	        		}
	            });
	        });
	    });
	 }
	
	public void buttonNext() {
		Stage stage = (Stage) button_next.getScene().getWindow();
		stage.close();
	}
}
	 
