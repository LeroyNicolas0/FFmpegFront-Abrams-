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
	 Task<Void> task;

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
						Process proc = procBuilder.start();
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
	                System.out.println("plop");
	                thread.interrupt();
	                Thread.currentThread().interrupt();
	                if(Thread.currentThread().isInterrupted()) {
	                	System.out.println("yep");
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
	 
