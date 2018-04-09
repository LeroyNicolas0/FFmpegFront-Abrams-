package data;
import java.util.List;

import Enums.*;

public class Source {
	public String file_path;//L'url du fichier
	public String name;//Le nom du fichier
	public float duration;//La dur�e du fichier, (en s)
	public VCodec vcodec;//Le Codec vid�o du fichier
	public float vbitrate;// le bitrate video ( en kb/s)
	public Extension extension;// L'extension du fichier
	public List<AudioTrack> pistes_audio;//Les pistes audio.
	public Resolution resolution;//la r�solution de la vid�o.
	public List<SubtitleTrack> st;//Liste de sous-titres;
}
