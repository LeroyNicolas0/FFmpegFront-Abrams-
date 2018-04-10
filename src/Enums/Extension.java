package Enums;

public enum Extension {
	//Audio
	MP3("mp3","MPEG-2 Audio Layer 3",(VCodec[])null,new ACodec[] {ACodec.MP3LAME}),
	M4A("m4a","MPEG-4 Audio",(VCodec[])null,new ACodec[] {ACodec.AAC,ACodec.MP3LAME}),
	MKA("mka","Matroska Audio",(VCodec[])null,new ACodec[] {ACodec.ALL}),
	OGG("ogg","OGG Vorbis",(VCodec[])null,new ACodec[] {ACodec.VORBIS}),
	OGA("oga","OGG container Audio",(VCodec[])null,new ACodec[] {ACodec.OPUS,ACodec.FLAC}),
	AAC("aac","Advanced Audio Codec",(VCodec[])null,new ACodec[] {ACodec.AAC}),
	//Video
	MP4("mp4","MPEG-4 Part 14",new VCodec[]{VCodec.LIBX264,VCodec.LIBX265,VCodec.LIBXVID},new ACodec[] {ACodec.AAC,ACodec.MP3LAME}),
	GP("3gp","3GPP file format",new VCodec[]{VCodec.LIBX264,VCodec.LIBXVID},new ACodec[] {ACodec.AAC}),
	G2("3g2","3GPP2 file format",new VCodec[]{VCodec.LIBX264,VCodec.LIBXVID},new ACodec[] {ACodec.AAC}),
	MKV("mkv","Matroska",new VCodec[]{VCodec.ALL},new ACodec[] {ACodec.ALL}),
	OGV("ogv","OGG container Video",new VCodec[]{VCodec.THEORA},new ACodec[] {ACodec.FLAC,ACodec.OPUS,ACodec.VORBIS}),
	AVI("avi", "Audio Video Interleaved",new VCodec[]{VCodec.LIBXVID},new ACodec[] {ACodec.MP3LAME}),
	//Sous-Titres 
	MKS("mks","Matroska subtitles",new VCodec[]{VCodec.ALL},new ACodec[] {ACodec.ALL}),
	SRT("srt","SubRip Subtitles",new VCodec[]{VCodec.ALL},new ACodec[] {ACodec.ALL});
	//Autres
	
	
	private String ext;
	private String description;
	
	Extension(String ext, String description, VCodec[] allowed_video_codecs, ACodec[] allowed_Audio_Codecs){
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
