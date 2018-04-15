package application;
	
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;

import data.Source;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;



public class Main extends Application {
	public static final String fileCommand ="command.bat";
	public static final String pathTempDirectory = System.getProperty("java.io.tmpdir");
	public static final String pathToJarFile = System.getProperty("java.class.path");
	public static final String fileFFMpeg = "ffmpeg.exe";
	static boolean isEncoding = false;
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			Scene scene = new Scene(root,720,720);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Abrams- FFmpegFront");
			primaryStage.setScene(scene);
			primaryStage.show();
			
			//Pour fermer toutes les fenêtres
			primaryStage.setOnCloseRequest(e -> Platform.exit());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		extractFFMPEG();
		launch(args);
	}
	//Construit un ficher .bat avec la commande ffmpeg correspondante
	public static void buildFile(String commandFFMPEGToAdd){
		String pathToFileToWrite;
		String currentCommand;
		int numberOfLines=0;
		//String pathFileDirectory = filePath.substring(0,filePath.length()-fileName.length());

		pathToFileToWrite = Main.pathTempDirectory+fileCommand;
			System.out.println("Constructing "+Main.fileCommand);
		
		try {
			BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(pathToFileToWrite));

			//This line add the support for special characters like greek ones.
			currentCommand = "CHCP 65001";
			writer.write(currentCommand.getBytes());
			writer.write(System.getProperty("line.separator").getBytes());
			numberOfLines++;

			//we add the path directly of the ffmpeg file in the variables of windows so we can use the tool
			currentCommand = "SET PATH="+ Main.pathTempDirectory +";%PATH%";
			writer.write(currentCommand.getBytes());
			writer.write(System.getProperty("line.separator").getBytes());
			numberOfLines++;

			//the getBytes function is used to ensure the right encoding of all characters.
			writer.write(commandFFMPEGToAdd.getBytes("UTF-8"));
			numberOfLines++;

			writer.close();
			System.out.println("File constructed without error ("+numberOfLines+" lines)");


		} catch (IOException e) {
			System.out.println("Error while creating .bat file (IO Exception) :");
			System.out.println(e.getMessage());

			e.printStackTrace();
		}
	}
	public static void extractFFMPEG(){
		try {
			System.out.println("Begin extraction of the source files...");
			
			final int bufferSize = 2048;
			BufferedOutputStream buffStreamDestinationFile = null;
			FileInputStream streamFromInputFile = new FileInputStream(new File(pathToJarFile));
			System.out.println(System.getProperty("java.class.path"));
			CheckedInputStream checksum = new CheckedInputStream(streamFromInputFile, new Adler32());
			JarInputStream currentJarFile = new JarInputStream(new BufferedInputStream(checksum));
			JarEntry currentJarEntry;

			//we look for the ffmpeg.exe file in the JAR archive and we uncompress it in the right place
			while((currentJarEntry = currentJarFile.getNextJarEntry()) != null){
				if(currentJarEntry.getName().equals(fileFFMpeg)){
					System.out.println("Extraction of : "+currentJarEntry.getName());
					File fileToCreate = new File(pathTempDirectory+currentJarEntry.getName());
					System.out.println("Extracting "+currentJarEntry.getName()+"...in" + pathTempDirectory);
					int negativeIfEOFReached;
					byte dataToTransfer[] = new byte[bufferSize];

					FileOutputStream streamToWriteOutputFile = new FileOutputStream(fileToCreate);
					buffStreamDestinationFile = new BufferedOutputStream(streamToWriteOutputFile, bufferSize);

					while ((negativeIfEOFReached = currentJarFile.read(dataToTransfer,0,bufferSize)) != -1) {
						buffStreamDestinationFile.write(dataToTransfer,0,negativeIfEOFReached);
					}

					buffStreamDestinationFile.flush();
					buffStreamDestinationFile.close();
					streamToWriteOutputFile.close();
					System.out.println("Extraction of "+currentJarEntry.getName()+" completed.");
				}
			}
			currentJarFile.close();
			System.out.println("Extractions done correctly");
		} catch (IOException e) {
			System.out.println("Problem while extracting :");
			System.out.println(e.getMessage());
		}
	}
	// set a transfromer les timestamp ffmpeg en temps en secondes.
	public static float stringToTime(String time){
		String[] timeSplited = time.split(":");
		return Float.parseFloat(timeSplited[0])*3600 + Float.parseFloat(timeSplited[1])*60 + Float.parseFloat(timeSplited[2]);
	}
	
	public static String timeToString(Float time){
		int timeInt=Math.round(time);
		int hour=timeInt/3600;
		timeInt=timeInt-3600*hour;
		int minute=timeInt/60;
		timeInt=timeInt-60*minute;
		int second=timeInt;
		String timeString="";
		if(hour!=0) {
			timeString=String.valueOf(hour) + "h";
		}
		timeString+= String.valueOf(minute) + "m" + String.valueOf(second) + "s";
		return timeString;
	}
	
}
