
package data;
import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Enums.*;
import application.Main;

public class Source {
	public String file_path;//L'url du fichier
	public String name;//Le nom du fichier
	public float duration;//La durée du fichier, (en s)
	public float vbitrate;// le bitrate video ( en kb/s)
	public Extension extension;// L'extension du fichier
	public List<AudioTrack> pistes_audio;//Les pistes audio.
	public Resolution resolution;//la résolution de la vidéo.
	public List<SubtitleTrack> st;//Liste de sous-titres;
	public float abitrate;// le bitrate audio ( en kb/s)
	public int sampling_rate;// Taux d'échantillonage audio
	
	public Source (Source src) {
		this.file_path=src.file_path;
		this.name=src.name;
		this.duration=src.duration;
		this.vbitrate=src.vbitrate;
		this.extension=null;
		this.pistes_audio=new ArrayList<AudioTrack>(src.pistes_audio);
		this.resolution=src.resolution;
		this.st=new ArrayList<SubtitleTrack>(src.st);
		this.abitrate=src.abitrate;
		this.sampling_rate=src.sampling_rate;
	}
	
	
	public Source(String filepath) {
		this.file_path=filepath;
		scanffmpeg(filepath);
	}
	
	public void scanffmpeg(String url) {
		Main.buildFile("ffmpeg -i \""+url+"\" ");
		pistes_audio=new ArrayList<AudioTrack>();
		st=new ArrayList<SubtitleTrack>();
		
		try {
			ProcessBuilder procBuilder = new ProcessBuilder(Main.pathTempDirectory+Main.fileCommand);
			procBuilder.redirectErrorStream(true);
			Process proc = procBuilder.start();
			boolean checked=false;
			
			BufferedReader infoBuff = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			String lineToParse = null;
			float fps = 0;
			int a=0;
			int s=0;
			int n=0;
			while ((lineToParse = infoBuff.readLine()) != null){

				/* We look for a line that looks like this :
				 * Input #0, mov,mp4,m4a,3gp,3g2,mj2, from 'physics.mp4':
				 * 
				 */				
				if (lineToParse.contains("Input #0")){
					String extractedLine = lineToParse.substring(lineToParse.indexOf("from \'")+6, lineToParse.indexOf("\':"));
					System.out.println("Line parsed : "+lineToParse);
					System.out.println("Strings extracted : "+extractedLine);
					
					name=extractedLine;
					extension=Extension.ORG;
					Extension.ORG.set_ext(name.substring(name.indexOf(".")+1));//On set l'extension a l'original.
					System.out.println("Extension:"+ extension.get_ext());
				}
				/* We look for a line that looks like this :
				 *   Duration: 00:00:11.02, start: 0.000000, bitrate: 9570 kb/s
			     *	 And we extract :  00:00:11:02
				 * 
				 */		
				if (lineToParse.contains("Duration")){
					System.out.println("Line parsed : "+lineToParse);
					String extractedLine = lineToParse.substring(lineToParse.indexOf("Duration: ")+9, lineToParse.indexOf(","));
					System.out.println("Strings extracted : "+extractedLine);
					duration=Main.stringToTime(extractedLine);
				}
				/* Here we look for the following line :
				 * Stream #0:0(eng): Video: h264 (High) (avc1 / 0x31637661), yuvj420p(pc), 1920x1080, 1319 kb/s, 23.98 fps, 23.98 tbr, 24k tbn, 47.95 tbc (default)
				 * And we extract : 1920x1080.
				 */
				if (lineToParse.contains("Stream") && lineToParse.toLowerCase().contains("video")){
					System.out.println("Line parsed : "+lineToParse);
					String cutTheEndOfTheLine = lineToParse.substring(0, lineToParse.indexOf("kb/s"));
					Pattern pattern = Pattern.compile("[0-9]+x[0-9]+\\s");
					Matcher matcher = pattern.matcher(cutTheEndOfTheLine);
					if (matcher.find())
					{
					    resolution = new Resolution(matcher.group(0));
					    System.out.println(resolution.print());
					}
					
					}
				
				/* We look for the following line :
				 * Stream #0:1(und): Audio: aac (LC) (mp4a / 0x6134706D), 48000 Hz, stereo, fltp, 128 kb/s (default)
				 * And we extract 1,(und), 48000 and 128.
				 */ 
				// Theses values will be the last track ones, but that doesn't really matter.
				if (lineToParse.contains("Stream") && lineToParse.toLowerCase().contains("audio")){
					System.out.println("Line parsed : "+lineToParse);
					a++;
					n++;
					AudioTrack at=new AudioTrack(url,lineToParse.substring(lineToParse.indexOf("(")+1,lineToParse.indexOf(")")),a,n);
					pistes_audio.add(at);
					System.out.println(pistes_audio.get(pistes_audio.size()-1).Print());
					String cutTheEndOfTheLine = lineToParse.substring(0, lineToParse.indexOf("kb/s"));
					abitrate = Float.parseFloat(cutTheEndOfTheLine.substring(cutTheEndOfTheLine.lastIndexOf(',')+1, cutTheEndOfTheLine.lastIndexOf(' ')));
					System.out.println("String extracted : "+ abitrate);
					String Line = cutTheEndOfTheLine.substring(cutTheEndOfTheLine.indexOf(",")+2, cutTheEndOfTheLine.indexOf("Hz")-1);
					System.out.println("String extracted2 : "+ Line);
					sampling_rate=Integer.parseInt(Line);
					}
				if (lineToParse.contains("Stream") && lineToParse.toLowerCase().contains("Subtitle")){
					System.out.println("Line parsed : "+lineToParse);
					s++;
					n++;
					SubtitleTrack t=new SubtitleTrack(url,lineToParse.substring(lineToParse.indexOf("(")+1,lineToParse.indexOf(")")),s,n);
					st.add(t);
					}
		}
		} catch (IOException e) {
			//System.out.println("Error while updating frames (IO Exception) :");
			//System.out.println(e.getMessage());
			//Console.getConsole().printEndOfMessage();
			e.printStackTrace();
		}
	}
	}
	

