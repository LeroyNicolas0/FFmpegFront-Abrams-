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
	SRT("srt","SubRip Subtitles",new VCodec[]{VCodec.ALL},new ACodec[] {ACodec.ALL}),
	//Autres
	ORG("org","Original Container",null,null);// utilisé seulement pour les fichiers en entrée.
	
	private String ext;
	private String description;
	private VCodec[] vcodecs;
	private ACodec[] acodecs;
	
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
	public VCodec[] get_Vcodecs() {
		if (this.vcodecs[0]!=VCodec.ALL) {
			return this.vcodecs;
		}else return VCodec.Get_All();
	}
	public ACodec[] get_Aocodecs() {
		if (this.acodecs[0]!=ACodec.ALL) {
			return this.acodecs;
		}else return ACodec.Get_All();
	}
	//Sert uniquement pour modifier orig. On saura pas ce qui est compatible avec, mais ffmepg devrait savoir, lui.
	public void set_ext(String n_ext) {
		this.ext=n_ext;
	}
	public Extension[] Get_Audio() {
		return new Extension[] {MP3,M4A,MKA,OGG,OGA,AAC};
	}
	public Extension[] Get_Video() {
		return new Extension[] {MP4,GP,G2,MKV,OGV,AVI};
	}
	public Extension[] Get_Subtitles() {
		return new Extension[] {MKS,SRT};
	}
}
