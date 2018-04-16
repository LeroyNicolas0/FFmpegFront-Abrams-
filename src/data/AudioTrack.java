package data;
import Enums.*;

public class AudioTrack {
	public String file;//Le fichier parent si elle est dedans, sinon, le fichier dans lequel elle est.
	public String track_name;//probablment le langage de la piste
	public int track_number;/*le num�ro de piste:
												 -Dans le fichier de base si en entr�e.
												 -Dans le fichier cible si en sortie*/
	public int stream_number;
	public boolean integrated;
	
	public AudioTrack(String file,String name,int n, int n2) {
		this.file=file;
		this.track_name=name;
		this.track_number=n;
		this.stream_number=n2;
	}
	public AudioTrack(String file) {
		this.file=file;
		this.track_name="und";
		this.track_number=0;
		this.stream_number=0;
	}
	
	public String Print() {
		return new String("Track number:"+ Integer.toString(track_number) +"---" + track_name + "  in "+ file  );
	}
}
