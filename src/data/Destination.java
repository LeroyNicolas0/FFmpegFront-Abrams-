package data;

import Enums.ACodec;
import Enums.VCodec;

public class Destination extends Source {
	
	public Destination(Source src) {
		super(src);
		start_cut=0;
		end_cut=0;
		extension=null;
		acodec=null;
		vcodec=null;
		String r=new String (name.substring(0, name.lastIndexOf(("."))));
		name= r+"(1)";
		r=new String (file_path.substring(0,file_path.lastIndexOf("\\")));
		file_path= r+ name;
		
	}
	public float start_cut;
	public float end_cut;
	public ACodec acodec;//Le codec audio
	public VCodec vcodec;//Le Codec vidéo du fichier
	
}
