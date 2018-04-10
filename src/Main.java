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


public class Main {
	static final String fileCommand ="command.bat";
	static final String pathTempDirectory = System.getProperty("java.io.tmpdir");
	static final String pathToJarFile = System.getProperty("java.class.path");
	static final String fileFFMpeg = "ffmpeg.exe";
	static boolean isEncoding = false;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	// On extrait FFMPEG du jar pour pouvoir l'utiliser.
	public static void extractFFMPEG(){
		try {
			//Console.getConsole().writeInConsole("Begin extraction of the source files...");
			
			final int bufferSize = 2048;
			BufferedOutputStream buffStreamDestinationFile = null;
			FileInputStream streamFromInputFile = new FileInputStream(new File(pathToJarFile));
			//Console.getConsole().writeInConsole(System.getProperty("java.class.path"));
			CheckedInputStream checksum = new CheckedInputStream(streamFromInputFile, new Adler32());
			JarInputStream currentJarFile = new JarInputStream(new BufferedInputStream(checksum));
			JarEntry currentJarEntry;

			//we look for the ffmpeg.exe file in the JAR archive and we uncompress it in the right place
			while((currentJarEntry = currentJarFile.getNextJarEntry()) != null){
				if(currentJarEntry.getName().equals(fileFFMpeg)){
					//Console.getConsole().writeInConsole("Extraction of : "+currentJarEntry.getName());
					File fileToCreate = new File(pathTempDirectory+currentJarEntry.getName());

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
					//Console.getConsole().writeInConsole("Extraction of "+currentJarEntry.getName()+" completed.");
				}
			}
			currentJarFile.close();
			//Console.getConsole().writeInConsole("Extractions done correctly");
			//Console.getConsole().printEndOfMessage();
		} catch (IOException e) {
			//Console.getConsole().writeInConsole("Problem while extracting :");
			//Console.getConsole().writeInConsole(e.getMessage());
			//Console.getConsole().printEndOfMessage();
		}
	}
	// set a transfromer les timestamp ffmpeg en temps en secondes.
	public static float stringToTime(String time){
		String[] timeSplited = time.split(":");
		return Float.parseFloat(timeSplited[0])*3600 + Float.parseFloat(timeSplited[1])*60 + Float.parseFloat(timeSplited[2]);
	}
}
