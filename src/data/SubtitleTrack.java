package data;

public class SubtitleTrack {
	public String file;//Le fichier parent si elle est dedans, sinon, le fichier dans lequel elle est.
	public String track_name;//probablment le langage de la piste
	public int track_number;/*le numéro de piste:
												 -Dans le fichier de base si en entrée.
												 -Dans le fichier cible si en sortie*/
	public int stream_number;
	public boolean integrated;
	public SubtitleTrack(String file,String name,int n,int n2) {
		this.file=file;
		this.track_name=name;
		this.track_number=n;
		this.stream_number=n2;
	}
	
	public String Print() {
		return new String("Track number:"+ Integer.toString(track_number) +"---" + track_name + "  in "+ file  );
	}
}


