package data;
import Enums.*;

public class AudioTrack {
	public String file;//Le fichier parent si elle est dedans, sinon, le fichier dans lequel elle est.
	public String track_name;//probablment le langage de la piste
	public int track_number;/*le numéro de piste:
												 -Dans le fichier de base si en entrée.
												 -Dans le fichier cible si en sortie*/
}
