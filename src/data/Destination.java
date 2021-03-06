package data;

import Enums.ACodec;
import Enums.Extension;
import Enums.VCodec;

public class Destination extends Source {
	
	public Destination(Source src) {
		super(src);
		start_cut=0;
		end_cut=0;
		extension=null;
		acodec=null;
		vcodec=null;
		name=null;
		file_path=null;
		use_crf=false;
		crf=51.0f;
	}
	
	public float start_cut;
	public float end_cut;
	public ACodec acodec;//Le codec audio
	public VCodec vcodec;//Le Codec vid�o du fichier
	public boolean use_crf;
	public float crf;
	
	
	public String Generate_command(Source origin) {
		String command="";
		command+="ffmpeg -y ";
		int input_n=0;
		if (start_cut!=0) {
			command +="-ss "+Float.toString(start_cut)+" ";
		}
		command+="-i \""+origin.file_path+"\" ";
		if (acodec!=ACodec.NONE) {
			for (AudioTrack a: this.pistes_audio){
				input_n++;
				if (!a.integrated) {
					command+="-i \""+a.file+"\" ";
				}
			}
		}
		if (vcodec!=VCodec.NONE && extension!=Extension.AVI) {
			for (SubtitleTrack s: this.st) {
				if (!s.integrated) {
					command+="-i \""+s.file+"\" ";
				}
			}
		}
		if (vcodec!=VCodec.NONE) {
			command+="-s "+this.resolution.print()+" ";
			if (this.use_crf) {
				command+="-crf "+ Float.toString(this.crf) +" ";
			}else {
				command+="-b:v "+ Float.toString(this.vbitrate) + "k "; 
			}
			command+="-vcodec " + this.vcodec.get_lib() +" ";
		}else {
			command+="-vn ";
		}
		if (acodec!=ACodec.NONE) {
			command+="-b:a "+ Float.toString(this.abitrate)+"k ";
			command+="-acodec " +this.acodec.get_lib()+" ";
		}else {
			command+="-an ";
		}
		if (!this.st.isEmpty() && vcodec!=VCodec.NONE && extension!=Extension.AVI) {
			command+="-scodec mov_text ";
		}
		for (int i=0; i<input_n;i++) {
			command+="-map "+ Integer.toString(i)+" ";
		}
		command+="-t "+Float.toString(this.duration)+" ";
		command+="-shortest ";
		command+="\""+this.file_path+"\" "; 
		return command;
	}
}
