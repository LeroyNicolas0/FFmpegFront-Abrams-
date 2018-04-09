package Enums;

public enum Extension {
	//Audio
	MP3("mp3","MPEG-Layer 3"),
	//Video
	MP4("mp4","MPEG-Layer 4 "),
	//Sous-Titres
	SRT("srt","");
	//Autres
	
	
	private String ext;
	private String description;
	
	Extension(String ext, String description){
		this.ext=ext;
		this.description=description;
	}
	public String get_ext() {
		return this.ext;
	}
	public String get_description() {
		return this.description;
	}

}
